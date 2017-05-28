import java.util.LinkedList;
import java.util.Queue;

public class Ult implements Executable{

    /**
     * Manejamos Jobs como una pila a la cual le devolvemos las tareas si no estan terminadas
     */
    private Queue<Job> jobs;
    private Integer arrivalTime;
    private String id;
    private String kltId;
    private String processId;
    private Integer lastClock;

    /**
     * Current CPU executions on this rafaga
     */
    private Integer executionTime;

    /**
     * Total CPU executions
     */
    private Integer ranInCore = 0;

    Ult(int arrivalTime, String id, Queue<Job> jobs, String kltId, String processId) {
    	this.jobs = jobs;
    	this.id = id;
    	this.arrivalTime = arrivalTime;
    	this.kltId = kltId;
    	this.processId = processId;
        this.executionTime = 0;
    }
    
    //Esto vuelaaaaaa!!!
    Ult(int arrivalTime, String id, int[] jobsArray, String kltId, String processId){
    	this.id = id;
    	this.jobs = new LinkedList<>();
    	for(int i = 0; i < jobsArray.length; i++){
    		Job.Type tipo = Job.Type.CPU;
    		if(i%2==1)
    			tipo = Job.Type.IO;
    		
    		Job j = new Job(tipo, jobsArray[i]);
    		jobs.add(j);
    	}
    	this.arrivalTime = arrivalTime;
    	this.kltId = kltId;
    	this.processId = processId;
        this.executionTime = 0;
    }

    public String getId() {return id;}

    public String getKltId() {return kltId;}

    public String getProcessId() {return processId;}

    @Override
    public String toString() {
        return this.id;
    }

//    public boolean isFinishedCurrentJob(){
//        if (jobs.isEmpty()) return true;
//        if (jobs.peek().getClock() == 0) {
//            jobs.remove(0);
//            return true;
//        }
//        return false;
//    }

    public boolean finished(){
        if (this.jobs.peek().getClock() == 0) {
            this.jobs.poll();
            return true;
        }

        return false;
    }

    public Boolean update(Integer clock) {
        this.lastClock = clock;
        if (this.jobs.isEmpty()) return false;

        this.executionTime ++;
        Job actualJob = this.jobs.peek();
        actualJob.decrementClock();
        
        if(actualJob.getType().equals(Job.Type.CPU)){
        	this.ranInCore++;
        }
        
        return true;
    }
    
    public Job.Type nextJobType(){
        if (this.jobs.isEmpty()) return null;

    	return jobs.peek().getType();
    	
    }

    public Integer remainingCpuClocks(){
    	int counter = 0;
    	for( Job each : jobs ){
    		if(each.getType().equals(Job.Type.CPU)){
    			counter += each.getClock();
    		}
    	}
    	return counter;
    }

    public Integer totalRanCpuClocks(){
        return ranInCore;
    }


    public Integer getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
    }

    public Integer getLastClock() {
        return lastClock;
    }

}