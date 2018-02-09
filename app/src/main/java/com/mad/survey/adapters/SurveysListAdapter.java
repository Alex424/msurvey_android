package com.mad.survey.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.survey.R;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.models.ProjectData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressLint("InflateParams")
public class SurveysListAdapter extends BaseAdapter{
	private Context ctx;
	private LayoutInflater inflater;
	private List<ProjectData> dataList;
    private OnClickListener mClickListener;

	public SurveysListAdapter(Context ctx, LayoutInflater inflater, OnClickListener onClickListener){
		this.ctx = ctx;
		this.inflater = inflater;
		this.dataList = new ArrayList<>();
        this.mClickListener = onClickListener;
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public List<ProjectData> getListData(){
		return this.dataList;
	}

	public void setData(List<ProjectData> dataList){
		this.dataList.clear();
		this.dataList.addAll(dataList);
		notifyDataSetChanged();
	}

	public void appendData(Collection<ProjectData> dataList) {
		this.dataList.addAll(dataList);
		notifyDataSetChanged();
	}

	public void insertData(ProjectData note, int index) {
		this.dataList.add(index, note);
		notifyDataSetChanged();
	}

	public void clearData() {
		if (this.dataList.isEmpty()) {
			return;
		}
		this.dataList.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item_survey_view, parent, false);

			holder.layoutSurvey = (LinearLayout) convertView.findViewById(R.id.layoutSurvey);
			holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
			holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
			holder.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final ProjectData data = dataList.get(position);

		holder.txtName.setText(data.getName());
		holder.txtDate.setText(data.getSurveyDate());
		if (data.getStatus().equals(GlobalConstant.STATUS_SUBMITTED)){
			holder.txtStatus.setTextColor(ctx.getResources().getColor(R.color.white));
			holder.txtStatus.setText(ctx.getResources().getString(R.string.submitted));
		}else {
			holder.txtStatus.setTextColor(ctx.getResources().getColor(R.color.red_color1));
			holder.txtStatus.setText(ctx.getResources().getString(R.string.not_submitted));
		}

        holder.layoutSurvey.setTag(data);
        holder.layoutSurvey.setOnClickListener(mClickListener);

		return convertView;
	}

	/** View holder for efficiency. */
	static class ViewHolder {
		TextView txtName;
		TextView txtDate;
		TextView txtStatus;
		LinearLayout layoutSurvey;
	}
}