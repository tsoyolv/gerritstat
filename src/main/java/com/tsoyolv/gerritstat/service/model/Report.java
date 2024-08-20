package com.tsoyolv.gerritstat.service.model;

public class Report {

    private int volume;
    private int totalChanges;
    private int xsCount;
    private int sCount;
    private int mCount;
    private int lCount;
    private int xlCount;
    private int totalLinesAdded;
    private int totalLinesRemoved;
    private int totalComments;
    private String xsAverageClosureTime;
    private String sAverageClosureTime;
    private String mAverageClosureTime;
    private String lAverageClosureTime;
    private String xlAverageClosureTime;

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getTotalChanges() {
        return totalChanges;
    }

    public void setTotalChanges(int totalChanges) {
        this.totalChanges = totalChanges;
    }

    public int getXsCount() {
        return xsCount;
    }

    public void setXsCount(int xsCount) {
        this.xsCount = xsCount;
    }

    public int getsCount() {
        return sCount;
    }

    public void setsCount(int sCount) {
        this.sCount = sCount;
    }

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public int getlCount() {
        return lCount;
    }

    public void setlCount(int lCount) {
        this.lCount = lCount;
    }

    public int getXlCount() {
        return xlCount;
    }

    public void setXlCount(int xlCount) {
        this.xlCount = xlCount;
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

    public String getXsAverageClosureTime() {
        return xsAverageClosureTime;
    }

    public void setXsAverageClosureTime(String xsAverageClosureTime) {
        this.xsAverageClosureTime = xsAverageClosureTime;
    }

    public String getsAverageClosureTime() {
        return sAverageClosureTime;
    }

    public void setsAverageClosureTime(String sAverageClosureTime) {
        this.sAverageClosureTime = sAverageClosureTime;
    }

    public String getmAverageClosureTime() {
        return mAverageClosureTime;
    }

    public void setmAverageClosureTime(String mAverageClosureTime) {
        this.mAverageClosureTime = mAverageClosureTime;
    }

    public String getlAverageClosureTime() {
        return lAverageClosureTime;
    }

    public void setlAverageClosureTime(String lAverageClosureTime) {
        this.lAverageClosureTime = lAverageClosureTime;
    }

    public String getXlAverageClosureTime() {
        return xlAverageClosureTime;
    }

    public void setXlAverageClosureTime(String xlAverageClosureTime) {
        this.xlAverageClosureTime = xlAverageClosureTime;
    }
}
