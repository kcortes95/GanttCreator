import java.util.Queue;

public class Ult {

    /**
     * Manejamos Jobs como una pila a la cual le devolvemos las tareas si no estan terminadas
     */
    private Queue<Job> jobs;
    private Integer arrivalTime;
    private String id;
    private String kltId;
    private String processId;
    
    /**
     * Esta variable la voy a utilizar luego para calcular el HRRN.
     * Con el HRRN debo llevar control de cuanto tiempo corrio el ULT.
     * Tengo luego que en el update sumarle 1 cada vez que se hace un decrement clock 
     * en CPU!!!
     */
    private Integer ranInCore = 0;

    Ult(Queue<Job> jobs) {this.jobs = jobs;}

    public Integer getArrivalTime() {return arrivalTime;}

    public String getId() {return id;}

    public String getKltId() {return kltId;}

    public String getProcessId() {return processId;}

    @Override
    public String toString() {
        return this.id+"-"+this.jobs.peek();
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
        if (this.jobs.isEmpty()) return true;

        if (this.jobs.peek().getClock() == 0)
            this.jobs.poll();
        return jobs.isEmpty();
    }

    public Boolean update() {
        if (this.jobs.isEmpty()) return false;
        
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
    
    /**
     * Calcula cuanto falta para que dicho proceso termine (contando unicamente
     * los tiempos de CPU, no los de IO)
     * Esto se hace para luego implementar los distintos algoritmos expulsivos
     * y no expulsivos
     * @return
     */
    public int jobsEstimator(){
    	int counter = 0;
    	for( Job each : jobs ){
    		if(each.getType().equals(Job.Type.CPU)){
    			counter += each.getClock();
    		}
    	}
    	return counter;
    }
    
    //MIRAR TABLA DE LOS ALGORITMOS DEL LIBRO!

}