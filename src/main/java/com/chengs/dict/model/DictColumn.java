package com.chengs.dict.model;

import java.io.Serializable;

public class DictColumn implements Serializable{

	private static final long serialVersionUID = 1975122331336482384L;

	private String columnName;        //字段名称
	
	private String columnType;        //字段类型
	
    private String ordinalPosition;   //序号
    
    private String columnDefault;     //字段默认
    
    private String isNullable;        //可否空
    
    private String extra;             //其他
    
    private String columnKey;         //主键约束
    
    private String columnComment;     //字段注释

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(String ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getColumnDefault() {
		return columnDefault;
	}

	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
    
	
}
