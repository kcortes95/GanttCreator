import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by nacho on 4/30/17.
 */
public class Core extends Resource{

    private Queue<Process> ready = new LinkedList<>();

    public Core(Integer id) {
        super(id, Job.Type.CPU);
    }

}
