package top.faved.ultracord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;
import top.faved.ultracord.managers.PluginConfig;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

public class UltraCord extends JavaPlugin {
    private static UltraCord instance;

    private PluginConfig config;
    private JDA bot;

    public static UltraCord getInstance() {
        return instance;
    }

    public PluginConfig getPluginConfig() {
        return this.config;
    }

    public JDA getBot() {
        return this.bot;
    }

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Hello! Starting UltraCord...");

        this.config = new PluginConfig();

        // Validate the token
        if (!validateToken(this.config.getToken())) {
            disableInvalidToken();
            return;
        }

        try {
            this.bot = JDABuilder.createLight(this.config.getToken())
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
                    .build();
        } catch (InvalidTokenException | IllegalArgumentException ex) {
            disableInvalidToken();
            return;
        }
    }

    private void disableInvalidToken() {
        getLogger().severe("The provided token in the configuration is invalid!");
        getLogger().severe("Please provide a valid bot token from Discord Developers page!");
        getLogger().severe("To check how to make a bot, go to the UltraCord GitHub page.");
        getServer().getPluginManager().disablePlugin(this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /**
     * Validate a token. <a href="https://user-images.githubusercontent.com/6506416/81051916-dd8c9900-8ec2-11ea-8794-daf12d6f31f0.png">Reference of token validation is found here.</a>
     * @param token The token which you want to validate.
     * @return If the provided token is a valid token.
     */
    private boolean validateToken(String token) {
        // Split the token into 3 parts: the bot ID, token creation date, and cryptographic component.
        String[] tokenParts = token.split("\\.");

        // If token doesn't have 3 parts, it's invalid.
        if (tokenParts.length != 3) return false;

        // Try to decode ID and parse as a long
        try {
            Long.parseLong(new String((Base64.getDecoder().decode(tokenParts[0])), StandardCharsets.UTF_8));
        } catch (NumberFormatException ex) {
            return false;
        }

        // Try to decode timestamp and check if it's a valid timestamp
        // A valid timestamp should be above May 2015.
        try {
            long timestamp = Long.parseLong(new String((Base64.getDecoder().decode(tokenParts[1])), StandardCharsets.UTF_8));
            // Add offset to make it real epoch time
            timestamp += 1293840000;
            // Make a new date
            LocalDateTime date = LocalDateTime.ofInstant(new Date(timestamp).toInstant(), ZoneId.systemDefault());

            // Check if year is below 2015
            if (date.getYear() < 2015) return false;
            // Check if month is below May
            if (date.getYear() == 2015 && date.getMonth().getValue() < Month.MAY.getValue()) return false;
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
