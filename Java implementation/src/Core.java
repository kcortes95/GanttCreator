/**
 * Created by nacho on 4/30/17.
 */
public class Core extends Resource{

    public Core(Integer id) {
        super(id, Job.Type.CPU);
    }
    
    public Process finished() {
    	Process aux = super.finished();
    	if (aux == null) return null;
    	
    	Boolean condition = true;
    	while (condition) {
    		if (this.obj.getDesignatedCore() == null || this.obj.getDesignatedCore().getId() == this.getId() ) {
    			condition = false;
    			this.obj.setDesignatedCore(this);
    		} else {
    			this.obj = this.queue.poll();
    			if (this.obj == null)
    				condition = false;
    		}
    			
    	}
    	
    	return aux;
    }

}
