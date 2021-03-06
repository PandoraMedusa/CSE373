//Amy Shantz
//11/12/13
//CSE 142
//TA: Michael Beswetherick
//Assignment #6
//
//(program description)

import java.awt.*;
import java.io.*;
import java.util.*;

public class Names {

   public static final int DECADES = 10;
   public static final int STARTYEAR = 1920;
   public static final int DECADEWIDTH = 90;
   
   public static void main(String[] args) throws FileNotFoundException {
      Scanner input = new Scanner(new File ("names2.txt"));
      Scanner console = new Scanner (System.in);
      intro();
      System.out.print("name? ");
      String name = console.next().toLowerCase();
      System.out.print("gender (M or F)? ");
      String gender = console.next().toLowerCase();
      String nameAndGender = name + " " + gender;
      String line = nameSearch(input, nameAndGender);
      if (line.length() > 0) {
         drawGraph(line);
      }
   }
   
   //(method comment)
   public static void intro() {
      System.out.println("This program allows you to search through the");
      System.out.println("data from the Social Security Administration");
      System.out.println("to see how popular a particular name has been");
      System.out.println("since" + STARTYEAR + ".");
      System.out.println();
   }
   
   //(method comment)
   public static String nameSearch(Scanner input, String nameAndGender) {
      while (input.hasNextLine()) {
         String line = input.nextLine();
         if (line.toLowerCase().startsWith(nameAndGender + " ")) {
            return line;
         }
      }
      System.out.println("name/gender combination not found");
      return "";
   }
   
   //(method comment)
   public static void drawChart(Graphics g) {
      g.drawLine(0, 25, DECADEWIDTH * DECADES, 25);
      g.drawLine(0, 525, DECADEWIDTH * DECADES, 525);
      for (int i=0; i <= DECADES; i++) {
         int w = i * DECADEWIDTH;
         int y = STARTYEAR + i * 10;
         g.drawLine(w, 0, w, 550);
         String x = "" + (STARTYEAR + i * 10);
         g.drawString(x, w, 550);
      }
   }
   
   //(method comment)
   public static void drawGraph(String line) {
      DrawingPanel p = new DrawingPanel(DECADEWIDTH * DECADES,550);
      Graphics g = p.getGraphics();
      Scanner info = new Scanner(line);
      drawChart(g);
      g.setColor(Color.RED);
      String name = info.next() + " " + info.next().toUpperCase();
      int number = 0;
      int ay = 0;
      int by = 0;
      int a = 0;
      int b = 0;
      int x = 0;
      a = info.nextInt();
      if (a == 0){
         ay = 525;
      }else{
         ay = 25 + (a-1) / 2;
      }
      while (info.hasNextInt()) {
         x = DECADEWIDTH * number;
         b = info.nextInt();
         by = 25 + (b-1) / 2;
         if (b == 0){
         	by = 525;
         }
         g.drawLine(x, ay, x + DECADEWIDTH, by);
         g.drawString(name + " " + a, x, ay);
         a = b;
         ay = by;
         number++;
      }
      g.drawString(name + " " + a, x + DECADEWIDTH, ay);
   }
}