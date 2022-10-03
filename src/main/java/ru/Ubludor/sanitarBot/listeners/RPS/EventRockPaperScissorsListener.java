package ru.Ubludor.sanitarBot.listeners.RPS;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EventRockPaperScissorsListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().equals("ножницы"))
        {
            event.getChannel().sendMessage("камень,"+"\n"+"я победил!)").queue();
        }
        else if(event.getMessage().getContentRaw().equals("бумага"))
        {
            event.getChannel().sendMessage("ножницы,"+"\n"+"я победил!)").queue();
        }
        else if(event.getMessage().getContentRaw().equals("камень"))
        {
            event.getChannel().sendMessage("бумага,"+"\n"+"я победил!)").queue();
        }
        else if(event.getMessage().getContentRaw().equals("колодец"))
        {
            event.getChannel().sendMessage("пошел нахуй!").queue();
        }
    }
}
