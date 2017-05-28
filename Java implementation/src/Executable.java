import java.util.LinkedList;
import java.util.PriorityQueue;

public interface Executable {
    Integer getExecutionTime();
    Integer remainingCpuClocks();
    Integer totalRanCpuClocks();
    Integer getLastClock();
}