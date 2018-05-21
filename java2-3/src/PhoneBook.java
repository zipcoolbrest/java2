import java.util.*;

public class PhoneBook {
    private Map<String, String> pb = new HashMap<>();

    public void add(String phone, String name){
        pb.put(phone, name);
    }

    public void get(String name){
        for (Map.Entry<String, String> entry : pb.entrySet()) {
            if (entry.getValue() == name) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
    }

}
