package ru.Ubludor.sanitarBot.listeners.Saper;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.Ubludor.sanitarBot.SanitarBot;

public class EventSaperListenerActivated extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getMessage().getChannel().getName().equals("сапер"))
        {
            if((event.getMessage().getContentRaw().equals("go")||(event.getMessage().getContentRaw().equals("start"))))
            {
                SanitarBot.saperActivated = true;
                //event.getChannel().sendMessage("Создаю поток для сапера").queue();
                SanitarBot.eventForSaper = event;
                SanitarBot.SaperThread  sT = new SanitarBot.SaperThread();
                SanitarBot.saperThread = sT;
                sT.start();
            }
        }
    }
}
