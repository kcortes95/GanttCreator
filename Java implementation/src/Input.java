import java.io.File;
import java.util.Scanner;


public class Input {
	
    public static void fileReader(String filepath) {
    	
    	/*
    	values[0] -> processAlg = 0;
    	values[1] -> ultAlg = 0;
    	values[2] -> noCores = 1; //1 by default
    	values[3] -> noProcess = 0;
    	values[4] -> totalThreads = 0; //puede que no sea necesario
		*/
    	Integer[] values = new Integer[5];
    	
        try {
            
            File file = new File(filepath);
            Scanner input = new Scanner(file);
            int lineCounter = 1;
            
            while (input.hasNextLine()) {
            	
            	String line = input.nextLine();
            	
            	if(lineCounter >= 1 && lineCounter <= 5){
            		values[lineCounter - 1] = lineParser(line);
            	}else{
            		//System.out.println("Leo la linea completa");
            	}
            	
            	lineCounter++;
            }
                        
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static int lineParser(String line){
    	return Integer.parseInt(line);
    }

}
