package com.zhm.rabbit.oa.model;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

public class LigerGridBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List rows;
	private long total;
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
	
}
