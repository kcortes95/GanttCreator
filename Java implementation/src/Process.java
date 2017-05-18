import java.util.*;

public class Process {

    private String id;
    private Core designatedCore;
    private PriorityQueue<Klt> kltQueue;
    private Klt klt;
    
//    private HashMap<Integer, Ult> threads = new HashMap<>();

    public Process(String id, PriorityQueue<Klt> klts) {
        this.id = id;
        this.designatedCore = null;
        this.kltQueue = klts;
    }

    public String getId() {
        return id;
    }

    public Core getDesignatedCore() {
        return designatedCore;
    }

    public void setDesignatedCore(Core designatedCore) {
        this.designatedCore = designatedCore;
    }

    public Boolean update() {
        return this.klt.update();
    }

    public boolean finished() {
//        return (this.jobs.peek().getClock() == 0);
        return (this.klt == null  && this.kltQueue.isEmpty());
    }

    public void assign(Ult ult) {
        String idKlt = ult.getKltId();
        if(this.klt.getId().equals(idKlt))
            this.klt.assign(ult);
        else {
            for(Klt each:this.kltQueue) {
                if(each.getId().equals(idKlt)) {
                    each.assign(ult);
                    break;
                }
            }
        }
    }
    
//    public Job.Type nextJobType(){
//        if (this.jobs.isEmpty()) return null;
//
//    	return jobs.peek().getType();
//    }

    @Override
    public String toString() {return "Running="+this.id+"-"+this.klt;}
    
    public Klt getKlt() {
		return klt;
	}
}
