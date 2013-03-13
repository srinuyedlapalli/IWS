package util;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class GlobalFunctions {

	public static String reportId;
	
	public static String id()
	{
		final String DATE_FORMAT_NOW="ddMMMHHmmss";
		SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT_NOW);
		Calendar cal=Calendar.getInstance();
		String tid=sdf.format(cal.getTime());
		reportId=tid;
		return tid;
	}
}
