import java.util.Comparator;

/**
 * Created by nacho on 5/22/17.
 */
public class Comparators {

    public static Comparator<Process> processComparator(Type type) {
        return processComparator(type, 0);
    }

    public static Comparator<Process> processComparator(Type type, Integer aux) {

        switch (type) {
            case FIFO:
                return processComparatorFifo();
            case RR:
                return processComparatorRR(aux);
            case AUX:;
            case SRT:;
            case HRRN:;
            default:
                return null;
        }

    }

    public enum Type {
        FIFO, SRT, HRRN, RR, AUX;

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
    private static Comparator<Process> processComparatorFifo(){
        return new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2)  {return 1;}
        };
    }

    /**
     * En las expulsiones devuelve siempre QUANTUM - executed time, si se llega a quantum expulsa
     * En las priority, siempre devuelve QUANTUM - 0 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Process> processComparatorRR(Integer q){
        return new Comparator<Process>() {
            private Integer quantum = q;
            @Override
            public int compare(Process o1, Process o2) {
                return this.quantum.compareTo(o1.getExecutionTime());
            }
        };
    }

}
