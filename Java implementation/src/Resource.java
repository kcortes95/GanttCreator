/**
 * Created by nacho on 4/30/17.
 */
public abstract class Resource {

    /**
     * Id of the resource
     * Example: IO 1, CPU 1, CPU 2
     */
    private Integer id;
    /**
     * Type of resource
     * Example: CPU, IO
     */
    private Job.Type type;
    /**
     * Process currently on resources
     */
    private Process process;
    /**
     * Clock cycles the current process has been active on resource
     */
    private Integer counter;

    public Resource(Integer id, Job.Type type) {
        this.id = id;
        this.type = type;
    }


    public void update() {
        this.counter++;
    }

    public Integer getId() {
        return id;
    }

    public Job.Type getType() {
        return type;
    }

    public abstract Process finished();

    public abstract void assign(Process process);

}
