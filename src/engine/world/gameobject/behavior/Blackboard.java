package engine.world.gameobject.behavior;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds arbitrary state for behavior trees.
 */
public class Blackboard {
	
	public class Value {
		private Class<?> type;
		private Object value;
		
		public Value(Object obj) {
			this.type = obj.getClass();
			this.value = obj;
		}
		
		public Class<?> getType() {
			return type;
		}
		
		public Object getValue() {
			return value;
		}
	}
	
	private Map<String, Value> store;

	public Blackboard() {
		store = new HashMap<String, Blackboard.Value>();
	}
	
	public void put(String key, Object value) {
		store.put(key, new Value(value));
	}
	
	public Value get(String key) {
		return store.get(key);
	}
	
}
