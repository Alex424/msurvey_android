package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.adapters.HallStationsListAdapter;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.PhotoData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.models.handlers.HallStationDataHandler;

import java.util.ArrayList;
import java.util.List;

public class HallStationListFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener{

    private TextView txtSubTitle;
    private ListView lstHallStation;
    private HallStationsListAdapter lstAdapter;
    private LayoutInflater inflater;
    private List<HallStationData> dataList = new ArrayList<>();

    public static HallStationListFragment newInstance(){
        HallStationListFragment fragment = new HallStationListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_hallstationlist, container, false);

        this.inflater = inflater;
        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        lstHallStation = (ListView) parent.findViewById(R.id.lstHallStations);
        lstAdapter = new HallStationsListAdapter(getActivity(), inflater, this);
        lstHallStation.setAdapter(lstAdapter);

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


        HallStationData data;
        dataList.clear();
        List<HallStationData> hallStationsList = hallStationDataHandler.getAll("projectId = " + projectData.getId() + " AND bankId = " + MADSurveyApp.getInstance().getBankNum(),null,null,null,null);
        for (int i = 0; i < hallStationsList.size(); i++){
            data = hallStationsList.get(i);
            dataList.add(data);
        }

        lstAdapter.setData(dataList);
    }

    private void goToNext(int index){

        HallStationData hallStationData = hallStationDataHandler.insertNewHallStationSameAs
                            (index, MADSurveyApp.getInstance().getProjectData().getId(),
                                    MADSurveyApp.getInstance().getBankNum(),
                                    MADSurveyApp.getInstance().getHallStationNum(),
                                    MADSurveyApp.getInstance().getFloorDescriptor(),
                                    MADSurveyApp.getInstance().getFloorNum());


        MADSurveyApp.getInstance().setHallStationData(hallStationData);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALLSTATION_SAMEAS_MEASUREMENTS, "hall_station_sameas_measurements");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;

            case R.id.layoutHallStation:
                HallStationData data = (HallStationData)v.getTag();
                goToNext(data.getId());
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
