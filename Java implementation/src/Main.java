import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {

		System.out.println("*******************");
		System.out.println("*  Gantt Creator  *");
		System.out.println("*******************\n\n");

		// First parse input or retrieve demo data created on some method in
		// this class
		// Collection<Process> processes = Input.fileReader("sample.txt");

		Queue<Job> p1Jobs = (new LinkedList<>());
		p1Jobs.add((new Job(Job.Type.CPU, 2)));
		p1Jobs.add((new Job(Job.Type.IO, 3)));
		p1Jobs.add((new Job(Job.Type.CPU, 3)));
		p1Jobs.add((new Job(Job.Type.IO, 1)));
		p1Jobs.add((new Job(Job.Type.CPU, 4)));


		Queue<Job> p2Jobs = (new LinkedList<>());
		p2Jobs.add((new Job(Job.Type.CPU, 3)));
		p2Jobs.add((new Job(Job.Type.IO, 1)));
		p2Jobs.add((new Job(Job.Type.CPU, 1)));
		p2Jobs.add((new Job(Job.Type.IO, 2)));
		p2Jobs.add((new Job(Job.Type.CPU, 1)));
		p2Jobs.add((new Job(Job.Type.IO, 1)));
		p2Jobs.add((new Job(Job.Type.CPU, 3)));

//		Process p1 = new Process(1, 0, p1Jobs);
//		Process p2 = new Process(2, 3, p2Jobs);

		Map<Integer, List<Ult>> readyMap = new HashMap<>();
		
		List<Process> l1 = new LinkedList<>();
//		l1.add(p1);
//		readyMap.put(0,l1);
		
		List<Process> l2 = new LinkedList<>();
//		l2.add(p2);
//		readyMap.put(3,l2);
		
		
		// Second create all Resources; cpu, io, etc
		Core core = new Core(1);
		IO io = new IO(1);
		CoreManager cm = new CoreManager();
		IOManager iom = new IOManager();
		cm.add(1, core);
		iom.add(1, io);
		
		// Fourth, start clock iterations until TaskManager signals halt
		Boolean finished = false;
		Boolean newUltAssigned = false;
		int clock = 0;

		Collection<IO> rio = iom.getValues();
		Collection<Core> rc = cm.getValues();
		Collection<Resource> resources = new LinkedList<>();
		resources.addAll(rio);
		resources.addAll(rc);

		while (!finished) {
			finished = true;
			System.out.println("*** TIEMPO: " + clock + " ***");

			if(readyMap.containsKey(clock)){
				for(Ult ult : readyMap.get(clock)){
					System.out.println("Entra en ready ---> " + ult);

					//iterar por todos los resources preguntando por ult.getProcessId()
					newUltAssigned = false;
					for (Resource each: resources) {
						if (each.assign(ult)) {
							newUltAssigned = true;
							break;
						}
					}
					if (!newUltAssigned) {
						PriorityQueue<Klt> auxKltQueue = new PriorityQueue<>();
						PriorityQueue<Ult> auxUltQueue = new PriorityQueue<>();
						auxUltQueue.add(ult);
						Klt auxKlt = new Klt(ult.getKltId(), auxUltQueue);
						auxKltQueue.add(auxKlt);
						Process auxProcess = new Process(ult.getProcessId(), auxKltQueue);
						cm.newProcess(auxProcess);
					}
				}
			} //cierre de ready map

			for (Resource resource : resources) {
				if(resource.update() == true)
					finished = false;
				Process p = resource.finished();
				
				/*
				if(p != null && p.nextJobType() != null) {
					switch (p.nextJobType()) {
					case CPU:
						cm.assign(p, p.getDesignatedCore().getId());
						break;

					case IO:
						iom.assign(p, 1);
						break;
					}

				}
				*/
				
				//tendriamos que trabajar con el nextTypeJob pero en la cola de jobs del ULT
				if(p != null){
					if( p.getKlt() != null && p.getKlt().getUlt() != null){
						Ult ult = p.getKlt().getUlt();
						if(ult.nextJobType() != null){
							switch (ult.nextJobType()) {
							case CPU:
								cm.assign(p, p.getDesignatedCore().getId());
								break;

							case IO:
								iom.assign(p, 1);
								break;
							}							
						}
					}
				}


			}
			cm.flush();
			iom.flush();

			clock++;
			System.out.println("*********");
		}


	}
	

}
