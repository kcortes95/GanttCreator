/**
 * Created by nacho on 4/30/17.
 */
public abstract class Resource<T> {

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
    protected T obj;
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
    }

    public Integer getId() {
        return id;
    }

    public Job.Type getType() {
        return type;
    }

    public abstract T finished();

    public abstract void assign(T obj);

}
