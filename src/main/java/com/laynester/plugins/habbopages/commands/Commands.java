package com.laynester.plugins.habbopages.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import java.util.List;
import com.laynester.plugins.habbopages.utils.Page;

/*
 * Author: Laynester
 */

public class Commands  extends Command {

    public Commands()
    {
        super("cmd_commands", Emulator.getTexts().getValue("commands.keys.cmd_commands").split(";"));
    }

    @Override
    public boolean handle(final GameClient gameClient, String[] strings) throws Exception {

        String name = "commands_" + gameClient.getHabbo().getHabboInfo().getRank().getId() + ".txt";

        StringBuilder message = new StringBuilder(Emulator.getTexts().getValue("commands.generic.cmd_commands.text")+ "\n");

        List<Command> commands = Emulator.getGameEnvironment().getCommandHandler().getCommandsForRank(gameClient.getHabbo().getHabboInfo().getRank().getId());

        message.append("<h2>")
                .append(Emulator.getTexts().getValue("commands.generic.cmd_commands.text"))
                .append(" (").append(commands.size()).append(")</h2>\n");

        message.append("<ul>");

        for (Command c : commands) {
            message.append("<li>")
                    .append(Emulator.getTexts().getValue("commands.description." + c.permission, "commands.description." + c.permission)
                    .replace("<", "(")
                    .replace(">", ")"))
                    .append("</li>\n");
        }

        message.append("</ul>");

        new Page(name,gameClient.getHabbo(),message);

        return true;
    }
}
