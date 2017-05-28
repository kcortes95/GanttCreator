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
        Comparator<Process> coreComparator = Comparators.processComparator(Comparators.Type.FIFO);
		Comparator<Klt> kltComparator = Comparators.kltComparator(Comparators.Type.FIFO,2);
		Comparator<Ult> ultComparator = Comparators.ultComparator(Comparators.Type.RR, 5);
		Core core1 = new Core(1, coreComparator);
//		Core core2 = new Core(2, coreComparator);
		IO io = new IO(1);
		CoreManager cm = new CoreManager();
		IOManager iom = new IOManager();
		cm.add(1, core1);
//		cm.add(2, core2);
		iom.add(1, io);

		
		// Fourth, start clock iterations until TaskManager signals halt
		Boolean finished = false;
		Boolean newUltAssigned;
		int clock = 0;

		Collection<IO> rio = iom.getValues();
		Collection<Core> rc = cm.getValues();
		Collection<Resource> resources = new LinkedList<>();
		resources.addAll(rio);
		resources.addAll(rc);
		
		while (!finished) {
			
			finished = true;

			if(readyMap.containsKey(clock)){
				for(Ult ult : readyMap.get(clock)){
					//System.out.println("Entra en ready ---> " + ult);

					//iterar por todos los resources preguntando por ult.getProcessId()
					newUltAssigned = false;
					PriorityQueue<Ult> tempUltQueue = new PriorityQueue<>(10, ultComparator);
					tempUltQueue.add(ult);
					
					for (Resource each: resources) {
						if (each.assign(tempUltQueue)) {
							newUltAssigned = true;
							break;
						}
					}
					if (!newUltAssigned) {
						PriorityQueue<Klt> auxKltQueue = new PriorityQueue<>(10, kltComparator);
						PriorityQueue<Ult> auxUltQueue = new PriorityQueue<>(10, ultComparator);
						auxUltQueue.add(ult);
						Klt auxKlt = new Klt(ult.getKltId(), auxUltQueue);
						auxKltQueue.add(auxKlt);
						Process auxProcess = new Process(ult.getProcessId(), auxKltQueue);
						cm.newProcess(auxProcess);
					}
				}
			} //cierre de ready map
			
			String toOutput = "";

			for (Resource resource : resources) {
				
				String resID = resource.getType().toString() + resource.getId().toString();
				toOutput += resID + ": " + resource.getRunning() + "|";
				
				if(resource.update())
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
			
			Output.getInstance().write("TIEMPO: " + clock + "=" + toOutput);
			cm.flush();
			iom.flush();

			clock++;
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
