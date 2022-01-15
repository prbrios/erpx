package com.github.prbrios.erpx.dao;

import java.util.ArrayList;
import java.util.List;

public class Rows extends ArrayList<Row> {

	private static final long serialVersionUID = 1L;
	
	private Long count;

	public Long getCount() {
		return count != null ? count : this.size();
	}

	void setCount(Long count) {
		this.count = count;
	}
	
	public Row getRowByKey(String key, String value) {
		Row found = null;
		
		for (Row row : this) {
			Object o = row.get(key);
			if (value.equals(o)) {
				found = row;
				break;
			}
		}
		
		return found;
	}
	
	public int getPositionByKey(String key, String value) {
		int found = -1;
		
		for (int i = 0; i < this.size(); i++) {
			Row row = this.get(i);
			Object o = row.get(key);
			if (value.equals(o)) {
				found = i;
				break;
			}
		}
		
		return found;
	}
	
	public List<Long> getIds() {
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < this.size(); i++) {
			Row row = this.get(i);
			ids.add(((Double)row.get("zk")).longValue());
		}
		
		return ids;
	}
}
