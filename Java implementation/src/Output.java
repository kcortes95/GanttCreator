import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Output {

	private static Output instance = null;
	private static String fileName;
	PrintWriter pw = null;

	public static Output getInstance() {
		if (instance == null) {
			instance = new Output();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ss");
			String allDate = cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.MONTH + 1) + "-" + cal.get(Calendar.YEAR);
			fileName = "gantt-" + sdf.format(cal.getTime()).toString() + "-" + allDate;
		}

		return instance;

	}

	public String getFileName(){
		return fileName;
	}
	
	public void write(String str){
		if(pw ==  null){
			try {
				pw = new PrintWriter(fileName+".txt","UTF-8");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		pw.println(str);
	}
	
	public void close(){
		pw.close();
	}
	
	

}