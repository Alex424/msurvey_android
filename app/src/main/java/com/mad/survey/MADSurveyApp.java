package com.mad.survey;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.models.CopData;
import com.mad.survey.models.HallEntranceData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.models.InteriorCarDoorData;
import com.mad.survey.models.LanternData;
import com.mad.survey.models.LobbyData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.models.handlers.ProjectDataHandler;
import com.mad.survey.sqlite.CustomSQLiteHelper;

public class MADSurveyApp extends Application {
	private static final String TAG = "MADSurveyApp";
	private static MADSurveyApp instance;

	private CustomSQLiteHelper mDbHelper;
	private SQLiteDatabase mDatabase;

	private ProjectData projectData;
	private LobbyData lobbyData;
	private BankData bankData;
	private HallStationData hallStationData;
	private LanternData lanternData;
	private CarData carData;
	private CopData copData;
	private InteriorCarData interiorCarData;
	private InteriorCarDoorData interiorCarDoorData;
	private HallEntranceData hallEntranceData;

	private int lobbyNum;
	private int bankNum;
	private int hallStationNum;
	private int floorNum;
	private int lanternNum;
	private int carNum;
	private int copNum;
	private int interiorCarNum;
	private int hallEntranceCarNum;

	private int lanternCnt;
	private String floorDescriptor = "";
	private boolean isEditMode = false;

	private boolean isInteriorAddFromEdit = false;

	public int getHallEntranceCarNum() {
		return hallEntranceCarNum;
	}

	public void setHallEntranceCarNum(int hallEntranceCarNum) {
		this.hallEntranceCarNum = hallEntranceCarNum;
	}

	public HallEntranceData getHallEntranceData() {
		return hallEntranceData;
	}

	public void setHallEntranceData(HallEntranceData hallEntranceData) {
		this.hallEntranceData = hallEntranceData;
	}

	public InteriorCarDoorData getInteriorCarDoorData() {
		return interiorCarDoorData;
	}

	public void setInteriorCarDoorData(InteriorCarDoorData interiorCarDoorData) {
		this.interiorCarDoorData = interiorCarDoorData;
	}

	public int getInteriorCarNum() {
		return interiorCarNum;
	}

	public void setInteriorCarNum(int interiorCarNum) {
		this.interiorCarNum = interiorCarNum;
	}

	public InteriorCarData getInteriorCarData() {
		return interiorCarData;
	}

	public void setInteriorCarData(InteriorCarData interiorCarData) {
		this.interiorCarData = interiorCarData;
	}

	public int getCarNum() {
		return carNum;
	}

	public int getCopNum() {
		return copNum;
	}

	public void setCopNum(int copNum) {
		this.copNum = copNum;
	}

	public CopData getCopData() {
		return copData;
	}

	public void setCopData(CopData copData) {
		this.copData = copData;
	}

	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}

	public CarData getCarData() {
		return carData;
	}

	public void setCarData(CarData carData) {
		this.carData = carData;
	}

	public int getLanternCnt() {
		return lanternCnt;
	}

	public void setLanternCnt(int lanternCnt) {
		this.lanternCnt = lanternCnt;
	}

	public boolean isEditMode() {
		return isEditMode;
	}

	public void setIsEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}

	public boolean isInteriorAddFromEdit() {
		return isInteriorAddFromEdit;
	}

	public void setIsInteriorAddFromEdit(boolean isInteriorAddFromEdit) {
		this.isInteriorAddFromEdit = isInteriorAddFromEdit;
	}

	public int getLanternNum() {
		return lanternNum;
	}

	public void setLanternNum(int lanternNum) {
		this.lanternNum = lanternNum;
	}

	public LanternData getLanternData() {
		return lanternData;
	}

	public void setLanternData(LanternData lanternData) {
		this.lanternData = lanternData;
	}

	public String getFloorDescriptor() {
		return floorDescriptor;
	}

	public void setFloorDescriptor(String floorDescriptor) {
		this.floorDescriptor = floorDescriptor;
	}

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

	public HallStationData getHallStationData() {
		return hallStationData;
	}

	public void setHallStationData(HallStationData hallStationData) {
		this.hallStationData = hallStationData;
	}

	public BankData getBankData() {
		return bankData;
	}

	public void setBankData(BankData bankData) {
		this.bankData = bankData;
	}



	public int getBankNum() {
		return bankNum;
	}

	public void setBankNum(int bankNum) {
		this.bankNum = bankNum;
	}

	public int getLobbyNum() {
		return lobbyNum;
	}

	public void setLobbyNum(int lobbyNum) {
		this.lobbyNum = lobbyNum;
	}

	public LobbyData getLobbyData() {
		return lobbyData;
	}

	public void setLobbyData(LobbyData lobbyData) {
		this.lobbyData = lobbyData;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		mDbHelper = new CustomSQLiteHelper(getApplicationContext());
		mDatabase = mDbHelper.getWritableDatabase();
		//mDbHelper.truncateAllTables(mDatabase);
	}

    public static MADSurveyApp getInstance () {
        return instance;
    }

	public CustomSQLiteHelper getDbHelper(){
		return this.mDbHelper;
	}

	public SQLiteDatabase getDatabase(){
		return this.mDatabase;
	}

	public ProjectData getProjectData(){
		return this.projectData;
	}

	public void setProjectData(ProjectData projectData){
		this.projectData = projectData;
	}

}