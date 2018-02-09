package com.mad.survey.models.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mad.survey.models.CarData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.sqlite.CustomSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class InteriorCarDataHandler extends AbstractDataSource<InteriorCarData> {
	public static final String TABLE_NAME = "interiorCars";
	public static final String COLUMN_ID = "id";
	private CustomSQLiteHelper mDbHelper;

	public InteriorCarDataHandler(CustomSQLiteHelper _mDbHelper, SQLiteDatabase database) {
		super(database);
		this.mDbHelper = _mDbHelper;
	}

	public void truncateTable() {
		this.mDbHelper.truncateTable(mDatabase, TABLE_NAME);
	}

	@Override
	public long insert(InteriorCarData entity) {
		if (entity == null) {
			return 0;
		}

		Log.d(getClass().getName(), "Inserting InteriorCar: " + entity.generateContentValuesFromObject().toString());

		return mDatabase.insert(TABLE_NAME, null, entity.generateContentValuesFromObject());
	}

	@Override
	public boolean delete(InteriorCarData entity) {
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
	public boolean update(InteriorCarData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_NAME, entity.generateContentValuesFromObject(), COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	@Override
	public List<InteriorCarData> getAll() {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, null);
		List<InteriorCarData> dataList = new ArrayList<InteriorCarData>();
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

	public InteriorCarData generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		InteriorCarData data = new InteriorCarData(cursor);
		return data;
	}

	@Override
	public List<InteriorCarData> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, "bankId asc");
		List<InteriorCarData> dataList = new ArrayList<InteriorCarData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public List<InteriorCarData> getAll(int projectId, boolean includeNoneInteriorCars) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId + (!includeNoneInteriorCars? " AND name <> 'none'":""), null, null, null, null);
		List<InteriorCarData> dataList = new ArrayList<InteriorCarData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public InteriorCarData insertNewInteriorCarWithDescription(int projectId, int bankNum , int InteriorCarNum, String description){
		// Check if the InteriorCar exists or not
		List<InteriorCarData> InteriorCarsList = getAll("projectId = " + projectId + " AND bankId = " + bankNum + " AND InteriorCarNum = " + InteriorCarNum, null, null, null, null);
		if (InteriorCarsList.size() > 0){
			return null;
		}else{
			InteriorCarData interiorCarData = new InteriorCarData();
			interiorCarData.setProjectId(projectId);
			interiorCarData.setBankId(bankNum);
			interiorCarData.setInteriorCarNum(InteriorCarNum);
			interiorCarData.setCarDescription(description);
			long nId = insert(interiorCarData);
			interiorCarData.setId((int) nId);
			return interiorCarData;
		}
	}
	public InteriorCarData get(int projectId, int bankNum , int InteriorCarNum) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId + " AND bankId = " + bankNum + " AND interiorCarNum = " + InteriorCarNum, null, null, null, null);

		if(cursor!=null && cursor.moveToFirst()) {
			InteriorCarData data = generateObjectFromCursor(cursor);
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

	public InteriorCarData getPreviousCarDataForSameAsLast(int projectId, int bankNum){
		List<InteriorCarData> InteriorCarsList = getAll("projectId = " + projectId + " AND bankId = " + bankNum  , null, null, null, "id asc");
		if (InteriorCarsList.size() > 1){
			return InteriorCarsList.get(InteriorCarsList.size() - 2);
		}

		return null;
	}

	public int getInteriorCarCountInBank(int projectId, int bankNum){
		List<InteriorCarData> InteriorCarsList = getAll("projectId = " + projectId + " AND bankId = " + bankNum  , null, null, null, "id asc");
		return InteriorCarsList.size();
	}

	public int getAllCnt(int projectId) {
		int cnt = 0;
		Cursor cursor;
		cursor = rawQuery("SELECT COUNT(*) AS cnt FROM " + TABLE_NAME + " WHERE projectId = " + projectId);
		if (cursor != null && cursor.moveToFirst())
			cnt = cursor.getInt(cursor.getColumnIndex("cnt"));
		return cnt;
	}
	public List<InteriorCarData> getAllForInteriorCarEdits(int projectId) {
		Cursor cursor = mDatabase.query(TABLE_NAME + " c LEFT JOIN " + BankDataHandler.TABLE_NAME + " b ON c.projectId = b.projectId AND c.bankId = b.bankNum", new String[]{"c.*, b.name bankDesc"},
				"c.projectId = " + projectId, null, null, null, "c.bankId asc,c.interiorCarNum asc");
		List<InteriorCarData> dataList = new ArrayList<InteriorCarData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public int getMaxInteriorCarNum(int projectId,int bankId){
		int cnt = 0;
		Cursor cursor;
		cursor = rawQuery("SELECT MAX(interiorCarNum) interiorCarNum FROM " + TABLE_NAME + " WHERE projectId = " + projectId + " AND bankId=" + bankId);
		if (cursor != null && cursor.moveToFirst())
			cnt = cursor.getInt(cursor.getColumnIndex("interiorCarNum"));
		return cnt;
	}
}
