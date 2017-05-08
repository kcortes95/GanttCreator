import java.util.HashMap;

public class Process {

    private Integer id;
    private Integer arrivalTime;
    private Core designatedCore;
    //Si, la libreria es el que maneja los algoritmos, esta bien...
    //Faltaria agregar los hilos que componen al proceso! (lo agrego mas abajo)
    //private ThreadLibrary library;
    
    /**
     * Esto deberia ser algo asi...
     */
    private HashMap<Integer, Thread> threads = new HashMap<>();

    //saque: , ThreadLibrary library
    public Process(Integer id, Integer arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.designatedCore = null;
        //this.library = library;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Core getDesignatedCore() {
        return designatedCore;
    }

    public void setDesignatedCore(Core designatedCore) {
        this.designatedCore = designatedCore;
    }

}
