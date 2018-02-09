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
import com.mad.survey.models.CarData;
import com.mad.survey.models.CopData;
import com.mad.survey.utils.Utils;

public class CarCopNotesFragment extends BaseFragment implements View.OnClickListener {

    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private EditText edtNotes;

    public static CarCopNotesFragment newInstance(){
        CarCopNotesFragment fragment = new CarCopNotesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_cop_notes, container, false);

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
        CarData carData = MADSurveyApp.getInstance().getCarData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
        }
        txtCarNumber.setText(getString(R.string.cop_n_title, MADSurveyApp.getInstance().getCopNum() + 1, MADSurveyApp.getInstance().getCarData().getNumberOfCops(), MADSurveyApp.getInstance().getCarData().getCarNumber()));
        CopData copData = MADSurveyApp.getInstance().getCopData();
        edtNotes.setText(copData.getNotes());
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        MADSurveyApp.getInstance().getCopData().setNotes(edtNotes.getText().toString());
        copDataHandler.update(MADSurveyApp.getInstance().getCopData());
        int copId = MADSurveyApp.getInstance().getCopData().getId();
        ((BaseActivity) getActivity()).replacePhotosFragment(copId, GlobalConstant.PROJECT_PHOTO_TYPE_CAR_COP, BaseActivity.FRAGMENT_ID_CAR_RIDING_LANTERN, "car_riding_lantern");
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
}
