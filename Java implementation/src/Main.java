import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {


		System.out.println("*******************");
		System.out.println("*  Gantt Creator  *");
		System.out.println("*******************\n\n");

		// First parse input or retrieve demo data created on some method in
		// this class
		Collection<Process> processes = Input.fileReader("sample.txt");

		// Second create all Resources; cpu, io, etc
		Resource core = new Core(1);
		Resource io = new IO(1);





		// Third create TaskManager, assign resources and pass all processes.
		TaskManager mng = new TaskManager();

		// Fourth, start clock iterations until TaskManager signals halt
		Boolean finished = false;
		int init_clock = 0;

		while (!finished) {

			motion(1);
			//do
			System.out.println("Clock Cycle " + init_clock);
			init_clock++;
			finished = mng.updata();
		}


	}
	
	private static void motion(int time){
		try {
			TimeUnit.SECONDS.sleep(time);
		} catch (Exception e) {
			System.out.println("Error on timed sleep");
		}
	}

}
