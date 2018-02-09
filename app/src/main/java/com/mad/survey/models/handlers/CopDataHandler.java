package com.mad.survey.models.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mad.survey.models.CopData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.sqlite.CustomSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class CopDataHandler extends AbstractDataSource<CopData> {
	public static final String TABLE_NAME = "cops";
	public static final String COLUMN_ID = "id";
	private CustomSQLiteHelper mDbHelper;

	public CopDataHandler(CustomSQLiteHelper _mDbHelper, SQLiteDatabase database) {
		super(database);
		this.mDbHelper = _mDbHelper;
	}

	public void truncateTable() {
		this.mDbHelper.truncateTable(mDatabase, TABLE_NAME);
	}

	@Override
	public long insert(CopData entity) {
		if (entity == null) {
			return 0;
		}

		Log.d(getClass().getName(), "Inserting Cop: " + entity.generateContentValuesFromObject().toString());

		return mDatabase.insert(TABLE_NAME, null, entity.generateContentValuesFromObject());
	}

	@Override
	public boolean delete(CopData entity) {
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
	public boolean update(CopData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_NAME, entity.generateContentValuesFromObject(), COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	@Override
	public List<CopData> getAll() {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, null);
		List<CopData> dataList = new ArrayList<CopData>();
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

	public CopData generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		CopData data = new CopData(cursor);
		return data;
	}

	@Override
	public List<CopData> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
		List<CopData> dataList = new ArrayList<CopData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public List<CopData> getAll(int projectId, boolean includeNoneCops) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId + (!includeNoneCops? " AND name <> 'none'":""), null, null, null, null);
		List<CopData> dataList = new ArrayList<CopData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public CopData insertNewCopWithCopName(int projectId, int bankNum , int carNum,int copNum, String copName){
		// Check if the Cop exists or not
		CopData existCopData = get(projectId, bankNum, carNum, copNum);
		if (existCopData != null){
			return null;
		}else{
			CopData copData = new CopData();
			copData.setProjectId(projectId);
			copData.setBankId(bankNum);
			copData.setCarNum(carNum);
			copData.setCopNum(copNum);
			copData.setCopName(copName);
			long nId = insert(copData);
			copData.setId((int) nId);
			return copData;
		}
	}
	public CopData get(int projectId, int bankNum,int carNum,int copNum) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId = " + projectId + " AND bankId = " + bankNum + " AND carNum = " + carNum + " AND copNum = " + copNum, null, null, null, null);

		if(cursor!=null && cursor.moveToFirst()) {
			CopData data = generateObjectFromCursor(cursor);
			return data;
		}
		return null;
	}
	public List<CopData> getAll(int projectId,int bankId,int carNum) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId = " + projectId + " AND bankId=" + bankId + " AND carNum = " + carNum, null, null, null, COLUMN_ID + " asc");
		List<CopData> dataList = new ArrayList<>();
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
}


