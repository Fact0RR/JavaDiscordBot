package ru.Ubludor.sanitarBot.Classes;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.Ubludor.sanitarBot.SanitarBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Saper {

    public class UpField {
        private int[][] cells = new int[10][10];

        UpField() {
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells.length; j++) {
                    cells[i][j] = -1;
                }
            }
        }
    }

    public class DownField {
        public int[][] cells = new int[10][10];
        public int bombs = 0;

        DownField() {
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    Random random = new Random();
                    if (random.nextInt(8) == 0) {
                        cells[i][j] = 9;
                        bombs++;
                    }
                }
            }

            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    int bombsAround = 0;
                    if (cells[i][j] != 9) {
                        if (i + 1 < cells.length) {
                            if (cells[i + 1][j] == 9) {
                                bombsAround++;
                            }
                        }
                        if (j + 1 < cells[i].length) {
                            if (cells[i][j + 1] == 9) {
                                bombsAround++;
                            }
                        }
                        if (i - 1 >= 0) {
                            if (cells[i - 1][j] == 9) {
                                bombsAround++;
                            }
                        }
                        if (j - 1 >= 0) {
                            if (cells[i][j - 1] == 9) {
                                bombsAround++;
                            }
                        }
                        if (i + 1 < cells.length && j + 1 < cells[i].length) {
                            if (cells[i + 1][j + 1] == 9) {
                                bombsAround++;
                            }
                        }
                        if (i + 1 < cells.length && j - 1 >= 0) {
                            if (cells[i + 1][j - 1] == 9) {
                                bombsAround++;
                            }
                        }
                        if (i - 1 >= 0 && j + 1 < cells[i].length) {
                            if (cells[i - 1][j + 1] == 9) {
                                bombsAround++;
                            }
                        }
                        if (i - 1 >= 0 && j - 1 >= 0) {
                            if (cells[i - 1][j - 1] == 9) {
                                bombsAround++;
                            }
                        }
                        cells[i][j] = bombsAround;
                    }

                }
            }

        }
    }


    public void launchGame() throws FileNotFoundException, InterruptedException {

        UpField upField = new UpField();
        DownField downField = new DownField();
        File file = new File("saperDB");
        PrintWriter pw = new PrintWriter(file);

        System.out.println("Бомб на поле: "+ downField.bombs+"!");
        SanitarBot.eventForSaper.getChannel().sendMessage("Бомб на поле: "+ downField.bombs+"!").queue();
        pw.print("Бомб на поле было: "+ downField.bombs+"\n");
        pw.print("Игрок : " + SanitarBot.eventForSaper.getAuthor().getName()+"\n\n");
        pw.print("Все ходы :\n");

        System.out.println(showUpField(upField));
        SanitarBot.eventForSaper.getChannel().sendMessage("`"+ showUpField(upField)+"`").queue();
        XY xy;
        int move=0;

        while (openCells(upField) != downField.bombs) {


            synchronized (SanitarBot.saperThread) {
                SanitarBot.saperThread.wait();
            }

            if (!SanitarBot.flagMode){
                    //int line = setLine();
                    //int column = setColumn();
                    xy = setCoordinateByChat();
                int line = xy.line;
                int column = xy.column;
                if(upField.cells[line][column] == -2)
                {
                    continue;
                }
                pw.print(line);
                pw.print(" " + column + "\n");
                move++;
                if (downField.cells[line][column] == 9) {
                    break;
                } else if (downField.cells[line][column] == 0) {

                    upField.cells[line][column] = downField.cells[line][column];
                    clearTheField(upField, downField);
                    System.out.println(showUpField(upField));
                    SanitarBot.eventForSaper.getChannel().sendMessage("`" + showUpField(upField) + "`").queue();
                } else {
                    upField.cells[line][column] = downField.cells[line][column];
                    System.out.println(showUpField(upField));
                    SanitarBot.eventForSaper.getChannel().sendMessage("`" + showUpField(upField) + "`").queue();
                }
            }
            else {
                xy = setCoordinateByChat();
                int line = xy.line;
                int column = xy.column;
                if (upField.cells[line][column] == -1){
                    upField.cells[line][column] = -2;
                    SanitarBot.eventForSaper.getChannel().sendMessage("`" + showUpField(upField) + "`").queue();
                }else if(upField.cells[line][column] == -2)
                {
                    upField.cells[line][column] = -1;
                    SanitarBot.eventForSaper.getChannel().sendMessage("`" + showUpField(upField) + "`").queue();
                }
                SanitarBot.flagMode = false;
            }
        }
        if (openCells(upField) == downField.bombs) {
            //вы победили
            System.out.println("Вы победили!!!");
            pw.print("Всего ходов:"+move+"\n");
            pw.print("Результат: победа\n");
            SanitarBot.eventForSaper.getChannel().sendMessage("Вы победили!!!").queue();
            System.out.println(showDownField(downField));
            SanitarBot.eventForSaper.getChannel().sendMessage("`"+ showDownField(downField)+"`").queue();

        } else {
            System.out.println("Вы проиграли");
            pw.print("Всего ходов:"+move+"\n");
            pw.print("Результат: проигрыш\n");
            SanitarBot.eventForSaper.getChannel().sendMessage("Вы проиграли").queue();
            System.out.println(showDownField(downField));
            SanitarBot.eventForSaper.getChannel().sendMessage("`"+ showDownField(downField)+"`").queue();
        }
        pw.close();
        SanitarBot.eventForSaper.getChannel().sendMessage(showStatistic()).queue();


    }

    private String showStatistic() throws FileNotFoundException {
        String bufer = "";
        File file = new File("saperDB");

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()){
            bufer = bufer + scanner.nextLine()+"\n";
        }
        return bufer;
    }

    private void clearTheField(UpField upField, DownField downField) {

        for (int k = 0; k < upField.cells.length; k++) {
            for (int i = 0; i < upField.cells.length; i++) {
                for (int j = 0; j < upField.cells[i].length; j++) {
                    if (upField.cells[i][j] == 0) {
                        if (i + 1 < upField.cells.length) {
                            upField.cells[i + 1][j] = downField.cells[i + 1][j];
                        }
                        if (j + 1 < upField.cells[i].length) {
                            upField.cells[i][j + 1] = downField.cells[i][j + 1];
                        }
                        if (i - 1 >= 0) {
                            upField.cells[i - 1][j] = downField.cells[i - 1][j];
                        }
                        if (j - 1 >= 0) {
                            upField.cells[i][j - 1] = downField.cells[i][j - 1];
                        }
                        if (i + 1 < upField.cells.length && j + 1 < upField.cells[i].length) {
                            upField.cells[i + 1][j + 1] = downField.cells[i + 1][j + 1];
                        }
                        if (i + 1 < upField.cells.length && j - 1 >= 0) {
                            upField.cells[i + 1][j - 1] = downField.cells[i + 1][j - 1];
                        }
                        if (i - 1 >= 0 && j + 1 < upField.cells[i].length) {
                            upField.cells[i - 1][j + 1] = downField.cells[i - 1][j + 1];
                            if (upField.cells[i - 1][j + 1] == 0);
                        }
                        if (i - 1 >= 0 && j - 1 >= 0) {
                            upField.cells[i - 1][j - 1] = downField.cells[i - 1][j - 1];

                        }
                    }

                }
            }
            for (int i = upField.cells.length-1; i >=0 ; i--) {
                for (int j = upField.cells[i].length-1; j >=0 ; j--) {
                    if (upField.cells[i][j] == 0) {
                        if (i + 1 < upField.cells.length) {
                            upField.cells[i + 1][j] = downField.cells[i + 1][j];
                        }
                        if (j + 1 < upField.cells[i].length) {
                            upField.cells[i][j + 1] = downField.cells[i][j + 1];
                        }
                        if (i - 1 >= 0) {
                            upField.cells[i - 1][j] = downField.cells[i - 1][j];
                        }
                        if (j - 1 >= 0) {
                            upField.cells[i][j - 1] = downField.cells[i][j - 1];
                        }
                        if (i + 1 < upField.cells.length && j + 1 < upField.cells[i].length) {
                            upField.cells[i + 1][j + 1] = downField.cells[i + 1][j + 1];
                        }
                        if (i + 1 < upField.cells.length && j - 1 >= 0) {
                            upField.cells[i + 1][j - 1] = downField.cells[i + 1][j - 1];
                        }
                        if (i - 1 >= 0 && j + 1 < upField.cells[i].length) {
                            upField.cells[i - 1][j + 1] = downField.cells[i - 1][j + 1];
                            if (upField.cells[i - 1][j + 1] == 0);
                        }
                        if (i - 1 >= 0 && j - 1 >= 0) {
                            upField.cells[i - 1][j - 1] = downField.cells[i - 1][j - 1];

                        }
                    }

                }
            }
        }
    }

    private String showDownField(DownField upField) {
        String mainString = "   ";
        for (int i = 0; i < upField.cells.length; i++){

            mainString = mainString + " " + i;
        }
        mainString = mainString + "\n   ";
        for (int i = 0; i < upField.cells.length; i++){

            mainString = mainString + " -";
        }
        mainString = mainString + "\n";
        for (int i = 0; i < upField.cells.length; i++) {

            for (int j = 0; j < upField.cells[i].length; j++)
            {
                if(j == 0)
                {
                    if(upField.cells[i][j]== 9) {
                        mainString = mainString + i + "|  *";
                    }
                    else if(upField.cells[i][j]==0) {
                        mainString = mainString + i + "|   ";
                    }
                    else
                    {
                        mainString = mainString + i + "|  " + upField.cells[i][j]+"";
                    }
                }
                else if(j == upField.cells[i].length-1)
                {
                    if(upField.cells[i][j]==9) {
                        mainString =mainString + " *  |" + i;
                    }
                    else if(upField.cells[i][j]==0) {
                        mainString =mainString + "    |" + i;
                    }
                    else
                    {
                        mainString = mainString  + " " + upField.cells[i][j]+"  |"+ i;
                    }
                }
                else
                {
                    if(upField.cells[i][j]==9) {
                        mainString =mainString + " *";
                    }
                    else if(upField.cells[i][j]==0) {
                        mainString =mainString + "  ";
                    }
                    else
                    {
                        mainString = mainString  + " " + upField.cells[i][j];
                    }
                }
            }
            mainString = mainString + "\n";
        }
        mainString =mainString + "   ";

        for (int i = 0; i < upField.cells.length; i++){

            mainString = mainString + " -";
        }
        mainString = mainString+ "\n";
        mainString =mainString + "   ";
        for (int i = 0; i < upField.cells.length; i++){

            mainString = mainString + " " + i;
        }
        return mainString;
    }
    private String showUpField(UpField upField) {
        String mainString = "   ";
        for (int i = 0; i < upField.cells.length; i++){

            mainString = mainString + " " + i;
        }
        mainString = mainString + "\n   ";
        for (int i = 0; i < upField.cells.length; i++){

            mainString = mainString + " -";
        }
        mainString = mainString + "\n";
        for (int i = 0; i < upField.cells.length; i++) {

            for (int j = 0; j < upField.cells[i].length; j++)
            {
                if(j == 0)
                {
                    if(upField.cells[i][j]==-1) {
                        mainString = mainString + i + "|  #";
                    }
                    else if(upField.cells[i][j]==0) {
                        mainString = mainString + i + "|   ";
                    }
                    else if(upField.cells[i][j]==-2) {
                        mainString = mainString + i + "|  >";
                    }
                    else
                    {
                        mainString = mainString + i + "|  " + upField.cells[i][j]+"";
                    }
                }
                else if(j == upField.cells[i].length-1)
                {
                    if(upField.cells[i][j]==-1) {
                        mainString =mainString + " #  |" + i;
                    }
                    else if(upField.cells[i][j]==0) {
                        mainString =mainString + "    |" + i;
                    }
                    else if(upField.cells[i][j]==-2) {
                        mainString =mainString + " >  |" + i;
                    }
                    else
                    {
                        mainString = mainString  + " " + upField.cells[i][j]+"  |"+ i;
                    }
                }
                else
                {
                    if(upField.cells[i][j]==-1) {
                        mainString =mainString + " #";
                    }
                    else if(upField.cells[i][j]==0) {
                        mainString =mainString + "  ";
                    }
                    else if(upField.cells[i][j]==-2) {
                        mainString =mainString + " >";
                    }
                    else
                    {
                        mainString = mainString  + " " + upField.cells[i][j];
                    }
                }
            }
            mainString = mainString + "\n";
        }
        mainString =mainString + "   ";

        for (int i = 0; i < upField.cells.length; i++){

            mainString = mainString + " -";
        }
        mainString = mainString+ "\n";
        mainString =mainString + "   ";
        for (int i = 0; i < upField.cells.length; i++){

            mainString = mainString + " " + i;
        }
        return mainString;
    }

    private int setColumn() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private class XY{
        public int line;
        public int column;
    }

    private XY setCoordinateByChat() {
        while(SanitarBot.waitСoordinate)
        {}
        SanitarBot.waitСoordinate = !SanitarBot.waitСoordinate;
        XY xy = new XY();
        xy.line = SanitarBot.currLine;
        xy.column = SanitarBot.currColumn;
        return xy;
    }

    private int setLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private int openCells(UpField upField) {
        int oc = 0;
        for(int i =0;i<upField.cells.length;i++)
        {
            for(int j=0;j<upField.cells[i].length;j++)
            {
                if((upField.cells[i][j]==-1)||(upField.cells[i][j]==-2))
                {
                    oc++;
                }
            }
        }

        return oc;
    }
}