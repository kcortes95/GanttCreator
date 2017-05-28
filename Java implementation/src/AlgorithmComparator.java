import java.util.Comparator;

/**
 * Created by nacho on 5/28/17.
 */
public class AlgorithmComparator {

    private Boolean isExpulsive;

    private Comparator<Executable> comparator;

    public AlgorithmComparator (Boolean isExpulsive, Comparator<Executable> cmp) {
        this.isExpulsive = isExpulsive;
        this.comparator = cmp;
    }

    public Boolean isExpulsive() {
        return this.isExpulsive;
    }

    public Comparator<Executable> getCmp() {
        return this.comparator;
    }

}
