package com.brokersystem.response;

import java.sql.Date;

public class ChatDialogResponse implements Comparable<ChatDialogResponse>{
	private Integer id;
	private String position;
	private String type;
	private String text;
	private String date;
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int compareTo(ChatDialogResponse arg) {
		return getId().compareTo(arg.getId());
	}
}
