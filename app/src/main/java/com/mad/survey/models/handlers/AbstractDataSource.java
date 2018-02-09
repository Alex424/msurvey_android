package com.mad.survey.models.handlers;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public abstract class AbstractDataSource<T>{
	protected SQLiteDatabase mDatabase;
	public AbstractDataSource(SQLiteDatabase database) {
		mDatabase = database;
	}
	public abstract long insert(T entity);
	public abstract boolean delete(T entity);
	public abstract boolean update(T entity);
	@SuppressWarnings("rawtypes")
	public abstract List getAll();
	@SuppressWarnings("rawtypes")
	public abstract List getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy);
} 
