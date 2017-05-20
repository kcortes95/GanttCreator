import java.util.*;

public abstract class Manager<T extends Resource> {

	protected Map<Integer, T> map = new HashMap<>();

	protected Map<Integer, List<Process>> mapTemp = new HashMap<>();

	protected T get(Integer key) {
		return map.get(key);
	}

	protected void add(Integer key, T element) {
		map.put(key, element);
	}

	public void assign(Process p, int id) {
		System.out.println("MANAGER: add proces " + p.getId() + " to " + id);
		if (mapTemp.containsKey(id)) {
			mapTemp.get(id).add(p);
		} else {
			mapTemp.put(id, new LinkedList<>());
			mapTemp.get(id).add(p);
		}
	}

	public void flush() {
		for (Integer coreId : mapTemp.keySet()) {
			if (map.containsKey(coreId))
				for (Process process : mapTemp.get(coreId)) {
					map.get(coreId).assign(process);
				}
		}
		this.mapTemp.clear();
	}

	public Collection<T> getValues() {
		return map.values();
	}

}
