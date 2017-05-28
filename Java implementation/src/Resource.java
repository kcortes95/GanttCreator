import java.util.*;

public abstract class Resource {

	protected Integer id;
	protected Job.Type type;
	protected Process obj;
	protected PriorityQueue<Process> queue = new PriorityQueue<>(10, Comparators.processComparator(Comparators.Type.FIFO));
	protected Integer counter = 0;

	Resource(Integer id, Job.Type type) {
		this.id = id;
		this.type = type;
	}

	public Boolean update() {
		this.counter++;
		if(this.obj != null) System.out.println(this.getType() + "" + this.id + ": " + obj);
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

		if (this.obj != null) {
			if (this.obj.finished()) {
				this.obj.setExecutionTime(0);
				Process aux = this.obj;
				this.obj = queue.poll();
				this.counter = 0;
				if (this.obj != null)
					this.obj.setExecutionTime(0);
				return aux;
			}
		}
		return null;
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
	
	public String getRunning(){
		String pid = "NULL";
		String kltid = "NULL";
		String ultid = "NULL";
		
		if(obj!=null){
			pid = obj.getId();

			if(obj.getKlt()!=null){
				kltid = obj.getKlt().getId();
				
				if(obj.getKlt().getUlt()!=null){
					ultid = obj.getKlt().getUlt().getId();
				}	
				
			}
			
		}
		
		return "" + pid + " " + kltid + " " + ultid;
	}
	

}
