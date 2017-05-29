import java.util.PriorityQueue;

public class Klt implements Executable {

    private String id;
    protected AlgorithmComparator aCmp;
    private PriorityQueue<Ult> ultQueue;
    private Ult ult;
    private Integer executionTime;

    public Klt(String id, PriorityQueue<Ult> ults, AlgorithmComparator aCmp) {
        this.id = id;
        this.ultQueue = ults;
        this.ult = this.ultQueue.poll();
        this.executionTime = 0;
        this.aCmp = aCmp;
    }

    public String getId() {
        return id;
    }

    public Boolean update(Integer clock) {
        if (this.ult == null)
            return false;

        this.executionTime++;
        return this.ult.update(clock);
    }

    public Boolean finished() {
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
        if ( !this.ultQueue.isEmpty() && this.ultQueue.comparator().compare(this.ult, this.ultQueue.peek()) > 0 && this.ult.nextJobType() == Job.Type.CPU ) {
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
        else {
            if (this.aCmp.isExpulsive() || this.ult.getExecutionTime() == 0) {
                if (this.aCmp.getCmp().compare(this.ult, ult) > 0) {
                    this.ultQueue.add(this.ult);
                    this.ult = ult;
                } else {
                    this.ultQueue.add(ult);
                }
            } else
                this.ultQueue.add(ult);
        }
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

    public Integer remainingCpuClocks(){
        int counter = 0;
        if (this.ult != null)
            counter += this.ult.remainingCpuClocks();
        for( Ult each : ultQueue){
            counter += each.remainingCpuClocks();
        }
        return counter;
    }

    public Integer totalRanCpuClocks(){
        int counter = 0;
        if (this.ult != null)
            counter += this.ult.totalRanCpuClocks();
        for( Ult each : ultQueue){
            counter += each.totalRanCpuClocks();
        }
        return counter;
    }

    public Integer getLastClock() {
        Ult latest = this.ult;
        if (latest == null)
            ultQueue.peek();
        for( Ult each : ultQueue){
            if (each.getLastClock() > latest.getLastClock())
                latest = each;
        }
        if (latest != null)
            return latest.getLastClock();
        else
            return 0;
    }
}
