import java.util.LinkedList;
import java.util.Queue;

public abstract class Resource {

    protected Integer id;
    protected Job.Type type;
    protected Process obj;
    protected Queue<Process> queue = new LinkedList<>();
    protected Integer counter = 0;

    Resource(Integer id, Job.Type type) {
        this.id = id;
        this.type = type;
    }

    public Boolean update() {
        this.counter++;
        System.out.println(this.getType()+": update " + obj);
        if (this.obj != null)
            return this.obj.update();
        return false;
    }

    public Integer getId() {
        return id;
    }

    public Job.Type getType() {
        return type;
    }

    public Process finished() {
        if(this.obj != null && !this.obj.finished()) return null;

        Process aux = this.obj;
        this.obj = null;
        //if (aux != null) aux.pollJob(); //version solo con procesos
        if( aux != null){
        	aux.getKlt().getUlt();
        }
        
        if (!queue.isEmpty())
            this.obj = queue.poll();
        this.counter = 0;
        return aux;
    }

    public boolean assign(Ult obj) {
        if (obj == null) return false;

        if (this.obj.getId().equals(obj.getProcessId())) {
            this.obj.assign(obj);
            return true;
        } else {
            for(Process proc: this.queue) {
                if (proc.getId().equals(obj.getProcessId())) {
                    proc.assign(obj);
                    return true;
                }
            }
        }
        return false;

    }

}
