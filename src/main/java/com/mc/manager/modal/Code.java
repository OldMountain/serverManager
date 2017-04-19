package com.mc.manager.modal;

public class Code {
	private String code = "";
	private String command = "";
	private int count = 0;
	private String record = "";

	
	public Code() {
	}

	public Code(String code, String command, int count, String record) {
		this.code = code;
		this.command = command;
		this.count = count;
		this.record = record;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	@Override
	public String toString() {
		return "Code [code=" + code + ", command=" + command + ", count=" + count + ", record=" + record + "]";
	}

}
