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

    public static ArrayList<Leaderboard> Seasonal(int type) {
        ArrayList<Leaderboard> seasonal = new ArrayList<>();

        String query = "SELECT amount,u.username,type from users_currency,users AS u WHERE u.id = users_currency.user_id AND type = ? ORDER BY amount desc LIMIT 10";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1,type);

            try (ResultSet set = statement.executeQuery()) {
                int ind = 1;

                while (set.next()) {
                    String username = set.getString("username");

                    int amount = set.getInt("amount");

                    seasonal.add(new Leaderboard(username,amount));

                    ind++;
                }
            }
        } catch (SQLException e) {
            Emulator.getLogging().logSQLException(e);
        }

        return seasonal;
    }
}
