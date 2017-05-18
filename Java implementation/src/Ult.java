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
        this.jobs.peek().decrementClock();
        return true;
    }

}