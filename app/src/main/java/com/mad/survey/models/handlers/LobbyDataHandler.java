package com.mad.survey.models.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mad.survey.models.LobbyData;
import com.mad.survey.models.PhotoData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.sqlite.CustomSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class LobbyDataHandler extends AbstractDataSource<LobbyData> {
	public static final String TABLE_NAME = "lobbys";
	public static final String COLUMN_ID = "id";
	private CustomSQLiteHelper mDbHelper;

	public LobbyDataHandler(CustomSQLiteHelper _mDbHelper, SQLiteDatabase database) {
		super(database);
		this.mDbHelper = _mDbHelper;
	}

	public void truncateTable() {
		this.mDbHelper.truncateTable(mDatabase, TABLE_NAME);
	}

	@Override
	public long insert(LobbyData entity) {
		if (entity == null) {
			Log.d(getClass().getName(), "Insert");
			return 0;
		}

		Log.d(getClass().getName(), "Inserting Project: " + entity.generateContentValuesFromObject().toString());

		return mDatabase.insert(TABLE_NAME, null, entity.generateContentValuesFromObject());
	}

	@Override
	public boolean delete(LobbyData entity) {
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
	public boolean update(LobbyData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_NAME, entity.generateContentValuesFromObject(), COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	@Override
	public List<LobbyData> getAll() {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " asc");
		List<LobbyData> dataList = new ArrayList<LobbyData>();
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

	public LobbyData generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		LobbyData data = new LobbyData(cursor);
		return data;
	}

	@Override
	public List<LobbyData> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, COLUMN_ID + " asc");
		List<LobbyData> dataList = new ArrayList<LobbyData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}


	public LobbyData get(int projectId, int lobbyNum) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId + " AND lobbyNum = " + lobbyNum, null, null, null, null);

		if(cursor!=null && cursor.moveToFirst()) {
			LobbyData data = generateObjectFromCursor(cursor);
			return data;
		}
		return null;
	}

    public boolean checkLobbyExistence(int projectId,int lobbyNum){
		LobbyData lobby = get(projectId, lobbyNum);
        return lobby!=null;
    }
	public List<Object> getAll(int projectId) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "projectId=" + projectId, null, null, null, COLUMN_ID + " asc");
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

	public int getMaxLobbyNum(int projectId){
		int cnt = 0;
		Cursor cursor;
		cursor = rawQuery("SELECT MAX(lobbyNum) lobbyNum FROM " + TABLE_NAME + " WHERE projectId = " + projectId);
		if (cursor != null && cursor.moveToFirst())
			cnt = cursor.getInt(cursor.getColumnIndex("lobbyNum"));
		return cnt;
	}
}