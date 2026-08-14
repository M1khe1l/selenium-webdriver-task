package utilities;

public class UtilityMethods {

    public static double parseDoubleFromString(String string)
    {
        String rawPrice = string.replaceAll("[^0-9.,]", "");
        rawPrice = rawPrice.replaceAll(",", ".");
        return Double.parseDouble(rawPrice);
    }

    public static double sumOfDoublesFromString(String... string)
    {
        double sum = 0;
        for(String s: string){
            sum += parseDoubleFromString(s);
        }
        return sum;
    }
}
