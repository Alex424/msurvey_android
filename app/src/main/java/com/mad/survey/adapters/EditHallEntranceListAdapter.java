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
import com.mad.survey.models.HallEntranceData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressLint("InflateParams")
public class EditHallEntranceListAdapter extends BaseAdapter{
	private Context ctx;
	private LayoutInflater inflater;
	private List<HallEntranceData> dataList;
    private OnClickListener mClickListener;

	public EditHallEntranceListAdapter(Context ctx, LayoutInflater inflater, OnClickListener onClickListener){
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

	public List<HallEntranceData> getListData(){
		return this.dataList;
	}

	public void setData(List<HallEntranceData> dataList){
		this.dataList.clear();
		this.dataList.addAll(dataList);
		notifyDataSetChanged();
	}

	public void appendData(Collection<HallEntranceData> dataList) {
		this.dataList.addAll(dataList);
		notifyDataSetChanged();
	}

	public void insertData(HallEntranceData note, int index) {
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
			convertView = inflater.inflate(R.layout.list_item_view, parent, false);
			holder.deleteBtn = (TextView) convertView.findViewById(R.id.btnDelete);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
			holder.txtDescriptor = (TextView) convertView.findViewById(R.id.txtDescriptor);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final HallEntranceData data = dataList.get(position);
		holder.txtDescriptor.setText(ctx.getResources().getString(R.string.edit_hall_entrance_item_title,data.getBankDesc(),data.getFloorDescription(),position+1,data.getDoorTypeString()));
        holder.layout.setTag(data);

        holder.layout.setOnClickListener(mClickListener);
		holder.deleteBtn.setTag(position);
		holder.deleteBtn.setOnClickListener(mClickListener);
		return convertView;
	}

	/** View holder for efficiency. */
	static class ViewHolder {
		TextView txtDescriptor;
		LinearLayout layout;
		TextView deleteBtn;
	}
}