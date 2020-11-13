package com.laynester.plugins.habbopages.utils;

public class Leaderboard {
    public String username;

    public int amount;

    public Leaderboard(String username, int amount) {
        this.username = username;

        this.amount = amount;
    }

    public String getUsername() { return username; }

    public int getAmount() { return amount; }

}
