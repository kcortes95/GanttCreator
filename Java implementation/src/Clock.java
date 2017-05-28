/**
 * Created by nacho on 5/28/17.
 */
public class Clock {
    private static Clock ourInstance = null;

    public static Clock getInstance() {
        if(ourInstance == null)
            ourInstance = new Clock();
        return ourInstance;
    }

    private Integer clock;

    private Clock() {
        this.clock = 0;
    }

    public Integer getClock() { return this.clock;}

    public void incrementClock() {this.clock++;}
}
