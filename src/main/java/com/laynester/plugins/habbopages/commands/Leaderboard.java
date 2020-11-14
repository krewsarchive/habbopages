package com.laynester.plugins.habbopages.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.laynester.plugins.habbopages.utils.Currency;
import com.laynester.plugins.habbopages.utils.Page;

import java.util.ArrayList;

/*
 * Author: Laynester
 */

public class Leaderboard extends Command {
    private StringBuilder message;

    private ArrayList<com.laynester.plugins.habbopages.utils.Leaderboard> Credits;

    private ArrayList<com.laynester.plugins.habbopages.utils.Leaderboard> Duckets;

    private ArrayList<com.laynester.plugins.habbopages.utils.Leaderboard> Diamonds;


    public Leaderboard() { super("cmd_leaderboard", Emulator.getTexts().getValue("commands.keys.cmd_leaderboard").split(";")); }

    @Override
    public boolean handle(final GameClient gameClient, String[] strings) throws Exception {
        String name = "leaderboard.txt";

        this.message = new StringBuilder();

        this.message.append(Emulator.getTexts().getValue("habbopages.leaderboard.header"))
                .append("\n");

        Credits = Duckets = Diamonds = new ArrayList<>();

        currency();

        new Page(name,gameClient.getHabbo(),this.message);

        return true;
    }

    public void currency() {
        if(Credits.size() <= 0) {
            Credits = Currency.Credits();

            Duckets = Currency.Seasonal(0);

            Diamonds = Currency.Seasonal(5);
        }

        this.message.append("<h2>")
                .append(Emulator.getTexts().getValue("habbopages.leaderboard.credits"))
                .append("</h2>\n")
                .append("<ul>");

        for(int i = 0; i < Credits.size(); i++) {
            this.message.append("<li>")
                    .append("<b>")
                    .append(Credits.get(i).getUsername())
                    .append(":</b>  ")
                    .append(Credits.get(i).getAmount())
                    .append("</li>");
        }

        this.message.append("</ul><h2></h2>");

        this.message.append("<h2>")
                .append(Emulator.getTexts().getValue("habbopages.leaderboard.duckets"))
                .append("</h2>\n")
                .append("<ul>");


        for(int i = 0; i < Duckets.size(); i++) {
            this.message.append("<li>")
                    .append("<b>")
                    .append(Duckets.get(i).getUsername())
                    .append(":</b>  ")
                    .append(Duckets.get(i).getAmount())
                    .append("</li>");
        }

        this.message.append("</ul><h2></h2>");

        this.message.append("<h2>")
                .append(Emulator.getTexts().getValue("habbopages.leaderboard.diamonds"))
                .append("</h2>\n")
                .append("<ul>");


        for(int i = 0; i < Diamonds.size(); i++) {
            this.message.append("<li>")
                    .append("<b>")
                    .append(Diamonds.get(i).getUsername())
                    .append(":</b>  ")
                    .append(Diamonds.get(i).getAmount())
                    .append("</li>");
        }

        this.message.append("</ul><h2></h2>");

        Emulator.getThreading().run(new Runnable()
        {
            @Override
            public void run() {
                Credits = Duckets = Diamonds = null;
            }
        }, Emulator.getConfig().getInt("habbopages.delay.mins") * 1000 * 60);
    }
}
