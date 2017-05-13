
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

    public void decrementClock() {
        this.clock -= 1;
    }

    public enum Type {
        IO, CPU;

        @Override
        public String toString() {
            switch (this) {
                case IO: return "IO";
                case CPU: return "CPU";
            }
            return "";
        }
    }

}