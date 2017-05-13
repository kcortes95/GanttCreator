import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {


		System.out.println("*******************");
		System.out.println("*  Gantt Creator  *");
		System.out.println("*******************\n\n");

		// First parse input or retrieve demo data created on some method in
		// this class
//		Collection<Process> processes = Input.fileReader("sample.txt");

		Queue<Job> p1Jobs = (new LinkedList<>());
		p1Jobs.add((new Job(Job.Type.CPU, 2)));
		p1Jobs.add((new Job(Job.Type.IO, 3)));
		p1Jobs.add((new Job(Job.Type.CPU, 3)));
		p1Jobs.add((new Job(Job.Type.IO, 1)));
		p1Jobs.add((new Job(Job.Type.CPU, 2)));

		Queue<Job> p2Jobs = (new LinkedList<>());
		p2Jobs.add((new Job(Job.Type.CPU, 3)));
		p2Jobs.add((new Job(Job.Type.IO, 1)));
		p2Jobs.add((new Job(Job.Type.CPU, 1)));
		p2Jobs.add((new Job(Job.Type.IO, 2)));
		p2Jobs.add((new Job(Job.Type.CPU, 1)));
		p2Jobs.add((new Job(Job.Type.IO, 1)));
		p2Jobs.add((new Job(Job.Type.CPU, 1)));

		Process p1 = new Process(1, 0, p1Jobs);
		Process p2 = new Process(2, 3, p2Jobs);



		// Second create all Resources; cpu, io, etc
		Resource core = new Core(1);
		Resource io = new IO(1);





		// Third create TaskManager, assign resources and pass all processes.
		TaskManager mng = new TaskManager();

		// Fourth, start clock iterations until TaskManager signals halt
		Boolean finished = false;
		int clock = 0;

		while (!finished) {

			motion(1);
			//do
			System.out.println("Clock Cycle " + (clock++));
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
