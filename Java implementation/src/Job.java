
public class Job {

    private Type type;
    private Integer clock;
    
    public Job(Type type, Integer clock) {
		this.type = type;
		this.clock = clock;
		
	}

    public Type getType() {
        return type;
    }

    public Integer getClock() {
        return clock;
    }

    public boolean decrementClock() {
        return (this.clock--) == 0;
    }

    public enum Type {
        IO, CPU;
    }

}