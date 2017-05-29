import java.util.PriorityQueue;

public class Core extends Resource {


	public Core(Integer id, AlgorithmComparator aCmp) {
		super(id, Job.Type.CPU);
		this.aCmp = aCmp;
		this.queue = new PriorityQueue<>(10, aCmp.getCmp());
	}

	public Process finished() {
		Process aux = super.finished();

		if (this.obj == null ) return aux;

		// If something to ran but expel
		if (!this.queue.isEmpty() && this.queue.comparator().compare(this.obj, this.queue.peek()) > 0 ) {
			this.obj.setExecutionTime(0);
			this.queue.add(this.obj);
			this.obj = this.queue.poll();
			this.obj.setExecutionTime(0);
		}

		// If loaded dosnt belong, discard and load next
		Boolean condition = true;
		while (condition) {
			if (this.obj.getDesignatedCore() == null || this.obj.getDesignatedCore().getId() == this.getId()) {
				condition = false;
				this.obj.setDesignatedCore(this);
			} else {
				this.obj = this.queue.poll();
				if (this.obj == null)
					condition = false;
				else
					this.obj.setExecutionTime(0);
			}

		}

		return aux;
	}

	public boolean assign(PriorityQueue<Ult> qult, AlgorithmComparator aCmpUlt) {
		boolean toret = super.assign(qult, aCmpUlt);

        if ( !this.queue.isEmpty() && this.obj != null && (this.aCmp.isExpulsive() || this.obj.getExecutionTime() == 0) ) {
            if (this.aCmp.getCmp().compare(this.obj, this.queue.peek()) > 0) {
                this.queue.add(this.obj);
                this.obj = this.queue.poll();
            }
        }

		return toret && this.checkDesignatedCore();
	}

	public boolean assign(Process p) {
		boolean toret = super.assign(p);

        if ((this.aCmp.isExpulsive() || this.obj.getExecutionTime() == 0) && !this.queue.isEmpty()) {
            if (this.aCmp.getCmp().compare(this.obj, this.queue.peek()) > 0) {
                this.queue.add(this.obj);
                this.obj = this.queue.poll();
            }
        }

		return toret && this.checkDesignatedCore();
	}

	public boolean checkDesignatedCore() {
		if (this.obj != null) {
			if (this.obj.getDesignatedCore() != null && !this.obj.getDesignatedCore().getId().equals(this.getId()) ) {
				this.obj = null;
				return false;
			} else
				this.obj.setDesignatedCore(this);
		}
		return true;
	}

}
