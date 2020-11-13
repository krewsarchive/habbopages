package com.laynester.plugins.habbopages;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import static com.eu.habbo.Emulator.*;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.laynester.plugins.habbopages.commands.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/*
 * Author: Laynester
 */


public class Plugin extends HabboPlugin implements EventListener {

    public static Plugin INSTANCE = null;

    public static String PN = "HabboPages";

    public void onEnable() throws Exception {
        INSTANCE = this;
        Logger("Has started");

        Emulator.getPluginManager().registerEvents(this,this);

        Emulator.getConfig().register("habbopages.directory","C:/inetpub/wwwroot/swf/gamedata/");

        Emulator.getConfig().register("habbopages.delay.mins","5");
    }

    public void onDisable() throws Exception {
        Logger("Has been disabled");
    }

    @EventHandler
    public void onEmulatorLoaded(EmulatorLoadedEvent event) {
        Emulator.getTexts().register("commands.keys.cmd_leaderboard","leaderboard;lb;leader");

        Emulator.getTexts().register("habbopages.leaderboard.header","Leaderboard");

        Emulator.getTexts().register("habbopages.leaderboard.credits","Credits Leaderboard:");

        Emulator.getTexts().register("habbopages.leaderboard.duckets", "Duckets Leaderboard:");

        Emulator.getTexts().register("habbopages.leaderboard.diamonds", "Diamonds Leaderboard:");

        registerPermission("cmd_leaderboard", "'0', '1', '2'", "1");

        CommandHandler.addCommand(new Commands());

        CommandHandler.addCommand(new Leaderboard());

        Logger("Has loaded");
    }

    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }

    public static void Logger(String message) {
        System.out.println("[" + ANSI_PURPLE + PN + ANSI_WHITE + "] " + message);
    }

    private static void registerPermission(String name, String options, String defaultValue) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement("ALTER TABLE  `permissions` ADD  `" + name +"` ENUM(  " + options + " ) NOT NULL DEFAULT  '" + defaultValue + "'"))
            {
                statement.execute();
            }
        } catch (SQLException ignored){}
    }

}
