import java.util.*;

public class Main {

	public static void main(String[] args) {

		System.out.println("*******************");
		System.out.println("*  Gantt Creator  *");
		System.out.println("*******************\n\n");

		// First parse input or retrieve demo data created on some method in
		// this class
		// Collection<Process> processes = Input.fileReader("sample.txt");

		Map<Integer, List<Ult>> readyMap = new HashMap<>();
		List<Ult> ary0 = new LinkedList<>();
		List<Ult> ary1 = new LinkedList<>();
		List<Ult> ary2 = new LinkedList<>();
		
		int[] ar1 = {3,3,5};
		Ult u1 = new Ult(0, "1ULT", ar1, "1KLT", "1P");

		int[] ar2 = {1,1,2};
		Ult u2 = new Ult(0, "2ULT", ar2, "1KLT", "1P");
		
		ary0.add(u1);
		ary0.add(u2);
		
		int[] ar3 = {2,1,3,1,1};
		Ult u3 = new Ult(1, "3ULT", ar3, "2KLT", "1P");
		
		int[] ar4 = {3,2,4,1,1};
		Ult u4 = new Ult(1, "4ULT", ar4, "2KLT", "1P");
		
		ary1.add(u3);
		ary1.add(u4);
		
		int[] ar5 = {1,1,3};
		Ult u5 = new Ult(2, "5ULT", ar5, "3KLT", "2P");
		
		ary2.add(u5);
		
		readyMap.put(0, ary0);
		readyMap.put(1, ary1);
		readyMap.put(2, ary2);	
		
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
					PriorityQueue<Ult> tempUltQueue = new PriorityQueue<>();
					tempUltQueue.add(ult);
					
					for (Resource each: resources) {
						if (each.assign(tempUltQueue)) {
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
