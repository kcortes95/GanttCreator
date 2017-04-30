import java.util.Collection;

/**
 * Created by nacho on 4/30/17.
 */
public class Core extends Resource{

    public Core(Integer id, Job.Type type) {
        super(id, type);
    }

    @Override
    public void update() {
        super.update();

    }

    @Override
    public Process finished() {
        return null;
    }

    @Override
    public void assign(Process process) {
        return;
    }

}
