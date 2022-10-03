package ru.Ubludor.sanitarBot;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import ru.Ubludor.sanitarBot.Classes.Saper;
import ru.Ubludor.sanitarBot.listeners.Image.EventImageListener;
import ru.Ubludor.sanitarBot.listeners.Pidr.EventPidrListener;
import ru.Ubludor.sanitarBot.listeners.RAV.EventRandomAgentValorant;
import ru.Ubludor.sanitarBot.listeners.RPS.EventRockPaperScissorsListener;
import ru.Ubludor.sanitarBot.listeners.RandNum.EventGuessTheNumberActivated;
import ru.Ubludor.sanitarBot.listeners.RandNum.EventGuessTheNumberSetUserNumber;
import ru.Ubludor.sanitarBot.listeners.Saper.EventSaperListenerActivated;
import ru.Ubludor.sanitarBot.listeners.Saper.EventTouchTheCell;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;


public class SanitarBot {
    public static int currLine;
    public static int currColumn;
    public static boolean waitСoordinate = true;
    public static boolean guessActivated = false;
    public static boolean saperActivated = false;
    public static boolean flagMode = false;
    public final static Thread mainThread = Thread.currentThread();
    public static MessageReceivedEvent eventForSaper;
    public static SaperThread saperThread;
    private final ShardManager shardManager;
    private final Dotenv config;

    public static class SaperThread extends Thread
    {
        @Override
        public void run() {
            Saper saper = new Saper();
            try {
                saper.launchGame();
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



    SanitarBot() throws LoginException {

        config = Dotenv.configure().load();

        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("за дуркой"));

        shardManager = builder.build();

        // слушатели:

        shardManager.addEventListener(new EventPidrListener());

        shardManager.addEventListener(new EventRockPaperScissorsListener());

        shardManager.addEventListener(new EventRandomAgentValorant());

        shardManager.addEventListener(new EventGuessTheNumberActivated());
        shardManager.addEventListener(new EventGuessTheNumberSetUserNumber());

        shardManager.addEventListener(new EventSaperListenerActivated());
        shardManager.addEventListener(new EventTouchTheCell());
        shardManager.addEventListener(new EventImageListener());
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try{
            SanitarBot bot = new SanitarBot();
        }
        catch (LoginException e)
        {
            System.out.println("ERROR: provide bot token is invalid");
        }
    }
}