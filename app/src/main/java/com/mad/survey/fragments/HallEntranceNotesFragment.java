package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.HallEntranceData;
import com.mad.survey.utils.Utils;

import org.w3c.dom.Text;

public class HallEntranceNotesFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private TextView txtFloorIdentifier;
    private EditText edtNotes;

    public static HallEntranceNotesFragment newInstance(){
        HallEntranceNotesFragment fragment = new HallEntranceNotesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_hall_entrance_notes, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        edtNotes = (EditText) parent.findViewById(R.id.edtNotes);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtNotes.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtNotes);
            }
        });
    }

    private void updateUIs(){

        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum() + 1, MADSurveyApp.getInstance().getProjectData().getNumFloors(), MADSurveyApp.getInstance().getFloorDescriptor()));
            txtCarNumber.setText(getString(R.string.car_n_title, MADSurveyApp.getInstance().getHallEntranceCarNum()+1, MADSurveyApp.getInstance().getBankData().getNumOfCar()));
        }
        HallEntranceData hallEntranceData = MADSurveyApp.getInstance().getHallEntranceData();
        edtNotes.setText(hallEntranceData.getNotes());
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        MADSurveyApp.getInstance().getHallEntranceData().setNotes(edtNotes.getText().toString());
        hallEntranceDataHandler.update(MADSurveyApp.getInstance().getHallEntranceData());
        int id = MADSurveyApp.getInstance().getHallEntranceData().getId();
        ((BaseActivity) getActivity()).replacePhotosFragment(id, GlobalConstant.PROJECT_PHOTO_TYPE_HALL_ENTRANCE, BaseActivity.FRAGMENT_ID_PROJECT_ALL_ENTERED, "project_all_entered");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnNext:
                goToNext();
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
