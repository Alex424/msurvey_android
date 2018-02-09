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
import com.mad.survey.adapters.EditCarsListAdapter;
import com.mad.survey.adapters.EditCarsListAdapter;
import com.mad.survey.dialogs.ItemDeleteConfirmDialog;
import com.mad.survey.dialogs.ItemDeleteDialog;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.LanternData;
import com.mad.survey.models.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class EditCarListFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener{

    private TextView txtSubTitle;
    private ListView listView;
    private EditCarsListAdapter lstAdapter;
    private LayoutInflater inflater;
    private List<CarData> dataList = new ArrayList<>();
    private CarData selectedItem=null;

    public static EditCarListFragment newInstance(){
        EditCarListFragment fragment = new EditCarListFragment();
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
        lstAdapter = new EditCarsListAdapter(getActivity(), inflater, this);
        listView.setAdapter(lstAdapter);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnAdd).setOnClickListener(this);
    }

    private void updateUIs(){
        txtSubTitle.setText(getString(R.string.edit_elevator_cars));
        loadData();
    }

    private void goToNext(CarData data){
        MADSurveyApp.getInstance().setCarNum(data.getCarNum());
        MADSurveyApp.getInstance().setBankNum(data.getBankId());
        BankData bankData = bankDataHandler.get(MADSurveyApp.getInstance().getProjectData().getId(),data.getBankId());
        MADSurveyApp.getInstance().setBankData(bankData);

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_DESCRIPTION, "car_description");

    }
    ItemDeleteDialog deleteDlg;
    ItemDeleteConfirmDialog deleteConfirmDlg;
    int nPosition;

    private void doOpenDeleteDialog(){
        deleteDlg = new ItemDeleteDialog(getActivity(),"Bank ("+selectedItem.getBankDesc()+")","Car"+(nPosition+1)+" ("+selectedItem.getCarNumber()+")","", this);
        deleteDlg.show();
    }
    private void doOpenDeleteConfirmDialog(){
        deleteConfirmDlg = new ItemDeleteConfirmDialog(getActivity(),"Bank ("+selectedItem.getBankDesc()+")","Car"+(nPosition+1)+" ("+selectedItem.getCarNumber()+")","",this);
        deleteConfirmDlg.show();
    }
    private void loadData(){
        dataList.clear();
        dataList.addAll(carDataHandler.getAllForCarEdits(MADSurveyApp.getInstance().getProjectData().getId()));
        lstAdapter.setData(dataList);
    }
    private void deleteItem(CarData data){
        deleteDlg.dismiss();
        carDataHandler.delete(data);
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
                CarData data = (CarData)v.getTag();
                goToNext(data);
                break;
            case R.id.btnAdd:
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_EDIT_BANK_LIST, "edit_bank_list", 3);
                break;
            case R.id.btnDelete:
                selectedItem = (CarData)((View)v.getParent()).getTag();
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
