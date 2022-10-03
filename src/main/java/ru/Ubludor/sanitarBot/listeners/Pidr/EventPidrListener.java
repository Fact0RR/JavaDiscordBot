package ru.Ubludor.sanitarBot.listeners.Pidr;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageEmbedEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EventPidrListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        //super.onMessageReceived(event);
        if(event.getMessage().getContentRaw().equals("пидр"))
        {
            event.getChannel().sendMessage("а может ты пидр?!").queue();
        }

    }
}
