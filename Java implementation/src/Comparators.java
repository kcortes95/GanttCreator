import java.util.Comparator;

/**
 * Created by nacho on 5/22/17.
 */
public class Comparators {

    public static Comparator<Process> processComparator(Type type) {
        return processComparator(type, 1);
    }
    public static Comparator<Process> processComparator(Type type, Integer aux) {

        switch (type) {
            case FIFO:
                return processComparatorFifo();
            case RR:
                return processComparatorRR(aux);
            case SPN:
                return processComparatorSPN();
            case SRT:;
                return processComparatorSRT();
            case HRRN:
                return processComparatorHRRN();
            default:
                return null;
        }

    }

    public static Comparator<Klt> kltComparator(Type type) {
        return kltComparator(type, 1);
    }
    public static Comparator<Klt> kltComparator(Type type, Integer aux) {

        switch (type) {
            case FIFO:
                return kltComparatorFifo();
            case RR:
                return kltComparatorRR(aux);
            case SPN:
                return kltComparatorSPN();
            case SRT:
                return kltComparatorSRT();
            case HRRN:
                return kltComparatorHRRN();
            default:
                return null;
        }

    }

    public static Comparator<Ult> ultComparator(Type type) {
        return ultComparator(type, 1);
    }
    public static Comparator<Ult> ultComparator(Type type, Integer aux) {

        switch (type) {
            case FIFO:
                return ultComparatorFifo();
            case RR:
                return ultComparatorRR(aux);
            case SPN:
                return ultComparatorSPN();
            case SRT:
                return ultComparatorSRT();
            case HRRN:
                return ultComparatorHRRN();
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
    private static Comparator<Process> processComparatorFifo(){
        return new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2)  {return 0;}
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
                return this.quantum.compareTo(o1.getExecutionTime()+1);
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre SRT1 - SRT2
     * En las priority, siempre devuelve SRT1 - SRT2 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Process> processComparatorSRT(){
        return new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return o2.remainingCpuClocks() - o1.remainingCpuClocks();
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre SRT1 - SRT2
     * En las priority, siempre devuelve SRT1 - SRT2 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Process> processComparatorSPN(){
        return new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
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
    private static Comparator<Process> processComparatorHRRN(){
        return new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                if (o1.totalRanCpuClocks() > o2.totalRanCpuClocks()) //solo ocurre en expel evaluation
                    return 0; //evita expulsion
                return
                        ((Clock.getInstance().getClock() - o1.getLastClock() +  o1.remainingCpuClocks() + o1.totalRanCpuClocks())/(o1.remainingCpuClocks() + o1.totalRanCpuClocks()))
                        - ((Clock.getInstance().getClock() - o2.getLastClock() +  o2.remainingCpuClocks() + o2.totalRanCpuClocks())/(o2.remainingCpuClocks() + o2.totalRanCpuClocks()));
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre 1 que es distinto de <=0 por ende no expulsa
     * En las priority, siempre devuelve positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Klt> kltComparatorFifo(){
        return new Comparator<Klt>() {
            @Override
            public int compare(Klt o1, Klt o2)  {return 0;}
        };
    }

    /**
     * En las expulsiones devuelve siempre QUANTUM - executed time, si se llega a quantum expulsa
     * En las priority, siempre devuelve QUANTUM - 0 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Klt> kltComparatorRR(Integer q){
        return new Comparator<Klt>() {
            private Integer quantum = q;
            @Override
            public int compare(Klt o1, Klt o2) {
                return this.quantum.compareTo(o1.getExecutionTime()+1);
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre SRT2 - SRT1
     * En las priority, siempre devuelve SRT2 - SRT1
     * @return
     */
    private static Comparator<Klt> kltComparatorSRT(){
        return new Comparator<Klt>() {
            @Override
            public int compare(Klt o1, Klt o2) {
                return o2.remainingCpuClocks() - o1.remainingCpuClocks();
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre SRT1 - SRT2
     * En las priority, siempre devuelve SRT1 - SRT2 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Klt> kltComparatorSPN(){
        return new Comparator<Klt>() {
            @Override
            public int compare(Klt o1, Klt o2) {
                if (o1.totalRanCpuClocks() > o2.totalRanCpuClocks()) //solo ocurre en expel evaluation
                    return 0; //evita expulsion
                return o2.remainingCpuClocks() + o2.totalRanCpuClocks() - o1.remainingCpuClocks() - o1.totalRanCpuClocks(); //ordena en la queue
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre SRT1 - SRT2
     * En las priority, siempre devuelve SRT1 - SRT2 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Klt> kltComparatorHRRN(){
        return new Comparator<Klt>() {
            @Override
            public int compare(Klt o1, Klt o2) {
                if (o1.totalRanCpuClocks() > o2.totalRanCpuClocks()) //solo ocurre en expel evaluation
                    return 0; //evita expulsion
                return
                        ((Clock.getInstance().getClock() - o1.getLastClock() +  o1.remainingCpuClocks() + o1.totalRanCpuClocks())/(o1.remainingCpuClocks() + o1.totalRanCpuClocks()))
                                - ((Clock.getInstance().getClock() - o2.getLastClock() +  o2.remainingCpuClocks() + o2.totalRanCpuClocks())/(o2.remainingCpuClocks() + o2.totalRanCpuClocks()));
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre 1 que es distinto de <=0 por ende no expulsa
     * En las priority, siempre devuelve positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Ult> ultComparatorFifo(){
        return new Comparator<Ult>() {
            @Override
            public int compare(Ult o1, Ult o2)  {return 0;}
        };
    }

    /**
     * En las expulsiones devuelve siempre QUANTUM - executed time, si se llega a quantum expulsa
     * En las priority, siempre devuelve QUANTUM - 0 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Ult> ultComparatorRR(Integer q){
        return new Comparator<Ult>() {
            private Integer quantum = q;
            @Override
            public int compare(Ult o1, Ult o2) {
                return this.quantum.compareTo(o1.getExecutionTime()+1);
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre SRT2 - SRT1
     * En las priority, siempre devuelve SRT2 - SRT1
     * @return
     */
    private static Comparator<Ult> ultComparatorSRT(){
        return new Comparator<Ult>() {
            @Override
            public int compare(Ult o1, Ult o2) {
                return o2.remainingCpuClocks() - o1.remainingCpuClocks();
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre SRT1 - SRT2
     * En las priority, siempre devuelve SRT1 - SRT2 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Ult> ultComparatorSPN(){
        return new Comparator<Ult>() {
            @Override
            public int compare(Ult o1, Ult o2) {
                if (o1.totalRanCpuClocks() > o2.totalRanCpuClocks()) //solo ocurre en expel evaluation
                    return 0; //evita expulsion
                return o2.remainingCpuClocks() + o2.totalRanCpuClocks() - o1.remainingCpuClocks() - o1.totalRanCpuClocks(); //ordena en la queue
            }
        };
    }

    /**
     * En las expulsiones devuelve siempre SRT1 - SRT2
     * En las priority, siempre devuelve SRT1 - SRT2 que es positivo enstonces hace fifo
     * @return
     */
    private static Comparator<Ult> ultComparatorHRRN(){
        return new Comparator<Ult>() {
            @Override
            public int compare(Ult o1, Ult o2) {
                if (o1.totalRanCpuClocks() > o2.totalRanCpuClocks()) //solo ocurre en expel evaluation
                    return 0; //evita expulsion
                return
                        ((Clock.getInstance().getClock() - o1.getLastClock() +  o1.remainingCpuClocks() + o1.totalRanCpuClocks())/(o1.remainingCpuClocks() + o1.totalRanCpuClocks()))
                                - ((Clock.getInstance().getClock() - o2.getLastClock() +  o2.remainingCpuClocks() + o2.totalRanCpuClocks())/(o2.remainingCpuClocks() + o2.totalRanCpuClocks()));
            }
        };
    }
}
