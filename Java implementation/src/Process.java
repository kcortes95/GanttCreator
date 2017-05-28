import java.lang.reflect.*;
import java.util.*;

public class Process implements Executable{

	private String id;
	private Core designatedCore;
    protected AlgorithmComparator aCmp;
	private PriorityQueue<Klt> kltQueue;
	private Klt klt;
	private Integer executionTime;

	public Process(String id, PriorityQueue<Klt> klts, AlgorithmComparator aCmp) {
		this.id = id;
		this.designatedCore = null;
		this.kltQueue = klts;
		this.klt = this.kltQueue.poll();
		this.executionTime = 0;
		this.aCmp = aCmp;
	}

	public Integer getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Integer executionTime) {
		this.executionTime = executionTime;
	}

	public String getId() {
		return id;
	}

	public Core getDesignatedCore() {
		return this.designatedCore;
	}

	public void setDesignatedCore(Core designatedCore) {
		this.designatedCore = designatedCore;
	}

	public Boolean update(Integer clock) {
		if (this.klt == null)
			return false;

		this.executionTime ++;
		return this.klt.update(clock);
	}

	public boolean finished() {
		if (this.klt == null) return true;

		if (this.klt.finished()) {
			this.klt.setExecutionTime(0);
			if (this.klt.getUlt() == null) {
				this.klt = this.kltQueue.poll();
				if (this.klt != null) return false;
			}
			return true;
		}

		// If something to ran but expel
		if ( !this.kltQueue.isEmpty() &&  this.kltQueue.comparator().compare(this.klt, this.kltQueue.peek()) < 0 && this.klt.getUlt().nextJobType() == Job.Type.CPU ) {
			this.klt.setExecutionTime(0);
			this.kltQueue.add(this.klt);
			this.klt = this.kltQueue.poll();
			this.klt.setExecutionTime(0);
		}

		return false;
	}

	public void assign(PriorityQueue<Ult> ultQ, AlgorithmComparator aCmpUlt) {

		Ult ult = ultQ.peek();

		String idKlt = ult.getKltId();

		Boolean done = false;

		if (this.klt.getId().equals(idKlt)) {
			this.klt.assign(ult);
			done = true;
		} else {
            for (Klt each : this.kltQueue) {
                if (each.getId().equals(idKlt)) {
                    each.assign(ult);
                    done = true;
                    break;
                }
            }
        }
        if (!done) {
            Klt kltAux = new Klt(ult.getKltId(), ultQ, aCmpUlt);
            this.kltQueue.add(kltAux);
        }

        if ((this.aCmp.isExpulsive() || this.klt.getExecutionTime() == 0) && !this.kltQueue.isEmpty()) {
            if (this.aCmp.getCmp().compare(this.klt, this.kltQueue.peek()) < 0) {
                this.kltQueue.add(this.klt);
                this.klt = this.kltQueue.poll();
            }
        }
	}

	@Override
	public String toString() {
		return this.id + "-" + this.klt;
	}

	public Klt getKlt() {
		return klt;
	}

	public Integer remainingCpuClocks(){
		int counter = 0;
		for( Klt each : kltQueue){
			counter += each.remainingCpuClocks();
		}
		return counter;
	}

	public Integer totalRanCpuClocks(){
		int counter = 0;
		for( Klt each : kltQueue){
			counter += each.totalRanCpuClocks();
		}
		return counter;
	}

	public Integer getLastClock() {
		Klt latest = kltQueue.peek();
		for( Klt each : kltQueue){
			if (each.getLastClock() > latest.getLastClock())
				latest = each;
		}
		if (latest != null)
			return latest.getLastClock();
		else
			return 0;
	}
}
