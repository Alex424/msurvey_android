package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.globals.GlobalConstant;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.CarData;
import com.mad.survey.models.CopData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class CarCopAppliedMeasurementsFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private EditText edtReturnPanelWidth, edtReturnPanelHeight, edtCopWidth, edtCopHeight;
    private EditText edtCoverToOpening, edtAFFValue;

    public static CarCopAppliedMeasurementsFragment newInstance(){
        CarCopAppliedMeasurementsFragment fragment = new CarCopAppliedMeasurementsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_cop_applied_measurements, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtReturnPanelWidth = (EditText) parent.findViewById(R.id.edtReturnPanelWidth);
        edtReturnPanelHeight = (EditText) parent.findViewById(R.id.edtReturnPanelHeight);
        edtCopWidth = (EditText) parent.findViewById(R.id.edtCopWidth);
        edtCopHeight = (EditText) parent.findViewById(R.id.edtCopHeight);
        edtCoverToOpening = (EditText) parent.findViewById(R.id.edtCoverToOpening);
        edtAFFValue = (EditText) parent.findViewById(R.id.edtAFFValue);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtReturnPanelWidth.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtReturnPanelWidth);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.cops), getString(R.string.sub_title_cop_applied_measurements), true, true);
    }

    private void updateUIs(){
        edtReturnPanelHeight.setText("");
        edtReturnPanelWidth.setText("");
        edtCopWidth.setText("");
        edtCopHeight.setText("");
        edtCoverToOpening.setText("");
        edtAFFValue.setText("");

        CarData carData = MADSurveyApp.getInstance().getCarData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
        }

        txtCarNumber.setText(getString(R.string.cop_n_title, MADSurveyApp.getInstance().getCopNum() + 1, MADSurveyApp.getInstance().getCarData().getNumberOfCops(), MADSurveyApp.getInstance().getCarData().getCarNumber()));
        CopData copData = MADSurveyApp.getInstance().getCopData();
        if(copData.getReturnPanelWidth()>=0)
            edtReturnPanelWidth.setText(copData.getReturnPanelWidth()+"");
        if(copData.getReturnPanelHeight()>=0)
            edtReturnPanelHeight.setText(copData.getReturnPanelHeight()+"");
        if(copData.getCoverWidth()>=0)
            edtCopWidth.setText(copData.getCoverWidth()+"");
        if(copData.getCoverHeight()>=0)
            edtCopHeight.setText(copData.getCoverHeight()+"");
        if(copData.getCoverToOpening()>=0)
            edtCoverToOpening.setText(copData.getCoverToOpening()+"");
        if(copData.getCoverAff()>=0)
            edtAFFValue.setText(copData.getCoverAff()+"");

    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double returnPanelWidth = ConversionUtils.getDoubleFromEditText(edtReturnPanelWidth);
        double returnPanelHeight = ConversionUtils.getDoubleFromEditText(edtReturnPanelHeight);
        double copWidth = ConversionUtils.getDoubleFromEditText(edtCopWidth);
        double copHeight = ConversionUtils.getDoubleFromEditText(edtCopHeight);
        double coverToOpening = ConversionUtils.getDoubleFromEditText(edtCoverToOpening);
        double affValue = ConversionUtils.getDoubleFromEditText(edtAFFValue);

        if(returnPanelWidth<0){
            edtReturnPanelWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_cop_return_panel_width), Toast.LENGTH_SHORT);
            return;
        }
        if(returnPanelHeight<0){
            edtReturnPanelHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_cop_return_panel_height), Toast.LENGTH_SHORT);
            return;
        }
        if(copWidth<0){
            edtCopWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_cop_width), Toast.LENGTH_SHORT);
            return;
        }
        if(copHeight<0){
            edtCopHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_cop_height), Toast.LENGTH_SHORT);
            return;
        }
        if(coverToOpening<0){
            edtCoverToOpening.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_cop_cover_to_opening), Toast.LENGTH_SHORT);
            return;
        }
        if(affValue<0){
            edtAFFValue.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_cop_aff_value), Toast.LENGTH_SHORT);
            return;
        }
        CopData copData = MADSurveyApp.getInstance().getCopData();
        copData.setReturnPanelWidth(returnPanelWidth);
        copData.setReturnPanelHeight(returnPanelHeight);
        copData.setCoverWidth(copWidth);
        copData.setCoverHeight(copHeight);
        copData.setCoverToOpening(coverToOpening);
        copData.setCoverAff(affValue);
        MADSurveyApp.getInstance().setCopData(copData);
        copDataHandler.update(copData);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_COP_NOTES, "car_notes");
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
            case R.id.btnHelp:
                showHelpDialog(getActivity(), getString(R.string.help_title_car_cop_measurements), R.drawable.img_help_31_car_cop_applied_measurements_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
