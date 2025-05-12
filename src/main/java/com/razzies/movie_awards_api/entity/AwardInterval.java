package com.razzies.movie_awards_api.entity;

public class AwardInterval {
    String producers;
    int interval;
    int previousWin;
    int followingWin;

    public AwardInterval(String producers, int interval, int previousWin, int followingWin) {
        this.producers = producers;
        this.interval = interval;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getPreviousWin() {
        return previousWin;
    }

    public void setPreviousWin(int previousWin) {
        this.previousWin = previousWin;
    }

    public int getFollowingWin() {
        return followingWin;
    }

    public void setFollowingWin(int followingWin) {
        this.followingWin = followingWin;
    }
}
