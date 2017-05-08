import java.util.LinkedList;
import java.util.List;

/**
 * Created by nacho on 4/30/17.
 */
public class IO extends Resource<Thread>{

    private List<Thread> blocked = new LinkedList<>();

    public IO(Integer id) {
        super(id, Job.Type.IO);
    }

    @Override
    public void update() {
        super.update();
        if (this.obj != null)
            this.obj.currentJob().decrementClock();
    }

    @Override
    public Thread finished() {
        if (!this.obj.isFinishedCurrentJob()) return null;

        Thread aux = this.obj;
        if (!blocked.isEmpty())
            this.obj = blocked.remove(0);
        this.counter = 0;
        return aux;
    }

    @Override
    public void assign(Thread obj) {blocked.add(obj);}

}
