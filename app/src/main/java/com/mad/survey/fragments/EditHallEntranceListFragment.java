package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.adapters.EditHallEntranceListAdapter;
import com.mad.survey.dialogs.ItemDeleteConfirmDialog;
import com.mad.survey.dialogs.ItemDeleteDialog;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.HallEntranceData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.models.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class EditHallEntranceListFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener{

    private TextView txtSubTitle;
    private ListView listView;
    private EditHallEntranceListAdapter lstAdapter;
    private LayoutInflater inflater;
    private List<HallEntranceData> dataList = new ArrayList<>();
    private HallEntranceData selectedItem = null;

    public static EditHallEntranceListFragment newInstance(){
        EditHallEntranceListFragment fragment = new EditHallEntranceListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_edit_list, container, false);

        this.inflater = inflater;
        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        listView = (ListView) parent.findViewById(R.id.listView);
        lstAdapter = new EditHallEntranceListAdapter(getActivity(), inflater, this);
        listView.setAdapter(lstAdapter);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnAdd).setOnClickListener(this);
    }

    private void updateUIs(){
        txtSubTitle.setText(getString(R.string.edit_hall_entrance));
        loadData();
    }

    private void goToNext(HallEntranceData data){
        MADSurveyApp.getInstance().setHallEntranceCarNum(data.getCarNum());
        MADSurveyApp.getInstance().setBankNum(data.getBankId());
        BankData bankData = bankDataHandler.get(MADSurveyApp.getInstance().getProjectData().getId(),data.getBankId());
        MADSurveyApp.getInstance().setBankData(bankData);
        MADSurveyApp.getInstance().setFloorDescriptor(data.getFloorDescription());
        MADSurveyApp.getInstance().setFloorNum(data.getFloorNum());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_DOOR_TYPE, "hallentrance_door_type");

    }
    ItemDeleteDialog deleteDlg;
    ItemDeleteConfirmDialog deleteConfirmDlg;
    int nPosition;

    private void doOpenDeleteDialog(){
        deleteDlg = new ItemDeleteDialog(getActivity(), "Bank ("+selectedItem.getBankDesc()+")","Floor ("+selectedItem.getFloorDescription()+")",selectedItem.getDoorTypeString(),  this);
        deleteDlg.show();
    }
    private void doOpenDeleteConfirmDialog(){
        deleteConfirmDlg = new ItemDeleteConfirmDialog(getActivity(), "Bank ("+selectedItem.getBankDesc()+")","Floor ("+selectedItem.getFloorDescription()+")",selectedItem.getDoorTypeString(), this);
        deleteConfirmDlg.show();
    }
    private void loadData(){
        dataList.clear();
        dataList.addAll(hallEntranceDataHandler.getAllForHallEntranceEdits(MADSurveyApp.getInstance().getProjectData().getId()));
        lstAdapter.setData(dataList);
    }
    private void deleteItem(HallEntranceData data){
        deleteDlg.dismiss();
        hallEntranceDataHandler.delete(data);
        loadData();
        doOpenDeleteConfirmDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;

            case R.id.layout:
                HallEntranceData data = (HallEntranceData)v.getTag();
                goToNext(data);
                break;
            case R.id.btnAdd:
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_EDIT_BANK_LIST, "edit_bank_list", 5);
                break;
            case R.id.btnDelete:
                selectedItem = (HallEntranceData)((View)v.getParent()).getTag();
                nPosition = Integer.parseInt(v.getTag().toString());
                doOpenDeleteDialog();
                break;
            case R.id.btnYes:
                deleteItem(selectedItem);
                break;
            case R.id.btnNo:
                deleteDlg.dismiss();
                break;
            case R.id.btnOK:
                deleteConfirmDlg.dismiss();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
