import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;



public class JDABot {
    public static JDA startbot() throws LoginException, InterruptedException {


        // if (main.bot.getPresence().equals("ty")){
        if (main.bot != null && main.bot.getPresence() != null){
            //todo Shut bot off here this isn't finished yet.
            System.out.println("test");
            System.out.println(main.bot.getPresence().getStatus());
            main.bot.shutdown();
        }

        JDABuilder builder;
        String token = main.dotenv.get("BOT_TOKEN");
        builder = JDABuilder.createDefault(token)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS);
        //builder.setToken(token);
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);

        if((true)){
            builder.setActivity(Activity.playing("Running on main server"));
        }else{
            builder.setActivity(Activity.playing("Running on test server"));
        }

        //todo Add twitch api check if Zed is live change status to Working on bot?
        //builder.setActivity(Activity.playing("offline for maintenance."));
        // builder.setActivity(Activity.playing("Action Bass 1997Â© For the Sony PlayStation 1"));
        //builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        // The above breaks the bot but was in thse example code for some reason
        System.out.println();
        JDA bot = builder.build();
        bot.awaitReady();
        System.out.println("JDA instace of bot is ready!");

        bot.getPresence().setStatus(OnlineStatus.ONLINE);
        System.out.println(bot.getPresence());


        //bot.getPresence().setStatus(OnlineStatus.INVISIBLE);

        bot.addEventListener(new Commands());

        List<CommandData> cmds = new ArrayList<CommandData>();
        cmds.add(new CommandData("user", "Shows information about a specific Discord User.")
                .addOption(OptionType.USER, "user", "The user you want to get the information from."));
        System.out.println(bot.getGuilds());


        return bot;

    }

}
