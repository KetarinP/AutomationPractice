package data;
import utils.ConfigReader;

public class Temp {
    public static void main(String[] args) {
        String url = ConfigReader.readProperty("url");

        System.out.println(url);
    }
}