package top.faved.ultracord.managers;

import org.simpleyaml.configuration.file.YamlFile;
import top.faved.ultracord.UltraCord;

import java.io.File;
import java.io.IOException;

public class PluginConfig {
    private static final File CONFIG_FILE = new File(UltraCord.getInstance().getDataFolder(), "config.yml");

    private final YamlFile yamlFile;

    public PluginConfig() {
        this.yamlFile = new YamlFile(CONFIG_FILE);
        try {
            this.yamlFile.createOrLoadWithComments();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load configuration:", e);
        }

        this.yamlFile.setHeader("""
                dP     dP dP   dP                      a88888b.                         dP\s
                88     88 88   88                     d8'   `88                         88\s
                88     88 88 d8888P 88d888b. .d8888b. 88        .d8888b. 88d888b. .d888b88\s
                88     88 88   88   88'  `88 88'  `88 88        88'  `88 88'  `88 88'  `88\s
                Y8.   .8P 88   88   88       88.  .88 Y8.   .88 88.  .88 88       88.  .88\s
                `Y88888P' dP   dP   dP       `88888P8  Y88888P' `88888P' dP       `88888P8\s
                ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                
                Made with <3 by Faved owners (Cerial and Emir)
                """);

        this.yamlFile.setComment("token", """
                Bot Token
                - This is the most important part, your bot's token.
                - To create a new bot, simply follow the guide as outlined in the GitHub README.
                - WARNING: Do NOT share this token with anyone, bad actors can pose as your bot and
                           make the bot user do things you wouldn't want it to.
                """);
        this.yamlFile.set("token", "YOUR-TOKEN-HERE");

    }

    public String getToken() {
        return this.yamlFile.getString("token");
    }
}
