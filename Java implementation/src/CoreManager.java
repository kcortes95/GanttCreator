
public class CoreManager extends Manager<Core> {

	public void newProcess(Process p){
		for(Core each : map.values()){
			each.assign(p);
		}
	}
	
}
