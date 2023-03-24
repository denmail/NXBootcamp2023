import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        HashMap<String, Customer> customers = new HashMap<>();
        new CdrHandler("src/main/resources/cdr.txt", customers);
        for (Customer customer: customers.values()) {
            customer.printReport();
        }
    }


}
