import java.util.Comparator;

/**
 * Created by nacho on 5/22/17.
 */
public class Comparators {

    public static AlgorithmComparator comparator(Type type) {
        return comparator(type, 1);
    }
    public static AlgorithmComparator comparator(Type type, Integer aux) {

        switch (type) {
            case FIFO:
                return new AlgorithmComparator( false , comparatorFifo());
            case RR:
                return new AlgorithmComparator( true, comparatorRR(aux));
            case SPN:
                return new AlgorithmComparator( false, comparatorSPN());
            case SRT:
                return new AlgorithmComparator( true, comparatorSRT());
            case HRRN:
                return new AlgorithmComparator( false, comparatorHRRN());
            default:
                return null;
        }

    }

    public enum Type {
        FIFO, SRT, HRRN, RR, SPN;

//        @Override
//        public String toString() {
//            switch (this) {
//                case IO: return "IO";
//                case CPU: return "CPU";
//            }
//            return "";
//        }
    }

    /**
     * En las expulsiones devuelve siempre 1 que es distinto de <=0 por ende no expulsa
     * En las priority, siempre devuelve positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Executable> comparatorFifo(){
        return new Comparator<Executable>() {
            @Override
            public int compare(Executable o1, Executable o2)  {return 0;}
        };
    }

    /**
     * En las expulsiones devuelve siempre QUANTUM - executed time, si se llega a quantum expulsa
     * En las priority, siempre devuelve QUANTUM - 0 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Executable> comparatorRR(Integer q){
        return new Comparator<Executable>() {
            private Integer quantum = q;
            @Override
            public int compare(Executable o1, Executable o2) {
                return this.quantum.compareTo(o1.getExecutionTime()+1);
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre SRT1 - SRT2
     * En las priority, siempre devuelve SRT1 - SRT2 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Executable> comparatorSRT(){
        return new Comparator<Executable>() {
            @Override
            public int compare(Executable o1, Executable o2) {
                return o2.remainingCpuClocks() - o1.remainingCpuClocks();
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre SRT1 - SRT2
     * En las priority, siempre devuelve SRT1 - SRT2 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Executable> comparatorSPN(){
        return new Comparator<Executable>() {
            @Override
            public int compare(Executable o1, Executable o2) {
                if (o1.totalRanCpuClocks() > o2.totalRanCpuClocks()) //solo ocurre en expel evaluation
                    return 0; //evita expulsion
                return o2.remainingCpuClocks() + o2.totalRanCpuClocks() - o1.remainingCpuClocks() - o1.totalRanCpuClocks();
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre SRT1 - SRT2
     * En las priority, siempre devuelve SRT1 - SRT2 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Executable> comparatorHRRN(){
        return new Comparator<Executable>() {
            @Override
            public int compare(Executable o1, Executable o2) {
                if (o1.totalRanCpuClocks() > o2.totalRanCpuClocks()) //solo ocurre en expel evaluation
                    return 0; //evita expulsion
                return
                        ((Clock.getInstance().getClock() - o1.getLastClock() +  o1.remainingCpuClocks() + o1.totalRanCpuClocks())/(o1.remainingCpuClocks() + o1.totalRanCpuClocks()))
                        - ((Clock.getInstance().getClock() - o2.getLastClock() +  o2.remainingCpuClocks() + o2.totalRanCpuClocks())/(o2.remainingCpuClocks() + o2.totalRanCpuClocks()));
            }
        };
    }
}
