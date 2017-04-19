package com.mc.manager.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mc.manager.connection.Conn;
import com.mc.manager.dao.CodeDao;
import com.mc.manager.modal.Code;

public class CodeImpl implements CodeDao {
	private Connection con = null;
	private Statement st = null;
	private ResultSet rs = null;
	private Code code = null;
	//内部静态类
	public static class CodeImplHandle{
		private static CodeImpl codeImpl = new CodeImpl();
	}
	//获取实例
	public static CodeImpl getInstance(){
		return CodeImplHandle.codeImpl;
	}
	@Override
	public Code selectCode(String codes) {
		String sql = "select * from code where code = '" + codes + "'";
		con = Conn.getCon();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String command = rs.getString("command");
				int count = rs.getInt("count");
				String record = rs.getString("record");
				code = new Code(codes, command, count, record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		Conn.shutCon(con);
		return code;
	}

	@Override
	public int updateCode(Code code, String playerName) {
		if (code.getCount() == 0) {
			return 0;
		}
		String record = code.getRecord();
		if (record !=null && !record.trim().equals("")) {
			record += "," + playerName;
		} else {
			record = playerName;
		}
		String sql = "update code set count = " + (code.getCount() - 1) + ",record = '" + record + "' where code = '"
				+ code.getCode() + "'";
		con = Conn.getCon();
		int i = 0;
		try {
			st = con.createStatement();
			i = st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Conn.shutCon(con);
		return i;
	}

}
