package ru.Ubludor.sanitarBot.listeners.Saper;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.Ubludor.sanitarBot.SanitarBot;

public class EventTouchTheCell extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(SanitarBot.saperActivated) {
            if (event.getMessage().getChannel().getName().equals("сапер")) {
                if(isCorrect(event.getMessage().getContentRaw()))
                {
                    if(event.getMessage().getContentRaw().charAt(1) == ' '){
                        int l = event.getMessage().getContentRaw().charAt(0)-'0';
                        int c = event.getMessage().getContentRaw().charAt(2)-'0';

                        SanitarBot.currLine = l;
                        SanitarBot.currColumn = c;

                        SanitarBot.waitСoordinate = !SanitarBot.waitСoordinate;


                    }
                    else
                    {
                        int l = event.getMessage().getContentRaw().charAt(0)-'0';
                        int c = event.getMessage().getContentRaw().charAt(2)-'0';

                        SanitarBot.currLine = l;
                        SanitarBot.currColumn = c;
                        SanitarBot.flagMode = true;
                        SanitarBot.waitСoordinate = !SanitarBot.waitСoordinate;


                    }
                    synchronized (SanitarBot.saperThread ) {
                        SanitarBot.saperThread.notify();
                    }
                }
            }
        }
    }

    private boolean isCorrect(String contentRaw) {
        if(contentRaw.length()!=3)
        {
            return false;
        }
        if((contentRaw.charAt(0)-'0'<0)||(contentRaw.charAt(2)-'0'<0))
        {
            return false;
        }
        if(('9'-contentRaw.charAt(0)<0)||('9'-contentRaw.charAt(2)<0))
        {
            return false;
        }
        if((contentRaw.charAt(1)!=' ')&&(contentRaw.charAt(1)!='f'))
        {
            return false;
        }
        return true;
    }
}
