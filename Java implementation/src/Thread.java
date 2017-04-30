import java.util.Collection;
import java.util.Stack;

public class Thread {

    /**
     * Manejamos Jobs como una pila a la cual le devolvemos las tareas si no estan terminadas
     */
    private Stack<Job> jobs;


    public Thread(Stack<Job> jobs) {
        this.jobs = jobs;
    }

    public Job next() {
        return jobs.pop();
    }

    public void insert(Job job) {
        jobs.push(job);
    }

}