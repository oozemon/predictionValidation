// Usman Zahid
// Insight Data Engineering Coding Challenge
 
import java.io.*;
import java.text.DecimalFormat;
import java.lang.*;
import java.math.*;
import java.util.*;

public class predictionValidation{

   public static void main (String [] args) throws Exception {
      double startTime = System.nanoTime();
      /*** variable declaration ***/
      int line = 0;
      File inFile = new File ("../input/actual.txt");
      File pFile = new File ("../input/predicted.txt");
      File wFile = new File ("../input/window.txt");
   
      Scanner scanW = new Scanner (wFile);   
      Scanner scan =  new Scanner (inFile);
      Scanner scanP = new Scanner (pFile);
      
      int window = scanW.nextInt();

      reader(scan, scanP, window); //read file/split line store in map
      
      double end = System.nanoTime();
      double total = end - startTime;
      total /= 1000000;
      System.out.println("current runtime is: " + total);
   }
 
   public static void reader(Scanner scan, Scanner scanP, int window) 
         throws FileNotFoundException{
      
      LinkedHashMap<String, Double> mapA = new LinkedHashMap<String, Double>();
      HashMap<Integer, Double> solCounts = new HashMap<Integer, Double>();
      HashMap<Integer, Double> sol = new HashMap<>();
      HashMap<Integer, Integer> counts = new HashMap<>();
      HashMap<Integer, Double> cumErrHash = new HashMap<>();
      
      double error = 0;
      double wSum = 0; 
      double cumErr = 0;
      int uniqueVal = 0;
      int check = window;
 
      while (scan.hasNextLine() && check >= 0 ){
         String data = scan.nextLine();
         if (data.matches(".*[A-Z].*")){
            String [] pars = data.split("\\|");
            double actual = Double.parseDouble(pars[2]);
            String keyA = pars[0] + pars[1];
            mapA.put(keyA, actual);
         }
      }
      while (scanP.hasNextLine()){
         String data = scanP.nextLine();
         if (data.matches(".*[A-Z].*")){
            String [] pars = data.split("\\|");
            String keyP = pars[0] + pars[1];
            int charKey = Integer.parseInt((pars[0]));
            System.out.printf("charkey: %d%n", charKey);
            double predicted = Double.parseDouble(pars[2]);
            if (mapA.containsKey(keyP)){
               error = Math.abs(predicted - mapA.get(keyP));
               if (sol.containsKey(charKey)){
                  uniqueVal ++;
                  cumErr += error; 
                  System.out.printf("cumErr: %f%n", cumErr);
                  cumErrHash.put(charKey, cumErr);
                  System.out.printf("uniqueVal: %d%n", uniqueVal);
                  counts.put(charKey, uniqueVal); 
                     
               }
               else {
                  cumErr = error;
                  uniqueVal = 1;
                  sol.put(charKey, cumErr);
               }
            }
         }
      }
      for(Integer key: sol.keySet()){
         System.out.println("counts "+ counts.get(key));
      }
      for(Integer key: sol.keySet()){     
          System.out.println("hashvals: "+ cumErrHash.get(key));
      }
      for(Integer key: sol.keySet()){
          System.out.println(key+ " " + sol.get(key));
      }

      PrintStream out = new PrintStream(new File("../output/comparisons.txt"));
      for(int i = 1; i<=window; i++){
         wSum =  cumErrHash.get(i) + cumErrHash.get(i+1);
         int wCount = counts.get(i) + counts.get(i+1);
         double avgErr = wSum/wCount;
         writeSol(i,avgErr, out);
      }
   } 
   
   public static void writeSol(int i, double avgErr, PrintStream out) throws FileNotFoundException {
      PrintStream console = System.out; 
      System.setOut(out);
      System.setOut(console);
      System.out.printf("%d|%d|%.2f %n", i, i+1, avgErr);
   }
}
