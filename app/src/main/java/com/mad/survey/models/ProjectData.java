package com.mad.survey.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.loopj.android.http.RequestParams;
import com.mad.survey.preferences.AppPreference;
import com.mad.survey.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectData implements Serializable {
	private int id = 0;
	private int no = 0;
	private int jobType = 0;
	private String name = "";
	private String companyName = "";
	private String companyContact = "";
	private String surveyDate = "";
	private String submitTime = "";
	private String notes = "";
	private int numBanks = 0;
	private int numFloors = 0;
	private int numLobbyPanels = 0;
	private String scaleUnit = "";
	private String status = "Not Submitted";
	private String uuid = "";
	private List<PhotoData> photosList = new ArrayList<>();

	public String getScaleUnit() {
		return scaleUnit;
	}

	public void setScaleUnit(String scaleUnit) {
		this.scaleUnit = scaleUnit;
	}

	public List<PhotoData> getPhotosList() {
		return photosList;
	}

	public void setPhotosList(List<PhotoData> photosList) {
		this.photosList = photosList;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNumLobbyPanels() {
		return numLobbyPanels;
	}

	public void setNumLobbyPanels(int numLobbyPanels) {
		this.numLobbyPanels = numLobbyPanels;
	}

	public int getNumFloors() {
		return numFloors;
	}

	public void setNumFloors(int numFloors) {
		this.numFloors = numFloors;
	}

	public int getNumBanks() {
		return numBanks;
	}

	public void setNumBanks(int numBanks) {
		this.numBanks = numBanks;
	}

	public int getJobType() {
		return jobType;
	}

	public void setJobType(int jobType) {
		this.jobType = jobType;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	private int lobbyPanels = 0;
    private int hallStations = 0;
    private int hallLanterns = 0;
    private int cops = 0;
	private int cabInteriors = 0;
	private int hallEntrances = 0;

	public int getCabInteriors() {
		return cabInteriors;
	}

	public void setCabInteriors(int cabInteriors) {
		this.cabInteriors = cabInteriors;
	}

	public int getHallEntrances() {
		return hallEntrances;
	}

	public void setHallEntrances(int hallEntrances) {
		this.hallEntrances = hallEntrances;
	}

	public int getCops() {
		return cops;
	}

	public void setCops(int cops) {
		this.cops = cops;
	}

	public int getHallStations() {
		return hallStations;
	}

	public void setHallStations(int hallStations) {
		this.hallStations = hallStations;
	}

	public int getHallLanterns() {
		return hallLanterns;
	}

	public void setHallLanterns(int hallLanterns) {
		this.hallLanterns = hallLanterns;
	}

	public int getLobbyPanels() {
		return lobbyPanels;
	}

	public void setLobbyPanels(int lobbyPanels) {
		this.lobbyPanels = lobbyPanels;
	}

	public ProjectData() {
		setUuid(Utils.createUUID());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
	}

	public String getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(String surveyDate) {
		this.surveyDate = surveyDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public ContentValues generateContentValuesFromObject() {
        ContentValues values = new ContentValues();
        values.put("no", getNo());
        values.put("name", getName());
        values.put("companyName", getCompanyName());
        values.put("companyContact", getCompanyContact());
        values.put("surveyDate", getSurveyDate());
		values.put("scaleUnit",getScaleUnit());
		values.put("submitTime", getSubmitTime());
		values.put("status", getStatus());
		values.put("notes", getNotes());
		values.put("jobType", getJobType());
		values.put("numBanks", getNumBanks());
		values.put("numFloors", getNumFloors());
		values.put("numLobbyPanels", getNumLobbyPanels());
		values.put("lobbyPanels", getLobbyPanels());
		values.put("hallStations", getHallStations());
		values.put("hallLanterns", getHallLanterns());
		values.put("cops", getCops());
		values.put("cabInteriors", getCabInteriors());
		values.put("hallEntrances", getHallEntrances());
		values.put("uuid", getUuid());

        return values;
    }

    public ProjectData(Cursor cursor) {
        super();

        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setNo(cursor.getInt(cursor.getColumnIndex("no")));
		setName(cursor.getString(cursor.getColumnIndex("name")));
		setCompanyName(cursor.getString(cursor.getColumnIndex("companyName")));
		setCompanyContact(cursor.getString(cursor.getColumnIndex("companyContact")));
		setNotes(cursor.getString(cursor.getColumnIndex("notes")));
		setSurveyDate(cursor.getString(cursor.getColumnIndex("surveyDate")));
		setScaleUnit(cursor.getString(cursor.getColumnIndex("scaleUnit")));
		setSubmitTime(cursor.getString(cursor.getColumnIndex("submitTime")));
		setStatus(cursor.getString(cursor.getColumnIndex("status")));
		setJobType(cursor.getInt(cursor.getColumnIndex("jobType")));
		setNumBanks(cursor.getInt(cursor.getColumnIndex("numBanks")));
		setNumFloors(cursor.getInt(cursor.getColumnIndex("numFloors")));
		setNumLobbyPanels(cursor.getInt(cursor.getColumnIndex("numLobbyPanels")));
		setLobbyPanels(cursor.getInt(cursor.getColumnIndex("lobbyPanels")));
		setHallStations(cursor.getInt(cursor.getColumnIndex("hallStations")));
		setHallLanterns(cursor.getInt(cursor.getColumnIndex("hallLanterns")));
		setCops(cursor.getInt(cursor.getColumnIndex("cops")));
		setCabInteriors(cursor.getInt(cursor.getColumnIndex("cabInteriors")));
		setHallEntrances(cursor.getInt(cursor.getColumnIndex("hallEntrances")));
		setUuid(cursor.getString(cursor.getColumnIndex("uuid")));

    }

	public RequestParams getPostParams(Context context, JSONObject projectDataJSON, String submitTimeForFileName){
		RequestParams paramObject = new RequestParams();
		try {
			paramObject.put("projectData", projectDataJSON.toString());
			paramObject.put("projectName", getName());
			paramObject.put("sender", AppPreference.getStringPrefValue(context, AppPreference.PREF_KEY_YOUR_NAME));
			paramObject.put("company", AppPreference.getStringPrefValue(context, AppPreference.PREF_KEY_YOUR_COMPANY));
			paramObject.put("mnumber", getNo());
			paramObject.put("date", getSurveyDate());
			paramObject.put("platform", "Android");
			paramObject.put("version", Utils.getVersionName(context));
			paramObject.put("email", AppPreference.getStringPrefValue(context, AppPreference.PREF_KEY_YOUR_EMAIL));
			paramObject.put("sendpdf", AppPreference.getEmailReportPreference(context)? 1:0);
			paramObject.put("file_name", getFileName(submitTimeForFileName));
		}catch(Exception e){
			e.printStackTrace();
		}

		return paramObject;
	}
	public JSONObject getProjectDataJSON(Context context){
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("project_id", getId());
			JSONArray summaryArray = new JSONArray();
			summaryArray.put(0, getSummaryJSON("_version",Utils.getVersionName(context)));
			summaryArray.put(1, getSummaryJSON("_deviceModel", Utils.getPhoneModel()));

			summaryArray.put(3, getSummaryJSON("_mobilePlatform", "Android"));
			summaryArray.put(4, getSummaryJSON("_osVersion",Utils.getOSVersion()));
			summaryArray.put(5, getSummaryJSON("_uuid", getUuid()));

			summaryArray.put(6, getSummaryJSON("_scale_unit",getScaleUnit()));
			summaryArray.put(7, getSummaryJSON("m_number",getNo()+""));
			summaryArray.put(8, getSummaryJSON("project_name", getName()));
			summaryArray.put(9, getSummaryJSON("project_company", getCompanyName()));
			summaryArray.put(10, getSummaryJSON("project_contact", getCompanyContact()));
			summaryArray.put(11, getSummaryJSON("project_date", getSurveyDate()));
			int i;
			for (i = 0; i < getPhotosList().size(); i++){
				PhotoData photoData = getPhotosList().get(i);
				summaryArray.put(i+12, photoData.getPostJSON(i + 1));
			}


			jsonObject.put("project_summary", summaryArray);
			jsonObject.put("submit_time", getSubmitTime());
			jsonObject.put("project_status", getStatus());


		}catch(Exception e){
			e.printStackTrace();
		}

		return jsonObject;
	}
	public String getFileName(String submitTimeFileName){
		String uuidConverted = getUuid().replace("-", "_");
		String pName = getName();
		pName = pName.replace(" ","_");
		return pName + "_" + uuidConverted + "_" + submitTimeFileName;
	}
	public String getFileName(){
		String name = getName();
		name = name.replace(" ","");
		return getNo()+name;
	}

	public JSONObject getSummaryJSON(String name, String value){
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("name", name);
			jsonObject.put("value", value);
		}catch(Exception e){
			e.printStackTrace();
		}

		return jsonObject;
	}

}
