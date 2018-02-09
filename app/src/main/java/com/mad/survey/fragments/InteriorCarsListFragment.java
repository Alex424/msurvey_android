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
import com.mad.survey.adapters.InteriorCarsListAdapter;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.models.InteriorCarDoorData;
import com.mad.survey.models.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class InteriorCarsListFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener{

    private TextView txtSubTitle;
    private ListView lstInteriorCars;
    private InteriorCarsListAdapter lstAdapter;
    private LayoutInflater inflater;
    private List<HallStationData> dataList = new ArrayList<>();

    public static InteriorCarsListFragment newInstance(){
        InteriorCarsListFragment fragment = new InteriorCarsListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_list, container, false);

        this.inflater = inflater;
        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        lstInteriorCars = (ListView) parent.findViewById(R.id.lstInteriorCars);
        lstAdapter = new InteriorCarsListAdapter(getActivity(), inflater, this);
        lstInteriorCars.setAdapter(lstAdapter);

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

        List<InteriorCarData> interiorCarsList = interiorCarDataHandler.getAll("projectId = " + projectData.getId()
                + " AND bankId = " + MADSurveyApp.getInstance().getBankNum(), null, null, null, "id asc");

        //remove the latest interior car, because it's just the current one already inserted at the car description screen.
        if (interiorCarsList.size() > 1){
            interiorCarsList.remove(interiorCarsList.size() - 1);
        }

        lstAdapter.setData(interiorCarsList);
    }

    private void goToNext(InteriorCarData previousInteriorCarData){
        copySameAsInteriorCarData(previousInteriorCarData);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;

            case R.id.layoutInteriorCar:
                copySameAsInteriorCarData((InteriorCarData)v.getTag());
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
