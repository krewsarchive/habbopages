package com.laynester.plugins.habbopages.utils;

import com.eu.habbo.Emulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Currency {

    public static ArrayList<Leaderboard> Credits() {
        ArrayList<Leaderboard> credits = new ArrayList<>();

        String query = "SELECT credits,username FROM users ORDER BY credits desc LIMIT 10";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet set = statement.executeQuery()) {
                int ind = 1;
                while (set.next()) {
                    String username = set.getString("username");

                    int amount = set.getInt("credits");

                    credits.add(new Leaderboard(username,amount));
                    ind++;
                }
            }
        } catch (SQLException e) {
            Emulator.getLogging().logSQLException(e);
        }

        return credits;
    }
}
