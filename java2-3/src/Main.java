
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //task 1
        String words [] = {"A", "B", "C", "D", "F", "G", "H", "I", "J", "K", "A", "A", "J", "D", "D", "D", "F", "B", "B", "B",};
        System.out.println("task1");
        System.out.println(changeArray(words));

        //task2
        System.out.println("\ntask2");
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("22-00-00", "Петров");
        phoneBook.add("22-00-11", "Сидоров");
        phoneBook.add("22-00-22", "Петров");
        phoneBook.add("22-00-33", "Иванов");
        phoneBook.add("22-00-44", "Петров");
        phoneBook.add("22-00-55", "Леонтьев");

        phoneBook.get("Петров");
    }


    private static HashMap<String, Integer> changeArray(String[] str){

        Map<String, Integer> hm = new HashMap<>();

        for (String word: str) {
            if(hm.containsKey(word)) {
                hm.put(word, hm.get(word) + 1);
            }else {
                hm.put(word, 1);
            }
        }

        return (HashMap<String, Integer>) hm;
    }
}
