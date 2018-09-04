import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class DemoBuildDictionary {
    public ConcurrentHashMap<String, String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(String word, String value) {
        this.dictionary.put(word,value);
    }

    private ConcurrentHashMap<String,String> dictionary = new ConcurrentHashMap<>();
    public static void main(String[] args){
        DemoBuildDictionary demoBuildDictionary = new DemoBuildDictionary();
        try {
            Scanner read = new Scanner(new File("D:\\WorkSpaces\\SM2\\ds\\Oxford_English_Dictionary.txt"));
            while (read.hasNext()){
                String word = read.next().toLowerCase();
                String meanings = read.nextLine();
                if(meanings.equals("\n")||meanings.equals(" ")){
                    continue;
                }
                demoBuildDictionary.setDictionary(word,meanings);
            }
            System.out.println(demoBuildDictionary.getDictionary().get("conclusion"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        new Thread(()->System.out.println(demoBuildDictionary.getDictionary().get("demo"))).start();
        new Thread(()->System.out.println(demoBuildDictionary.getDictionary().get("banana"))).start();
        new Thread(()->System.out.println(demoBuildDictionary.getDictionary().get("house"))).start();
    }
}
