import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Call {
    private String timeStart;
    private String timeEnd;
    private String dir;
    private int duration;
    private float cost;

    public Call(String timeStart, String timeEnd, String dir, int duration, float cost) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dir = dir;
        this.duration = duration;
        this.cost = cost;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        LocalDateTime startCall = LocalDateTime.parse(this.timeStart, formatter);
        LocalDateTime endCall = LocalDateTime.parse(this.timeEnd, formatter);
        Duration duration = Duration.between(startCall, endCall);
        long inSeconds = duration.toSeconds();
        int hours = (int)(inSeconds/3600);
        String strHours = hours>9?""+hours:"0"+hours;
        int seconds = (int)(inSeconds%60);
        String strSeconds = seconds>9?""+seconds:"0"+seconds;
        int minutes = (int)((inSeconds/60)%60);
        String strMinutes = minutes>9?""+minutes:"0"+minutes;
        String dur = strHours+":"+strMinutes+":"+strSeconds;

        String result = String.format("%1s %6s %5s %20s %2s %20s %2s %10s %2s %7s %2s",
                "|",dir,"|",startCall,"|",endCall,"|",dur,"|",cost,"|");
        return result;
    }
}
