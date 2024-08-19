package com.tsoyolv.gerritstat.port.output.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "id",
        "project",
        "branch",
        "change_id",
        "subject",
        "status",
        "created",
        "updated",
        "submitted",
        "insertions",
        "deletions",
        "total_comment_count",
        "unresolved_comment_count",
        "has_review_started",
        "submission_id",
        "meta_rev_id",
        "_number",
        "_more_changes",
        "requirements"
})
public class ChangesetResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("project")
    private String project;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("change_id")
    private String changeId;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("status")
    private String status;
    @JsonProperty("created")
    private LocalDateTime created;
    @JsonProperty("updated")
    private LocalDateTime updated;
    @JsonProperty("submitted")
    private LocalDateTime submitted;
    @JsonProperty("insertions")
    private Integer insertions;
    @JsonProperty("deletions")
    private Integer deletions;
    @JsonProperty("total_comment_count")
    private Integer totalCommentCount;
    @JsonProperty("unresolved_comment_count")
    private Integer unresolvedCommentCount;
    @JsonProperty("has_review_started")
    private Boolean hasReviewStarted;
    @JsonProperty("submission_id")
    private String submissionId;
    @JsonProperty("meta_rev_id")
    private String metaRevId;
    @JsonProperty("_number")
    private Integer number;
    @JsonProperty("_more_changes")
    private Boolean moreChanges;
    @JsonProperty("requirements")
    private List<Object> requirements;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("project")
    public String getProject() {
        return project;
    }

    @JsonProperty("project")
    public void setProject(String project) {
        this.project = project;
    }

    @JsonProperty("branch")
    public String getBranch() {
        return branch;
    }

    @JsonProperty("branch")
    public void setBranch(String branch) {
        this.branch = branch;
    }

    @JsonProperty("change_id")
    public String getChangeId() {
        return changeId;
    }

    @JsonProperty("change_id")
    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("created")
    public LocalDateTime getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @JsonProperty("updated")
    public LocalDateTime getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @JsonProperty("submitted")
    public LocalDateTime getSubmitted() {
        return submitted;
    }

    @JsonProperty("submitted")
    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    @JsonProperty("insertions")
    public Integer getInsertions() {
        return insertions;
    }

    @JsonProperty("insertions")
    public void setInsertions(Integer insertions) {
        this.insertions = insertions;
    }

    @JsonProperty("deletions")
    public Integer getDeletions() {
        return deletions;
    }

    @JsonProperty("deletions")
    public void setDeletions(Integer deletions) {
        this.deletions = deletions;
    }

    @JsonProperty("total_comment_count")
    public Integer getTotalCommentCount() {
        return totalCommentCount;
    }

    @JsonProperty("total_comment_count")
    public void setTotalCommentCount(Integer totalCommentCount) {
        this.totalCommentCount = totalCommentCount;
    }

    @JsonProperty("unresolved_comment_count")
    public Integer getUnresolvedCommentCount() {
        return unresolvedCommentCount;
    }

    @JsonProperty("unresolved_comment_count")
    public void setUnresolvedCommentCount(Integer unresolvedCommentCount) {
        this.unresolvedCommentCount = unresolvedCommentCount;
    }

    @JsonProperty("has_review_started")
    public Boolean getHasReviewStarted() {
        return hasReviewStarted;
    }

    @JsonProperty("has_review_started")
    public void setHasReviewStarted(Boolean hasReviewStarted) {
        this.hasReviewStarted = hasReviewStarted;
    }

    @JsonProperty("submission_id")
    public String getSubmissionId() {
        return submissionId;
    }

    @JsonProperty("submission_id")
    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    @JsonProperty("meta_rev_id")
    public String getMetaRevId() {
        return metaRevId;
    }

    @JsonProperty("meta_rev_id")
    public void setMetaRevId(String metaRevId) {
        this.metaRevId = metaRevId;
    }

    @JsonProperty("_number")
    public Integer getNumber() {
        return number;
    }

    @JsonProperty("_number")
    public void setNumber(Integer number) {
        this.number = number;
    }

    @JsonProperty("_more_changes")
    public Boolean getMoreChanges() {
        return moreChanges;
    }

    @JsonProperty("_more_changes")
    public void setMoreChanges(Boolean moreChanges) {
        this.moreChanges = moreChanges;
    }

    @JsonProperty("requirements")
    public List<Object> getRequirements() {
        return requirements;
    }

    @JsonProperty("requirements")
    public void setRequirements(List<Object> requirements) {
        this.requirements = requirements;
    }

}
