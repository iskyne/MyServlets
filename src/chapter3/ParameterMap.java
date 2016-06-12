package chapter3;

import java.util.HashMap;
import java.util.Map;

public class ParameterMap<K,V> extends HashMap<K,V>{
	public ParameterMap(){
		super();
	}
	
	public ParameterMap(int initialCapacity){
		super(initialCapacity);
	}
	
	public ParameterMap(int initialCapacity,float factor){
		super(initialCapacity,factor);
	}
	
	public ParameterMap(Map<K,V> map){
		super(map);
	}
	
	private boolean locked=false;
	
	public void setLocked(boolean locked){
		this.locked=locked;
	}
	
	public boolean isLocked(){
		return this.locked;
	}
	
	public void clear(){
		if(locked){
			throw new IllegalStateException("ParameterMap locked");
		}else{
			super.clear();
		}
	}
	
	public V put(K key,V value){
		if(locked){
			throw new IllegalStateException("ParameterMap locked");
		}else{
			return (super.put(key, value));
		}
	}
	
	public void putAll(Map map){
		if(locked){
			throw new IllegalStateException("ParameterMap locked");
		}else{
			super.putAll(map);
		}
	}
	
	public V remove(Object key){
		if(locked){
			throw new IllegalStateException("Parameter locked");
		}else{
			return (super.remove(key));
		}
	}
	
	
}
