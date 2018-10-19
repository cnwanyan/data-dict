package com.chengs.dict.model;

import java.io.Serializable;
import java.util.List;

public class DictTable implements Serializable{

	private static final long serialVersionUID = -6320005536467556782L;

	private String tableName;  //表名
	
	private String tableComment; //表注释
	
	private List<DictColumn> columns;  //列

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<DictColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<DictColumn> columns) {
		this.columns = columns;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
}
