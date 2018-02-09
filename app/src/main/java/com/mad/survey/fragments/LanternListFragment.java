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
import com.mad.survey.adapters.HallStationsListAdapter;
import com.mad.survey.adapters.LanternListAdapter;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.LanternData;
import com.mad.survey.models.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class LanternListFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtSubTitle;
    private ListView lstLantern;

    private LanternListAdapter lstAdapter;
    private LayoutInflater inflater;
    private List<LanternData> dataList = new ArrayList<>();

    public static LanternListFragment newInstance(){
        LanternListFragment fragment = new LanternListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_lanternlist, container, false);

        this.inflater = inflater;
        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        lstLantern = (ListView) parent.findViewById(R.id.lstLanterns);
        lstAdapter = new LanternListAdapter(getActivity(), inflater, this);
        lstLantern.setAdapter(lstAdapter);

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
        LanternData data;
        dataList.clear();
        List<LanternData> lanternDatas = lanternDataHandler.getAll("projectId = " + projectData.getId() + " AND bankId = " + MADSurveyApp.getInstance().getBankNum(),null,null,null,null);
        for (int i = 0; i < lanternDatas.size(); i++){
            data = lanternDatas.get(i);
            dataList.add(data);
        }

        lstAdapter.setData(dataList);
    }

    private void goToNext(int index){

        LanternData lanternData = lanternDataHandler.insertNewLanternSameAs
                (index, MADSurveyApp.getInstance().getProjectData().getId(),
                        MADSurveyApp.getInstance().getBankNum(),
                        MADSurveyApp.getInstance().getLanternNum(),
                        MADSurveyApp.getInstance().getFloorDescriptor());


        MADSurveyApp.getInstance().setLanternData(lanternData);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LANTERN_SAMEAS_MEASUREMENTS, "lantern_sameas_measurements");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;

            case R.id.layoutLantern:
                LanternData data = (LanternData)v.getTag();
                goToNext(data.getId());
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
