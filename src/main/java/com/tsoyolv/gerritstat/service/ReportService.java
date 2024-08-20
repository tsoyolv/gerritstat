package com.tsoyolv.gerritstat.service;

import com.tsoyolv.gerritstat.controller.dto.ReportRequest;
import com.tsoyolv.gerritstat.port.output.rest.GerritRestClient;
import com.tsoyolv.gerritstat.port.output.rest.dto.ChangesetResponse;
import com.tsoyolv.gerritstat.service.model.Report;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final GerritRestClient restClient;

    @Value("${gerrit.user.url}")
    private String url;

    public ReportService(GerritRestClient restClient) {
        this.restClient = restClient;
    }

    public List<Report> generateReport(List<String> userList, ReportRequest request) {
        return userList
                .stream()
                .map(u -> getReport(u, request))
                .collect(Collectors.toList());
    }

    private Report getReport(String user, ReportRequest request) {
        var changeSets = restClient.getChangeSets(user, request.getCookie(), request.getFromDate());
        changeSets = changeSets.stream()
                .filter(
                        c -> !c.getSubmitted().toLocalDate().isBefore(request.getFromDate()) &&
                                !c.getSubmitted().toLocalDate().isAfter(request.getToDate()))
                .collect(Collectors.toList());
        System.out.println();

        Report report = mapToReport(changeSets);
        report.setUser(user);
        report.setLink(url + user + ")");
        return report;
    }

    private static Report mapToReport(List<ChangesetResponse> changeSets) {
        Report report = new Report();
        report.setTotalChanges(changeSets.size());
        Durs durs = new Durs();
        for (ChangesetResponse changeset : changeSets) {
            report.setTotalComments(report.getTotalComments() + changeset.getTotalCommentCount());
            report.setTotalLinesAdded(report.getTotalLinesAdded() + changeset.getInsertions());
            report.setTotalLinesRemoved(report.getTotalLinesRemoved() + changeset.getDeletions());
            int delta = changeset.getDeletions() + changeset.getInsertions();
            Duration duration = Duration.between(changeset.getCreated(), changeset.getSubmitted());
            if (delta < 10) {
                report.setXsCount(report.getXsCount() + 1);
                if (durs.xs == null) {
                    durs.xs = duration;
                } else {
                    durs.xs = durs.xs.plus(duration);
                }
            } else if (delta < 50) {
                report.setsCount(report.getsCount() + 1);
                if (durs.s == null) {
                    durs.s = duration;
                } else {
                    durs.s = durs.s.plus(duration);
                }
            } else if (delta < 250) {
                report.setmCount(report.getmCount() + 1);
                if (durs.m == null) {
                    durs.m = duration;
                } else {
                    durs.m = durs.m.plus(duration);
                }
            } else if (delta < 1000) {
                report.setlCount(report.getlCount() + 1);
                if (durs.l == null) {
                    durs.l = duration;
                } else {
                    durs.l = durs.l.plus(duration);
                }
            } else {
                report.setXlCount(report.getXlCount() + 1);
                if (durs.xl == null) {
                    durs.xl = duration;
                } else {
                    durs.xl = durs.xl.plus(duration);
                }
            }
        }
        report.setVolume(
                report.getXsCount()
                        + report.getsCount() * 2
                        + report.getmCount() * 3
                        + report.getlCount() * 6
                        + report.getXlCount() * 10
        );
        Duration temp;
        if (durs.xs != null) {
            temp = durs.xs.dividedBy(report.getXsCount());
            report.setXsAverageClosureTime("%s часов и %s минут".formatted(temp.toHours(), temp.toMinutesPart()));
        }

        if (durs.s != null) {
            temp = durs.s.dividedBy(report.getsCount());
            report.setsAverageClosureTime("%s часов и %s минут".formatted(temp.toHours(), temp.toMinutesPart()));
        }

        if (durs.m != null) {
            temp = durs.m.dividedBy(report.getmCount());
            report.setmAverageClosureTime("%s часов и %s минут".formatted(temp.toHours(), temp.toMinutesPart()));
        }
        if (durs.l != null) {
            temp = durs.l.dividedBy(report.getlCount());
            report.setlAverageClosureTime("%s часов и %s минут".formatted(temp.toHours(), temp.toMinutesPart()));
        }
        if (durs.xl != null) {
            temp = durs.xl.dividedBy(report.getXlCount());
            report.setXlAverageClosureTime("%s часов и %s минут".formatted(temp.toHours(), temp.toMinutesPart()));
        }
        return report;
    }

    private static class Durs {
        private Duration xs;
        private Duration s;
        private Duration m;
        private Duration l;
        private Duration xl;
    }
}
