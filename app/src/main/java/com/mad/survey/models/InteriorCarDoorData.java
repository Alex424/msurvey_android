package com.mad.survey.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.mad.survey.globals.GlobalConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class InteriorCarDoorData extends  BaseData implements Serializable {
    private int id = 0;
    private int projectId = 0;
    private int interiorCarId = 0;
    private int doorStyle = 0;

    private String wallType = "";
    private int centerOpening = 0;
    private double width = -1 , height = -1,  sideWallMainWidth = -1,sideWallAuxWidth = -1;
    private double doorOpeningWidth = -1 , doorOpeningHeight = -1;
    private double returnSideWallDepth = -1 , slamSideWallDepth = -1 , slideWallWidth = -1;
    private String typeOfSlamPost = "";
    private String otherSlamPost = "";
    private double slamPostMeasurementsA = -1;
    private double slamPostMeasurementsB = -1;
    private double slamPostMeasurementsC = -1;
    private double slamPostMeasurementsD = -1;
    private double slamPostMeasurementsE = -1;
    private double slamPostMeasurementsF = -1;
    private double slamPostMeasurementsG = -1;
    private double slamPostMeasurementsH = -1;

    private String typeOfFrontReturn = "";
    private double leftSideA = -1;
    private double leftSideB = -1;
    private double leftSideC = -1;
    private double leftSideD = -1;
    private double leftSideE = -1;

    private double rightSideA = -1;
    private double rightSideB = -1;
    private double rightSideC = -1;
    private double rightSideD = -1;
    private double rightSideE = -1;

    private double leftSideATypeB = -1;
    private double leftSideBTypeB = -1;
    private double leftSideCTypeB = -1;

    private double rightSideATypeB = -1;
    private double rightSideBTypeB = -1;
    private double rightSideCTypeB = -1;


    private double frontReturnMeasurementsHeight = -1;
    private String otherFrontReturnMeasurements = "";
    private int carDoorType = 0;
    private int carDoorOpeningDirection = 0;

    private double transomMeasurementsHeight = -1;
    private double transomMeasurementsWidth = -1;
    private double transomMeasurementsLeft = -1;
    private double transomMeasurementsCenterLeft = -1;
    private double transomMeasurementsCenterRight = -1;
    private double transomMeasurementsRight = -1;
    private double transomMeasurementsCenter = -1;

    private double transomProfileHeight = -1;
    private double transomProfileDepth = -1;
    private double transomProfileReturn = -1;
    private double transomProfileColonnade = -1;
    private double transomProfileColonnade2 = -1;

    private double lTransomWidth = -1;
    private double lTransomHeight = -1;
    private double headerReturnHoistWay = -1;
    private double headerThroat = -1;
    private double headerWidth = -1;
    private double headerHeight = -1;
    private double headerReturnWall = -1;
    private double flatFrontLeftWidth = -1;
    private double flatFrontLeftHeight = -1;
    private double flatFrontRightWidth = -1;
    private double flatFrontRightHeight = -1;


    private String isThereNewCop = "";

    private double mainCopWidth = -1;
    private double mainCopHeight = -1;
    private double mainCopLeft = -1;
    private double mainCopRight = -1;
    private double mainCopTop = -1;
    private double mainCopBottom = -1;
    private double mainCopThroat = -1;
    private double mainCopReturn = -1;

    private double auxCopWidth = -1;
    private double auxCopHeight = -1;
    private double auxCopLeft = -1;
    private double auxCopRight = -1;
    private double auxCopTop = -1;
    private double auxCopBottom = -1;
    private double auxCopThroat = -1;
    private double auxCopReturn = -1;

    private String notes = "";
    private String uuid = "";

    @Override
    public int getProjectId() {
        return projectId;
    }

    @Override
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getIsThereNewCop() {
        return isThereNewCop;
    }

    public void setIsThereNewCop(String isThereNewCop) {
        this.isThereNewCop = isThereNewCop;
    }

    public double getLeftSideATypeB() {
        return leftSideATypeB;
    }

    public void setLeftSideATypeB(double leftSideATypeB) {
        this.leftSideATypeB = leftSideATypeB;
    }

    public double getLeftSideBTypeB() {
        return leftSideBTypeB;
    }

    public void setLeftSideBTypeB(double leftSideBTypeB) {
        this.leftSideBTypeB = leftSideBTypeB;
    }

    public double getLeftSideCTypeB() {
        return leftSideCTypeB;
    }

    public void setLeftSideCTypeB(double leftSideCTypeB) {
        this.leftSideCTypeB = leftSideCTypeB;
    }

    public double getRightSideATypeB() {
        return rightSideATypeB;
    }

    public void setRightSideATypeB(double rightSideATypeB) {
        this.rightSideATypeB = rightSideATypeB;
    }

    public double getRightSideBTypeB() {
        return rightSideBTypeB;
    }

    public void setRightSideBTypeB(double rightSideBTypeB) {
        this.rightSideBTypeB = rightSideBTypeB;
    }

    public double getRightSideCTypeB() {
        return rightSideCTypeB;
    }

    public void setRightSideCTypeB(double rightSideCTypeB) {
        this.rightSideCTypeB = rightSideCTypeB;
    }

    public String getTypeOfFrontReturn() {
        return typeOfFrontReturn;
    }

    public double getTransomMeasurementsHeight() {
        return transomMeasurementsHeight;
    }

    public void setTransomMeasurementsHeight(double transomMeasurementsHeight) {
        this.transomMeasurementsHeight = transomMeasurementsHeight;
    }

    public void setTypeOfFrontReturn(String typeOfFrontReturn) {
        this.typeOfFrontReturn = typeOfFrontReturn;
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

    public double getLeftSideE() {
        return leftSideE;
    }

    public void setLeftSideE(double leftSideE) {
        this.leftSideE = leftSideE;
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

    public double getRightSideE() {
        return rightSideE;
    }

    public void setRightSideE(double rightSideE) {
        this.rightSideE = rightSideE;
    }

    public double getFrontReturnMeasurementsHeight() {
        return frontReturnMeasurementsHeight;
    }

    public void setFrontReturnMeasurementsHeight(double frontReturnMeasurementsHeight) {
        this.frontReturnMeasurementsHeight = frontReturnMeasurementsHeight;
    }

    public String getOtherFrontReturnMeasurements() {
        return otherFrontReturnMeasurements;
    }

    public void setOtherFrontReturnMeasurements(String otherFrontReturnMeasurements) {
        this.otherFrontReturnMeasurements = otherFrontReturnMeasurements;
    }

    public int getCarDoorType() {
        return carDoorType;
    }

    public void setCarDoorType(int carDoorType) {
        this.carDoorType = carDoorType;
    }

    public int getCarDoorOpeningDirection() {
        return carDoorOpeningDirection;
    }

    public void setCarDoorOpeningDirection(int carDoorOpeningDirection) {
        this.carDoorOpeningDirection = carDoorOpeningDirection;
    }


    public double getTransomMeasurementsWidth() {
        return transomMeasurementsWidth;
    }

    public void setTransomMeasurementsWidth(double transomMeasurementsWidth) {
        this.transomMeasurementsWidth = transomMeasurementsWidth;
    }

    public double getTransomMeasurementsLeft() {
        return transomMeasurementsLeft;
    }

    public void setTransomMeasurementsLeft(double transomMeasurementsLeft) {
        this.transomMeasurementsLeft = transomMeasurementsLeft;
    }

    public double getTransomMeasurementsCenterLeft() {
        return transomMeasurementsCenterLeft;
    }

    public void setTransomMeasurementsCenterLeft(double transomMeasurementsCenterLeft) {
        this.transomMeasurementsCenterLeft = transomMeasurementsCenterLeft;
    }

    public double getTransomMeasurementsCenterRight() {
        return transomMeasurementsCenterRight;
    }

    public void setTransomMeasurementsCenterRight(double transomMeasurementsCenterRight) {
        this.transomMeasurementsCenterRight = transomMeasurementsCenterRight;
    }

    public double getTransomMeasurementsRight() {
        return transomMeasurementsRight;
    }

    public void setTransomMeasurementsRight(double transomMeasurementsRight) {
        this.transomMeasurementsRight = transomMeasurementsRight;
    }

    public double getTransomMeasurementsCenter() {
        return transomMeasurementsCenter;
    }

    public void setTransomMeasurementsCenter(double transomMeasurementsCenter) {
        this.transomMeasurementsCenter = transomMeasurementsCenter;
    }

    public double getTransomProfileHeight() {
        return transomProfileHeight;
    }

    public void setTransomProfileHeight(double transomProfileHeight) {
        this.transomProfileHeight = transomProfileHeight;
    }

    public double getTransomProfileDepth() {
        return transomProfileDepth;
    }

    public void setTransomProfileDepth(double transomProfileDepth) {
        this.transomProfileDepth = transomProfileDepth;
    }

    public double getTransomProfileReturn() {
        return transomProfileReturn;
    }

    public void setTransomProfileReturn(double transomProfileReturn) {
        this.transomProfileReturn = transomProfileReturn;
    }

    public double getTransomProfileColonnade() {
        return transomProfileColonnade;
    }

    public void setTransomProfileColonnade(double transomProfileColonnade) {
        this.transomProfileColonnade = transomProfileColonnade;
    }

    public double getTransomProfileColonnade2() {
        return transomProfileColonnade2;
    }

    public void setTransomProfileColonnade2(double transomProfileColonnade2) {
        this.transomProfileColonnade2 = transomProfileColonnade2;
    }

    public double getlTransomWidth() {
        return lTransomWidth;
    }

    public void setlTransomWidth(double lTransomWidth) {
        this.lTransomWidth = lTransomWidth;
    }

    public double getlTransomHeight() {
        return lTransomHeight;
    }

    public void setlTransomHeight(double lTransomHeight) {
        this.lTransomHeight = lTransomHeight;
    }

    public double getHeaderReturnHoistWay() {
        return headerReturnHoistWay;
    }

    public void setHeaderReturnHoistWay(double headerReturnHoistWay) {
        this.headerReturnHoistWay = headerReturnHoistWay;
    }

    public double getHeaderThroat() {
        return headerThroat;
    }

    public void setHeaderThroat(double headerThroat) {
        this.headerThroat = headerThroat;
    }

    public double getHeaderWidth() {
        return headerWidth;
    }

    public void setHeaderWidth(double headerWidth) {
        this.headerWidth = headerWidth;
    }

    public double getHeaderHeight() {
        return headerHeight;
    }

    public void setHeaderHeight(double headerHeight) {
        this.headerHeight = headerHeight;
    }

    public double getHeaderReturnWall() {
        return headerReturnWall;
    }

    public void setHeaderReturnWall(double headerReturnWall) {
        this.headerReturnWall = headerReturnWall;
    }

    public double getFlatFrontLeftWidth() {
        return flatFrontLeftWidth;
    }

    public void setFlatFrontLeftWidth(double flatFrontLeftWidth) {
        this.flatFrontLeftWidth = flatFrontLeftWidth;
    }

    public double getFlatFrontLeftHeight() {
        return flatFrontLeftHeight;
    }

    public void setFlatFrontLeftHeight(double flatFrontLeftHeight) {
        this.flatFrontLeftHeight = flatFrontLeftHeight;
    }

    public double getFlatFrontRightWidth() {
        return flatFrontRightWidth;
    }

    public void setFlatFrontRightWidth(double flatFrontRightWidth) {
        this.flatFrontRightWidth = flatFrontRightWidth;
    }

    public double getFlatFrontRightHeight() {
        return flatFrontRightHeight;
    }

    public void setFlatFrontRightHeight(double flatFrontRightHeight) {
        this.flatFrontRightHeight = flatFrontRightHeight;
    }

    public double getMainCopWidth() {
        return mainCopWidth;
    }

    public void setMainCopWidth(double mainCopWidth) {
        this.mainCopWidth = mainCopWidth;
    }

    public double getMainCopHeight() {
        return mainCopHeight;
    }

    public void setMainCopHeight(double mainCopHeight) {
        this.mainCopHeight = mainCopHeight;
    }

    public double getMainCopLeft() {
        return mainCopLeft;
    }

    public void setMainCopLeft(double mainCopLeft) {
        this.mainCopLeft = mainCopLeft;
    }

    public double getMainCopRight() {
        return mainCopRight;
    }

    public void setMainCopRight(double mainCopRight) {
        this.mainCopRight = mainCopRight;
    }

    public double getMainCopTop() {
        return mainCopTop;
    }

    public void setMainCopTop(double mainCopTop) {
        this.mainCopTop = mainCopTop;
    }

    public double getMainCopBottom() {
        return mainCopBottom;
    }

    public void setMainCopBottom(double mainCopBottom) {
        this.mainCopBottom = mainCopBottom;
    }

    public double getMainCopThroat() {
        return mainCopThroat;
    }

    public void setMainCopThroat(double mainCopThroat) {
        this.mainCopThroat = mainCopThroat;
    }

    public double getMainCopReturn() {
        return mainCopReturn;
    }

    public void setMainCopReturn(double mainCopReturn) {
        this.mainCopReturn = mainCopReturn;
    }

    public double getAuxCopWidth() {
        return auxCopWidth;
    }

    public void setAuxCopWidth(double auxCopWidth) {
        this.auxCopWidth = auxCopWidth;
    }

    public double getAuxCopHeight() {
        return auxCopHeight;
    }

    public void setAuxCopHeight(double auxCopHeight) {
        this.auxCopHeight = auxCopHeight;
    }

    public double getAuxCopLeft() {
        return auxCopLeft;
    }

    public void setAuxCopLeft(double auxCopLeft) {
        this.auxCopLeft = auxCopLeft;
    }

    public double getAuxCopRight() {
        return auxCopRight;
    }

    public void setAuxCopRight(double auxCopRight) {
        this.auxCopRight = auxCopRight;
    }

    public double getAuxCopTop() {
        return auxCopTop;
    }

    public void setAuxCopTop(double auxCopTop) {
        this.auxCopTop = auxCopTop;
    }

    public double getAuxCopBottom() {
        return auxCopBottom;
    }

    public void setAuxCopBottom(double auxCopBottom) {
        this.auxCopBottom = auxCopBottom;
    }

    public double getAuxCopThroat() {
        return auxCopThroat;
    }

    public void setAuxCopThroat(double auxCopThroat) {
        this.auxCopThroat = auxCopThroat;
    }

    public double getAuxCopReturn() {
        return auxCopReturn;
    }

    public void setAuxCopReturn(double auxCopReturn) {
        this.auxCopReturn = auxCopReturn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoorStyle() {
        return doorStyle;
    }

    public void setDoorStyle(int doorStyle) {
        this.doorStyle = doorStyle;
    }

    public int getInteriorCarId() {
        return interiorCarId;
    }

    public void setInteriorCarId(int interiorCarId) {
        this.interiorCarId = interiorCarId;
    }

    public String getOtherSlamPost() {
        return otherSlamPost;
    }

    public void setOtherSlamPost(String otherSlamPost) {
        this.otherSlamPost = otherSlamPost;
    }

    public int getCenterOpening() {
        return centerOpening;
    }

    public void setCenterOpening(int centerOpening) {
        this.centerOpening = centerOpening;
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

    public double getSideWallMainWidth() {
        return sideWallMainWidth;
    }

    public void setSideWallMainWidth(double sideWallMainWidth) {
        this.sideWallMainWidth = sideWallMainWidth;
    }

    public double getSideWallAuxWidth() {
        return sideWallAuxWidth;
    }

    public void setSideWallAuxWidth(double sideWallAuxWidth) {
        this.sideWallAuxWidth = sideWallAuxWidth;
    }

    public double getDoorOpeningWidth() {
        return doorOpeningWidth;
    }

    public void setDoorOpeningWidth(double doorOpeningWidth) {
        this.doorOpeningWidth = doorOpeningWidth;
    }

    public double getDoorOpeningHeight() {
        return doorOpeningHeight;
    }

    public void setDoorOpeningHeight(double doorOpeningHeight) {
        this.doorOpeningHeight = doorOpeningHeight;
    }

    public double getReturnSideWallDepth() {
        return returnSideWallDepth;
    }

    public void setReturnSideWallDepth(double returnSideWallDepth) {
        this.returnSideWallDepth = returnSideWallDepth;
    }

    public double getSlamSideWallDepth() {
        return slamSideWallDepth;
    }

    public void setSlamSideWallDepth(double slamSideWallDepth) {
        this.slamSideWallDepth = slamSideWallDepth;
    }

    public double getSlideWallWidth() {
        return slideWallWidth;
    }

    public void setSlideWallWidth(double slideWallWidth) {
        this.slideWallWidth = slideWallWidth;
    }

    public String getTypeOfSlamPost() {
        return typeOfSlamPost;
    }

    public void setTypeOfSlamPost(String typeOfSlamPost) {
        this.typeOfSlamPost = typeOfSlamPost;
    }

    public double getSlamPostMeasurementsA() {
        return slamPostMeasurementsA;
    }

    public void setSlamPostMeasurementsA(double slamPostMeasurementsA) {
        this.slamPostMeasurementsA = slamPostMeasurementsA;
    }

    public double getSlamPostMeasurementsB() {
        return slamPostMeasurementsB;
    }

    public void setSlamPostMeasurementsB(double slamPostMeasurementsB) {
        this.slamPostMeasurementsB = slamPostMeasurementsB;
    }

    public double getSlamPostMeasurementsC() {
        return slamPostMeasurementsC;
    }

    public void setSlamPostMeasurementsC(double slamPostMeasurementsC) {
        this.slamPostMeasurementsC = slamPostMeasurementsC;
    }

    public double getSlamPostMeasurementsD() {
        return slamPostMeasurementsD;
    }

    public void setSlamPostMeasurementsD(double slamPostMeasurementsD) {
        this.slamPostMeasurementsD = slamPostMeasurementsD;
    }

    public double getSlamPostMeasurementsE() {
        return slamPostMeasurementsE;
    }

    public void setSlamPostMeasurementsE(double slamPostMeasurementsE) {
        this.slamPostMeasurementsE = slamPostMeasurementsE;
    }

    public double getSlamPostMeasurementsF() {
        return slamPostMeasurementsF;
    }

    public void setSlamPostMeasurementsF(double slamPostMeasurementsF) {
        this.slamPostMeasurementsF = slamPostMeasurementsF;
    }

    public double getSlamPostMeasurementsG() {
        return slamPostMeasurementsG;
    }

    public void setSlamPostMeasurementsG(double slamPostMeasurementsG) {
        this.slamPostMeasurementsG = slamPostMeasurementsG;
    }

    public double getSlamPostMeasurementsH() {
        return slamPostMeasurementsH;
    }

    public void setSlamPostMeasurementsH(double slamPostMeasurementsH) {
        this.slamPostMeasurementsH = slamPostMeasurementsH;
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

    public String getWallType() {
        return wallType;
    }

    public void setWallType(String wallType) {
        this.wallType = wallType;
    }

    public ContentValues generateContentValuesFromObject() {
        ContentValues values = new ContentValues();
        values.put("projectId", getProjectId());
        values.put("interiorCarId", getInteriorCarId());
        values.put("doorStyle", getDoorStyle());

        values.put("walltype", getWallType());
        values.put("centerOpening", getCenterOpening());
        values.put("width", getWidth());
        values.put("height", getHeight());
        values.put("sideWallMainWidth", getSideWallMainWidth());
        values.put("sideWallAuxWidth", getSideWallAuxWidth());
        values.put("doorOpeningWidth", getDoorOpeningWidth());
        values.put("doorOpeningHeight", getDoorOpeningHeight());
        values.put("returnSideWallDepth", getReturnSideWallDepth());
        values.put("slamSideWallDepth", getSlamSideWallDepth());
        values.put("slideWallWidth", getSlideWallWidth());
        values.put("typeOfSlamPost", getTypeOfSlamPost());
        values.put("slamPostMeasurementsA", getSlamPostMeasurementsA());
        values.put("slamPostMeasurementsB", getSlamPostMeasurementsB());
        values.put("slamPostMeasurementsC", getSlamPostMeasurementsC());
        values.put("slamPostMeasurementsD", getSlamPostMeasurementsD());
        values.put("slamPostMeasurementsE", getSlamPostMeasurementsE());
        values.put("slamPostMeasurementsF", getSlamPostMeasurementsF());
        values.put("slamPostMeasurementsG", getSlamPostMeasurementsG());
        values.put("slamPostMeasurementsH", getSlamPostMeasurementsH());
        values.put("otherSlamPost" , getOtherSlamPost());
        values.put("typeOfFrontReturn" , getTypeOfFrontReturn());
        values.put("leftSideA" , getLeftSideA());
        values.put("leftSideB" , getLeftSideB());
        values.put("leftSideC" , getLeftSideC());
        values.put("leftSideD" , getLeftSideD());
        values.put("leftSideE" , getLeftSideE());
        values.put("rightSideA" , getRightSideA());
        values.put("rightSideB" , getRightSideB());
        values.put("rightSideC" , getRightSideC());
        values.put("rightSideD" , getRightSideD());
        values.put("rightSideE" , getRightSideE());

        values.put("leftSideATypeB" , getLeftSideATypeB());
        values.put("leftSideBTypeB" , getLeftSideBTypeB());
        values.put("leftSideCTypeB" , getLeftSideCTypeB());

        values.put("rightSideATypeB" , getRightSideATypeB());
        values.put("rightSideBTypeB" , getRightSideBTypeB());
        values.put("rightSideCTypeB" , getRightSideCTypeB());

        values.put("frontReturnMeasurementsHeight" , getFrontReturnMeasurementsHeight());
        values.put("otherFrontReturnMeasurements" , getOtherFrontReturnMeasurements());
        values.put("carDoorType" , getCarDoorType());
        values.put("carDoorOpeningDirection" , getCarDoorOpeningDirection());
        values.put("transomMeasurementsHeight" , getTransomMeasurementsHeight());
        values.put("transomMeasurementsWidth" , getTransomMeasurementsWidth());
        values.put("transomMeasurementsLeft" , getTransomMeasurementsLeft());
        values.put("transomMeasurementsCenterLeft" , getTransomMeasurementsCenterLeft());
        values.put("transomMeasurementsCenterRight" , getTransomMeasurementsCenterRight());
        values.put("transomMeasurementsRight" , getTransomMeasurementsRight());
        values.put("transomMeasurementsCenter" , getTransomMeasurementsCenter());
        values.put("transomProfileHeight" , getTransomProfileHeight());
        values.put("transomProfileDepth" , getTransomProfileDepth());
        values.put("transomProfileReturn" , getTransomProfileReturn());
        values.put("transomProfileColonnade" , getTransomProfileColonnade());
        values.put("transomProfileColonnade2" , getTransomProfileColonnade2());
        values.put("lTransomWidth", getlTransomWidth());
        values.put("lTransomHeight", getlTransomHeight());
        values.put("headerReturnHoistWay", getHeaderReturnHoistWay());
        values.put("headerThroat", getHeaderThroat());
        values.put("headerWidth", getHeaderWidth());
        values.put("headerHeight", getHeaderHeight());
        values.put("headerReturnWall", getHeaderReturnWall());
        values.put("flatFrontLeftWidth", getFlatFrontLeftWidth());
        values.put("flatFrontLeftHeight", getFlatFrontLeftHeight());
        values.put("flatFrontRightWidth", getFlatFrontRightWidth());
        values.put("flatFrontRightHeight", getFlatFrontRightHeight());
        values.put("isThereNewCop" , getIsThereNewCop());
        values.put("mainCopWidth" , getMainCopWidth());
        values.put("mainCopHeight" , getMainCopHeight());
        values.put("mainCopLeft" , getMainCopLeft());
        values.put("mainCopRight" , getMainCopRight());
        values.put("mainCopTop" , getMainCopTop());
        values.put("mainCopBottom" , getMainCopBottom());
        values.put("mainCopThroat" , getMainCopThroat());
        values.put("mainCopReturn" , getMainCopReturn());
        values.put("auxCopWidth" , getAuxCopWidth());
        values.put("auxCopHeight" , getAuxCopHeight());
        values.put("auxCopLeft" , getAuxCopLeft());
        values.put("auxCopRight" , getAuxCopRight());
        values.put("auxCopTop" , getAuxCopTop());
        values.put("auxCopBottom" , getAuxCopBottom());
        values.put("auxCopThroat" , getAuxCopThroat());
        values.put("auxCopReturn" , getAuxCopReturn());

        values.put("notes", getNotes());
        values.put("uuid", getUuid());
        return values;
    }

    public InteriorCarDoorData() {
    }

    public InteriorCarDoorData(Cursor cursor) {
        super();

        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setProjectId(cursor.getInt(cursor.getColumnIndex("projectId")));
        setInteriorCarId(cursor.getInt(cursor.getColumnIndex("interiorCarId")));
        setDoorStyle(cursor.getInt(cursor.getColumnIndex("doorStyle")));

        setWallType(cursor.getString(cursor.getColumnIndex("walltype")));
        setCenterOpening(cursor.getInt(cursor.getColumnIndex("centerOpening")));
        setWidth(cursor.getDouble(cursor.getColumnIndex("width")));
        setHeight(cursor.getDouble(cursor.getColumnIndex("height")));
        setSideWallMainWidth(cursor.getDouble(cursor.getColumnIndex("sideWallMainWidth")));
        setSideWallAuxWidth(cursor.getDouble(cursor.getColumnIndex("sideWallAuxWidth")));
        setDoorOpeningHeight(cursor.getDouble(cursor.getColumnIndex("doorOpeningHeight")));
        setDoorOpeningWidth(cursor.getDouble(cursor.getColumnIndex("doorOpeningWidth")));
        setReturnSideWallDepth(cursor.getDouble(cursor.getColumnIndex("returnSideWallDepth")));
        setSlamSideWallDepth(cursor.getDouble(cursor.getColumnIndex("slamSideWallDepth")));
        setSlideWallWidth(cursor.getDouble(cursor.getColumnIndex("slideWallWidth")));
        setTypeOfSlamPost(cursor.getString(cursor.getColumnIndex("typeOfSlamPost")));
        setSlamPostMeasurementsA(cursor.getDouble(cursor.getColumnIndex("slamPostMeasurementsA")));
        setSlamPostMeasurementsB(cursor.getDouble(cursor.getColumnIndex("slamPostMeasurementsB")));
        setSlamPostMeasurementsC(cursor.getDouble(cursor.getColumnIndex("slamPostMeasurementsC")));
        setSlamPostMeasurementsD(cursor.getDouble(cursor.getColumnIndex("slamPostMeasurementsD")));
        setSlamPostMeasurementsE(cursor.getDouble(cursor.getColumnIndex("slamPostMeasurementsE")));
        setSlamPostMeasurementsF(cursor.getDouble(cursor.getColumnIndex("slamPostMeasurementsF")));
        setSlamPostMeasurementsG(cursor.getDouble(cursor.getColumnIndex("slamPostMeasurementsG")));
        setSlamPostMeasurementsH(cursor.getDouble(cursor.getColumnIndex("slamPostMeasurementsH")));
        setOtherSlamPost(cursor.getString(cursor.getColumnIndex("otherSlamPost")));
        setTypeOfFrontReturn(cursor.getString(cursor.getColumnIndex("typeOfFrontReturn")));
        setLeftSideA(cursor.getDouble(cursor.getColumnIndex("leftSideA")));
        setLeftSideB(cursor.getDouble(cursor.getColumnIndex("leftSideB")));
        setLeftSideC(cursor.getDouble(cursor.getColumnIndex("leftSideC")));
        setLeftSideD(cursor.getDouble(cursor.getColumnIndex("leftSideD")));
        setLeftSideE(cursor.getDouble(cursor.getColumnIndex("leftSideE")));

        setRightSideA(cursor.getDouble(cursor.getColumnIndex("rightSideA")));
        setRightSideB(cursor.getDouble(cursor.getColumnIndex("rightSideB")));
        setRightSideC(cursor.getDouble(cursor.getColumnIndex("rightSideC")));
        setRightSideD(cursor.getDouble(cursor.getColumnIndex("rightSideD")));
        setRightSideE(cursor.getDouble(cursor.getColumnIndex("rightSideE")));

        setLeftSideATypeB(cursor.getDouble(cursor.getColumnIndex("leftSideATypeB")));
        setLeftSideBTypeB(cursor.getDouble(cursor.getColumnIndex("leftSideBTypeB")));
        setLeftSideCTypeB(cursor.getDouble(cursor.getColumnIndex("leftSideCTypeB")));


        setRightSideATypeB(cursor.getDouble(cursor.getColumnIndex("rightSideATypeB")));
        setRightSideBTypeB(cursor.getDouble(cursor.getColumnIndex("rightSideBTypeB")));
        setRightSideCTypeB(cursor.getDouble(cursor.getColumnIndex("rightSideCTypeB")));

        setFrontReturnMeasurementsHeight(cursor.getDouble(cursor.getColumnIndex("frontReturnMeasurementsHeight")));
        setOtherFrontReturnMeasurements(cursor.getString(cursor.getColumnIndex("otherFrontReturnMeasurements")));
        setCarDoorType(cursor.getInt(cursor.getColumnIndex("carDoorType")));
        setCarDoorOpeningDirection(cursor.getInt(cursor.getColumnIndex("carDoorOpeningDirection")));
        setTransomMeasurementsHeight(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsHeight")));
        setTransomMeasurementsWidth(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsWidth")));
        setTransomMeasurementsLeft(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsLeft")));
        setTransomMeasurementsCenterLeft(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsCenterLeft")));
        setTransomMeasurementsCenterRight(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsCenterRight")));
        setTransomMeasurementsRight(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsRight")));
        setTransomMeasurementsCenter(cursor.getDouble(cursor.getColumnIndex("transomMeasurementsCenter")));

        setTransomProfileHeight(cursor.getDouble(cursor.getColumnIndex("transomProfileHeight")));
        setTransomProfileDepth(cursor.getDouble(cursor.getColumnIndex("transomProfileDepth")));
        setTransomProfileReturn(cursor.getDouble(cursor.getColumnIndex("transomProfileReturn")));
        setTransomProfileColonnade(cursor.getDouble(cursor.getColumnIndex("transomProfileColonnade")));
        setTransomProfileColonnade2(cursor.getDouble(cursor.getColumnIndex("transomProfileColonnade2")));

        setlTransomWidth(cursor.getDouble(cursor.getColumnIndex("lTransomWidth")));
        setlTransomHeight(cursor.getDouble(cursor.getColumnIndex("lTransomHeight")));
        setHeaderReturnHoistWay(cursor.getDouble(cursor.getColumnIndex("headerReturnHoistWay")));
        setHeaderThroat(cursor.getDouble(cursor.getColumnIndex("headerThroat")));
        setHeaderWidth(cursor.getDouble(cursor.getColumnIndex("headerWidth")));
        setHeaderHeight(cursor.getDouble(cursor.getColumnIndex("headerHeight")));
        setHeaderReturnWall(cursor.getDouble(cursor.getColumnIndex("headerReturnWall")));
        setFlatFrontLeftWidth(cursor.getDouble(cursor.getColumnIndex("flatFrontLeftWidth")));
        setFlatFrontLeftHeight(cursor.getDouble(cursor.getColumnIndex("flatFrontLeftHeight")));
        setFlatFrontRightWidth(cursor.getDouble(cursor.getColumnIndex("flatFrontRightWidth")));
        setFlatFrontRightHeight(cursor.getDouble(cursor.getColumnIndex("flatFrontRightHeight")));

        setIsThereNewCop(cursor.getString(cursor.getColumnIndex("isThereNewCop")));

        setMainCopWidth(cursor.getDouble(cursor.getColumnIndex("mainCopWidth")));
        setMainCopHeight(cursor.getDouble(cursor.getColumnIndex("mainCopHeight")));
        setMainCopLeft(cursor.getDouble(cursor.getColumnIndex("mainCopLeft")));
        setMainCopRight(cursor.getDouble(cursor.getColumnIndex("mainCopRight")));
        setMainCopTop(cursor.getDouble(cursor.getColumnIndex("mainCopTop")));
        setMainCopBottom(cursor.getDouble(cursor.getColumnIndex("mainCopBottom")));
        setMainCopThroat(cursor.getDouble(cursor.getColumnIndex("mainCopThroat")));
        setMainCopReturn(cursor.getDouble(cursor.getColumnIndex("mainCopReturn")));

        setAuxCopWidth(cursor.getDouble(cursor.getColumnIndex("auxCopWidth")));
        setAuxCopHeight(cursor.getDouble(cursor.getColumnIndex("auxCopHeight")));
        setAuxCopLeft(cursor.getDouble(cursor.getColumnIndex("auxCopLeft")));
        setAuxCopRight(cursor.getDouble(cursor.getColumnIndex("auxCopRight")));
        setAuxCopTop(cursor.getDouble(cursor.getColumnIndex("auxCopTop")));
        setAuxCopBottom(cursor.getDouble(cursor.getColumnIndex("auxCopBottom")));
        setAuxCopThroat(cursor.getDouble(cursor.getColumnIndex("auxCopThroat")));
        setAuxCopReturn(cursor.getDouble(cursor.getColumnIndex("auxCopReturn")));

        setNotes(cursor.getString(cursor.getColumnIndex("notes")));
        setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
    }
    public JSONArray getPostJSON(){
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(0, getJSON("opening", "1"));

            // added by alex - 2017/12/15
            jsonArray.put(1, getJSON("wall_type", getWallType()));
            jsonArray.put(2, getJSON("notes", getNotes()));

            jsonArray.put(3, getJSON("center_opening", getCenterOpening()==1?"YES":"NO"));
            jsonArray.put(4, getJSON("width", getDoubleForJSON(getWidth())));
            jsonArray.put(5, getJSON("height", getDoubleForJSON(getHeight())));
            jsonArray.put(6, getJSON("side_wall_main_width", getCenterOpening()==1?getDoubleForJSON(getSideWallMainWidth()):""));
            jsonArray.put(7, getJSON("side_wall_aux_width", getCenterOpening()==1?getDoubleForJSON(getSideWallAuxWidth()):""));
            jsonArray.put(8, getJSON("return_side_wall_depth", getCenterOpening()==2?getDoubleForJSON(getReturnSideWallDepth()):""));
            jsonArray.put(9, getJSON("slam_side_wall_depth", getCenterOpening()==2?getDoubleForJSON(getSlamSideWallDepth()):""));

            // commented by alex - 2017/12/15
            //jsonArray.put(8, getJSON("slide_wall_width", getCenterOpening()==2?getSlideWallWidth()+"":"0.0"));

            jsonArray.put(10, getJSON("door_opening_width", getDoubleForJSON(getDoorOpeningWidth())));
            jsonArray.put(11, getJSON("door_opening_height", getDoubleForJSON(getDoorOpeningHeight())));
            jsonArray.put(12, getJSON("type_of_slam_post", getCenterOpening()==2&&!getTypeOfSlamPost().equals("OTHER")?getTypeOfSlamPost():""));
            jsonArray.put(13, getJSON("type_of_slam_post_image", getCenterOpening()==2&&!getTypeOfSlamPost().equals("OTHER")?getTypeOfSlamPost():""));
            jsonArray.put(14, getJSON("other_slam_post", getCenterOpening()==2&&getTypeOfSlamPost().equals("OTHER")?getOtherSlamPost():""));
            jsonArray.put(15, getJSON("slam_post_measurements_A", getCenterOpening()==2&&!getTypeOfSlamPost().equals("OTHER")?getDoubleForJSON(getSlamPostMeasurementsA()):""));
            jsonArray.put(16, getJSON("slam_post_measurements_B", getCenterOpening()==2&&!getTypeOfSlamPost().equals("OTHER")?getDoubleForJSON(getSlamPostMeasurementsB()):""));
            jsonArray.put(17, getJSON("slam_post_measurements_C", getCenterOpening()==2&&!getTypeOfSlamPost().equals("OTHER")?getDoubleForJSON(getSlamPostMeasurementsC()):""));
            jsonArray.put(18, getJSON("slam_post_measurements_D", getCenterOpening()==2&&!getTypeOfSlamPost().equals("OTHER")&&!getTypeOfSlamPost().equals("C")?getDoubleForJSON(getSlamPostMeasurementsD()):""));
            jsonArray.put(19, getJSON("slam_post_measurements_E", getCenterOpening()==2&&!getTypeOfSlamPost().equals("OTHER")&&!getTypeOfSlamPost().equals("C")?getDoubleForJSON(getSlamPostMeasurementsE()):""));
            jsonArray.put(20, getJSON("slam_post_measurements_F", getCenterOpening()==2&&!getTypeOfSlamPost().equals("OTHER")&&!getTypeOfSlamPost().equals("C")?getDoubleForJSON(getSlamPostMeasurementsF()):""));
            jsonArray.put(21, getJSON("slam_post_measurements_G", getCenterOpening()==2&&!getTypeOfSlamPost().equals("OTHER")?getDoubleForJSON(getSlamPostMeasurementsG()):""));
            jsonArray.put(22, getJSON("slam_post_measurements_H", getCenterOpening()==2&&!getTypeOfSlamPost().equals("OTHER")?getDoubleForJSON(getSlamPostMeasurementsH()):""));
            jsonArray.put(23, getJSON("type_of_front_return", !getTypeOfFrontReturn().equals("OTHER")?getTypeOfFrontReturn():""));

            jsonArray.put(24, getJSON("leftside_image", !getTypeOfFrontReturn().equals("OTHER")?getTypeOfFrontReturn():""));

            jsonArray.put(25, getJSON("left_side_A", !getTypeOfFrontReturn().equals("OTHER")?getDoubleForJSON(getLeftSideA()):""));
            jsonArray.put(26, getJSON("left_side_B", !getTypeOfFrontReturn().equals("OTHER")?getDoubleForJSON(getLeftSideB()):""));
            jsonArray.put(27, getJSON("left_side_C", !getTypeOfFrontReturn().equals("OTHER")?getDoubleForJSON(getLeftSideC()):""));
            jsonArray.put(28, getJSON("left_side_D", !getTypeOfFrontReturn().equals("OTHER")&&!getTypeOfFrontReturn().equals("B")?getDoubleForJSON(getLeftSideD()):""));
            jsonArray.put(29, getJSON("left_side_E", !getTypeOfFrontReturn().equals("OTHER")&&!getTypeOfFrontReturn().equals("B")?getDoubleForJSON(getLeftSideE()):""));

            jsonArray.put(30, getJSON("rightside_image", getCenterOpening()==1&&!getTypeOfFrontReturn().equals("OTHER")?getTypeOfFrontReturn():""));

            jsonArray.put(31, getJSON("right_side_A", getCenterOpening()==1?getDoubleForJSON(getRightSideA()):""));
            jsonArray.put(32, getJSON("right_side_B", getCenterOpening()==1?getDoubleForJSON(getRightSideB()):""));
            jsonArray.put(33, getJSON("right_side_C", getCenterOpening()==1?getDoubleForJSON(getRightSideC()):""));
            jsonArray.put(34, getJSON("right_side_D", getCenterOpening()==1&&!getTypeOfFrontReturn().equals("B")?getDoubleForJSON(getRightSideD()):""));
            jsonArray.put(35, getJSON("right_side_E", getCenterOpening()==1&&!getTypeOfFrontReturn().equals("B")?getDoubleForJSON(getRightSideE()):""));
            jsonArray.put(36, getJSON("front_return_measurements_height", !getTypeOfFrontReturn().equals("OTHER")?getDoubleForJSON(getFrontReturnMeasurementsHeight()):""));
            jsonArray.put(37, getJSON("other_front_return_measurements", getTypeOfFrontReturn().equals("OTHER")?getOtherFrontReturnMeasurements():""));
            String str = "";
            if(getCarDoorType() == 2)
                str = "Single Speed";
            else if(getCarDoorType() == 1)
                str = "Two Speed";
            jsonArray.put(38, getJSON("car_door_type", str));
            str = "";
            if(getCarDoorOpeningDirection() == 1)
                str = "Slides Open To Left";
            else if(getCarDoorOpeningDirection() == 2)
                str = "Slides Open To Right";
            jsonArray.put(39, getJSON("car_door_opening_direction",getCenterOpening()==2?str:""));

            jsonArray.put(40, getJSON("transom_image", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? (getCarDoorType() == 2? "1s":"2s"):""));

            jsonArray.put(41, getJSON("transom_measurements_height", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getTransomMeasurementsHeight()):""));
            jsonArray.put(42, getJSON("transom_measurements_width", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getTransomMeasurementsWidth()):""));
            jsonArray.put(43, getJSON("transom_measurements_left", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getTransomMeasurementsLeft()):""));
            jsonArray.put(44, getJSON("transom_measurements_center_left", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE) && getCenterOpening()==1?getDoubleForJSON(getTransomMeasurementsCenterLeft()):""));
            jsonArray.put(45, getJSON("transom_measurements_center", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE) && getCenterOpening()==2?getDoubleForJSON(getTransomMeasurementsCenter()):""));
            jsonArray.put(46, getJSON("transom_measurements_center_right", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE) && getCenterOpening()==1?getDoubleForJSON(getTransomMeasurementsCenterRight()):""));
            jsonArray.put(47, getJSON("transom_measurements_right", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getTransomMeasurementsRight()):""));

            jsonArray.put(48, getJSON("transom_profile_image", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? (getCarDoorType() == 2? "1s":"2s"):""));

            jsonArray.put(49, getJSON("transom_profile_height", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getTransomProfileHeight()):""));
            jsonArray.put(50, getJSON("transom_profile_depth", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getTransomProfileDepth()):""));
            jsonArray.put(51, getJSON("transom_profile_return", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getTransomProfileReturn()):""));
            jsonArray.put(52, getJSON("transom_profile_colonnade", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getTransomProfileColonnade()):""));
            jsonArray.put(53, getJSON("transom_profile_colonnade2", !getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE) && getCarDoorType()==1?getDoubleForJSON(getTransomProfileColonnade2()):""));


            // added by alex - 2017/12/15
            // L-transom for only 5 Piece Wall Type
            jsonArray.put(54, getJSON("l_transom_width", getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getlTransomWidth()):""));
            jsonArray.put(55, getJSON("l_transom_height", getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getlTransomHeight()):""));
            jsonArray.put(56, getJSON("return_hoist_way", getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getHeaderReturnHoistWay()):""));
            jsonArray.put(57, getJSON("header_throat", getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getHeaderThroat()):""));
            jsonArray.put(58, getJSON("header_width", getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getHeaderWidth()):""));
            jsonArray.put(59, getJSON("header_height", getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getHeaderHeight()):""));
            jsonArray.put(60, getJSON("header_return_wall", getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getHeaderReturnWall()):""));
            jsonArray.put(61, getJSON("flat_front_left_width", getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getFlatFrontLeftWidth()):""));
            jsonArray.put(62, getJSON("flat_front_left_height", getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getFlatFrontLeftHeight()):""));
            jsonArray.put(63, getJSON("flat_front_right_width", getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getFlatFrontRightWidth()):""));
            jsonArray.put(64, getJSON("flat_front_right_height", getWallType().equals(GlobalConstant.WALL_TYPE_5_PIECE)? getDoubleForJSON(getFlatFrontRightHeight()):""));
            // -------------------------------------------------------------------------------------

            jsonArray.put(65, getJSON("is_there_new_cop", getIsThereNewCop()));

            jsonArray.put(66, getJSON("cop_width", getIsThereNewCop().equals("YES")?"Main="+getDoubleForJSON(getMainCopWidth())+",Aux="+getDoubleForJSON(getAuxCopWidth()):""));
            jsonArray.put(67, getJSON("cop_height", getIsThereNewCop().equals("YES")?"Main="+getDoubleForJSON(getMainCopHeight())+",Aux=" + getDoubleForJSON(getAuxCopHeight()):""));
            jsonArray.put(68, getJSON("cop_left", getIsThereNewCop().equals("YES")?"Main="+getDoubleForJSON(getMainCopLeft())+",Aux=" + getDoubleForJSON(getAuxCopLeft()):""));
            jsonArray.put(69, getJSON("cop_right", getIsThereNewCop().equals("YES")?"Main="+getDoubleForJSON(getMainCopRight())+",Aux=" + getDoubleForJSON(getAuxCopRight()):""));
            jsonArray.put(70, getJSON("cop_top", getIsThereNewCop().equals("YES")?"Main="+getDoubleForJSON(getMainCopTop())+",Aux=" + getDoubleForJSON(getAuxCopTop()):""));
            jsonArray.put(71, getJSON("cop_bottom", getIsThereNewCop().equals("YES")?"Main="+getDoubleForJSON(getMainCopBottom())+",Aux=" + getDoubleForJSON(getAuxCopBottom()):""));
            jsonArray.put(72, getJSON("cop_throat", getIsThereNewCop().equals("YES")?"Main="+getDoubleForJSON(getMainCopThroat())+",Aux=" + getDoubleForJSON(getAuxCopThroat()):""));
            jsonArray.put(73, getJSON("cop_return", getIsThereNewCop().equals("YES")?"Main="+getDoubleForJSON(getMainCopReturn())+",Aux=" + getDoubleForJSON(getAuxCopReturn()):""));

