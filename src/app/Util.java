package app;

public class Util {
    public static boolean isNumber(String s){
        for(Character c : s.toCharArray()){
            if(!Character.isDigit(c)) return false;
        }
        return true;
    }
}
