import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Input {

	public Map<Integer, List<Ult>> getMap(String file_path) {

		int algorithm_number = 0;
		int algorithm_thread_lib = 0;
		int tot_cores = 0;

		Map<Integer, List<Ult>> readyMap = new HashMap<>();

		try {
			File file = new File(file_path);

			Scanner input = new Scanner(file);
			int count = 0;

			while (input.hasNextLine()) {

				if (count == 0)
					algorithm_number = Integer.parseInt(input.nextLine());
				if (count == 1)
					algorithm_thread_lib = Integer.parseInt(input.nextLine());
				if (count == 2)
					tot_cores = Integer.parseInt(input.nextLine());
				if (count >= 3)
					parseLine(input.nextLine(), readyMap);
				count++;

			}

			input.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		//System.out.println(algorithm_number);
		//System.out.println(algorithm_thread_lib);
		//System.out.println(tot_cores);
		
		printMap(readyMap);
		
		return readyMap;

	}

	private static void parseLine(String line, Map<Integer, List<Ult>> readyMap) {
		String pid = "";
		String kltid = "";
		String ultid = "";
		int arr_time = 0;
		int tot_jobs = 0;

		String[] l = line.split(" ");
		pid = l[0];
		kltid = l[1];
		ultid = l[2];
		arr_time = Integer.parseInt(l[3]);
		tot_jobs = Integer.parseInt(l[4]);

		int[] jobs = new int[tot_jobs];
		
		for(int i = 0 ; i < tot_jobs ; i++){
			jobs[i] = Integer.parseInt(l[i+5]);
		}
		
		Ult u = new Ult(arr_time, ultid, jobs, kltid, pid);
		
		if(!readyMap.containsKey(arr_time)){
			List<Ult> ults = new LinkedList<>();
			readyMap.put(arr_time, ults);
		}
		
		readyMap.get(arr_time).add(u);
		
	}
	
	private static void printMap(Map<Integer, List<Ult>> readyMap){
		for(Integer each : readyMap.keySet()){
			//System.out.println(readyMap.get(each).toString());
		}
	}

}
