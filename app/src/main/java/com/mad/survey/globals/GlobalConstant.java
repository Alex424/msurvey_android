package com.mad.survey.globals;

import android.os.Environment;

import java.io.File;

public class GlobalConstant {
	/** System Common Constants **/
	public static final String TAG = "MADSurvey";

	public static final String STATUS_SUBMITTED = "submitted";

	public static final String HOME_DIR = ".MadSurvey";
	public static final String PHOTO_DIR = "photos";

	public static final int UNIT_CENTIMETERS = 2;
	public static final int UNIT_INCHES = 1;

	public static final int EMPTY_LANTERN_RECORD_ID = 10000;

	public static final double PHOTO_SIZE_RATE = 0.87;

	public static final double HELP_PHOTO_SIZE_RATE = 1.33333;

	public static final int PROJECT_PHOTO_TYPE_BUILDING = 1;
	public static final int PROJECT_PHOTO_TYPE_LOBBY = 2;
	public static final int PROJECT_PHOTO_TYPE_BANK = 3;
	public static final int PROJECT_PHOTO_TYPE_HALLSTATION = 4;
	public static final int PROJECT_PHOTO_TYPE_LANTERNPI = 5;
	public static final int PROJECT_PHOTO_TYPE_CAR = 6;
    public static final int PROJECT_PHOTO_TYPE_CAR_COP = 7;
	public static final int PROJECT_PHOTO_TYPE_CAR_RIDING_LANTERN = 8;
	public static final int PROJECT_PHOTO_TYPE_CAR_SEPERATEPI = 9;
	public static final int PROJECT_PHOTO_TYPE_CAR_INTERIOR = 10;
	public static final int PROJECT_PHOTO_TYPE_HALL_ENTRANCE = 11;
	public static final int PROJECT_SETTING_UNITS_INCHES = 12;
	public static final int PROJECT_SETTING_UNITS_CENTIMETERS = 13;
	public static final int PROJECT_JOB_TYPE_SERVICE = 14;
	public static final int PROJECT_JOB_TYPE_MOD = 15;
	public static final int LOBBY_ELEVATORS_VISIBLE  = 16;
	public static final int LOBBY_ELEVATORS_INVISIBLE  = 17;

	public static final String LOBBY_LOCATION_CACFROOM = "CACF Room";
	public static final String LOBBY_LOCATION_FIRE_RECALL_PANEL = "Fire Recall Panel";
	public static final String LOBBY_LOCATION_SECURITY_DESK = "Security Desk";
	public static final String LOBBY_LOCATION_OTHER = "";
	public static final String TERMINAL_HALL_STATION = "TERMINAL Hall Station";
	public static final String INTERMEDIATE_HALL_STATION = "Intermediate Hall Station";
	public static final String FIRE_OPERATION_STATION = "Fire Operation Station";
	public static final String EPO_STATION = "EPO Station";
	public static final String ACCESS_STATION = "Access Station";
	public static final String SWING_SERVICE_HALL_STATION = "Swing Service Hall Station";
	public static final String SWING_SERVICE_TERMINAL_STATION = "Swing Service Terminal Station";
	public static final String FLUSH_MOUNT = "Flush Mount";
	public static final String SURFACE_MOUNT = "Surface Mount";
	public static final String HALLSTATION_DRYWALL = "Drywall";
	public static final String HALLSTATION_PLASTER = "Plaster";
	public static final String HALLSTATION_CONCRETE = "Concrete";
	public static final String HALLSTATION_BRICK = "Brick";
	public static final String HALLSTATION_MARBLE = "Marble";
	public static final String HALLSTATION_GRANIT = "Granite";
	public static final String HALLSTATION_GLASS = "Glass";
	public static final String HALLSTATION_TILE = "Tile";
	public static final String HALLSTATION_METAL = "Metal";
	public static final String HALLSTATION_WOOD = "Wood";
	public static final String LANTERN_POSITION_INDICATOR = "Position Indicator";
	public static final String LANTERN_LANTERN = "Lantern";
	public static final String LANTERN_PI_LANTERN_COMBO = "PI Lantern Combo";
	public static final String CAR_PASSENGER_ELEVATOR = "Passenger Elevator";
	public static final String CAR_SERVICE_ELEVATOR = "Service Elevator";
	public static final String CAR_FREIGHT_ELEVATOR = "Freight Elevator";
	public static final String CAR_DOOR_COINCIDING = "coinciding";
	public static final String CAR_DOOR_NON_COINCIDING = "non_coinciding";
	public static final String COP_STYLE_APPLIED = "Applied";
	public static final String COP_STYLE_SWING = "Swing";
	public static final String COP_HINGING_SIDE_LEFT = "Left";
	public static final String COP_HINGING_SIDE_RIGHT = "Right";
	public static final String FLOORING_CERAMIC = "Ceramic";
	public static final String FLOORING_PORCELAIN = "Porcelain";
	public static final String FLOORING_RUBBER_TILES = "Rubber Tiles";
	public static final String FLOORING_MARBLE = "Marble";
	public static final String FLOORING_GRANIT = "Granite";
	public static final String TYPE_OF_CEILING_FRAME_CEILING = "Ceiling Mounted";
	public static final String TYPE_OF_CEILING_FRAME_WALL = "Wall Mounted";
	public static final String TYPE_OF_CEILING_MOUNTING_TYPE_BOLTED = "Bolted";
	public static final String TYPE_OF_CEILING_MOUNTING_TYPE_WELDED = "Welded";
	public static final String TYPE_OF_CAR_HYDRAULIC = "Hydraulic";
	public static final String TYPE_OF_CAR_GEARED = "Geared";
	public static final String BIRD_CAGE_YES = "Yes";
	public static final String BIRD_CAGE_NO = "No";
	public static final String BIRD_CAGE_TBD = "TBD";


	public static final String WALL_TYPE_3_PIECE = "3 piece";
	public static final String WALL_TYPE_5_PIECE = "5 piece";
	public static final String WALL_TYPE_HYBRID = "Hybrid";


	public static String getHomeDirPath(){
		File extStore = Environment.getExternalStorageDirectory();
		String result = String.format("%s/%s", extStore.getPath(), HOME_DIR);
		return result;
	}

	public static String getPhotoDirPath(){
		String result = String.format("%s/%s", getHomeDirPath(), PHOTO_DIR);
		return result;
	}

	public static String getPhotoFilePath(String fileName){
		String result = String.format("%s/%s", getPhotoDirPath(), fileName);
        return result;
	}

    public static String getCameraTempFilePath() {
        return String.format("%s/%s", new Object[]{getHomeDirPath(), "camera_temp.jpg"});
    }
}