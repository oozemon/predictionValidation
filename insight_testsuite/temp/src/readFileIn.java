// Usman Zahid
// Insight Data Engineering Coding Challenge
 
import java.io.File;
import java.util.Scanner;
import java.util.LinkedHashMap;
import java.text.DecimalFormat;
import java.lang.*;
import java.math.*;


public class readFileIn{

   public static void main (String [] args) throws Exception {
      double startTime = System.nanoTime();
      /*** variable declaration ***/

      int line = 0;
      File inFile = new File ("../input/actual.txt");
      File pFile = new File ("../input/predicted.txt");
         
      Scanner scan =  new Scanner (inFile);
      Scanner scanP = new Scanner (pFile);

      reader(line, scan, scanP); //read file/split line store in map


      double end = System.nanoTime();
      double total = end - startTime;
      total /= 1000000;
      System.out.println("current runtime is: " + total);
   }
 
   public static void reader(int line, Scanner scan, Scanner scanP){
      LinkedHashMap<String, Double> map = new LinkedHashMap<String, Double>();
      int value = 0; 
      while (scan.hasNextLine()){
         String data = scan.nextLine();
         if (data.matches(".*[A-Z].*")){
            String [] pars = data.split("\\|");
            double actual = Double.parseDouble(pars[2]);
            String keyA = pars[0] + pars[1];
            keyA.replaceAll("\\s","");
            System.out.printf("The keys of actual: %s%n", keyA);
            map.put(keyA, actual);
            line++;
         }
      }
      String str = map.values().toString();
      str = str.replaceAll("\\[", "").replaceAll("\\]","");
      System.out.printf("%s%n", str);
      double error =0; 
      double avgErr; 
      while (scanP.hasNextLine()){
         String data = scanP.nextLine();
         if (data.matches(".*[A-Z].*")){
            String [] pars = data.split("\\|");
            String keyP = pars[0] + pars[1];
            System.out.printf("The keys of predicted: %s%n", keyP);
            if (map.containsKey(keyP)){
               System.out.println("hit");
               value++;
               double predicted = Double.parseDouble(pars[2]);
               System.out.printf("Value is %d%n", value);
               error += Math.abs(predicted - map.get(keyP));
               System.out.printf("error is: %f%n", error); 
            }
            else{ 
               System.out.println("miss");
            }
         }
      }
      avgErr = round(error / value, 2); 
      System.out.printf("average error is: %f%n", avgErr);
   } 
   public static double round(double val, int places){ 
      if (places < 0) throw new IllegalArgumentException();
      DecimalFormat df = new DecimalFormat("0.##"); 
      BigDecimal bd = new BigDecimal(val).setScale(places, RoundingMode.HALF_UP); 
      return bd.doubleValue();
   }
}
