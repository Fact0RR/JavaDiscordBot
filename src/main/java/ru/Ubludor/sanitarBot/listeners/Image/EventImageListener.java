package ru.Ubludor.sanitarBot.listeners.Image;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EventImageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        //super.onMessageReceived(event);
        if (event.getMessage().getChannel().getName().equals("преобразователь-в-ascii")) {

                //System.out.println(event.getMessage().);

        }
    }
}
