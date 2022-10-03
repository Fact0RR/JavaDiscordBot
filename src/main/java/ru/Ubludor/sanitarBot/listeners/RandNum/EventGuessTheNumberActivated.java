package ru.Ubludor.sanitarBot.listeners.RandNum;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.Ubludor.sanitarBot.SanitarBot;

public class EventGuessTheNumberActivated extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getMessage().getChannel().getName().equals("угадай-число"))
        {
            if((event.getMessage().getContentRaw().equals("go")||(event.getMessage().getContentRaw().equals("start"))))
            {
                SanitarBot.guessActivated = true;
                event.getChannel().sendMessage("я загадал число от 0 до 9(все включительно), попробуй угадать!").queue();
            }
        }
    }

}
