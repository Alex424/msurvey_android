package com.mad.survey.models.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.models.BankData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.LanternData;
import com.mad.survey.sqlite.CustomSQLiteHelper;
import com.mad.survey.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class HallStationDataHandler extends AbstractDataSource<HallStationData> {
	public static final String TABLE_NAME = "hallStations";
	public static final String COLUMN_ID = "id";
	private CustomSQLiteHelper mDbHelper;

	public HallStationDataHandler(CustomSQLiteHelper _mDbHelper, SQLiteDatabase database) {
		super(database);
		this.mDbHelper = _mDbHelper;
	}

	public void truncateTable() {
		this.mDbHelper.truncateTable(mDatabase, TABLE_NAME);
	}

	@Override
	public long insert(HallStationData entity) {
		if (entity == null) {
			return 0;
		}

		Log.d(getClass().getName(), "Inserting HallStation: " + entity.generateContentValuesFromObject().toString());

		return mDatabase.insert(TABLE_NAME, null, entity.generateContentValuesFromObject());
	}

	@Override
	public boolean delete(HallStationData entity) {
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
	public boolean update(HallStationData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_NAME, entity.generateContentValuesFromObject(), COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	@Override
	public List<HallStationData> getAll() {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, null);
		List<HallStationData> dataList = new ArrayList<HallStationData>();
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

	public HallStationData generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		HallStationData data = new HallStationData(cursor);
		return data;
	}

	@Override
	public List<HallStationData> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, "bankId asc,floorNumber asc");
		List<HallStationData> dataList = new ArrayList<HallStationData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public List<HallStationData> getAllForHallStationEdits(int projectId) {
		Cursor cursor = mDatabase.query(TABLE_NAME + " h LEFT JOIN " + BankDataHandler.TABLE_NAME + " b ON h.projectId = b.projectId AND h.bankId = b.bankNum", new String[]{"h.*, b.name bankDesc"},
				"h.projectId = " + projectId, null, null, null, "h.bankId asc,h.floorNumber asc");
		List<HallStationData> dataList = new ArrayList<HallStationData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public HallStationData insertNewHallStationWithFloorNumber(int projectId, int bankId,int hallStationNum, String floorNumber,int floorNum){
		// Check if the hallStation exists or not
		HallStationData existHallStationData = get(projectId, bankId, hallStationNum, floorNumber);
		if (existHallStationData != null){
			return null;
		}else{
			HallStationData hallStationData = new HallStationData();
			hallStationData.setProjectId(projectId);
			hallStationData.setBankId(bankId);
			hallStationData.setHallStationNum(hallStationNum);
			hallStationData.setFloorNumber(floorNumber);
			hallStationData.setFloorNum(floorNum);
			long nId = insert(hallStationData);
			hallStationData.setId((int) nId);
			return hallStationData;
		}
	}
	public List<HallStationData> getAllHallStations(int projectId,int bankId) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId = " + projectId + " AND bankId=" + bankId, null, null, null, COLUMN_ID + " asc");
		List<HallStationData> dataList = new ArrayList<>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}
	public List<String> getFloorNumbers(int projectId,int bankId){

		TreeSet<String> floorNumbers = new TreeSet<>();
		List<HallStationData> hallStationDataList = getAllHallStations(projectId, bankId);
		List<String> listFloorNumbers = new ArrayList<>();
		int i;
		for(i = 0 ; i < hallStationDataList.size() ; i ++){
			String floorNumber = hallStationDataList.get(i).getFloorNumber();
			if(!floorNumbers.contains(floorNumber)){
				floorNumbers.add(floorNumber);
				listFloorNumbers.add(floorNumber);
			}
		}
		return listFloorNumbers;
	}


	public HallStationData insertNewHallStationSameAs(int sameAsId, int projectId, int bankNum, int hallStationNum, String floorNumber,int floorNum){
		//-------------CHECK existing
		HallStationData existHallStationData = get(projectId,bankNum,hallStationNum,floorNumber);

		//-----------------------in case OF Same As Last------------
		Cursor cursor;
		if(sameAsId == -1){

			cursor = rawQuery("SELECT MAX(id) AS id FROM " + TABLE_NAME + " WHERE projectId = " + projectId + " AND bankId = " + bankNum);
			if (cursor != null && cursor.moveToFirst())
				sameAsId = cursor.getInt(cursor.getColumnIndex("id"));
			Log.d("sameAsID = ",sameAsId+"");
		}
		//-----------------------------------------------------------

		//----------------------in case OF Same As Id-------------
		else{

		}
		//-----------------------------------------------------------

		cursor = mDatabase.query(TABLE_NAME, null, "id=" + sameAsId, null, null, null, null);
		if(cursor!=null && cursor.moveToFirst()) {
			HallStationData hallStationData = generateObjectFromCursor(cursor);
			hallStationData.setSameAs(hallStationData.getUuid());
			hallStationData.setProjectId(projectId);
			hallStationData.setBankId(bankNum);
			hallStationData.setHallStationNum(hallStationNum);
			hallStationData.setFloorNumber(floorNumber);
			hallStationData.setFloorNum(floorNum);
			hallStationData.setNotes("");
			if(existHallStationData == null) {
				hallStationData.setUuid(Utils.createUUID());
				long nId = insert(hallStationData);
				hallStationData.setId((int) nId);
			}else{
				hallStationData.setUuid(existHallStationData.getUuid());
				hallStationData.setId(existHallStationData.getId());
				update(hallStationData);
			}
			return hallStationData;
		}
		return null;

	}


	public HallStationData get(int projectId, int bankId,int hallStationNum,String floorNumber) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId + " AND bankId = " + bankId + " AND hallStationNum = " + hallStationNum + " AND floorNumber = '" + floorNumber + "'", null, null, null, null);

		if(cursor!=null && cursor.moveToFirst()) {
			HallStationData data = generateObjectFromCursor(cursor);
			return data;
		}
		return null;
	}
	public List<Object> getAll(int projectId,int bankId) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId = " + projectId + " AND bankId=" + bankId, null, null, null, COLUMN_ID + " asc");
		List<Object> dataList = new ArrayList<Object>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}
	public int getAllCnt(int projectId) {
		int cnt = 0;
		Cursor cursor;
		cursor = rawQuery("SELECT COUNT(*) AS cnt FROM " + TABLE_NAME + " WHERE projectId = " + projectId);
		if (cursor != null && cursor.moveToFirst())
			cnt = cursor.getInt(cursor.getColumnIndex("cnt"));
		return cnt;
	}
	public int getCntPerFloor(int projectId,int bankId,String floorDescription){

		int cnt = 0;
		Cursor cursor;
		cursor = rawQuery("SELECT COUNT(*) AS cnt FROM " + TABLE_NAME + " WHERE projectId = " + projectId + " AND bankId=" + bankId + " AND floorNumber='" + floorDescription+"'");
		if (cursor != null && cursor.moveToFirst())
			cnt = cursor.getInt(cursor.getColumnIndex("cnt"));
		return cnt;

	}

	public int getMaxHallStationNum(int projectId,int bankId,String floorDescription){
		int cnt = 0;
		Cursor cursor;
		cursor = rawQuery("SELECT MAX(hallStationNum) hallStationNum FROM " + TABLE_NAME + " WHERE projectId = " + projectId + " AND bankId=" + bankId + " AND floorNumber='" + floorDescription+"'");
		if (cursor != null && cursor.moveToFirst())
			cnt = cursor.getInt(cursor.getColumnIndex("hallStationNum"));
		return cnt;
	}
}