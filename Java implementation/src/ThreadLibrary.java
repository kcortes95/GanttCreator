import java.util.Collection;
import java.util.Map;

public abstract class ThreadLibrary {

	public abstract void addThread(Thread thread);

    public abstract Thread next();

    public abstract boolean isFinished();

    public abstract void update();

}
