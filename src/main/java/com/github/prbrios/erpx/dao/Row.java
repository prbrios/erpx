package com.github.prbrios.erpx.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Row implements Serializable, Map<String, Object>  {
	private static final long serialVersionUID = 1L;

	private HashMap<String, Object> map;

	public Row() {
		this.map = new HashMap<String, Object>();
	}

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return this.map.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		this.map.put(key, value);
		return value;
	}

	@Override
	public Object remove(Object key) {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> keySet() {
		return this.map.keySet();
	}

	@Override
	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return this.map.entrySet();
	}

}
