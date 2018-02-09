package com.mad.survey.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class BankData extends BaseData implements Serializable {
	private int bankNum = 0;
	private String name = "";
	private int numOfElevator = -1;
	private int numOfRiser = -1;
	private String description = "";
	private int numOfCar = -1;
	private String notes = "";
	private int numOfInteriorCar = -1;

	public int getNumOfInteriorCar() {
		return numOfInteriorCar;
	}

	public void setNumOfInteriorCar(int numOfInteriorCar) {
		this.numOfInteriorCar = numOfInteriorCar;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getNumOfCar() {
		return numOfCar;
	}

	public void setNumOfCar(int numOfCar) {
		this.numOfCar = numOfCar;
	}

	public int getBankNum() {
		return bankNum;
	}

	public void setBankNum(int bankNum) {
		this.bankNum = bankNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BankData() {}


    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumOfElevator() {
		return numOfElevator;
	}

	public void setNumOfElevator(int numOfElevator) {
		this.numOfElevator = numOfElevator;
	}

	public int getNumOfRiser() {
		return numOfRiser;
	}

	public void setNumOfRiser(int numOfRiser) {
		this.numOfRiser = numOfRiser;
	}

	public ContentValues generateContentValuesFromObject() {
		ContentValues values = new ContentValues();
        values.put("projectId", getProjectId());
		values.put("name", getName());
		values.put("numOfElevator", getNumOfElevator());
		values.put("numOfRiser", getNumOfRiser());
		values.put("numOfCar", getNumOfCar());
		values.put("numOfInteriorCar", getNumOfInteriorCar());
		values.put("description", getDescription());
		values.put("bankNum", getBankNum());
		values.put("notes", getNotes());
		return values;
	}

	public BankData(Cursor cursor) {
		super();
		setId(cursor.getInt(cursor.getColumnIndex("id")));
        setProjectId(cursor.getInt(cursor.getColumnIndex("projectId")));
		setName(cursor.getString(cursor.getColumnIndex("name")));
		setNumOfElevator(cursor.getInt(cursor.getColumnIndex("numOfElevator")));
		setNumOfRiser(cursor.getInt(cursor.getColumnIndex("numOfRiser")));
		setNumOfCar(cursor.getInt(cursor.getColumnIndex("numOfCar")));
		setBankNum(cursor.getInt(cursor.getColumnIndex("bankNum")));
		setNumOfInteriorCar(cursor.getInt(cursor.getColumnIndex("numOfInteriorCar")));
		setNotes(cursor.getString(cursor.getColumnIndex("notes")));
		setDescription(cursor.getString(cursor.getColumnIndex("description")));
	}
	public JSONObject getPostJSON(List<Object> hallsList, List<Object> lanternsList, List<Object> carsList,List<Object> cabsList,List<Object> entrancesList){
		JSONObject jsonObject = new JSONObject();
		try {

			JSONArray bankSummary = new JSONArray();
			bankSummary.put(0,getJSON("bank_name",getName()));
			bankSummary.put(1,getJSON("num_of_car",getNumOfCar()+""));
			bankSummary.put(2,getJSON("notes",getNotes()));

			for (int i = 0; i < getPhotosList().size(); i++){
				PhotoData photoData = getPhotosList().get(i);
				bankSummary.put(i+3, photoData.getPostJSON(i + 1));
			}

			jsonObject.put("bank_summary",bankSummary);

			JSONArray hallsAry = new JSONArray();

			for (int i = 0; i < hallsList.size(); i++){
				hallsAry.put(i, ((HallStationData)hallsList.get(i)).getPostJSON());

			}
			jsonObject.put("hallstation_list", hallsAry);

			JSONArray lanternsAry = new JSONArray();
			for (int i = 0; i < lanternsList.size(); i++){
				lanternsAry.put(i, ((LanternData) lanternsList.get(i)).getPostJSON());
			}
			jsonObject.put("lanternpi_list", lanternsAry);

			JSONArray carsAry = new JSONArray();
			for (int i = 0; i < carsList.size(); i++){
				carsAry.put(i, ((CarData) carsList.get(i)).getPostJSON());
			}
			jsonObject.put("car_list", carsAry);

			JSONArray cabsAry = new JSONArray();
			for (int i = 0; i < cabsList.size(); i++){
				cabsAry.put(i, ((InteriorCarData) cabsList.get(i)).getPostJSON());
			}
			jsonObject.put("cab_interior_list", cabsAry);

			JSONArray entrancesAry = new JSONArray();
			for (int i = 0; i < entrancesList.size(); i++){
				entrancesAry.put(i, ((HallEntranceData) entrancesList.get(i)).getPostJSON());
			}
			jsonObject.put("hall_entrance_list", entrancesAry);

		}catch(Exception e){
			e.printStackTrace();
		}

		return jsonObject;
	}



}
