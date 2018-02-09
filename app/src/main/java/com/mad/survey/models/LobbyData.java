package com.mad.survey.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.mad.survey.globals.GlobalConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LobbyData implements Serializable {
	private int id = 0;
	private int projectId = 0;
	private String location = "";
	private int lobbyNum = 0;
	private int visibility = 0;
	private double panelWidth = -1,panelHeight = -1 , screwCenterWidth = -1 , screwCenterHeight = -1;
	private String specialFeature = "",specialCommunicationOption = "";
	private String notes = "";
	private List<PhotoData> photosList = new ArrayList<>();

	public List<PhotoData> getPhotosList() {
		return photosList;
	}

	public void setPhotosList(List<PhotoData> photosList) {
		this.photosList = photosList;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSpecialFeature() {
		return specialFeature;
	}

	public void setSpecialFeature(String specialFeature) {
		this.specialFeature = specialFeature;
	}

	public String getSpecialCommunicationOption() {
		return specialCommunicationOption;
	}

	public void setSpecialCommunicationOption(String specialCommunicationOption) {
		this.specialCommunicationOption = specialCommunicationOption;
	}

	public double getPanelWidth() {
		return panelWidth;
	}

	public void setPanelWidth(double panelWidth) {
		this.panelWidth = panelWidth;
	}

	public double getPanelHeight() {
		return panelHeight;
	}

	public void setPanelHeight(double panelHeight) {
		this.panelHeight = panelHeight;
	}

	public double getScrewCenterWidth() {
		return screwCenterWidth;
	}

	public void setScrewCenterWidth(double screwCenterWidth) {
		this.screwCenterWidth = screwCenterWidth;
	}

	public double getScrewCenterHeight() {
		return screwCenterHeight;
	}

	public void setScrewCenterHeight(double screwCenterHeight) {
		this.screwCenterHeight = screwCenterHeight;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public int getLobbyNum() {
		return lobbyNum;
	}

	public void setLobbyNum(int lobbyNum) {
		this.lobbyNum = lobbyNum;
	}

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LobbyData() {}

	public ContentValues generateContentValuesFromObject() {
        ContentValues values = new ContentValues();
        values.put("projectId", getProjectId());
		values.put("location", getLocation());
		values.put("lobbyNum", getLobbyNum());
		values.put("visibility", getVisibility());
		values.put("panelWidth", getPanelWidth());
		values.put("panelHeight", getPanelHeight());
		values.put("screwCenterWidth", getScrewCenterWidth());
		values.put("screwCenterHeight", getScrewCenterHeight());
		values.put("specialFeature", getSpecialFeature());
		values.put("specialCommunicationOption", getSpecialCommunicationOption());
		values.put("notes",getNotes());
        return values;
    }

    public LobbyData(Cursor cursor) {
        super();
		setId(cursor.getInt(cursor.getColumnIndex("id")));
		setProjectId(cursor.getInt(cursor.getColumnIndex("projectId")));
		setLocation(cursor.getString(cursor.getColumnIndex("location")));
		setLobbyNum(cursor.getInt(cursor.getColumnIndex("lobbyNum")));
		setVisibility(cursor.getInt(cursor.getColumnIndex("visibility")));
		setPanelWidth(cursor.getDouble(cursor.getColumnIndex("panelWidth")));
		setPanelHeight(cursor.getDouble(cursor.getColumnIndex("panelHeight")));
		setScrewCenterWidth(cursor.getDouble(cursor.getColumnIndex("screwCenterWidth")));
		setScrewCenterHeight(cursor.getDouble(cursor.getColumnIndex("screwCenterHeight")));
		setSpecialFeature(cursor.getString(cursor.getColumnIndex("specialFeature")));
		setSpecialCommunicationOption(cursor.getString(cursor.getColumnIndex("specialCommunicationOption")));
		setNotes(cursor.getString(cursor.getColumnIndex("notes")));

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

	public JSONArray getPostJSON(){
		JSONArray jsonArray = new JSONArray();
		try {
			jsonArray.put(0, getJSON("panel_location", getLocation()));
			jsonArray.put(1, getJSON("elevator_visibility", getVisibility()== GlobalConstant.LOBBY_ELEVATORS_VISIBLE?"YES":"NO"));
			jsonArray.put(2, getJSON("panel_width", getDoubleForJSON(getPanelWidth())));
			jsonArray.put(3, getJSON("panel_height", getDoubleForJSON(getPanelHeight())));
			jsonArray.put(4, getJSON("screw_center_width", getDoubleForJSON(getScrewCenterWidth())));
			jsonArray.put(5, getJSON("screw_center_height", getDoubleForJSON(getScrewCenterHeight())));
			jsonArray.put(6, getJSON("feature", getSpecialFeature()));
			jsonArray.put(7, getJSON("integral_communication", getSpecialCommunicationOption()));
			jsonArray.put(8, getJSON("notes", getNotes()));

			for (int i = 0; i < getPhotosList().size(); i++){
				PhotoData photoData = getPhotosList().get(i);
				jsonArray.put(i+9, photoData.getPostJSON(i + 1));
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return jsonArray;
	}


}
