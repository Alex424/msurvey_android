package com.mad.survey.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mad.survey.utils.Utils;

import org.json.JSONArray;

import java.io.Serializable;

public class HallEntranceData extends  BaseData implements Serializable {

    private int carNum = 0;
    private int floorNum = 0;
    private String floorDescription = "";

    private int doorType = 0;
    private int direction = 0;

    private double leftSideA = -1;
    private double leftSideB = -1;
    private double leftSideC = -1;
    private double leftSideD = -1;

    private double rightSideA = -1;
    private double rightSideB = -1;
    private double rightSideC = -1;
    private double rightSideD = -1;

    private double height = -1;

    private double transomMeasurementsA = -1;
    private double transomMeasurementsB = -1;
    private double transomMeasurementsC = -1;
    private double transomMeasurementsD = -1;
    private double transomMeasurementsE = -1;
    private double transomMeasurementsF = -1;
    private double transomMeasurementsG = -1;
    private double transomMeasurementsH = -1;
    private double transomMeasurementsI = -1;

    private String notes = "";

    public HallEntranceData() {

    }

    public String getFloorDescription() {
        return floorDescription;
    }

    public void setFloorDescription(String floorDescription) {
        this.floorDescription = floorDescription;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public int getDoorType() {
        return doorType;
    }

    public void setDoorType(int doorType) {
        this.doorType = doorType;
    }

    public int getCarNum() {
        return carNum;
    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }


    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public double getLeftSideA() {
        return leftSideA;
    }

    public void setLeftSideA(double leftSideA) {
        this.leftSideA = leftSideA;
    }

    public double getLeftSideB() {
        return leftSideB;
    }

    public void setLeftSideB(double leftSideB) {
        this.leftSideB = leftSideB;
    }

    public double getLeftSideC() {
        return leftSideC;
    }

    public void setLeftSideC(double leftSideC) {
        this.leftSideC = leftSideC;
    }

    public double getLeftSideD() {
        return leftSideD;
    }

    public void setLeftSideD(double leftSideD) {
        this.leftSideD = leftSideD;
    }

    public double getRightSideA() {
        return rightSideA;
    }

    public void setRightSideA(double rightSideA) {
        this.rightSideA = rightSideA;
    }

    public double getRightSideB() {
        return rightSideB;
    }

    public void setRightSideB(double rightSideB) {
        this.rightSideB = rightSideB;
    }

    public double getRightSideC() {
        return rightSideC;
    }

    public void setRightSideC(double rightSideC) {
        this.rightSideC = rightSideC;
    }

    public double getRightSideD() {
        return rightSideD;
    }

    public void setRightSideD(double rightSideD) {
        this.rightSideD = rightSideD;
    }

    public double getTransomMeasurementsA() {
        return transomMeasurementsA;
    }

    public void setTransomMeasurementsA(double transomMeasurementsA) {
        this.transomMeasurementsA = transomMeasurementsA;
    }

    public double getTransomMeasurementsB() {
        return transomMeasurementsB;
    }

    public void setTransomMeasurementsB(double transomMeasurementsB) {
        this.transomMeasurementsB = transomMeasurementsB;
    }

    public double getTransomMeasurementsC() {
        return transomMeasurementsC;
    }

    public void setTransomMeasurementsC(double transomMeasurementsC) {
        this.transomMeasurementsC = transomMeasurementsC;
    }

    public double getTransomMeasurementsD() {
        return transomMeasurementsD;
    }

    public void setTransomMeasurementsD(double transomMeasurementsD) {
        this.transomMeasurementsD = transomMeasurementsD;
    }

    public double getTransomMeasurementsE() {
        return transomMeasurementsE;
    }

    public void setTransomMeasurementsE(double transomMeasurementsE) {
        this.transomMeasurementsE = transomMeasurementsE;
    }

    public double getTransomMeasurementsF() {
        return transomMeasurementsF;
    }

    public void setTransomMeasurementsF(double transomMeasurementsF) {
        this.transomMeasurementsF = transomMeasurementsF;
    }

    public double getTransomMeasurementsG() {
        return transomMeasurementsG;
    }

    public void setTransomMeasurementsG(double transomMeasurementsG) {
        this.transomMeasurementsG = transomMeasurementsG;
    }

    public double getTransomMeasurementsH() {
        return transomMeasurementsH;
    }

    public void setTransomMeasurementsH(double transomMeasurementsH) {
        this.transomMeasurementsH = transomMeasurementsH;
    }

    public double getTransomMeasurementsI() {
        return transomMeasurementsI;
    }

    public void setTransomMeasurementsI(double transomMeasurementsI) {
        this.transomMeasurementsI = transomMeasurementsI;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ContentValues generateContentValuesFromObject() {
        ContentValues values = new ContentValues();
        values.put("projectId", getProjectId());
        values.put("bankId", getBankId());
        values.put("doorType", getDoorType());
        values.put("carNum", getCarNum());
        values.put("floorNum" , getFloorNum());
        values.put("floorDescription" , getFloorDescription());

        values.put("direction", getDirection());
        values.put("leftSideA", getLeftSideA());
        values.put("leftSideB", getLeftSideB());
        values.put("leftSideC", getLeftSideC());
        values.put("leftSideD", getLeftSideD());
        values.put("rightSideA", getRightSideA());
        values.put("rightSideB", getRightSideB());
        values.put("rightSideC", getRightSideC());
        values.put("rightSideD", getRightSideD());
        values.put("height", getHeight());
        values.put("transomMeasurementsA", getTransomMeasurementsA());
        values.put("transomMeasurementsB", getTransomMeasurementsB());
        values.put("transomMeasurementsC", getTransomMeasurementsC());
        values.put("transomMeasurementsD", getTransomMeasurementsD());
        values.put("transomMeasurementsE", getTransomMeasurementsE());
        values.put("transomMeasurementsF", getTransomMeasurementsF());
        values.put("transomMeasurementsG", getTransomMeasurementsG());
        values.put("transomMeasurementsH", getTransomMeasurementsH());
        values.put("transomMeasurementsI", getTransomMeasurementsI());

        values.put("notes", getNotes());
        return values;
    }

    public HallEntranceData(Cursor cursor) {
        super();
        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setProjectId(cursor.getInt(cursor.getColumnIndex("projectId")));
        setBankId(cursor.getInt(cursor.getColumnIndex("bankId")));
        if(cursor.getColumnIndex("bankDesc")>0)
            setBankDesc(cursor.getString(cursor.getColumnIndex("bankDesc")));
        setCarNum(cursor.getInt(cursor.getColumnIndex("carNum")));
        setFloorNum(cursor.getInt(cursor.getColumnIndex("floorNum")));
        setFloorDescription(cursor.getString(cursor.getColumnIndex("floorDescription")));

        setDoorType(cursor.getInt(cursor.getColumnIndex("doorType")));
        setDirection(cursor.getInt(cursor.getColumnIndex("direction")));
        setLeftSideA(cursor.getDouble(cursor.getColumnIndex("leftSideA")));
        setLeftSideB(cursor.getDouble(cursor.getColumnIndex("leftSideB")));
        setLeftSideC(cursor.getDouble(cursor.getColumnIndex("leftSideC")));
        setLeftSideD(cursor.getDouble(cursor.getColumnIndex("leftSideD")));

        setHeight(cursor.getDouble(cursor.getColumnIndex("height")));

        setRightSideA(cursor.getDouble(cursor.getColumnIndex("rightSideA")));
        setRightSideB(cursor.getDouble(cursor.getColumnIndex("rightSideB")));
        setRightSideC(cursor.getDouble(cursor.getColumnIndex("rightSideC")));
        setRightSideD(cursor.getDouble(cursor.getColumnIndex("rightSideD")));

        setTransomMeasurementsA(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsA")));
        setTransomMeasurementsB(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsB")));
        setTransomMeasurementsC(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsC")));
        setTransomMeasurementsD(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsD")));
        setTransomMeasurementsE(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsE")));
        setTransomMeasurementsF(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsF")));
        setTransomMeasurementsG(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsG")));
        setTransomMeasurementsH(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsH")));
        setTransomMeasurementsI(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsI")));

        setNotes(cursor.getString(cursor.getColumnIndex("notes")));

    }

    public String getDoubleForJSON(double value){
        return value < 0? "":(value + "");
    }

    public JSONArray getPostJSON(){
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(0, getJSON("floor_number", getFloorDescription()+""));
            String doorType = "";
            switch (getDoorType()){
                case 3:
                    doorType = "Center";
                    break;
                case 2:
                    doorType = "Single Speed";
                    break;
                case 1:
                    doorType = "Two speed";
            }
            jsonArray.put(1, getJSON("door_type", doorType));
            String direction = "";
            switch (getDirection()){
                case 1:
                    direction = "slides_open_to_left";
                    break;
                case 2:
                    direction = "slides_open_to_right";
                    break;

            }
            jsonArray.put(2, getJSON("direction", getDoorType()!=3?direction:""));
            jsonArray.put(3, getJSON("left_side_A", getDoubleForJSON(getLeftSideA())));
            jsonArray.put(4, getJSON("left_side_B", getDoubleForJSON(getLeftSideB())));
            jsonArray.put(5, getJSON("left_side_C", getDoubleForJSON(getLeftSideC())));
            jsonArray.put(6, getJSON("left_side_D", getDoubleForJSON(getLeftSideD())));
            jsonArray.put(7, getJSON("right_side_A", getDoubleForJSON(getRightSideA())));
            jsonArray.put(8, getJSON("right_side_B", getDoubleForJSON(getRightSideB())));
            jsonArray.put(9, getJSON("right_side_C", getDoubleForJSON(getRightSideC())));
            jsonArray.put(10, getJSON("right_side_D", getDoubleForJSON(getRightSideD())));
            jsonArray.put(11, getJSON("height", getDoubleForJSON(getHeight())));
            jsonArray.put(12, getJSON("transom_measurements_A", getDoubleForJSON(getTransomMeasurementsA())));
            jsonArray.put(13, getJSON("transom_measurements_B", getDoubleForJSON(getTransomMeasurementsB())));
            jsonArray.put(14, getJSON("transom_measurements_C", getDoubleForJSON(getTransomMeasurementsC())));
            jsonArray.put(15, getJSON("transom_measurements_D", getDoubleForJSON(getTransomMeasurementsD())));
            jsonArray.put(16, getJSON("transom_measurements_E", getDoubleForJSON(getTransomMeasurementsE())));
            jsonArray.put(17, getJSON("transom_measurements_F", getDoubleForJSON(getTransomMeasurementsF())));
            jsonArray.put(18, getJSON("transom_measurements_G", getDoubleForJSON(getTransomMeasurementsG())));
            jsonArray.put(19, getJSON("transom_measurements_H", getDoorType()!=2?getDoubleForJSON(getTransomMeasurementsH()):""));
            jsonArray.put(20, getJSON("transom_measurements_I", getDoorType()!=2?getDoubleForJSON(getTransomMeasurementsI()):""));
            jsonArray.put(21, getJSON("notes", getNotes()));

            for (int i = 0; i < getPhotosList().size(); i++){
                PhotoData photoData = getPhotosList().get(i);
                jsonArray.put(i+22, photoData.getPostJSON(i + 1));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return jsonArray;
    }

    public String getDoorTypeString(){
        String retStr = "";
        switch (getDoorType()){
            case 3:
                retStr = "Center";
                break;
            case 2:
                retStr = "Single Speed";
                break;
            case 1:
                retStr = "2 Speed";
        }

        return retStr;
    }
}
