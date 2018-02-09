package com.mad.survey.models.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mad.survey.models.InteriorCarDoorData;
import com.mad.survey.sqlite.CustomSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class InteriorCarDoorDataHandler extends AbstractDataSource<InteriorCarDoorData> {
	public static final String TABLE_NAME = "interiorCarDoors";
	public static final String COLUMN_ID = "id";
	private CustomSQLiteHelper mDbHelper;

	public InteriorCarDoorDataHandler(CustomSQLiteHelper _mDbHelper, SQLiteDatabase database) {
		super(database);
		this.mDbHelper = _mDbHelper;
	}

	public void truncateTable() {
		this.mDbHelper.truncateTable(mDatabase, TABLE_NAME);
	}

	@Override
	public long insert(InteriorCarDoorData entity) {
		if (entity == null) {
			return 0;
		}

		Log.d(getClass().getName(), "Inserting InteriorCarDoor: " + entity.generateContentValuesFromObject().toString());

		return mDatabase.insert(TABLE_NAME, null, entity.generateContentValuesFromObject());
	}

	@Override
	public boolean delete(InteriorCarDoorData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.delete(TABLE_NAME, COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	public void delete(String condition, String[] conditionParams) {
		this.mDatabase.delete(TABLE_NAME, condition, conditionParams);
	}

	@Override
	public boolean update(InteriorCarDoorData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_NAME, entity.generateContentValuesFromObject(), COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	@Override
	public List<InteriorCarDoorData> getAll() {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, null);
		List<InteriorCarDoorData> dataList = new ArrayList<InteriorCarDoorData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public Cursor rawQuery(String sql) {
		Cursor cursor = mDatabase.rawQuery(sql, null);
		return cursor;
	}

	public InteriorCarDoorData generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		InteriorCarDoorData data = new InteriorCarDoorData(cursor);
		return data;
	}

	@Override
	public List<InteriorCarDoorData> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
		List<InteriorCarDoorData> dataList = new ArrayList<InteriorCarDoorData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}


	public InteriorCarDoorData copyFrontToBackDoor(InteriorCarDoorData frontDoor){
		//Checking existence
		InteriorCarDoorData backDoor;
		List<InteriorCarDoorData> interiorCarDoorsList = getAll("interiorCarId = " + frontDoor.getInteriorCarId() + " AND doorStyle = " + 2 , null, null, null, null);
		if (interiorCarDoorsList.size() > 0){
			return interiorCarDoorsList.get(0);
		}else{
			backDoor = frontDoor;
			//set backDoor
			backDoor.setDoorStyle(2);

			long nId = insert(backDoor);
			backDoor.setId((int) nId);
			return backDoor;
		}
	}
	public InteriorCarDoorData insertNewInteriorCarDoor(int projectId,int interiorCarId, int doorStyle, int centerOpening){
		// Check if the InteriorCarDoor exists or not
		List<InteriorCarDoorData> interiorCarDoorsList = getAll("projectId = " + projectId + " AND interiorCarId = " + interiorCarId + " AND doorStyle = " + doorStyle , null, null, null, null);
		if (interiorCarDoorsList.size() > 0){
			return null;
		}else{
			InteriorCarDoorData interiorCarDoorData = new InteriorCarDoorData();
			interiorCarDoorData.setProjectId(projectId);
			interiorCarDoorData.setInteriorCarId(interiorCarId);
			interiorCarDoorData.setDoorStyle(doorStyle);
			interiorCarDoorData.setCenterOpening(centerOpening);

			long nId = insert(interiorCarDoorData);
			interiorCarDoorData.setId((int) nId);
			return interiorCarDoorData;
		}
	}

	public InteriorCarDoorData insertNewInteriorCarDoorWithStartWallTypeAndNotes(int projectId,int interiorCarId, int doorStyle, String wallType, String notes){
		// Check if the InteriorCarDoor exists or not
		List<InteriorCarDoorData> interiorCarDoorsList = getAll("projectId = " + projectId + " AND interiorCarId = " + interiorCarId + " AND doorStyle = " + doorStyle , null, null, null, null);
		if (interiorCarDoorsList.size() > 0){
			return null;
		}else{
			InteriorCarDoorData interiorCarDoorData = new InteriorCarDoorData();
			interiorCarDoorData.setProjectId(projectId);
			interiorCarDoorData.setInteriorCarId(interiorCarId);
			interiorCarDoorData.setDoorStyle(doorStyle);
			interiorCarDoorData.setWallType(wallType);
			interiorCarDoorData.setNotes(notes);

			long nId = insert(interiorCarDoorData);
			interiorCarDoorData.setId((int) nId);
			return interiorCarDoorData;
		}
	}

	public InteriorCarDoorData get(int interiorCarId , int doorType) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "interiorCarId= " + interiorCarId + " AND doorStyle = " + doorType, null, null, null, null);

		if(cursor!=null && cursor.moveToFirst()) {
			InteriorCarDoorData data = generateObjectFromCursor(cursor);
			return data;
		}
		return null;
	}
	public InteriorCarDoorData get(int id) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "id= " + id , null, null, null, null);

		if(cursor!=null && cursor.moveToFirst()) {
			InteriorCarDoorData data = generateObjectFromCursor(cursor);
			return data;
		}
		return null;
	}
}


