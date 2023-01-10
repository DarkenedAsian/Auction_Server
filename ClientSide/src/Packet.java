import java.io.Serializable;
import java.util.Map;

public class Packet implements Serializable {
    Map<String, Item> itemSet;
    String username;
    String test;
    public Packet(){

    }
}
