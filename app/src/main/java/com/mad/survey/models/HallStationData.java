package com.mad.survey.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.mad.survey.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class HallStationData extends BaseData implements Serializable {

    private int hallStationNum = 0;
    private String floorNumber = "";
    private int floorNum = 0;
    private String description = "";
    private String mount = "";
    private String wallFinish = "";
    private double width = -1, height = -1;
    private double screwCenterWidth = -1, screwCenterHeight = -1;
    private double affValue = -1;
    private String notes = "";
    private String sameAs = "";
    private String uuid = "";

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public int getHallStationNum() {
        return hallStationNum;
    }

    public void setHallStationNum(int hallStationNum) {
        this.hallStationNum = hallStationNum;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getMount() {
        return mount;
    }

    public void setMount(String mount) {
        this.mount = mount;
    }

    public String getWallFinish() {
        return wallFinish;
    }

    public void setWallFinish(String wallFinish) {
        this.wallFinish = wallFinish;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
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

    public double getAffValue() {
        return affValue;
    }

    public void setAffValue(double affValue) {
        this.affValue = affValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSameAs() {
        return sameAs;
    }

    public void setSameAs(String sameAs) {
        this.sameAs = sameAs;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public HallStationData() {
        setUuid(Utils.createUUID());
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ContentValues generateContentValuesFromObject() {
        ContentValues values = new ContentValues();
        values.put("projectId", getProjectId());
        values.put("bankId", getBankId());
        values.put("hallStationNum", getHallStationNum());
        values.put("floorNum", getFloorNum());
        values.put("floorNumber", getFloorNumber());
        values.put("description", getDescription());
        values.put("mount", getMount());
        values.put("wallFinish", getWallFinish());
        values.put("width", getWidth());
        values.put("height", getHeight());
        values.put("screwCenterHeight", getScrewCenterHeight());
        values.put("screwCenterWidth", getScrewCenterWidth());
        values.put("affValue", getAffValue());
        values.put("notes", getNotes());
        values.put("sameAs", getSameAs());
        values.put("uuid", getUuid());


        return values;
    }

    public HallStationData(Cursor cursor) {
        super();
        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setProjectId(cursor.getInt(cursor.getColumnIndex("projectId")));
        if (cursor.getColumnIndex("bankDesc") > 0) {
            setBankDesc(cursor.getString(cursor.getColumnIndex("bankDesc")));
        }
        setBankId(cursor.getInt(cursor.getColumnIndex("bankId")));
        setHallStationNum(cursor.getInt(cursor.getColumnIndex("hallStationNum")));
        setFloorNumber(cursor.getString(cursor.getColumnIndex("floorNumber")));
        setFloorNum(cursor.getInt(cursor.getColumnIndex("floorNum")));
        setDescription(cursor.getString(cursor.getColumnIndex("description")));
        setMount(cursor.getString(cursor.getColumnIndex("mount")));
        setWallFinish(cursor.getString(cursor.getColumnIndex("wallFinish")));
        setWidth(cursor.getDouble(cursor.getColumnIndex("width")));
        setHeight(cursor.getDouble(cursor.getColumnIndex("height")));
        setScrewCenterWidth(cursor.getDouble(cursor.getColumnIndex("screwCenterWidth")));
        setScrewCenterHeight(cursor.getDouble(cursor.getColumnIndex("screwCenterHeight")));
        setAffValue(cursor.getDouble(cursor.getColumnIndex("affValue")));
        setNotes(cursor.getString(cursor.getColumnIndex("notes")));
        setSameAs(cursor.getString(cursor.getColumnIndex("sameAs")));
        setUuid(cursor.getString(cursor.getColumnIndex("uuid")));

    }

    public String getDoubleForJSON(double value){
        return value < 0? "":(value + "");
    }

    public JSONArray getPostJSON() {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(0, getJSON("floor_number", getFloorNumber()));
            jsonArray.put(1, getJSON("description", getDescription()));
            jsonArray.put(2, getJSON("mount", getMount()));
            jsonArray.put(3, getJSON("wall_finish", getWallFinish()));
            jsonArray.put(4, getJSON("width", getDoubleForJSON(getWidth())));
            jsonArray.put(5, getJSON("height", getDoubleForJSON(getHeight())));
            jsonArray.put(6, getJSON("screw_center_width", getDoubleForJSON(getScrewCenterWidth())));
            jsonArray.put(7, getJSON("screw_center_height", getDoubleForJSON(getScrewCenterHeight())));
            jsonArray.put(8, getJSON("bottom_of_plate_aff", getDoubleForJSON(getAffValue())));
            //jsonArray.put(9,getJSON("uuid",getUuid()));
            //jsonArray.put(10,getJSON("sameas",getSameAs()));
            jsonArray.put(9, getJSON("notes", getNotes()));

            int i;
            for (i = 0; i < getPhotosList().size(); i++) {
                PhotoData photoData = getPhotosList().get(i);
                jsonArray.put(i + 10, photoData.getPostJSON(i + 1));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}
