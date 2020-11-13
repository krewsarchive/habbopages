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
        ArrayList<Leaderboard> duckets = new ArrayList<>();
        ArrayList<Leaderboard> diamonds = new ArrayList<>();

        String query = "SELECT amount,u.username,type from users_currency,users AS u WHERE u.id = users_currency.user_id";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet set = statement.executeQuery()) {
                int ind = 1;

                while (set.next()) {
                    String username = set.getString("username");

                    int amount = set.getInt("amount");

                    int typee = set.getInt("type");

                    switch(typee) {
                        case 0:
                            duckets.add(new Leaderboard(username,amount));
                            break;
                        case 5:
                            diamonds.add(new Leaderboard(username,amount));
                            break;
                    }

                    ind++;
                }
            }
        } catch (SQLException e) {
            Emulator.getLogging().logSQLException(e);
        }

        switch(type) {
            case 0:
                return duckets;
            case 5:
                return diamonds;
        }
        return duckets;
    }
}
