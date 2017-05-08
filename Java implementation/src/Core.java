import java.util.Collection;

/**
 * Created by nacho on 4/30/17.
 */
public class Core extends Resource<Process>{

    public Core(Integer id) {
        super(id, Job.Type.CPU);
    }

    @Override
    public void update() {
        if (this.obj == null) return;
        super.update();
        this.obj.getLibrary().update();
    }

    @Override
    public Process finished() {
        if (this.obj.getLibrary().isFinished()) {
            Process aux = this.obj;
            this.obj = null;
            return aux;
        }
        return null;
    }

    @Override
    public void assign(Process obj) {this.obj = obj;}

}
