package com.landray.kmss.third.ding.dto;

import java.util.ArrayList;
import java.util.List;

public class DingNotifyTodoDto {

	DingSysNotifyTodo todo;
	List<String>  persons = new ArrayList<String>();
	public DingSysNotifyTodo getTodo() {
		return todo;
	}
	public void setTodo(DingSysNotifyTodo todo) {
		this.todo = todo;
	}
	public List<String> getPersons() {
		return persons;
	}
	public void setPersons(List<String> persons) {
		this.persons = persons;
	}

}
