import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by nacho on 4/30/17.
 */
public class IO extends Resource {

    private Queue<Process> blocked = new LinkedList<>();

    public IO(Integer id) {
        super(id, Job.Type.IO);
    }

}
