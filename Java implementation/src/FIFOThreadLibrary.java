import java.util.*;


public class FIFOThreadLibrary extends ThreadLibrary {

    private List<Thread> threads;

    public FIFOThreadLibrary() {
        this.threads = new LinkedList<Thread>() {
        };
    }

    public void addThread(Thread thread) {
        this.threads.add(thread);
    }


    public void update() {
        threads.get(0).currentJob().decrementClock();
    }

    public boolean isFinished() {
        if(threads.get(0).isFinished())
            threads.remove(0);

        return threads.isEmpty();
    }


}
