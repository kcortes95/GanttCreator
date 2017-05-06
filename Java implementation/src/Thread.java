import java.util.Stack;

public class Thread {

    /**
     * Manejamos Jobs como una pila a la cual le devolvemos las tareas si no estan terminadas
     */
    private Stack<Job> jobs;
    private Type type = Type.ULT; //by default is ULT


    public Thread(int type, Stack<Job> jobs) {
        if(type == 1)
        	this.type = Type.KLT;
    	
    	this.jobs = jobs;
    }

    public Type getType() {
		return type;
	}
    
    public Job next() {
        return jobs.pop();
    }

    public void insert(Job job) {
        jobs.push(job);
    }
    
    public enum Type {
    	ULT, KLT;
    }

}