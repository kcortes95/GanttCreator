import java.util.PriorityQueue;

public class Klt {

    private String id;
    private PriorityQueue<Ult> ultQueue;
    private Ult ult;

    public Klt(String id, PriorityQueue<Ult> ults) {
        this.id = id;
        this.ultQueue = ults;
        this.ult = this.ultQueue.poll();
    }

    public String getId() {
        return id;
    }

    public Boolean update() {
        if (this.ult == null)
            return false;

        return this.ult.update();
    }

    public boolean finished() {

        if (this.ult != null && this.ult.finished()) {
            this.ult = this.ultQueue.poll();
        }

        return (this.ult == null  && this.ultQueue.isEmpty());
    }

    public void assign(Ult ult) {
        if (this.ult == null)
            this.ult = ult;
        else
            this.ultQueue.add(ult);
    }

    @Override
    public String toString() {return this.id+"-"+this.ult;}
    
    public Ult getUlt() {
		return ult;
	}
}
