package com.mad.survey.models.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mad.survey.models.CarData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.sqlite.CustomSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class CarDataHandler extends AbstractDataSource<CarData> {
	public static final String TABLE_NAME = "cars";
	public static final String COLUMN_ID = "id";
	private CustomSQLiteHelper mDbHelper;

	public CarDataHandler(CustomSQLiteHelper _mDbHelper, SQLiteDatabase database) {
		super(database);
		this.mDbHelper = _mDbHelper;
	}

	public void truncateTable() {
		this.mDbHelper.truncateTable(mDatabase, TABLE_NAME);
	}

	@Override
	public long insert(CarData entity) {
		if (entity == null) {
			return 0;
		}

		Log.d(getClass().getName(), "Inserting car: " + entity.generateContentValuesFromObject().toString());

		return mDatabase.insert(TABLE_NAME, null, entity.generateContentValuesFromObject());
	}

	@Override
	public boolean delete(CarData entity) {
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
	public boolean update(CarData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_NAME, entity.generateContentValuesFromObject(), COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	@Override
	public List<CarData> getAll() {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, null);
		List<CarData> dataList = new ArrayList<CarData>();
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

	public CarData generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		CarData data = new CarData(cursor);
		return data;
	}

	@Override
	public List<CarData> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, "bankId asc");
		List<CarData> dataList = new ArrayList<CarData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public List<CarData> getAll(int projectId, boolean includeNonecars) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId + (!includeNonecars? " AND name <> 'none'":""), null, null, null, null);
		List<CarData> dataList = new ArrayList<CarData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public CarData insertNewcarWithCarNumber(int projectId, int bankNum , int carNum, String carNumber){
		// Check if the car exists or not
		List<CarData> carsList = getAll("projectId = " + projectId + " AND bankId = " + bankNum + " AND carNum = " + carNum , null, null, null, null);
		if (carsList.size() > 0){
			return null;
		}else{
			CarData carData = new CarData();
			carData.setProjectId(projectId);
			carData.setBankId(bankNum);
			carData.setCarNum(carNum);
			carData.setCarNumber(carNumber);
			long nId = insert(carData);
			carData.setId((int) nId);
			return carData;
		}
	}
	public CarData get(int projectId, int bankNum,int carNum) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId + " AND bankId = " + bankNum + " AND carNum = " + carNum, null, null, null, null);

		if(cursor!=null && cursor.moveToFirst()) {
			CarData data = generateObjectFromCursor(cursor);
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

	public List<CarData> getAllForCarEdits(int projectId) {
		Cursor cursor = mDatabase.query(TABLE_NAME + " c LEFT JOIN " + BankDataHandler.TABLE_NAME + " b ON c.projectId = b.projectId AND c.bankId = b.bankNum", new String[]{"c.*, b.name bankDesc"},
				"c.projectId = " + projectId, null, null, null, "c.bankId asc,c.carNum asc");
		List<CarData> dataList = new ArrayList<CarData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public int getMaxCarNum(int projectId,int bankId){
		int cnt = 0;
		Cursor cursor;
		cursor = rawQuery("SELECT MAX(carNum) carNum FROM " + TABLE_NAME + " WHERE projectId = " + projectId + " AND bankId=" + bankId);
		if (cursor != null && cursor.moveToFirst())
			cnt = cursor.getInt(cursor.getColumnIndex("carNum"));
		return cnt;
	}
}
