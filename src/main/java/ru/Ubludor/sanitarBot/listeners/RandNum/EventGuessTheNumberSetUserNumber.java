package ru.Ubludor.sanitarBot.listeners.RandNum;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.Ubludor.sanitarBot.SanitarBot;

import java.util.Random;

public class EventGuessTheNumberSetUserNumber extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(SanitarBot.guessActivated)
        {
            if(event.getMessage().getChannel().getName().equals("угадай-число")) {
                if(event.getMessage().getContentRaw().length() == 1)
                {
                    if((event.getMessage().getContentRaw().charAt(0)-'0'>=0)&&('9'-event.getMessage().getContentRaw().charAt(0)>=0))
                    {
                        Random random = new Random();
                        int rn = random.nextInt(10);//

                        if(event.getMessage().getContentRaw().charAt(0)-'0' == rn )
                        {
                            event.getChannel().sendMessage("ты угадал, "+rn+" это загаданное число!!!").queue();
                        }
                        else {
                            event.getChannel().sendMessage("ты не угадал, "+rn+" было загаданным числом(").queue();
                        }
                        SanitarBot.guessActivated = false;
                    }

                }
            }
        }
    }
    private boolean isNotInavlid(String contentRaw) {
        if(contentRaw.length()!=3){return false;}
        if(contentRaw.charAt(2) != '0'){return false;} if(contentRaw.charAt(0) != '0'){return false;}
        if(contentRaw.charAt(2) != '1'){return false;} if(contentRaw.charAt(0) != '1'){return false;}
        if(contentRaw.charAt(2) != '2'){return false;} if(contentRaw.charAt(0) != '2'){return false;}
        if(contentRaw.charAt(2) != '3'){return false;} if(contentRaw.charAt(0) != '3'){return false;}
        if(contentRaw.charAt(2) != '4'){return false;} if(contentRaw.charAt(0) != '4'){return false;}
        if(contentRaw.charAt(2) != '5'){return false;} if(contentRaw.charAt(0) != '5'){return false;}
        if(contentRaw.charAt(2) != '6'){return false;} if(contentRaw.charAt(0) != '6'){return false;}
        if(contentRaw.charAt(2) != '7'){return false;} if(contentRaw.charAt(0) != '7'){return false;}
        if(contentRaw.charAt(2) != '8'){return false;} if(contentRaw.charAt(0) != '8'){return false;}
        if(contentRaw.charAt(2) != '9'){return false;} if(contentRaw.charAt(0) != '9'){return false;}
        if(contentRaw.charAt(1) != ' '){return false;}
        return true;
    }
}
