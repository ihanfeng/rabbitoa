package com.zhm.rabbit.oa.model;

import java.util.List;

public class GridResultBean {
	private Integer page;
	private Integer total;
	private Integer records;
	private List<GridBean> rows;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getRecords() {
		return records;
	}
	public void setRecords(Integer records) {
		this.records = records;
	}
	public List<GridBean> getRows() {
		return rows;
	}
	public void setRows(List<GridBean> rows) {
		this.rows = rows;
	}
	
}
