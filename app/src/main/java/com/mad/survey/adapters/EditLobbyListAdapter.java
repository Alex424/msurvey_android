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
import com.mad.survey.models.LobbyData;
import com.mad.survey.models.LobbyData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressLint("InflateParams")
public class EditLobbyListAdapter extends BaseAdapter{
	private Context ctx;
	private LayoutInflater inflater;
	private List<LobbyData> dataList;
    private OnClickListener mClickListener;

	public EditLobbyListAdapter(Context ctx, LayoutInflater inflater, OnClickListener onClickListener){
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

	public List<LobbyData> getListData(){
		return this.dataList;
	}

	public void setData(List<LobbyData> dataList){
		this.dataList.clear();
		this.dataList.addAll(dataList);
		notifyDataSetChanged();
	}

	public void appendData(Collection<LobbyData> dataList) {
		this.dataList.addAll(dataList);
		notifyDataSetChanged();
	}

	public void insertData(LobbyData note, int index) {
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

		final LobbyData data = dataList.get(position);

		holder.txtDescriptor.setText(ctx.getString(R.string.edit_lobby_panel_item_title, position+1, data.getLocation()));

        holder.layout.setTag(data);
        holder.layout.setOnClickListener(mClickListener);
		holder.deleteBtn.setOnClickListener(mClickListener);
		holder.deleteBtn.setTag(position);

		return convertView;
	}

	/** View holder for efficiency. */
	static class ViewHolder {
		TextView txtDescriptor;
		LinearLayout layout;
		TextView deleteBtn;
	}
}