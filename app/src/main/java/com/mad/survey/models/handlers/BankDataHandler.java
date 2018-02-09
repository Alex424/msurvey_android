package com.mad.survey.models.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.mad.survey.models.BankData;
import com.mad.survey.models.LobbyData;
import com.mad.survey.sqlite.CustomSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class BankDataHandler extends AbstractDataSource<BankData> {
	public static final String TABLE_NAME = "banks";
	public static final String COLUMN_ID = "id";
	private CustomSQLiteHelper mDbHelper;

	public BankDataHandler(CustomSQLiteHelper _mDbHelper, SQLiteDatabase database) {
		super(database);
		this.mDbHelper = _mDbHelper;
	}

	public void truncateTable() {
		this.mDbHelper.truncateTable(mDatabase, TABLE_NAME);
	}

	@Override
	public long insert(BankData entity) {
		if (entity == null) {
			return 0;
		}

		Log.d(getClass().getName(), "Inserting Bank: " + entity.generateContentValuesFromObject().toString());

		return mDatabase.insert(TABLE_NAME, null, entity.generateContentValuesFromObject());
	}

	@Override
	public boolean delete(BankData entity) {
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
	public boolean update(BankData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_NAME, entity.generateContentValuesFromObject(), COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	@Override
	public List<BankData> getAll() {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, null);
		List<BankData> dataList = new ArrayList<BankData>();
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

	public BankData generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		BankData data = new BankData(cursor);
		return data;
	}

	@Override
	public List<BankData> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, "bankNum asc");
		List<BankData> dataList = new ArrayList<BankData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public List<BankData> getAll(int projectId, boolean includeNoneBanks) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId + (!includeNoneBanks? " AND name <> 'none'":""), null, null, null, null);
		List<BankData> dataList = new ArrayList<BankData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public BankData insertNewBankWithName(int projectId, int bankNum, String name){
		// Check if the bank exists or not
		List<BankData> banksList = getAll("projectId = " + projectId + " AND bankNum = " + bankNum , null, null, null, null);
		if (banksList.size() > 0){
			return null;
		}else{
			BankData bankData = new BankData();
            bankData.setProjectId(projectId);
			bankData.setBankNum(bankNum);
			bankData.setName(name);
			long nId = insert(bankData);
			bankData.setId((int) nId);
            return bankData;
		}
	}
	public BankData get(int projectId, int bankNum) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId + " AND bankNum = " + bankNum, null, null, null, null);

		if(cursor!=null && cursor.moveToFirst()) {
			BankData data = generateObjectFromCursor(cursor);
			return data;
		}
		return null;
	}
	public int getAllCnt(int projectId) {
		int cnt = 0;
		Cursor cursor;
		cursor = rawQuery("SELECT COUNT(*) AS cnt FROM " + TABLE_NAME + " WHERE projectId = " + projectId);
		if (cursor != null && cursor.moveToFirst())
			cnt = cursor.getInt(cursor.getColumnIndex("cnt"));
		return cnt;
	}
	public int getMaxBankNum(int projectId){
		int cnt = 0;
		Cursor cursor;
		cursor = rawQuery("SELECT MAX(bankNum) bankNum FROM " + TABLE_NAME + " WHERE projectId = " + projectId);
		if (cursor != null && cursor.moveToFirst())
			cnt = cursor.getInt(cursor.getColumnIndex("bankNum"));
		return cnt;
	}
}