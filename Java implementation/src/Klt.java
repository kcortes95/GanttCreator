import java.util.PriorityQueue;

public class Klt {

    private String id;
    private PriorityQueue<Ult> ultQueue;
    private Ult ult;
    private Integer executionTime;

    public Klt(String id, PriorityQueue<Ult> ults) {
        this.id = id;
        this.ultQueue = ults;
        this.ult = this.ultQueue.poll();
        this.executionTime = 0;
    }

    public String getId() {
        return id;
    }

    public Boolean update() {
        if (this.ult == null)
            return false;

        this.executionTime++;
        return this.ult.update();
    }

    public boolean finished() {
        if (this.ult == null) return true;

        if (this.ult.finished()) {
            this.ult.setExecutionTime(0);
            if (this.ult.nextJobType() == null) {
                this.ult = this.ultQueue.poll();
                if (this.ult != null) return false;
            }
            return true;
        }

        // If something to ran but expel
        if ( !this.ultQueue.isEmpty() && this.ultQueue.comparator().compare(this.ult, this.ultQueue.peek()) <= 0 && this.ult.nextJobType() == Job.Type.CPU ) {
            this.ult.setExecutionTime(0);
            this.ultQueue.add(this.ult);
            this.ult = this.ultQueue.poll();
            this.ult.setExecutionTime(0);
        }

        return false;
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

    public Integer getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
    }
}
