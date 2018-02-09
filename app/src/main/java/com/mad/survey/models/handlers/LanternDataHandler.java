package com.mad.survey.models.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.models.FloorData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.LanternData;
import com.mad.survey.sqlite.CustomSQLiteHelper;
import com.mad.survey.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class LanternDataHandler extends AbstractDataSource<LanternData> {
	public static final String TABLE_NAME = "lanterns";
	public static final String COLUMN_ID = "id";
	private CustomSQLiteHelper mDbHelper;

	public LanternDataHandler(CustomSQLiteHelper _mDbHelper, SQLiteDatabase database) {
		super(database);
		this.mDbHelper = _mDbHelper;
	}

	public void truncateTable() {
		this.mDbHelper.truncateTable(mDatabase, TABLE_NAME);
	}

	@Override
	public long insert(LanternData entity) {
		if (entity == null) {
			return 0;
		}

		Log.d(getClass().getName(), "Inserting lantern: " + entity.generateContentValuesFromObject().toString());

		return mDatabase.insert(TABLE_NAME, null, entity.generateContentValuesFromObject());
	}

	@Override
	public boolean delete(LanternData entity) {
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
	public boolean update(LanternData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_NAME, entity.generateContentValuesFromObject(), COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	@Override
	public List<LanternData> getAll() {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, null);
		List<LanternData> dataList = new ArrayList<LanternData>();
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

	public LanternData generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		LanternData data = new LanternData(cursor);
		return data;
	}

	@Override
	public List<LanternData> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, "bankId asc,floorNumber asc");
		List<LanternData> dataList = new ArrayList<>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				LanternData lanternData = generateObjectFromCursor(cursor);
				if(lanternData.getLanternNum() != GlobalConstant.EMPTY_LANTERN_RECORD_ID) {
					dataList.add(lanternData);
				}
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}



	public LanternData insertNewLanternWithFloorNumber(int projectId, int bankId,int lanternNum, String floorNumber){
		//-------------CHECK existing
		LanternData existLanternData = get(projectId, bankId, lanternNum, floorNumber);
		if (existLanternData != null){
			return null;
		}else{
			LanternData lanternData = new LanternData();
			lanternData.setProjectId(projectId);
			lanternData.setBankId(bankId);
			lanternData.setLanternNum(lanternNum);
			lanternData.setFloorNumber(floorNumber);
			long nId = insert(lanternData);
			lanternData.setId((int) nId);
			return lanternData;
		}
	}
	public LanternData insertNewLanternSameAs(int sameAsId, int projectId, int bankNum, int lanternNum, String floorNumber){
		//-------------CHECK existing
		LanternData existLanternData = get(projectId, bankNum, lanternNum, floorNumber);

		//-----------------------in case OF Same As Last------------
		Cursor cursor;
		if(sameAsId == -1){
			sameAsId = getLastId(projectId,bankNum);
			Log.d("sameAsID = ",sameAsId+"");
		}
		cursor = mDatabase.query(TABLE_NAME, null, "id=" + sameAsId, null, null, null, null);

		if(cursor!=null && cursor.moveToFirst()) {
			LanternData lanternData = generateObjectFromCursor(cursor);
			lanternData.setSameAs(lanternData.getUuid());
			lanternData.setProjectId(projectId);
			lanternData.setBankId(bankNum);
			lanternData.setLanternNum(lanternNum);
			lanternData.setFloorNumber(floorNumber);
			lanternData.setNotes("");
			if(existLanternData == null) {
				lanternData.setUuid(Utils.createUUID());
				long nId = insert(lanternData);
				lanternData.setId((int) nId);
			}else{
				lanternData.setUuid(existLanternData.getUuid());
				lanternData.setId(existLanternData.getId());
				update(lanternData);
			}
			return lanternData;
		}
		return null;

	}


	public LanternData get(int projectId, int bankNum,int lanternNum,String floorNumber) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId + " AND bankId = " + bankNum + " AND lanternNum = " + lanternNum + " AND floorNumber = '" + floorNumber + "'", null, null, null, null);

		if(cursor!=null && cursor.moveToFirst()) {
			LanternData data = generateObjectFromCursor(cursor);
			return data;
		}
		return null;
	}
	public List<LanternData> getAllLanterns(int projectId,int bankId) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId = " + projectId + " AND bankId=" + bankId, null, null, null, COLUMN_ID + " asc");
		List<LanternData> dataList = new ArrayList<>();
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
		// SELECT DISTINCT from TABLE_NAME where
		TreeSet<String> floorNumbers = new TreeSet<>();
		List<LanternData> lanternDataList = getAllLanterns(projectId, bankId);
		List<String> listFloorNumbers = new ArrayList<>();
		int i;
		for(i = 0 ; i < lanternDataList.size() ; i ++){
			String floorNumber = lanternDataList.get(i).getFloorNumber();
			if(!floorNumbers.contains(floorNumber)){
				floorNumbers.add(floorNumber);
				listFloorNumbers.add(floorNumber);
			}
		}
		return listFloorNumbers;
	}

	public int getLastId(int projectId,int bankNum){
		Cursor cursor = rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE projectId = " + projectId + " AND bankId = " + bankNum + " ORDER BY id DESC");
		if(cursor != null && cursor.moveToFirst()){
			while (!cursor.isAfterLast()) {
				LanternData lanternData = generateObjectFromCursor(cursor);
				if(lanternData.getLanternNum() != GlobalConstant.EMPTY_LANTERN_RECORD_ID){
					cursor.close();
					return lanternData.getId();
				}
				cursor.moveToNext();
			}
			cursor.close();
		}
		return -1;
	}

	public int getLanternCntPerFloor(int projectId,int bankNum,String floorNumber){
		List<LanternData> lanternList = getAll("projectId = " + projectId + " AND bankId = " + bankNum + " AND floorNumber = '" + floorNumber + "'", null, null, null, null);
		if(lanternList.size() == 1){
			if(lanternList.get(0).getLanternNum() == GlobalConstant.EMPTY_LANTERN_RECORD_ID)
				return 0;
		}
		return lanternList.size();
}

	public int getAllCnt(int projectId){
		List<LanternData> lanternList = getAll("projectId = " + projectId , null, null, null, null);
		int cnt = 0;
		for(int i = 0 ; i < lanternList.size() ; i ++){
			LanternData lanternData = lanternList.get(i);
			if(lanternData.getLanternNum() == GlobalConstant.EMPTY_LANTERN_RECORD_ID)
				continue;
			cnt++;
		}
		return cnt;
	}

	public int getLanternCntPerBank(int projectId,int bankNum){
		List<LanternData> lanternList = getAll("projectId = " + projectId + " AND bankId = " + bankNum, null, null, null, null);
		int cnt = 0;
		for(int i = 0 ; i < lanternList.size() ; i ++){
			LanternData lanternData = lanternList.get(i);
			if(lanternData.getLanternNum() == GlobalConstant.EMPTY_LANTERN_RECORD_ID)
				continue;
			cnt ++;
		}
		return cnt;
	}

	public List<Object> getAll(int projectId,int bankId) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId = " + projectId + " AND bankId=" + bankId, null, null, null, COLUMN_ID + " asc");
		List<Object> dataList = new ArrayList<>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				LanternData lanternData = generateObjectFromCursor(cursor);
				if(lanternData.getLanternNum() != GlobalConstant.EMPTY_LANTERN_RECORD_ID) {
					dataList.add(lanternData);
				}
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public List<LanternData> getAllForLanternEdits(int projectId) {
		Cursor cursor = mDatabase.query(TABLE_NAME + " l LEFT JOIN " + BankDataHandler.TABLE_NAME + " b ON l.projectId = b.projectId AND l.bankId = b.bankNum", new String[]{"l.*, b.name bankDesc"},
				"l.projectId = " + projectId, null, null, null, "l.bankId asc,l.floorNumber asc");
		List<LanternData> dataList = new ArrayList<LanternData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public int getMaxLanternNum(int projectId,int bankId,String floorDescription){
		int cnt = 0;
		Cursor cursor;
		cursor = rawQuery("SELECT MAX(lanternNum) lanternNum FROM " + TABLE_NAME + " WHERE projectId = " + projectId + " AND bankId=" + bankId + " AND floorNumber='" + floorDescription+"'");
		if (cursor != null && cursor.moveToFirst())
			cnt = cursor.getInt(cursor.getColumnIndex("lanternNum"));
		return cnt;
	}
}
