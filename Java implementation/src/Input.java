//import java.io.File;
//import java.util.*;
//
//import javax.management.RuntimeErrorException;
//
//public class Input {
//
//    public static Collection<Process> fileReader(String filepath) {
//
//    	/*
//    	//Los valores a insertarse son los siguientes
//    	values[0] -> processAlg
//    	values[1] -> ultAlg
//    	values[2] -> noCores
//    	values[3] -> noProcess
//    	values[4] -> totalThreads //puede que no sea necesario
//		*/
//    	Integer[] values = new Integer[5];
//    	HashMap<Integer, Process> processMap = new HashMap<>();
//
//        try {
//
//            File file = new File(filepath);
//            Scanner input = new Scanner(file);
//            int lineCounter = 1;
//
//            while (input.hasNextLine()) {
//
//            	String line = input.nextLine();
//
//            	if(lineCounter >= 1 && lineCounter <= 5){
//            		values[lineCounter - 1] = lineParser(line);
//            	}else{
//            		lineParser(line, processMap);
//            	}
//
//            	lineCounter++;
//            }
//
//            input.close();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return processMap.values();
//    }
//
//    private static int lineParser(String line){
//    	return Integer.parseInt(line);
//    }
//
//    private static void lineParser(String line, HashMap<Integer, Process> processMap ){
//    	String[] splittedLine = line.split(" ");
//    	int nro_proc = Integer.parseInt(splittedLine[0]);
//    	int nro_hilo = Integer.parseInt(splittedLine[1]);
//    	int ult_o_klt = Integer.parseInt(splittedLine[2]);
//    	int arrival_time = Integer.parseInt(splittedLine[3]);
//    	int tot_cols = Integer.parseInt(splittedLine[4]);
//
//    	Integer[] cpu_io = new Integer[tot_cols];
//
//    	Stack<Job> jobs = new Stack<Job>();
//
//    	for(int i = 5; i < 5 + tot_cols; i++){
//    		// (i-5) % 2 entonces es ULT, sino es KLT.
//    		//Esto quiere decir que, en el pensarlo como el array de splittedLine, en las
//    		//posiciones pares tendriamos los valores de CPU y en las impares los valores de IO
//    		Job j = new Job((i-5)%2, Integer.parseInt(splittedLine[i]));
//    		jobs.push(j);
//    	}
//
//    	Thread t = new Thread(ult_o_klt, jobs);
//
//    	if(!processMap.containsKey(nro_proc)){
//    		ThreadLibrary lib = new FIFOThreadLibrary();
//    		lib.addThread(t);
//    		Process proc = new Process(nro_proc, arrival_time, lib);
//			processMap.put(nro_proc, proc);
//    	}else{
//
//    		Process proc = processMap.get(nro_proc);
//
//    		if(arrival_time != proc.getArrivalTime()){
//    			Error e = new Error("Ha ocurrido un error en la lectura del archivo. Incompatibilidad de tiempos al arribar el proceso " + nro_proc);
//    			throw new RuntimeErrorException(e);
//    		}
//
//    		proc.addThread(t);
//    	}
//
//    }
//
//}