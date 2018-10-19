package com.chengs.dict.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chengs.dict.model.DictColumn;
import com.chengs.dict.model.DictTable;

public class DatabaseDao {
	
	public DatabaseDao(Connection conn) {
		super();
		this.conn = conn;
	}

	private Connection conn;

	public List<String> getAllTable(String dbName) throws SQLException{
		String sql="select table_name from information_schema.tables where table_schema='?' and table_type='base table'";
		PreparedStatement sta = conn.prepareStatement(sql);
		sta.setString(1, dbName);
		ResultSet rs = sta.executeQuery();
		List<String> result=new ArrayList<>();
		while (rs.next()) {
			String table=rs.getString("table_name");
			result.add(table);
            System.out.println(table);
        }
		rs.close();
		return result;
	}
	
	public List<DictTable> getAllColumn(List<String> tableNameList,String dbName) throws SQLException{
		String sql = "SELECT\r\n" + 
				"	COLUMN_NAME as columnName,\r\n" + 
				"	COLUMN_DEFAULT as columnDefault,\r\n" + 
				"	IS_NULLABLE as isNullable,\r\n" + 
				"	COLUMN_TYPE as columnType,\r\n" + 
				"	COLUMN_COMMENT as columnComment,\r\n" + 
				"	COLUMN_KEY as columnKey,\r\n" + 
				"	EXTRA as extra\r\n" + 
				"from information_schema. COLUMNS\r\n" + 
				"WHERE\r\n" + 
				"	TABLE_SCHEMA = '?' \r\n" + 
				"  and TABLE_NAME = '?' ";
		List<DictTable> ret=new ArrayList<>();
		PreparedStatement sta = conn.prepareStatement(sql);
		sta.setString(1, dbName);
		for (String tableName : tableNameList) {
			sta.setString(2, tableName);
			ResultSet rs = sta.executeQuery();
			DictTable dt=new DictTable();
			dt.setTableName(tableName);
			List<DictColumn> list=new ArrayList<>();
			while (rs.next()) {
				DictColumn dc=new DictColumn();
				dc.setColumnName(rs.getString("columnName"));
				dc.setColumnDefault(rs.getString("columnDefault"));
				dc.setIsNullable(rs.getString("isNullable"));
				dc.setColumnType(rs.getString("columnType"));
				dc.setColumnComment(rs.getString("columnComment"));
				dc.setColumnKey(rs.getString("columnKey"));
				dc.setExtra(rs.getString("extra"));
				list.add(dc);
	        }
			dt.setColumns(list);
			rs.close();
			ret.add(dt);
		}
		return ret;
	}
	
	
}
