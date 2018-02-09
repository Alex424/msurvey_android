package com.mad.survey.models;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseData implements Serializable {
	private int id = 0;
    private int projectId = 0;
	private int bankId = 0;
	private String bankDesc;
	private List<PhotoData> photosList = new ArrayList<>();


	public BaseData() {}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getBankDesc() {
		return bankDesc;
	}

	public void setBankDesc(String bankDesc) {
		this.bankDesc = bankDesc;
	}

	public List<PhotoData> getPhotosList() {
		return photosList;
	}

	public void setPhotosList(List<PhotoData> photosList) {
		this.photosList = photosList;
	}

	public JSONObject getJSON(String name, String value){
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("name", name);
			jsonObject.put("value", value);
		}catch(Exception e){
			e.printStackTrace();
		}

		return jsonObject;
	}

	public String getDoubleForJSON(double value){
		return value < 0? "":(value + "");
	}
}
