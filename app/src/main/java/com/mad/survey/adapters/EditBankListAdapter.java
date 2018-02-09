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
import com.mad.survey.models.BankData;
import com.mad.survey.models.BankData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressLint("InflateParams")
public class EditBankListAdapter extends BaseAdapter{
	private Context ctx;
	private LayoutInflater inflater;
	private List<BankData> dataList;
    private OnClickListener mClickListener;

	public EditBankListAdapter(Context ctx, LayoutInflater inflater, OnClickListener onClickListener){
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

	public List<BankData> getListData(){
		return this.dataList;
	}

	public void setData(List<BankData> dataList){
		this.dataList.clear();
		this.dataList.addAll(dataList);
		notifyDataSetChanged();
	}

	public void appendData(Collection<BankData> dataList) {
		this.dataList.addAll(dataList);
		notifyDataSetChanged();
	}

	public void insertData(BankData note, int index) {
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

		final BankData data = dataList.get(position);

		holder.txtDescriptor.setText(ctx.getString(R.string.edit_bank_item_title, position + 1, data.getName()));

        holder.layout.setTag(data);
		holder.deleteBtn.setTag(position);
        holder.layout.setOnClickListener(mClickListener);
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