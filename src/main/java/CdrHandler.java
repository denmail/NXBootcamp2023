import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CdrHandler {
    BufferedReader br;

    public CdrHandler(String fileName, HashMap<String, Customer> customers) throws IOException {
        br = new BufferedReader(new FileReader(fileName));
        String str = br.readLine();
        while (str != null) {
            String[] line = str.split(", ");
            String number = line[1];
            String start = line[2];
            String end = line[3];
            String dir = line[0];
            if (!customers.containsKey(number)) {
                String tariff = line[4];
                customers.put(number, new Customer(number, tariff, start, end, dir));

            } else {
                customers.get(number).addCall(start, end, dir);
            }
            str = br.readLine();
        }
    }

    public String[] readLine() throws IOException {
        return br.readLine().split(", ");
    }



}
