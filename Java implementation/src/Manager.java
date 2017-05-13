import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Manager<T extends Resource> {

	protected Map<Integer,T> map = new HashMap<>();
	
	protected T get(Integer key){
			return map.get(key);
	}
	
	protected void add(Integer key, T element ){
		map.put(key, element);
	}
	
	public void assign(Process p, int id){
		if(map.containsKey(id)){
			map.get(id).assign(p);
		}
	}
	
	public Collection<T> getValues(){
		return map.values();
	}
	
}
