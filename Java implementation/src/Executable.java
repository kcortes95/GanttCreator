public interface Executable {
    Integer getExecutionTime();
    Integer remainingCpuClocks();
    Integer totalRanCpuClocks();
    Integer getLastClock();
}