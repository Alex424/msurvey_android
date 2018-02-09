package com.mad.survey.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;

import org.json.JSONObject;

import java.io.Serializable;

public class PhotoData implements Serializable {
	private int id = 0;
	private int projectId = 0;
    private int linkedId = 0;		// cab id, or hall id, or other id
    private int type = 0;           // 1:CAB CO, 2: CAB SSO, 3:HALL STATION, 4:HALL LANTERN, 5: OTHER
    private int isRearEntrance = 0; // 0: no rear entrance, 1: rear entrance
	private String picNum = "";		// "pic_1"
	private String fileURL = "";
	private String fileName = "";

	private View thumbView = null;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public PhotoData() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getLinkedId() {
		return linkedId;
	}

	public void setLinkedId(int linkedId) {
		this.linkedId = linkedId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsRearEntrance() {
		return isRearEntrance;
	}

	public void setIsRearEntrance(int isRearEntrance) {
		this.isRearEntrance = isRearEntrance;
	}

	public String getPicNum() {
		return picNum;
	}

	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public View getThumbView() {
		return thumbView;
	}

	public void setThumbView(View thumbView) {
		this.thumbView = thumbView;
	}

	public ContentValues generateContentValuesFromObject() {
		ContentValues values = new ContentValues();
		values.put("projectId", getProjectId());
		values.put("linkedId", getLinkedId());
		values.put("type", getType());
		values.put("isRearEntrance", getIsRearEntrance());
		values.put("picNum", getPicNum());
		values.put("fileURL", getFileURL());
		values.put("fileName", getFileName());
		return values;
	}

	public PhotoData(Cursor cursor) {
		super();
		setId(cursor.getInt(cursor.getColumnIndex("id")));
		setProjectId(cursor.getInt(cursor.getColumnIndex("projectId")));
		setLinkedId(cursor.getInt(cursor.getColumnIndex("linkedId")));
		setType(cursor.getInt(cursor.getColumnIndex("type")));
		setIsRearEntrance(cursor.getInt(cursor.getColumnIndex("isRearEntrance")));
		setPicNum(cursor.getString(cursor.getColumnIndex("picNum")));
		setFileURL(cursor.getString(cursor.getColumnIndex("fileURL")));
		setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
	}

	public JSONObject getPostJSON(int index){
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("name", "image_src" + index);
			jsonObject.put("value", getFileName());
		}catch(Exception e){
			e.printStackTrace();
		}

		return jsonObject;
	}
}
