
public class Job {

    private Type type;
    private Integer clock;

    public Type getType() {
        return type;
    }

    public Integer getClock() {
        return clock;
    }

    public enum Type {
        IO, CPU;
    }

}