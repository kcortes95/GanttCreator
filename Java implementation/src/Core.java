import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by nacho on 4/30/17.
 */
public class Core extends Resource {


	public Core(Integer id, Comparator<Process> cmp) {
		super(id, Job.Type.CPU);
		this.queue = new PriorityQueue<Process>(10, cmp);
	}

	public Process finished() {
		Process aux = super.finished();
		if (this.obj == null || aux == null)
			return aux;

		Boolean condition = true;
		while (condition) {
			if (this.obj.getDesignatedCore() == null || this.obj.getDesignatedCore().getId() == this.getId()) {
				condition = false;
				this.obj.setDesignatedCore(this);
			} else {
				this.obj = this.queue.poll();
				if (this.obj == null)
					condition = false;
			}

		}

		return aux;
	}

	/*
	 * //Previous version (only for processes) public void assign(Process p) {
	 * super.assign(p); if (this.queue.isEmpty()) { if
	 * (this.obj.getDesignatedCore() != null &&
	 * this.obj.getDesignatedCore().getId() != this.getId()) this.obj = null; if
	 * (this.obj.getDesignatedCore() == null) this.obj.setDesignatedCore(this);
	 * } }
	 */

	public boolean assign(PriorityQueue<Ult> qult) {
		boolean toret = super.assign(qult);

		return toret && this.checkDesignatedCore();
	}

	public boolean assign(Process p) {
		boolean toret = super.assign(p);

		return toret && this.checkDesignatedCore();
	}

	public boolean checkDesignatedCore() {
		if (this.queue.isEmpty() && this.obj != null) {
			if (this.obj.getDesignatedCore() != null && !this.obj.getDesignatedCore().getId().equals(this.getId()) ) {
				this.obj = null;
				return false;
			} else
				this.obj.setDesignatedCore(this);
		}
		return true;
	}

}
