import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class Resource {

	protected Integer id;
	protected Job.Type type;
	protected Process obj;
	protected Queue<Process> queue = new LinkedList<>();
	protected Integer counter = 0;

	Resource(Integer id, Job.Type type) {
		this.id = id;
		this.type = type;
	}

	public Boolean update() {
		this.counter++;
		//System.out.println(this.getType() + "" + this.id + ": update " + obj);
		if (this.obj != null)
			return this.obj.update();
		return false;
	}

	public Integer getId() {
		return id;
	}

	public Job.Type getType() {
		return type;
	}

	public Process finished() {
		if (this.obj != null && !this.obj.finished())
			return null;

		Process aux = this.obj;
		this.obj = null;

		if (!queue.isEmpty())
			this.obj = queue.poll();
		this.counter = 0;
		return aux;
	}

	public boolean assign(PriorityQueue<Ult> qult) {

		Ult obj = qult.peek();
		if (this.obj == null)
			return false;

		if (this.obj.getId().equals(obj.getProcessId())) {
			this.obj.assign(qult);
			return true;
		} else {
			for (Process proc : this.queue) {
				if (proc.getId().equals(obj.getProcessId())) {
					proc.assign(qult);
					return true;
				}
			}
		}
		return false;

	}

	public boolean assign(Process obj) {
		if (obj == null)
			return false;
		if (this.obj == null) {
			this.obj = obj;
		} else {
			this.queue.add(obj);
		}

		return true;
	}

}
