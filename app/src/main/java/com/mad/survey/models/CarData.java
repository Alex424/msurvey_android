package com.mad.survey.models;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarData extends BaseData implements Serializable {

    private int carNum = 0;
    private String carNumber = "";
    private String jobType = "";
    private String weightScale = "KG";
    private double capacityWeight = -1;
    private int capacityNumberPersons = 0;
    private String installNumber = "";
    private String description = "";
    private String doorsCoinciding = "";
    private int numberOfOpenings = 0;
    private String floorMarkings = "";
    private double frontDoorOpeningHeight = -1,frontDoorSlideJambWidth = -1,frontDoorStrikeJambWidth = -1;
    private double rearDoorOpeningHeight = -1,rearDoorSlideJambWidth = -1 , rearDoorStrikeJambWidth = -1;
    private double handRailHeightFromFloor = -1,handRailDistanceFromWall = -1,handRailDistanceFromReturn = -1;
    private int isThereHandRail = 0, isThereRearDoor = 0 , isThereCDI = 0 , isThereSPI = 0;
    private String notes = "";
    private int numberOfCops = 0;
    private  List<CopData> copsList = new ArrayList<>();
    //-------------CDI--------------
    private String mountCDI = "";
    private int numberPerCabCDI = -1;
    private double coverWidthCDI = -1;
    private double coverHeightCDI = -1;
    private double depthCDI = -1;
    private double coverScrewCenterWidthCDI = -1;
    private double coverScrewCenterHeightCDI = -1;
    private List<PhotoData> cdiPhotosList = new ArrayList<>();
    private String notesCDI = "";
    //---------------------
    //--------------SeparatePi---
    private String mountSPI = "";
    private int numberPerCabSPI = 0;
    private double coverWidthSPI = -1;
    private double coverHeightSPI = -1;
    private double depthSPI = -1;
    private double coverScrewCenterWidthSPI = -1;
    private double coverScrewCenterHeightSPI = -1;
    private double spaceAvailableWidthSPI = -1;
    private double spaceAvailableHeightSPI = -1;
    private List<PhotoData> spiPhotosList = new ArrayList<>();
    private String notesSPI = "";

    //---------------------------


    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public List<PhotoData> getCdiPhotosList() {
        return cdiPhotosList;
    }

    public void setCdiPhotosList(List<PhotoData> cdiPhotosList) {
        this.cdiPhotosList = cdiPhotosList;
    }

    public List<PhotoData> getSpiPhotosList() {
        return spiPhotosList;
    }

    public void setSpiPhotosList(List<PhotoData> spiPhotosList) {
        this.spiPhotosList = spiPhotosList;
    }

    public List<CopData> getCopsList() {
        return copsList;
    }

    public void setCopsList(List<CopData> copsList) {
        this.copsList = copsList;
    }

    public double getCoverHeightSPI() {
        return coverHeightSPI;
    }

    public void setCoverHeightSPI(double coverHeightSPI) {
        this.coverHeightSPI = coverHeightSPI;
    }

    public String getNotesCDI() {
        return notesCDI;
    }

    public void setNotesCDI(String notesCDI) {
        this.notesCDI = notesCDI;
    }

    public String getNotesSPI() {
        return notesSPI;
    }

    public void setNotesSPI(String notesSPI) {
        this.notesSPI = notesSPI;
    }

    public double getCoverScrewCenterHeightCDI() {
        return coverScrewCenterHeightCDI;
    }

    public void setCoverScrewCenterHeightCDI(double coverScrewCenterHeightCDI) {
        this.coverScrewCenterHeightCDI = coverScrewCenterHeightCDI;
    }

    public int getNumberPerCabSPI() {
        return numberPerCabSPI;
    }

    public void setNumberPerCabSPI(int numberPerCabSPI) {
        this.numberPerCabSPI = numberPerCabSPI;
    }

    private String uuid = "";

    public int getIsThereCDI() {
        return isThereCDI;
    }

    public void setIsThereCDI(int isThereCDI) {
        this.isThereCDI = isThereCDI;
    }

    public int getIsThereSPI() {
        return isThereSPI;
    }

    public void setIsThereSPI(int isThereSPI) {
        this.isThereSPI = isThereSPI;
    }

    public String getMountCDI() {
        return mountCDI;
    }

    public void setMountCDI(String mountCDI) {
        this.mountCDI = mountCDI;
    }

    public int getNumberPerCabCDI() {
        return numberPerCabCDI;
    }

    public void setNumberPerCabCDI(int numberPerCabCDI) {
        this.numberPerCabCDI = numberPerCabCDI;
    }

    public double getCoverWidthCDI() {
        return coverWidthCDI;
    }

    public void setCoverWidthCDI(double coverWidthCDI) {
        this.coverWidthCDI = coverWidthCDI;
    }

    public double getCoverHeightCDI() {
        return coverHeightCDI;
    }

    public void setCoverHeightCDI(double coverHeightCDI) {
        this.coverHeightCDI = coverHeightCDI;
    }

    public double getDepthCDI() {
        return depthCDI;
    }

    public void setDepthCDI(double depthCDI) {
        this.depthCDI = depthCDI;
    }

    public double getCoverScrewCenterWidthCDI() {
        return coverScrewCenterWidthCDI;
    }

    public void setCoverScrewCenterWidthCDI(double coverScrewCenterWidthCDI) {
        this.coverScrewCenterWidthCDI = coverScrewCenterWidthCDI;
    }



    public String getMountSPI() {
        return mountSPI;
    }

    public void setMountSPI(String mountSPI) {
        this.mountSPI = mountSPI;
    }


    public double getCoverWidthSPI() {
        return coverWidthSPI;
    }

    public void setCoverWidthSPI(double coverWidthSPI) {
        this.coverWidthSPI = coverWidthSPI;
    }


    public double getDepthSPI() {
        return depthSPI;
    }

    public void setDepthSPI(double depthSPI) {
        this.depthSPI = depthSPI;
    }

    public double getCoverScrewCenterWidthSPI() {
        return coverScrewCenterWidthSPI;
    }

    public void setCoverScrewCenterWidthSPI(double coverScrewCenterWidthSPI) {
        this.coverScrewCenterWidthSPI = coverScrewCenterWidthSPI;
    }

    public double getCoverScrewCenterHeightSPI() {
        return coverScrewCenterHeightSPI;
    }

    public void setCoverScrewCenterHeightSPI(double coverScrewCenterHeightSPI) {
        this.coverScrewCenterHeightSPI = coverScrewCenterHeightSPI;
    }

    public double getSpaceAvailableWidthSPI() {
        return spaceAvailableWidthSPI;
    }

    public void setSpaceAvailableWidthSPI(double spaceAvailableWidthSPI) {
        this.spaceAvailableWidthSPI = spaceAvailableWidthSPI;
    }

    public double getSpaceAvailableHeightSPI() {
        return spaceAvailableHeightSPI;
    }

    public void setSpaceAvailableHeightSPI(double spaceAvailableHeightSPI) {
        this.spaceAvailableHeightSPI = spaceAvailableHeightSPI;
    }

    public int getNumberOfCops() {
        return numberOfCops;
    }

    public void setNumberOfCops(int numberOfCops) {
        this.numberOfCops = numberOfCops;
    }


    public int getCarNum() {
        return carNum;
    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getWeightScale() {
        return weightScale;
    }

    public void setWeightScale(String weightScale) {
        this.weightScale = weightScale;
    }

    public double getCapacityWeight() {
        return capacityWeight;
    }

    public void setCapacityWeight(double capacityWeight) {
        this.capacityWeight = capacityWeight;
    }

    public int getCapacityNumberPersons() {
        return capacityNumberPersons;
    }

    public void setCapacityNumberPersons(int capacityNumberPersons) {
        this.capacityNumberPersons = capacityNumberPersons;
    }

    public String getInstallNumber() {
        return installNumber;
    }

    public void setInstallNumber(String installNumber) {
        this.installNumber = installNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoorsCoinciding() {
        return doorsCoinciding;
    }

    public void setDoorsCoinciding(String doorsCoinciding) {
        this.doorsCoinciding = doorsCoinciding;
    }

    public int getNumberOfOpenings() {
        return numberOfOpenings;
    }

    public void setNumberOfOpenings(int numberOfOpenings) {
        this.numberOfOpenings = numberOfOpenings;
    }

    public String getFloorMarkings() {
        return floorMarkings;
    }

    public void setFloorMarkings(String floorMarkings) {
        this.floorMarkings = floorMarkings;
    }

    public double getFrontDoorOpeningHeight() {
        return frontDoorOpeningHeight;
    }

    public void setFrontDoorOpeningHeight(double frontDoorOpeningHeight) {
        this.frontDoorOpeningHeight = frontDoorOpeningHeight;
    }

    public double getFrontDoorSlideJambWidth() {
        return frontDoorSlideJambWidth;
    }

    public void setFrontDoorSlideJambWidth(double frontDoorSlideJambWidth) {
        this.frontDoorSlideJambWidth = frontDoorSlideJambWidth;
    }

    public double getFrontDoorStrikeJambWidth() {
        return frontDoorStrikeJambWidth;
    }

    public void setFrontDoorStrikeJambWidth(double frontDoorStrikeJambWidth) {
        this.frontDoorStrikeJambWidth = frontDoorStrikeJambWidth;
    }

    public double getRearDoorOpeningHeight() {
        return rearDoorOpeningHeight;
    }

    public void setRearDoorOpeningHeight(double rearDoorOpeningHeight) {
        this.rearDoorOpeningHeight = rearDoorOpeningHeight;
    }

    public double getRearDoorSlideJambWidth() {
        return rearDoorSlideJambWidth;
    }

    public void setRearDoorSlideJambWidth(double rearDoorSlideJambWidth) {
        this.rearDoorSlideJambWidth = rearDoorSlideJambWidth;
    }

    public double getRearDoorStrikeJambWidth() {
        return rearDoorStrikeJambWidth;
    }

    public void setRearDoorStrikeJambWidth(double rearDoorStrikeJambWidth) {
        this.rearDoorStrikeJambWidth = rearDoorStrikeJambWidth;
    }

    public double getHandRailHeightFromFloor() {
        return handRailHeightFromFloor;
    }

    public void setHandRailHeightFromFloor(double handRailHeightFromFloor) {
        this.handRailHeightFromFloor = handRailHeightFromFloor;
    }

    public double getHandRailDistanceFromWall() {
        return handRailDistanceFromWall;
    }

    public void setHandRailDistanceFromWall(double handRailDistanceFromWall) {
        this.handRailDistanceFromWall = handRailDistanceFromWall;
    }

    public double getHandRailDistanceFromReturn() {
        return handRailDistanceFromReturn;
    }

    public void setHandRailDistanceFromReturn(double handRailDistanceFromReturn) {
        this.handRailDistanceFromReturn = handRailDistanceFromReturn;
    }

    public int getIsThereHandRail() {
        return isThereHandRail;
    }

    public void setIsThereHandRail(int isThereHandRail) {
        this.isThereHandRail = isThereHandRail;
    }

    public int getIsThereRearDoor() {
        return isThereRearDoor;
    }

    public void setIsThereRearDoor(int isThereRearDoor) {
        this.isThereRearDoor = isThereRearDoor;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        values.put("carNumber",getCarNumber());
        values.put("weightScale",getWeightScale());
        values.put("capacityWeight",getCapacityWeight());
        values.put("capacityNumberPersons",getCapacityNumberPersons());
        values.put("installNumber",getInstallNumber());
        values.put("description",getDescription());
        values.put("doorsCoinciding",getDoorsCoinciding());
        values.put("numberOfOpenings",getNumberOfOpenings());
        values.put("floorMarkings",getFloorMarkings());
        values.put("frontDoorOpeningHeight",getFrontDoorOpeningHeight());
        values.put("frontDoorSlideJambWidth",getFrontDoorSlideJambWidth());
        values.put("frontDoorStrikeJambWidth",getFrontDoorStrikeJambWidth());
        values.put("rearDoorOpeningHeight",getRearDoorOpeningHeight());
        values.put("rearDoorSlideJambWidth",getRearDoorSlideJambWidth());
        values.put("rearDoorStrikeJambWidth",getRearDoorStrikeJambWidth());
        values.put("handRailHeightFromFloor",getHandRailHeightFromFloor());
        values.put("handRailDistanceFromWall",getHandRailDistanceFromWall());
        values.put("handRailDistanceFromReturn",getHandRailDistanceFromReturn());
        values.put("isThereHandRail",getIsThereHandRail());
        values.put("isThereRearDoor",getIsThereRearDoor());

        values.put("isThereCDI",getIsThereCDI());
        values.put("mountCDI",getMountCDI());
        values.put("numberPerCabCDI",getNumberPerCabCDI());
        values.put("coverWidthCDI",getCoverWidthCDI());
        values.put("coverHeightCDI",getCoverHeightCDI());
        values.put("coverScrewCenterHeightCDI",getCoverScrewCenterHeightCDI());
        values.put("coverScrewCenterWidthCDI",getCoverScrewCenterWidthCDI());
        values.put("depthCDI",getDepthCDI());
        values.put("notesCDI",getNotesCDI());

        values.put("isThereSPI",getIsThereSPI());
        values.put("mountSPI",getMountSPI());
        values.put("depthSPI",getDepthSPI());
        values.put("coverHeightSPI",getCoverHeightSPI());
        values.put("coverWidthSPI",getCoverWidthSPI());
        values.put("numberPerCabSPI",getNumberPerCabSPI());
        values.put("spaceAvailableHeightSPI",getSpaceAvailableHeightSPI());
        values.put("spaceAvailableWidthSPI",getSpaceAvailableWidthSPI());
        values.put("coverScrewCenterHeightSPI",getCoverScrewCenterHeightSPI());
        values.put("coverScrewCenterWidthSPI",getCoverScrewCenterWidthSPI());
        values.put("notesSPI",getNotesSPI());

        values.put("notes",getNotes());
        values.put("numberOfCops",getNumberOfCops());
        values.put("uuid", getUuid());
        return values;
    }
    public CarData(){}
    public CarData(Cursor cursor) {
        super();
        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setProjectId(cursor.getInt(cursor.getColumnIndex("projectId")));
        setBankId(cursor.getInt(cursor.getColumnIndex("bankId")));
        if(cursor.getColumnIndex("bankDesc")>0)
            setBankDesc(cursor.getString(cursor.getColumnIndex("bankDesc")));
        setCarNum(cursor.getInt(cursor.getColumnIndex("carNum")));
        setCarNumber(cursor.getString(cursor.getColumnIndex("carNumber")));
        setWeightScale(cursor.getString(cursor.getColumnIndex("weightScale")));
        setCapacityWeight(cursor.getDouble(cursor.getColumnIndex("capacityWeight")));
        setCapacityNumberPersons(cursor.getInt(cursor.getColumnIndex("capacityNumberPersons")));
        setInstallNumber(cursor.getString(cursor.getColumnIndex("installNumber")));
        setDescription(cursor.getString(cursor.getColumnIndex("description")));
        setDoorsCoinciding(cursor.getString(cursor.getColumnIndex("doorsCoinciding")));
        setNumberOfOpenings(cursor.getInt(cursor.getColumnIndex("numberOfOpenings")));
        setFloorMarkings(cursor.getString(cursor.getColumnIndex("floorMarkings")));
        setFrontDoorOpeningHeight(cursor.getDouble(cursor.getColumnIndex("frontDoorOpeningHeight")));
        setFrontDoorSlideJambWidth(cursor.getDouble(cursor.getColumnIndex("frontDoorSlideJambWidth")));
        setFrontDoorStrikeJambWidth(cursor.getDouble(cursor.getColumnIndex("frontDoorStrikeJambWidth")));
        setRearDoorOpeningHeight(cursor.getDouble(cursor.getColumnIndex("rearDoorOpeningHeight")));
        setRearDoorSlideJambWidth(cursor.getDouble(cursor.getColumnIndex("rearDoorSlideJambWidth")));
        setRearDoorStrikeJambWidth(cursor.getDouble(cursor.getColumnIndex("rearDoorStrikeJambWidth")));
        setHandRailHeightFromFloor(cursor.getDouble(cursor.getColumnIndex("handRailHeightFromFloor")));
        setHandRailDistanceFromWall(cursor.getDouble(cursor.getColumnIndex("handRailDistanceFromWall")));
        setHandRailDistanceFromReturn(cursor.getDouble(cursor.getColumnIndex("handRailDistanceFromReturn")));
        setIsThereHandRail(cursor.getInt(cursor.getColumnIndex("isThereHandRail")));
        setIsThereRearDoor(cursor.getInt(cursor.getColumnIndex("isThereRearDoor")));
        setNotes(cursor.getString(cursor.getColumnIndex("notes")));
        setNumberOfCops(cursor.getInt(cursor.getColumnIndex("numberOfCops")));


        setIsThereCDI(cursor.getInt(cursor.getColumnIndex("isThereCDI")));
        setMountCDI(cursor.getString(cursor.getColumnIndex("mountCDI")));
        setNumberPerCabCDI(cursor.getInt(cursor.getColumnIndex("numberPerCabCDI")));
        setCoverWidthCDI(cursor.getDouble(cursor.getColumnIndex("coverWidthCDI")));
        setCoverHeightCDI(cursor.getDouble(cursor.getColumnIndex("coverHeightCDI")));
        setCoverScrewCenterHeightCDI(cursor.getDouble(cursor.getColumnIndex("coverScrewCenterHeightCDI")));
        setCoverScrewCenterWidthCDI(cursor.getDouble(cursor.getColumnIndex("coverScrewCenterWidthCDI")));
        setDepthCDI(cursor.getDouble(cursor.getColumnIndex("depthCDI")));
        setNotesCDI(cursor.getString(cursor.getColumnIndex("notesCDI")));


        setIsThereSPI(cursor.getInt(cursor.getColumnIndex("isThereSPI")));
        setMountSPI(cursor.getString(cursor.getColumnIndex("mountSPI")));
        setDepthSPI(cursor.getDouble(cursor.getColumnIndex("depthSPI")));
        setCoverHeightSPI(cursor.getDouble(cursor.getColumnIndex("coverHeightSPI")));
        setCoverWidthSPI(cursor.getDouble(cursor.getColumnIndex("coverWidthSPI")));
        setNumberPerCabSPI(cursor.getInt(cursor.getColumnIndex("numberPerCabSPI")));
        setSpaceAvailableHeightSPI(cursor.getDouble(cursor.getColumnIndex("spaceAvailableHeightSPI")));
        setSpaceAvailableWidthSPI(cursor.getDouble(cursor.getColumnIndex("spaceAvailableWidthSPI")));
        setCoverScrewCenterHeightSPI(cursor.getDouble(cursor.getColumnIndex("coverScrewCenterHeightSPI")));
        setCoverScrewCenterWidthSPI(cursor.getDouble(cursor.getColumnIndex("coverScrewCenterWidthSPI")));
        setNotesSPI(cursor.getString(cursor.getColumnIndex("notesSPI")));

        setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
    }



    public JSONObject getPostJSON(){
        JSONObject jsonObject = new JSONObject();
        try {

            JSONArray summaryJSON = new JSONArray();
            summaryJSON.put(0, getJSON("car_number", getCarNumber()));
            summaryJSON.put(1, getJSON("job_type",getJobType()));
            summaryJSON.put(2, getJSON("weight_scale", getWeightScale()));
            summaryJSON.put(3, getJSON("capacity_weight", getDoubleForJSON(getCapacityWeight())));
            summaryJSON.put(4, getJSON("capacity_number_persons", getCapacityNumberPersons()+""));
            summaryJSON.put(5, getJSON("install_number", getInstallNumber()));
            summaryJSON.put(6, getJSON("description", getDescription()));
            summaryJSON.put(7, getJSON("doors_coinciding", getDoorsCoinciding()));
            summaryJSON.put(8, getJSON("number_of_openings", getNumberOfOpenings()+""));
            summaryJSON.put(9, getJSON("floor_markings", getFloorMarkings()));
            summaryJSON.put(10, getJSON("front_door_opening_height", getDoubleForJSON(getFrontDoorOpeningHeight())));
            summaryJSON.put(11, getJSON("front_door_slide_jamb_width", getDoubleForJSON(getFrontDoorSlideJambWidth())));
            summaryJSON.put(12, getJSON("front_door_strike_jamb_width", getDoubleForJSON(getFrontDoorStrikeJambWidth())));
            summaryJSON.put(13, getJSON("is_there_rear_door", getIsThereRearDoor()==1?"yes":"no"));
            summaryJSON.put(14, getJSON("rear_door_opening_height", getDoubleForJSON(getRearDoorOpeningHeight())));
            summaryJSON.put(15, getJSON("rear_door_slide_jamb_width", getDoubleForJSON(getRearDoorSlideJambWidth())));
            summaryJSON.put(16, getJSON("rear_door_strike_jamb_width", getDoubleForJSON(getRearDoorStrikeJambWidth())));
            summaryJSON.put(17, getJSON("is_there_hand_rail",getIsThereHandRail()==1?"yes":"no"));
            summaryJSON.put(18, getJSON("hand_rail_height_from_floor", getDoubleForJSON(getHandRailHeightFromFloor())));
            summaryJSON.put(19, getJSON("hand_rail_distance_from_wall", getDoubleForJSON(getHandRailDistanceFromWall())));
            summaryJSON.put(20, getJSON("hand_rail_distance_from_return", getDoubleForJSON(getHandRailDistanceFromReturn())));
            summaryJSON.put(21, getJSON("notes", getNotes()));

            int i;
            for (i = 0; i < getPhotosList().size(); i++){
                PhotoData photoData = getPhotosList().get(i);
                summaryJSON.put(i+22, photoData.getPostJSON(i + 1));
            }


            jsonObject.put("car_summary",summaryJSON);

            JSONArray copJSON = new JSONArray();

            for(i = 0; i < getCopsList().size(); i ++){
                CopData copData = getCopsList().get(i);
                copJSON.put(i,copData.getPostJSON());
            }

            jsonObject.put("cop_list",copJSON);

            JSONArray cdaJSON = new JSONArray();
            if(getIsThereCDI() == 1) {
                cdaJSON.put(0, getJSON("mount", getMountCDI()));
                cdaJSON.put(1, getJSON("number_per_cab", getNumberPerCabCDI() + ""));
                cdaJSON.put(2, getJSON("cover_width", getDoubleForJSON(getCoverWidthCDI())));
                cdaJSON.put(3, getJSON("cover_height", getDoubleForJSON(getCoverHeightCDI())));
                cdaJSON.put(4, getJSON("depth", getDoubleForJSON(getDepthCDI())));
                cdaJSON.put(5, getJSON("cover_screw_center_width", getDoubleForJSON(getCoverScrewCenterWidthCDI())));
                cdaJSON.put(6, getJSON("cover_screw_center_height", getDoubleForJSON(getCoverScrewCenterHeightCDI())));
                cdaJSON.put(7, getJSON("notes", getNotesCDI()));

                for (i = 0; i < getCdiPhotosList().size(); i++) {
                    PhotoData photoData = getCdiPhotosList().get(i);
                    cdaJSON.put(i + 8, photoData.getPostJSON(i + 1));
                }
            }
            jsonObject.put("cda", cdaJSON);

            JSONArray separatePIJSON = new JSONArray();
            if(getIsThereSPI() == 1) {
                separatePIJSON.put(0, getJSON("mount", getMountSPI()));
                separatePIJSON.put(1, getJSON("number_per_cab", getNumberPerCabSPI() + ""));
                separatePIJSON.put(2, getJSON("cover_width", getDoubleForJSON(getCoverWidthSPI())));
                separatePIJSON.put(3, getJSON("cover_height", getDoubleForJSON(getCoverHeightSPI())));
                separatePIJSON.put(4, getJSON("depth", getDoubleForJSON(getDepthSPI())));
                separatePIJSON.put(5, getJSON("cover_screw_center_width", getDoubleForJSON(getCoverScrewCenterWidthSPI())));
                separatePIJSON.put(6, getJSON("cover_screw_center_height", getDoubleForJSON(getCoverScrewCenterHeightSPI())));
                separatePIJSON.put(7, getJSON("space_available_width", getDoubleForJSON(getSpaceAvailableWidthSPI())));
                separatePIJSON.put(8, getJSON("space_available_height", getDoubleForJSON(getSpaceAvailableHeightSPI())));
                separatePIJSON.put(9, getJSON("notes", getNotesSPI()));

                for (i = 0; i < getSpiPhotosList().size(); i++) {
                    PhotoData photoData = getSpiPhotosList().get(i);
                    separatePIJSON.put(i + 10, photoData.getPostJSON(i + 1));
                }


            }
            jsonObject.put("separatepi", separatePIJSON);



        }catch(Exception e){
            e.printStackTrace();
        }

        return jsonObject;
    }
}
