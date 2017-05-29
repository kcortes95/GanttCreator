import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Input {
	
	private static Input instance = null;
	
	public static Input getInstance(){
		if(instance==null){
			instance = new Input();
		}
		return instance;
	}
	
	private static int algorithm_number = 0;
	private static int quantum_proc = 0;
	
	private static int algorithm_thread_lib = 0;
	private static int quantum_thread = 0;
	
	private static int tot_cores = 0;
	private static int tot_io = 0;
	
	private static Map<Integer, List<Ult>> readyMap = null;
	
	public Map<Integer, List<Ult>> getMap(String file_path) {

		readyMap = new HashMap<>();

		try {
			File file = new File(file_path);

			Scanner input = new Scanner(file);
			int count = 0;

			while (input.hasNextLine()) {

				if (count == 0){
					String[] value = input.nextLine().split(" ");
					algorithm_number = Integer.parseInt(value[0]);
					quantum_proc = Integer.parseInt(value[1]);
				}
				if (count == 1){
					String[] value = input.nextLine().split(" ");
					algorithm_thread_lib = Integer.parseInt(value[0]);
					quantum_thread = Integer.parseInt(value[1]);
				}
				if (count == 2)
					tot_cores = Integer.parseInt(input.nextLine());
				if (count == 3)
					tot_io = Integer.parseInt(input.nextLine());
				if (count >= 4)
					parseLine(input.nextLine(), readyMap);
				count++;

			}

			input.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

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
	
	public Comparators.Type getProcessAlg(){
		return getAlgFromFile(algorithm_number);
	}
	
	public Comparators.Type getThreadAlg(){
		return getAlgFromFile(algorithm_thread_lib);
	}
	
	public int getProcessQuantum(){
		return quantum_proc;
	}
	
	public int getThreadQuantum(){
		return quantum_thread;
	}
	
	private Comparators.Type getAlgFromFile(int value){
		switch (value) {
		case 0:
			return Comparators.Type.FIFO;
		case 1:
			return Comparators.Type.RR;
		case 2:
			return Comparators.Type.SRT;			
		case 3:
			return Comparators.Type.SPN;			
		case 4:
			return Comparators.Type.HRRN;			
		default:
			return Comparators.Type.FIFO;
		}
	}
	
	public int getCores() {
		return tot_cores;
	}
	
	public int getIO() {
		return tot_io;
	}


}
