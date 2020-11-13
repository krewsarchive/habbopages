package com.laynester.plugins.habbopages.utils;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.habboway.nux.NuxAlertComposer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Author: Laynester
 */

public class Page {
    private String name;

    private StringBuilder content;

    private Habbo habbo;

    private File file;

    public Page(String name, Habbo habbo, StringBuilder message) throws IOException {
        this.name = name;

        this.habbo = habbo;

        this.content = message;

        this.createFile();
    }

    public void createFile() throws IOException {
        String file = Emulator.getConfig().getValue("habbopages.directory") + "habbopages/" + this.name;

        this.file = new File(file);

        if(this.file.exists()) {
            this.openPage();

            return;
        }

        FileWriter myWriter = new FileWriter(file);

        myWriter.write(this.content.toString());

        myWriter.close();

        this.openPage();
    }

    public void openPage() {
        habbo.getClient().sendResponse(new NuxAlertComposer("habbopages/" + this.name));

        final File f = this.file;

        Emulator.getThreading().run(new Runnable()
        {
            @Override
            public void run() {
                f.delete();
            }
        }, Emulator.getConfig().getInt("habbopages.delay.mins") * 1000 * 60);
    }
}
