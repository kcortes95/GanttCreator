import java.util.List;
import java.util.Stack;

public class Thread {

    /**
     * Manejamos Jobs como una pila a la cual le devolvemos las tareas si no estan terminadas
     */
    private List<Job> jobs;
    private Type type = Type.ULT; //by default is ULT


    Thread(int type, List<Job> jobs) {
        if(type == 1)
        	this.type = Type.KLT;
    	
    	this.jobs = jobs;
    }

    public Type getType() {
		return type;
	}
    
    public Job currentJob() {
        if (jobs.isEmpty()) return null;
        return jobs.get(0);
    }

    public boolean isFinishedCurrentJob(){
        if (jobs.isEmpty()) return true;
        if (jobs.get(0).getClock() == 0) {
            jobs.remove(0);
            return true;
        }
        return false;
    }

    public boolean isFinished(){
        isFinishedCurrentJob();
        return jobs.isEmpty();
    }

    public void insert(Job job) {
        jobs.add(job);
    }
    
    public enum Type {
    	ULT, KLT;
    }

}