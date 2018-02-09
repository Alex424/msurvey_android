package com.mad.survey.models.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mad.survey.models.HallEntranceData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.sqlite.CustomSQLiteHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class HallEntranceDataHandler extends AbstractDataSource<HallEntranceData> {
	public static final String TABLE_NAME = "hallEntrances";
	public static final String COLUMN_ID = "id";
	private CustomSQLiteHelper mDbHelper;

	public HallEntranceDataHandler(CustomSQLiteHelper _mDbHelper, SQLiteDatabase database) {
		super(database);
		this.mDbHelper = _mDbHelper;
	}

	public void truncateTable() {
		this.mDbHelper.truncateTable(mDatabase, TABLE_NAME);
	}

	@Override
	public long insert(HallEntranceData entity) {
		if (entity == null) {
			return 0;
		}

		Log.d(getClass().getName(), "Inserting HallEntrance: " + entity.generateContentValuesFromObject().toString());

		return mDatabase.insert(TABLE_NAME, null, entity.generateContentValuesFromObject());
	}

	@Override
	public boolean delete(HallEntranceData entity) {
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
	public boolean update(HallEntranceData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_NAME, entity.generateContentValuesFromObject(), COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	@Override
	public List<HallEntranceData> getAll() {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, null);
		List<HallEntranceData> dataList = new ArrayList<HallEntranceData>();
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

	public HallEntranceData generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		HallEntranceData data = new HallEntranceData(cursor);
		return data;
	}

	@Override
	public List<HallEntranceData> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, "bankId asc,floorDescription asc");
		List<HallEntranceData> dataList = new ArrayList<HallEntranceData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public List<HallEntranceData> getAll(int projectId, boolean includeNoneHallEntrances) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId + (!includeNoneHallEntrances? " AND name <> 'none'":""), null, null, null, null);
		List<HallEntranceData> dataList = new ArrayList<HallEntranceData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public List<HallEntranceData> getAllHallEntrances(int projectId,int bankId) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId = " + projectId + " AND bankId=" + bankId, null, null, null, COLUMN_ID + " asc");
		List<HallEntranceData> dataList = new ArrayList<>();
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
		List<HallEntranceData> hallEntranceDataList = getAllHallEntrances(projectId, bankId);
		List<String> listFloorNumbers = new ArrayList<>();
		int i;
		for(i = 0 ; i < hallEntranceDataList.size() ; i ++){
			String floorNumber = hallEntranceDataList.get(i).getFloorDescription();
			if(!floorNumbers.contains(floorNumber)){
				floorNumbers.add(floorNumber);
				listFloorNumbers.add(floorNumber);
			}
		}
		return listFloorNumbers;
	}
	public HallEntranceData insertNewHallEntrance(int projectId, int bankNum , int carNum, String floorDescription,int type){
		// Check if the HallEntrance exists or not
		List<HallEntranceData> hallEntrancesList = getAll("projectId = " + projectId + " AND bankId = " + bankNum + " AND carNum = " + carNum + " AND floorDescription = '" + floorDescription + "'", null, null, null, null);
		if (hallEntrancesList.size() > 0){
			return null;
		}else{
			HallEntranceData hallEntranceData = new HallEntranceData();
			hallEntranceData.setProjectId(projectId);
			hallEntranceData.setCarNum(carNum);
			hallEntranceData.setBankId(bankNum);
			hallEntranceData.setFloorDescription(floorDescription);
			hallEntranceData.setDoorType(type);
			long nId = insert(hallEntranceData);
			hallEntranceData.setId((int) nId);
			return hallEntranceData;
		}
	}
	public HallEntranceData get(int projectId, int bankNum , String floorDescription , int carNum) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId + " AND bankId = " + bankNum + " AND floorDescription = '" + floorDescription + "' AND carNum = " + carNum, null, null, null, null);

		if(cursor!=null && cursor.moveToFirst()) {
			HallEntranceData data = generateObjectFromCursor(cursor);
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
		cursor = rawQuery("SELECT COUNT(*) AS cnt FROM " + TABLE_NAME + " WHERE projectId = " + projectId + " AND bankId=" + bankId + " AND floorDescription='" + floorDescription+"'");
		if (cursor != null && cursor.moveToFirst())
			cnt = cursor.getInt(cursor.getColumnIndex("cnt"));
		return cnt;

	}

	public List<HallEntranceData> getAllForHallEntranceEdits(int projectId) {
		Cursor cursor = mDatabase.query(TABLE_NAME + " h LEFT JOIN " + BankDataHandler.TABLE_NAME + " b ON h.projectId = b.projectId AND h.bankId = b.bankNum", new String[]{"h.*, b.name bankDesc"},
				"h.projectId = " + projectId, null, null, null, "h.bankId asc,h.floorDescription asc");
		List<HallEntranceData> dataList = new ArrayList<HallEntranceData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public int getMaxHallEntranceNum(int projectId,int bankId,String floorDescription){
		int cnt = 0;
		Cursor cursor;
		cursor = rawQuery("SELECT MAX(carNum) carNum FROM " + TABLE_NAME + " WHERE projectId = " + projectId + " AND bankId=" + bankId + " AND floorDescription='" + floorDescription+"'");
		if (cursor != null && cursor.moveToFirst())
			cnt = cursor.getInt(cursor.getColumnIndex("carNum"));
		return cnt;
	}

	public HallEntranceData getHallEntranceDataForSameAsLast(int projectId,int bankId){
		List<HallEntranceData> dataList = getAll("projectId = " + projectId + " AND bankId = " + bankId , null, null, null, "id asc");
		if (dataList.size() > 0){
			return dataList.get(dataList.size() - 1);
		}

		return null;
	}
}
