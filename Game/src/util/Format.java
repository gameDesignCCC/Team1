package util;

public class Format {

    public static String boolAsOnOff(boolean b){
        return b ? "ON" : "OFF";
    }

    public static double round(double decimal) {
        return Math.round(decimal * 100) / 100;
    }
}
