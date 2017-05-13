import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by nacho on 4/30/17.
 */
public abstract class Resource {

    /**
     * Id of the resource
     * Example: IO 1, CPU 1, CPU 2
     */
    protected Integer id;
    /**
     * Type of resource
     * Example: CPU, IO
     */
    protected Job.Type type;
    /**
     * T currently on resources
     */
    protected Process obj;
    /**
     * T Queue
     */
    protected Queue<Process> queue = new LinkedList<>();
    /**
     * Clock cycles the current process has been active on resource
     */
    protected Integer counter;

    Resource(Integer id, Job.Type type) {
        this.id = id;
        this.type = type;
    }


    public void update() {
        this.counter++;
        if (this.obj != null)
            this.obj.update();
    }

    public Integer getId() {
        return id;
    }

    public Job.Type getType() {
        return type;
    }

    public Process finished() {
        if (!this.obj.finished()) return null;

        Process aux = this.obj;
        if (!queue.isEmpty())
            this.obj = queue.poll();
        this.counter = 0;
        return aux;
    }

    public void assign(Process obj) {this.queue.add(obj);}

}
