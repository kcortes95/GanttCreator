import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Process {

    private Integer id;
    private Integer arrivalTime;
    private Core designatedCore;
    //Si, la libreria es el que maneja los algoritmos, esta bien...
    //Faltaria agregar los hilos que componen al proceso! (lo agrego mas abajo)
    private ThreadLibrary library;
    private Queue<Job> jobs;
    
    /**
     * Esto deberia ser algo asi...
     */
    private HashMap<Integer, Thread> threads = new HashMap<>();

    public Process(Integer id, Integer arrivalTime, ThreadLibrary library) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.designatedCore = null;
//        this.library = library;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
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
    
//    public ThreadLibrary getLibrary() {
//        return library;
//    }
//
//    public void setLibrary(ThreadLibrary library) {
//        this.library = library;
//    }
//
//    public void addThread(Thread thread) {
//        this.library.addThread(thread);
//    }

    @Override
    public String toString() {
        return "Process-id="+this.id+"-timeLeftJob="+this.jobs.peek().getClock()+this.jobs.peek().getType();
    }
}
