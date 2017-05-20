import java.util.*;

public class Process {

    private String id;
    private Core designatedCore;
    private PriorityQueue<Klt> kltQueue;
    private Klt klt;


    public Process(String id, PriorityQueue<Klt> klts) {
        this.id = id;
        this.designatedCore = null;
        this.kltQueue = klts;
        this.klt = this.kltQueue.poll();
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
        if (this.klt == null)
            return false;
        else
            return this.klt.update();
    }

    public boolean finished() {

        if (this.klt != null && this.klt.finished()) {
            this.klt = this.kltQueue.poll();
        }

        return (this.klt == null  && this.kltQueue.isEmpty());
    }

    public void assign(PriorityQueue<Ult> ultQ) {

        Ult ult = ultQ.peek();

        String idKlt = ult.getKltId();

        if(this.klt.getId().equals(idKlt)) {
            this.klt.assign(ult);
            return;
        }

        for(Klt each:this.kltQueue) {
            if(each.getId().equals(idKlt)) {
                each.assign(ult);
                return;
            }
        }

        //crear el klt, assign el ult y enconlarlo

    }



    @Override
    public String toString() {return "Running="+this.id+"-"+this.klt;}
    
    public Klt getKlt() {
		return klt;
	}
}
