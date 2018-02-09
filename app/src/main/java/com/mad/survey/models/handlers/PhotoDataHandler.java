package com.mad.survey.models.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.models.PhotoData;
import com.mad.survey.sqlite.CustomSQLiteHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

public class PhotoDataHandler extends AbstractDataSource<PhotoData> {
	public static final String TABLE_NAME = "photos";
	public static final String COLUMN_ID = "id";
	private CustomSQLiteHelper mDbHelper;

	public PhotoDataHandler(CustomSQLiteHelper _mDbHelper, SQLiteDatabase database) {
		super(database);
		this.mDbHelper = _mDbHelper;
	}

	public void truncateTable() {
		this.mDbHelper.truncateTable(mDatabase, TABLE_NAME);
	}

	@Override
	public long insert(PhotoData entity) {
		if (entity == null) {
			return 0;
		}
		return mDatabase.insert(TABLE_NAME, null, entity.generateContentValuesFromObject());
	}

	@Override
	public boolean delete(PhotoData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.delete(TABLE_NAME, COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	public void deletePhotos(String condition,String[] conditionParams){

	}
	public void delete(String condition, String[] conditionParams) {
		List<PhotoData> photosList = getAll(condition, conditionParams, null,null,null);
		PhotoData photo;
		for (int i = 0; i < photosList.size(); i++){
			photo = photosList.get(i);
			File file = new File(GlobalConstant.getPhotoFilePath(photo.getFileName()));
			file.delete();
		}
		this.mDatabase.delete(TABLE_NAME, condition, conditionParams);
	}

	@Override
	public boolean update(PhotoData entity) {
		if (entity == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_NAME, entity.generateContentValuesFromObject(), COLUMN_ID + " = " + entity.getId(), null);
		return result != 0;
	}

	@Override
	public List<PhotoData> getAll() {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, null, null, null, null, null);
		List<PhotoData> dataList = new ArrayList<PhotoData>();
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

	public PhotoData generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		PhotoData data = new PhotoData(cursor);
		return data;
	}

	@Override
	public List<PhotoData> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
		List<PhotoData> dataList = new ArrayList<PhotoData>();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				dataList.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return dataList;
	}

	public List<PhotoData> getAll(int linkedId, int type, int rearEntrance) {
		Cursor cursor = mDatabase.query(TABLE_NAME, null, "linkedId=" + linkedId + " AND type = " + type, null, null, null, null);
		List<PhotoData> dataList = new ArrayList<PhotoData>();
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