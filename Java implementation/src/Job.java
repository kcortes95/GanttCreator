
public class Job {

    private Type type;
    private Integer clock;
    
    public Job(int type, Integer clock) {
		if(type == 0)
			this.type = Type.CPU;
		else
			this.type = Type.IO;
		
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