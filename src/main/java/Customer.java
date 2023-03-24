import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Customer {
    private String number;
    private String tariff;
    private float totalCost;
    private int remainingMinutes;

    private ArrayList<Call> calls = new ArrayList<>();

    public Customer() { }

    public Customer(String number, String tariff, String timeStartCall, String timeEndCall, String dir) {
        this.number = number;
        this.tariff = tariff;
        switch (this.tariff) {
            case ("06"):
                this.totalCost = 100;
                this.remainingMinutes = 300;
                break;
            case ("03"):
                this.remainingMinutes = 0;
                break;
            case ("11"):
                this.remainingMinutes = 100;
                break;
        }
        addCall(timeStartCall, timeEndCall, dir);
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public int getRemainingMinutes() {
        return remainingMinutes;
    }

    private float calculateCost(String a, String b, String dir) {
        float cost = 0;
        int callTime = timeDiffCalc(a, b);
        switch (this.tariff) {
            case ("06"):
                if (remainingMinutes > callTime) {
                    remainingMinutes -= callTime;
                } else {
                    callTime -= remainingMinutes;
                    cost = callTime;
                }
                break;
            case ("03"):
                cost = (float) (callTime * 1.5);
                break;
            case ("11"):
                if (dir.equals("02")) {
                    cost = 0;
                } else if (dir.equals("01")) {
                    if (remainingMinutes > callTime) {
                        remainingMinutes -= callTime;
                        cost = (float) (callTime*0.5);
                    } else {
                        callTime -= remainingMinutes;
                        cost = (float)((float)(remainingMinutes)*0.5+(float)(callTime)*1.5);
                    }
                }
                break;
        }
        this.totalCost+=cost;
        return cost;
    }

    public void addCall(String start, String end, String dir) {
        float cost = calculateCost(start, end, dir);
        int duration = timeDiffCalc(start, end);
        Call call = new Call(start, end, dir, duration, cost);
        this.calls.add(call);
    }

    private int timeDiffCalc(String start, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        LocalDateTime startCall = LocalDateTime.parse(start, formatter);
        LocalDateTime endCall = LocalDateTime.parse(end, formatter);

        Duration duration = Duration.between(startCall, endCall);
        long diffInSeconds = duration.getSeconds();

        int inMinutes = (int)(diffInSeconds/60);
        int seconds = (int)(diffInSeconds%60);
        if (seconds > 0){
            inMinutes++;
        }
        return inMinutes;
    }

    public void printReport() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("reports/"+number+".txt"));
        bw.write("tariff index: "+tariff+"\n");
        bw.write("-".repeat(87)+"\n");
        bw.write("Report for phone number "+number+": "+"\n");
        bw.write("-".repeat(87)+"\n");
        bw.write(String.format("%1s %5s %2s %15s %7s %15s %7s %10s %2s %7s %2s\n",
                "|","Call Type","|","Start Time","|","End Time","|","Duration","|","Cost","|"));
        bw.write("-".repeat(87)+"\n");
        for (Call call:calls) {
            bw.write(call.toString()+"\n");
        }
        bw.write("-".repeat(87)+"\n");
        bw.write(String.format("%1s %57s %2s %21s %2s\n","|", "Total cost:","|", totalCost+" rubles","|"));
        bw.write("-".repeat(87)+"\n");
        bw.close();
    }


}
