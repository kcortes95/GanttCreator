
public class Process {

    private Integer id;
    private Integer arrivalTime;
    private Core designatedCore;
    private Library library;

    public Process(Integer id, Integer arrivalTime, Library library) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.designatedCore = null;
        this.library = library;
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

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public void addThread(Thread thread) {
        this.library.addThread(thread);
    }
}
