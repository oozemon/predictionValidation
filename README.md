USMAN ZAHID 
2019 INSIGHT DATA FELLOWSHIP APPLICATION


#APPROACH:
The basic approach to this problem consisted of a few steps:
 
1.) Parse the incoming data from the actual.txt file and place the values into a LinkedHashMap
with the <K,V> format where the key was a concatenated value of the time and symbol
and the value with the price of the particular stock.

2.) After this value was stored, I then read in values from the predicted file, only if 
there was a match from the LinkedHashMap. If there was a match, the absolute value of the
difference was stored, as well as the amount of values that matched for that time window.
Then I took the average error by diving the sum of the absolute difference by the 
amount of values that had a difference in the particular window. 

3.) From that point I wrote to file, making sure to maintain the format that was 
in the description. To determine consistency, I ensured unit tests to make each 
feature of the program was working consistently. 

##DEPENDECIES 

The program only requires installation of Java 9. 

###RUN INSTRUCTIONS

To run this program for the /src/ directory, simply type:

$ make 
$ java predictionValidation

Output will be written to out file named "comparisons.txt". 
