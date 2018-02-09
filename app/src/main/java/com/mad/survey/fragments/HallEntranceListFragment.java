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
import com.mad.survey.adapters.HallEntrancesListAdapter;
import com.mad.survey.adapters.HallStationsListAdapter;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.HallEntranceData;
import com.mad.survey.models.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class HallEntranceListFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener{

    private TextView txtSubTitle;
    private ListView lstHallEntrances;
    private HallEntrancesListAdapter lstAdapter;
    private LayoutInflater inflater;
    private List<HallEntranceData> dataList = new ArrayList<>();

    public static HallEntranceListFragment newInstance(){
        HallEntranceListFragment fragment = new HallEntranceListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_hall_entrance_list, container, false);

        this.inflater = inflater;
        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        lstHallEntrances = (ListView) parent.findViewById(R.id.lstHallEntrances);
        lstAdapter = new HallEntrancesListAdapter(getActivity(), inflater, this);
        lstHallEntrances.setAdapter(lstAdapter);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
    }

    private void updateUIs(){
        ProjectData projectData = MADSurveyApp.getInstance().getProjectData();

        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title,MADSurveyApp.getInstance().getBankNum()+1,MADSurveyApp.getInstance().getBankData().getName()));
        }

        dataList.clear();
        List<HallEntranceData> dataList = hallEntranceDataHandler.getAll("projectId = " + projectData.getId() + " AND bankId = " + MADSurveyApp.getInstance().getBankNum(),null,null,null,"id asc");

        lstAdapter.setData(dataList);
    }

    private void goToNext(HallEntranceData previousHallEntranceData){
        previousHallEntranceData.setFloorDescription(MADSurveyApp.getInstance().getFloorDescriptor());
        previousHallEntranceData.setCarNum(MADSurveyApp.getInstance().getHallEntranceCarNum());
        long nId = hallEntranceDataHandler.insert(previousHallEntranceData);
        previousHallEntranceData.setId((int) nId);

        MADSurveyApp.getInstance().setHallEntranceData(previousHallEntranceData);

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_DOOR_TYPE, "hall_entrance_door_type");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;

            case R.id.layoutHallEntrance:
                HallEntranceData data = (HallEntranceData)v.getTag();
                goToNext(data);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
