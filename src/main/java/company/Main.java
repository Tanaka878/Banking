package company;


import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) {

        //new Test();
       // new InterBank();
       // new TransHistory();
	// write your code here
        new Design();
       // new ChangePin();
        //new Bills();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        LocalTime time = LocalTime.now();
        String t = format.format(Calendar.getInstance().getTime());
        System.out.println(t);


    }
}
