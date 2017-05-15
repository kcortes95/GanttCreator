import java.util.*;

public class Process {

    private Integer id;
    private Core designatedCore;
    private PriorityQueue<Klt> klts;
//    private Queue<Job> jobs;
    

//    private HashMap<Integer, Thread> threads = new HashMap<>();

    public Process(Integer id, PriorityQueue<Klt> klts) {
        this.id = id;
        this.designatedCore = null;
        this.klts = klts;
    }

    public Process(Integer id, Integer arrivalTime, Queue<Job> jobs) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.designatedCore = null;
        this.jobs = jobs;
    }

    public Integer getId() {
        return id;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public Core getDesignatedCore() {
        return designatedCore;
    }

    public void setDesignatedCore(Core designatedCore) {
        this.designatedCore = designatedCore;
    }

    public boolean update() {
        if (this.jobs.isEmpty()) return false;
        this.jobs.peek().decrementClock();
        return true;
    }

    public boolean finished() {
        return (this.jobs.peek().getClock() == 0);
    }

    public void pollJob() {
        this.jobs.poll();
    }
    
    public Job.Type nextJobType(){
        if (this.jobs.isEmpty()) return null;

    	return jobs.peek().getType();
    }

    @Override
    public String toString() {
        return "Process-id="+this.id+"-timeLeftJob="+this.jobs.peek().getClock()+this.jobs.peek().getType();
    }
}
