import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConvert{
	public static void main(String args[]){
		int timestamp = Integer.parseInt( args[0] );
		String dateAsText = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                          .format(new Date(timestamp * 1000L));
		System.out.println("DATE = " + dateAsText + "\n");
	}
}
