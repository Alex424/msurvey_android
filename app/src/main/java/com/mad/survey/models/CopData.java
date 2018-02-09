package com.mad.survey.models;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class CopData extends BaseData implements Serializable {

    private int carNum = 0;
    private int copNum = 0;
    private String copName = "";
    private String options = "";
    private String returnHinging = "";
    private double returnPanelWidth = -1;
    private double returnPanelHeight = -1;
    private double swingPanelWidth = -1;
    private double swingPanelHeight = -1;
    private double coverWidth = -1;
    private double coverHeight = -1;
    private double coverToOpening = -1;
    private double coverAff = -1;
    private String notes = "";
    private String uuid = "";

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getSwingPanelWidth() {
        return swingPanelWidth;
    }

    public void setSwingPanelWidth(double swingPanelWidth) {
        this.swingPanelWidth = swingPanelWidth;
    }

    public double getSwingPanelHeight() {
        return swingPanelHeight;
    }

    public void setSwingPanelHeight(double swingPanelHeight) {
        this.swingPanelHeight = swingPanelHeight;
    }

    public int getCarNum() {
        return carNum;
    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }

    public int getCopNum() {
        return copNum;
    }

    public void setCopNum(int copNum) {
        this.copNum = copNum;
    }

    public String getCopName() {
        return copName;
    }

    public void setCopName(String copName) {
        this.copName = copName;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getReturnHinging() {
        return returnHinging;
    }

    public void setReturnHinging(String returnHinging) {
        this.returnHinging = returnHinging;
    }

    public double getReturnPanelWidth() {
        return returnPanelWidth;
    }

    public void setReturnPanelWidth(double returnPanelWidth) {
        this.returnPanelWidth = returnPanelWidth;
    }

    public double getReturnPanelHeight() {
        return returnPanelHeight;
    }

    public void setReturnPanelHeight(double returnPanelHeight) {
        this.returnPanelHeight = returnPanelHeight;
    }

    public double getCoverWidth() {
        return coverWidth;
    }

    public void setCoverWidth(double coverWidth) {
        this.coverWidth = coverWidth;
    }

    public double getCoverHeight() {
        return coverHeight;
    }

    public void setCoverHeight(double coverHeight) {
        this.coverHeight = coverHeight;
    }

    public double getCoverToOpening() {
        return coverToOpening;
    }

    public void setCoverToOpening(double coverToOpening) {
        this.coverToOpening = coverToOpening;
    }

    public double getCoverAff() {
        return coverAff;
    }

    public void setCoverAff(double coverAff) {
        this.coverAff = coverAff;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ContentValues generateContentValuesFromObject() {
        ContentValues values = new ContentValues();
        values.put("projectId", getProjectId());
        values.put("bankId", getBankId());
        values.put("carNum", getCarNum());
        values.put("copNum", getCopNum());
        values.put("copName", getCopName());
        values.put("options", getOptions());
        values.put("returnHinging", getReturnHinging());
        values.put("returnPanelWidth", getReturnPanelWidth());
        values.put("returnPanelHeight", getReturnPanelHeight());
        values.put("swingPanelWidth", getSwingPanelWidth());
        values.put("swingPanelHeight", getSwingPanelHeight());
        values.put("coverWidth", getCoverWidth());
        values.put("coverHeight", getCoverHeight());
        values.put("coverToOpening", getCoverToOpening());
        values.put("coverAff", getCoverAff());
        values.put("notes", getNotes());
        values.put("uuid", getUuid());
        return values;
    }

    public CopData() {
    }

    public CopData(Cursor cursor) {
        super();
        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setProjectId(cursor.getInt(cursor.getColumnIndex("projectId")));
        setBankId(cursor.getInt(cursor.getColumnIndex("bankId")));
        setCarNum(cursor.getInt(cursor.getColumnIndex("carNum")));
        setCopNum(cursor.getInt(cursor.getColumnIndex("copNum")));
        setCopName(cursor.getString(cursor.getColumnIndex("copName")));
        setOptions(cursor.getString(cursor.getColumnIndex("options")));
        setReturnHinging(cursor.getString(cursor.getColumnIndex("returnHinging")));
        setReturnPanelWidth(cursor.getDouble(cursor.getColumnIndex("returnPanelWidth")));
        setReturnPanelHeight(cursor.getDouble(cursor.getColumnIndex("returnPanelHeight")));
        setSwingPanelWidth(cursor.getDouble(cursor.getColumnIndex("swingPanelWidth")));
        setSwingPanelHeight(cursor.getDouble(cursor.getColumnIndex("swingPanelHeight")));
        setCoverWidth(cursor.getDouble(cursor.getColumnIndex("coverWidth")));
        setCoverHeight(cursor.getDouble(cursor.getColumnIndex("coverHeight")));
        setCoverToOpening(cursor.getDouble(cursor.getColumnIndex("coverToOpening")));
        setCoverAff(cursor.getDouble(cursor.getColumnIndex("coverAff")));
        setNotes(cursor.getString(cursor.getColumnIndex("notes")));
        setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
    }

    public JSONArray getPostJSON(){
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(0, getJSON("cop_name", getCopName()));
            jsonArray.put(1, getJSON("options", getOptions()));
            jsonArray.put(2, getJSON("return_hinging", getReturnHinging()));
            jsonArray.put(3, getJSON("return_panel_width", getOptions().equals("Applied")?getDoubleForJSON(getReturnPanelWidth()):""));
            jsonArray.put(4, getJSON("return_panel_height", getOptions().equals("Applied")?getDoubleForJSON(getReturnPanelHeight()):""));
            jsonArray.put(5, getJSON("cover_width", getOptions().equals("Applied")?getDoubleForJSON(getCoverWidth()):""));
            jsonArray.put(6, getJSON("cover_height", getOptions().equals("Applied")?getDoubleForJSON(getCoverHeight()):""));
            jsonArray.put(7, getJSON("cover_to_opening", getOptions().equals("Applied")?getDoubleForJSON(getCoverToOpening()):""));
            jsonArray.put(8, getJSON("swing_panel_width", getOptions().equals("Swing")?getDoubleForJSON(getSwingPanelWidth()):""));
            jsonArray.put(9, getJSON("swing_panel_height", getOptions().equals("Swing")?getDoubleForJSON(getSwingPanelHeight()):""));
            jsonArray.put(10, getJSON("cover_aff", getDoubleForJSON(getCoverAff())));
            jsonArray.put(11, getJSON("notes", getNotes()));

            for (int i = 0; i < getPhotosList().size(); i++){
                PhotoData photoData = getPhotosList().get(i);
                jsonArray.put(i+12, photoData.getPostJSON(i + 1));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return jsonArray;
    }
}

