import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

	public static void main(String[] args) {

		//System.out.println("*******************");
		//System.out.println("*  Gantt Creator  *");
		//System.out.println("*******************\n\n");

		// First parse input or retrieve demo data created on some method in
		// this class
		// Collection<Process> processes = Input.fileReader("sample.txt");

		Input i = new Input();
        Map<Integer, List<Ult>> readyMap = i.getMap("." + File.separator + "src" + File.separator + "ejemplo.txt");
		
		// Second create all Resources; cpu, io, etc
        AlgorithmComparator coreAlgComparator = Comparators.comparator(Comparators.Type.FIFO, 2);
        AlgorithmComparator kltAlgComparator = Comparators.comparator(Comparators.Type.FIFO, 2);
        AlgorithmComparator ultAlgComparator = Comparators.comparator(Comparators.Type.FIFO, 2);

		Core core1 = new Core(1, coreAlgComparator);
		Core core2 = new Core(2, coreAlgComparator);
		IO io = new IO(1);
		CoreManager cm = new CoreManager();
		IOManager iom = new IOManager();
		cm.add(1, core1);
//		cm.add(2, core2);
		iom.add(1, io);

		
		// Fourth, start clock iterations until TaskManager signals halt
		Boolean finished = false;
		Boolean newUltAssigned;

		Collection<IO> rio = iom.getValues();
		Collection<Core> rc = cm.getValues();
		Collection<Resource> resources = new LinkedList<>();
		resources.addAll(rio);
		resources.addAll(rc);
		
		while (!finished) {
            System.out.println("Clock " + Clock.getInstance().getClock());

            finished = true;

			if(readyMap.containsKey(Clock.getInstance().getClock())){
				for(Ult ult : readyMap.get(Clock.getInstance().getClock())){
					//System.out.println("Entra en ready ---> " + ult);

					//iterar por todos los resources preguntando por ult.getProcessId()
					newUltAssigned = false;
					PriorityQueue<Ult> tempUltQueue = new PriorityQueue<>(10, ultAlgComparator.getCmp());
					tempUltQueue.add(ult);

					for (Resource each: resources) {
						if (each.assign(tempUltQueue, ultAlgComparator)) {
							newUltAssigned = true;
							break;
						}
					}
					if (!newUltAssigned) {
						PriorityQueue<Klt> auxKltQueue = new PriorityQueue<>(10, kltAlgComparator.getCmp());
						PriorityQueue<Ult> auxUltQueue = new PriorityQueue<>(10, ultAlgComparator.getCmp());
						auxUltQueue.add(ult);
						Klt auxKlt = new Klt(ult.getKltId(), auxUltQueue, ultAlgComparator);
						auxKltQueue.add(auxKlt);
						Process auxProcess = new Process(ult.getProcessId(), auxKltQueue, kltAlgComparator);
						cm.newProcess(auxProcess);
					}
				}
			} //cierre de ready map
			
			String toOutput = "";

			for (Resource resource : resources) {
				
				String resID = resource.getType().toString() + resource.getId().toString();
				toOutput += resID + ": " + resource.getRunning() + "|";
				
				if(resource.update(Clock.getInstance().getClock()))
					finished = false;
				Process p = (Process)resource.finished();
				
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
			
			Output.getInstance().write("TIEMPO: " + Clock.getInstance().getClock() + "=" + toOutput);
			cm.flush();
			iom.flush();

            Clock.getInstance().incrementClock();
			//System.out.println("*********");
		}
		
		Output.getInstance().close();
		runUI();


	}
	
	public static void runUI(){
		Runtime rTime = Runtime.getRuntime();
		String url = "index.html";
		File htmlFile = new File(url);
		
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
