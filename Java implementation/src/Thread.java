
public class Thread {

    /**
     * Manejamos Jobs como una pila a la cual le devolvemos las tareas si no estan terminadas
     */
    private Collection<Job> jobs;

    public Process() {
        this.jobs = jobs;
    }

    public Job next() {
        return jobs.pop();
    }

    public void insert(Job job) {
        jobs.push(job)
    }

}