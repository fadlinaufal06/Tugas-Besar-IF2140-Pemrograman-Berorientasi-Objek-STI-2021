/**
 * Class Scan
 * Biar kalo scannya harus integer ga error pas input string
 *  + biar ga marah-marah karena ga diclose scannernya wkwk
 */

import java.util.Scanner;

public class Scan {
    private static Scanner scan;
    public Scan(){
        scan = new Scanner(System.in);
    }
    public static int intScanner(){
        int n=0;
        boolean run=true;
        while (run){    
            String input = scan.nextLine();
            if (isNumeric(input)){
                run = false;
                n=Integer.parseInt(input);
            }
            else {
                System.out.println("Kamu harus input angka.");
            }
        }
        return n;
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static String strInput(){
        String s = scan.nextLine();
        return s;
    }
    public static void closeScanner(){
        scan.close();
    }
}
