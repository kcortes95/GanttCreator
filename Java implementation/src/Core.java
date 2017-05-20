import java.util.PriorityQueue;

/**
 * Created by nacho on 4/30/17.
 */
public class Core extends Resource {

	public Core(Integer id) {
		super(id, Job.Type.CPU);
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

		if (this.queue.isEmpty() && this.obj != null) {
			if (this.obj.getDesignatedCore() != null && this.obj.getDesignatedCore().getId() != this.getId())
				this.obj = null;
			if (this.obj.getDesignatedCore() == null)
				this.obj.setDesignatedCore(this);
		}

		return toret;
	}

}