/*
            jsonArray.put(48, getJSON("main_cop_width", getMainCopWidth()+""));
            jsonArray.put(49, getJSON("main_cop_height", getMainCopHeight()+""));
            jsonArray.put(50, getJSON("main_cop_left", getMainCopLeft()+""));
            jsonArray.put(51, getJSON("main_cop_right", getMainCopRight()+""));
            jsonArray.put(52, getJSON("main_cop_top", getMainCopTop()+""));
            jsonArray.put(53, getJSON("main_cop_bottom", getMainCopBottom()+""));
            jsonArray.put(51, getJSON("main_cop_throat", getMainCopThroat()+""));
            jsonArray.put(55, getJSON("main_cop_return", getMainCopReturn()+""));
            jsonArray.put(56, getJSON("aux_cop_width", getAuxCopWidth()+""));
            jsonArray.put(57, getJSON("aux_cop_height", getAuxCopHeight()+""));
            jsonArray.put(58, getJSON("aux_cop_left", getAuxCopLeft()+""));
            jsonArray.put(59, getJSON("aux_cop_right", getAuxCopRight()+""));
            jsonArray.put(60, getJSON("aux_cop_top", getAuxCopTop()+""));
            jsonArray.put(61, getJSON("aux_cop_bottom", getAuxCopBottom()+""));
            jsonArray.put(62, getJSON("aux_cop_throat", getAuxCopThroat()+""));
            jsonArray.put(63, getJSON("aux_cop_return", getAuxCopReturn()+""));
*/

            // commented by alex 2017/11/25
            /*for (int i = 0; i < getPhotosList().size(); i++){
                PhotoData photoData = getPhotosList().get(i);
                jsonArray.put(i+62, photoData.getPostJSON(i + 1));
            }*/



        }catch(Exception e){
            e.printStackTrace();
        }

        return jsonArray;
    }
}

