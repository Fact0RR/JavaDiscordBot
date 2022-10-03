package ru.Ubludor.sanitarBot.listeners.RAV;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.Ubludor.sanitarBot.SanitarBot;

import java.util.Random;

public class EventRandomAgentValorant extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMessage().getChannel().getName().equals("рандомайзер-агентов-валорант")) {
            if(event.getMessage().getContentRaw().equals("go")) {
                int ch = 0;
                Random r = new Random();
                ch = r.nextInt(19);

                if((!event.getAuthor().getName().equals("Денис Маслов"))&&(!event.getAuthor().getName().equals("КрутойПоцан"))) {
                    if (ch == 0) {
                        event.getChannel().sendMessage(" Brimstone").queue();
                    } else if (ch == 1) {
                        event.getChannel().sendMessage("Viper").queue();
                    } else if (ch == 2) {
                        event.getChannel().sendMessage("Omen").queue();
                    } else if (ch == 3) {
                        event.getChannel().sendMessage("Killjoy").queue();
                    } else if (ch == 4) {
                        event.getChannel().sendMessage("Cypher").queue();
                    } else if (ch == 5) {
                        event.getChannel().sendMessage("Sova").queue();
                    } else if (ch == 6) {
                        event.getChannel().sendMessage("Sage").queue();
                    } else if (ch == 7) {
                        event.getChannel().sendMessage("Phoenix").queue();
                    } else if (ch == 8) {
                        event.getChannel().sendMessage("Jett").queue();
                    } else if (ch == 9) {
                        event.getChannel().sendMessage("Reyna").queue();
                    } else if (ch == 11) {
                        event.getChannel().sendMessage("Raze").queue();
                    } else if (ch == 12) {
                        event.getChannel().sendMessage("Breach").queue();
                    } else if (ch == 13) {
                        event.getChannel().sendMessage("Skye").queue();
                    } else if (ch == 14) {
                        event.getChannel().sendMessage("Yoru").queue();
                    } else if (ch == 15) {
                        event.getChannel().sendMessage("Astra").queue();
                    } else if (ch == 16) {
                        event.getChannel().sendMessage("KAY/O").queue();
                    } else if (ch == 17) {
                        event.getChannel().sendMessage("Chamber").queue();
                    } else if (ch == 18) {
                        event.getChannel().sendMessage("Neon").queue();
                    }
                }else if((event.getAuthor().getName().equals("Денис Маслов"))) {
                    event.getChannel().sendMessage("Cypher").queue();
                }else
                {
                    event.getChannel().sendMessage("Omen").queue();
                }

            }
        }

    }
}