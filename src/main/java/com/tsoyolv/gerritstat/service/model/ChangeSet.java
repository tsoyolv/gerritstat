package com.tsoyolv.gerritstat.service.model;

import java.time.LocalDateTime;

public class ChangeSet {
    private String link;
    private String linkName;
    private String name;
    private String size;
    private LocalDateTime createDate;
    private LocalDateTime closeDate;
    private int totalLinesAdded;
    private int totalLinesRemoved;
    private int totalComments;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public int getTotalLinesAdded() {
        return totalLinesAdded;
    }

    public void setTotalLinesAdded(int totalLinesAdded) {
        this.totalLinesAdded = totalLinesAdded;
    }

    public int getTotalLinesRemoved() {
        return totalLinesRemoved;
    }

    public void setTotalLinesRemoved(int totalLinesRemoved) {
        this.totalLinesRemoved = totalLinesRemoved;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }
}
