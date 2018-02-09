package com.mad.survey.models.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mad.survey.models.ProjectData;
import com.mad.survey.sqlite.CustomSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class ProjectDataHandler extends AbstractDataSource<ProjectData> {
	public static final String TABLE_NAME = "projects";
	public static final String COLUMN_ID = "id";
	private CustomSQLiteHelper mDbHelper;

	public ProjectDataHandler(CustomSQLiteHelper _mDbHelper, SQLiteDatabase database) {

		super(database);
		this.mDbHelper = _mDbHelper;
		Log.d("YYY","YYY");
	}

	public void truncateTable() {
		this.mDbHelper.truncateTable(mDatabase, TABLE_NAME);
	}

	@Override
	public long insert(ProjectData entity) {
		if (entity == null) {
			Log.d(getClass().getName(), "Insert");
			return 0;
		}

		Log.d(getClass().getName(), "Inserting Project: " + entity.generateContentValuesFromObject().toString());

		return mDatabase.insert(TABLE_NAME, null, entity.generateContentValuesFromObject());
	}

	@Override
	public boolean delete(ProjectData entity) {
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
	public boolean update(ProjectData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_NAME, entity.generateContentValuesFromObject(), COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	@Override
	public List<ProjectData> getAll() {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " asc");
		List<ProjectData> dataList = new ArrayList<ProjectData>();
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

	public ProjectData generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		ProjectData data = new ProjectData(cursor);
		return data;
	}

	@Override
	public List<ProjectData> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
		List<ProjectData> dataList = new ArrayList<ProjectData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

    public boolean checkProjectIdDuplicate(String projectNo){
        List<ProjectData> projects = getAll("no = '" + projectNo + "'", null, null, null, null);
        return projects.size() > 0;
    }
}