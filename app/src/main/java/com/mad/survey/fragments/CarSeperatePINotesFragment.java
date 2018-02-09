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
import com.mad.survey.models.CarData;
import com.mad.survey.utils.Utils;

public class CarSeperatePINotesFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private EditText edtNotes;

    public static CarSeperatePINotesFragment newInstance(){
        CarSeperatePINotesFragment fragment = new CarSeperatePINotesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_seperatepi_notes, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);

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
        edtNotes.setText("");
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getCarNum()+1, bankData.getNumOfCar(),MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }

        CarData carData = MADSurveyApp.getInstance().getCarData();
        edtNotes.setText(carData.getNotesSPI());
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String notes = edtNotes.getText().toString();
        if(!notes.equals("")){
            MADSurveyApp.getInstance().getCarData().setNotesSPI(notes);
            carDataHandler.update(MADSurveyApp.getInstance().getCarData());
        }
        int id = MADSurveyApp.getInstance().getCarData().getId();
        ((BaseActivity) getActivity()).replacePhotosFragment(id, GlobalConstant.PROJECT_PHOTO_TYPE_CAR_SEPERATEPI, BaseActivity.FRAGMENT_ID_INTERIOR_CAR_COUNT, "interior_car_count");
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
