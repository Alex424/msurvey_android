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
import com.mad.survey.adapters.EditInteriorCarListAdapter;
import com.mad.survey.adapters.EditInteriorCarListAdapter;
import com.mad.survey.dialogs.ItemDeleteConfirmDialog;
import com.mad.survey.dialogs.ItemDeleteDialog;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.models.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class EditInteriorCarListFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener{

    private TextView txtSubTitle;
    private ListView listView;
    private EditInteriorCarListAdapter lstAdapter;
    private LayoutInflater inflater;
    private List<InteriorCarData> dataList = new ArrayList<>();
    private InteriorCarData selectedItem = null;

    public static EditInteriorCarListFragment newInstance(){
        EditInteriorCarListFragment fragment = new EditInteriorCarListFragment();
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
        lstAdapter = new EditInteriorCarListAdapter(getActivity(), inflater, this);
        listView.setAdapter(lstAdapter);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnAdd).setOnClickListener(this);
    }

    private void updateUIs(){
        txtSubTitle.setText(getString(R.string.edit_cab_interior));
        loadData();
    }

    private void goToNext(InteriorCarData data){
        MADSurveyApp.getInstance().setInteriorCarNum(data.getInteriorCarNum());
        MADSurveyApp.getInstance().setBankNum(data.getBankId());
        BankData bankData = bankDataHandler.get(MADSurveyApp.getInstance().getProjectData().getId(),data.getBankId());
        MADSurveyApp.getInstance().setBankData(bankData);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_DESCRIPTION, "interiorCar_description");

    }
    ItemDeleteDialog deleteDlg;
    ItemDeleteConfirmDialog deleteConfirmDlg;
    int nPosition;

    private void doOpenDeleteDialog(){
        deleteDlg = new ItemDeleteDialog(getActivity(),"Bank ("+selectedItem.getBankDesc()+")","Cab Interior"+(nPosition+1)+" ("+selectedItem.getCarDescription()+")","", this);
        deleteDlg.show();
    }
    private void doOpenDeleteConfirmDialog(){
        deleteConfirmDlg = new ItemDeleteConfirmDialog(getActivity(), "Bank ("+selectedItem.getBankDesc()+")","Cab Interior"+(nPosition+1)+" ("+selectedItem.getCarDescription()+")","", this);
        deleteConfirmDlg.show();
    }
    private void loadData(){
        dataList.clear();
        dataList.addAll(interiorCarDataHandler.getAllForInteriorCarEdits(MADSurveyApp.getInstance().getProjectData().getId()));
        lstAdapter.setData(dataList);
    }
    private void deleteItem(InteriorCarData data){
        deleteDlg.dismiss();
        interiorCarDataHandler.delete(data);
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
                MADSurveyApp.getInstance().setIsInteriorAddFromEdit(false);
                InteriorCarData data = (InteriorCarData)v.getTag();
                goToNext(data);
                break;
            case R.id.btnAdd:
                MADSurveyApp.getInstance().setIsInteriorAddFromEdit(true);
                ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_EDIT_BANK_LIST, "edit_bank_list", 4);
                break;
            case R.id.btnDelete:
                selectedItem = (InteriorCarData)((View)v.getParent()).getTag();
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
