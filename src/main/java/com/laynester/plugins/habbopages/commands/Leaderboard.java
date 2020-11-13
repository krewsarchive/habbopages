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

    public Leaderboard() { super("cmd_leaderboard", Emulator.getTexts().getValue("commands.keys.cmd_leaderboard").split(";")); }

    @Override
    public boolean handle(final GameClient gameClient, String[] strings) throws Exception {
        String name = "leaderboard.txt";
        this.message = new StringBuilder();

        this.message.append(Emulator.getTexts().getValue("habbopages.leaderboard.header"))
                .append("\n");

        currency();

        new Page(name,gameClient.getHabbo(),this.message);
        return true;
    }

    public void currency() {

        this.message.append("<h2>")
                .append(Emulator.getTexts().getValue("habbopages.leaderboard.credits"))
                .append("</h2>\n")
                .append("<ul>");

        ArrayList<com.laynester.plugins.habbopages.utils.Leaderboard> cred = Currency.Credits();

        for(int i = 0; i < cred.size(); i++) {
            this.message.append("<li>")
                    .append("<b>")
                    .append(cred.get(i).getUsername())
                    .append(":</b>  ")
                    .append(cred.get(i).getAmount())
                    .append("</li>");
        }

        this.message.append("</ul>");

    }
}
