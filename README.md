# gerritstat

A small web app for reporting on Gerrit changes. Select authors and a date range to see their merged changes, diff sizes, lines added and removed, comments, and average time to merge. Drill down into each author's changes and open them directly in Gerrit.

**Built with:** Java 17 · Spring Boot 3.3.2 · Thymeleaf · Gradle 8.8.

## Features

- Compare multiple authors in a single report.
- Include only `MERGED` changes, filtered by merge date.
- Group changes by size: XS, S, M, L, and XL.
- See totals for added lines, removed lines, and comments on changes.
- Track average time from creation to merge for each size category.
- Calculate a weighted change volume using weights from 1 to 10.
- Browse each author's changes with titles, sizes, dates, line counts, comments, and Gerrit links, sorted by merge date, newest first.

No database is required: the app fetches data directly from the Gerrit REST API. The web interface is currently in Russian.

## Quick start

You need **JDK 17**, Git, and access to your Gerrit instance. The repository includes the Gradle Wrapper, so there is no need to install Gradle separately. The first run requires internet access to download Gradle and dependencies.

### 1. Clone the repository

```bash
git clone https://github.com/tsoyolv/gerritstat.git
cd gerritstat
```

### 2. Configure Gerrit URLs

For Bash/Zsh, replace `gerrit.example.com` with your Gerrit host:

```bash
export GERRIT_HOST_URL='https://gerrit.example.com/changes/?O=1000081'
export GERRIT_USER_URL='https://gerrit.example.com/q/status:Merged+(o:'
export GERRIT_CHANGE_URL='https://gerrit.example.com/c/'
```

| Environment variable | Spring property | Purpose |
| --- | --- | --- |
| `GERRIT_HOST_URL` | `gerrit.host.url` | REST API request URL prefix. The client appends `&S=…&n=…&q=…`, so this must include `/changes/` and an initial query parameter. |
| `GERRIT_USER_URL` | `gerrit.user.url` | URL prefix for an author's change search in Gerrit. The app appends the author identifier and a closing `)`. |
| `GERRIT_CHANGE_URL` | `gerrit.change.url` | URL prefix for individual changes. The app appends `<project>/+/<number>`, so the trailing `/` is required. |

If Gerrit is hosted under a path such as `https://example.com/gerrit`, include `/gerrit` in all three URLs.

You can also set these properties in [`src/main/resources/application.properties`](src/main/resources/application.properties). The values committed to the repository are placeholders; replace all three before connecting.

### 3. Start the app

```bash
./gradlew bootRun --args='--server.address=127.0.0.1'
```

Open [http://localhost:8080](http://localhost:8080).

On Windows, set the same environment variables and use `gradlew.bat` instead of `./gradlew`.

This example binds the app to the loopback interface. The app has no authentication of its own, and the Cookie field displays its value as plain text. Run it locally or in a protected environment. Your Cookie grants access to your Gerrit session; keep it out of commits and screenshots.

## Generate a report

The labels below match the current Russian interface:

1. In **User** (`Пользователь`), enter an identifier accepted by Gerrit's `owner:` / `o:` search operator. Separate multiple authors with commas: `alice,bob`.
2. Select **From date** (`Дата от`) and **To date** (`Дата до`). Both boundaries are inclusive. Filtering uses `submitted`, the date a change was merged, even if it was created before the selected period.
3. In **Cookie**, paste the value of the `Cookie` header from an authenticated request to your Gerrit instance, without the `Cookie:` prefix. Find it in your browser's developer tools under **Network → a Gerrit request → Request Headers → Cookie**. You can leave this field empty if the API allows anonymous access.
4. Click **Generate report** (`Получить отчет`).
5. Click an author in the report to view their changes for the selected period. In the detail view, each change number links to its page in Gerrit.

The app authenticates API requests using the supplied Cookie. There are currently no separate settings for API tokens or username/password authentication.

## Metric definitions

### Change size

A change's size is the sum of its added and removed lines:

```text
size = insertions + deletions
```

| Size | Lines changed | Volume weight |
| --- | ---: | ---: |
| XS | 0–9 | 1 |
| S | 10–49 | 2 |
| M | 50–249 | 3 |
| L | 250–999 | 6 |
| XL | 1,000 or more | 10 |

For example, a change with 30 added and 25 removed lines has a size of 55 lines and falls into category M.

### Volume

```text
volume = XS × 1 + S × 2 + M × 3 + L × 6 + XL × 10
```

Here, XS–XL are the number of changes in each size category. Volume is a rough measure based on diff size. It does not measure task complexity, code quality, or business value, and should not be used on its own to assess developer performance.

### Time to merge and comments

- **Average time to merge** is the arithmetic mean of `submitted − created` within each size category. It includes all elapsed time, including waiting and weekends. The cell is blank when a category has no changes.
- **Total comments** is the sum of `total_comment_count` across the selected changes. It counts comments **on the author's changes**, not review comments written by that author.
- Timestamps are handled as `LocalDateTime`, without conversion to the browser's or user's time zone.

See [`ReportService.java`](src/main/java/com/tsoyolv/gerritstat/service/ReportService.java) for report calculations and [`ChangeService.java`](src/main/java/com/tsoyolv/gerritstat/service/ChangeService.java) for size classification in the detail view.

## Build and test

```bash
# Run the existing Spring context loading test
./gradlew test

# Build an executable JAR
./gradlew bootJar

# Run the JAR with the environment variables configured above
java -jar build/libs/gerritstat-0.0.1-SNAPSHOT.jar --server.address=127.0.0.1
```

To use a different port, add `--server.port=8081`. With Gradle, pass both options through `--args`:

```bash
./gradlew bootRun --args='--server.address=127.0.0.1 --server.port=8081'
```

The repository contains one context loading test. Metric calculations and the Gerrit integration do not yet have dedicated tests.

## Known limitations and troubleshooting

- **Empty API pages are not handled.** The client fetches 25 changes per page and stops when the last item's `updated` date is earlier than the report's start date. If Gerrit returns an empty list first, the request can fail. This can happen when an author has no merged changes or the selected period covers their entire history. The client does not currently use `_more_changes` to detect the end of the results.
- **Long date ranges can be slow.** Authors and pages are processed sequentially, without caching. Opening an author's detail view fetches the data again.
- **The form does not validate the date range.** Fill in the author and both dates, and make sure the start date is not after the end date. API failures are not translated into helpful messages in the UI.
- **`401` / `403` responses.** Check your access to Gerrit and whether your Cookie has expired. With SSO, you may need multiple cookies from the original request header.
- **URL or JSON parsing errors.** Check `GERRIT_HOST_URL`: it must point to the REST API endpoint shown above. A login page or the Gerrit home page will not work. The timestamp parser expects `yyyy-MM-dd HH:mm:ss.SSSSSSSSS`.
- **Gradle cannot find Java 17.** The build requires a JDK 17 toolchain. Install it and, if needed, point `JAVA_HOME` to its directory. Having only JDK 21 installed is not sufficient for the current build configuration.

## Code map

| Location | Responsibility |
| --- | --- |
| [`controller/`](src/main/java/com/tsoyolv/gerritstat/controller) | Report form, report generation endpoint, and author detail page. |
| [`service/`](src/main/java/com/tsoyolv/gerritstat/service) | Date filtering, metric calculations, and change detail mapping. |
| [`port/output/rest/`](src/main/java/com/tsoyolv/gerritstat/port/output/rest) | Gerrit requests, pagination, and response parsing. |
| [`templates/`](src/main/resources/templates) | Thymeleaf HTML pages. |
| [`application.properties`](src/main/resources/application.properties) | Application settings and Gerrit URLs. |
