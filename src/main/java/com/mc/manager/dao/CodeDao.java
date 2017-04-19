package com.mc.manager.dao;

import com.mc.manager.modal.Code;

public interface CodeDao {
	//查询激活码对应指令、数量、使用记录（谁用过了）
	public Code selectCode(String code);
	//使用激活码或更新数量
	public int updateCode(Code code,String playerName);
}
