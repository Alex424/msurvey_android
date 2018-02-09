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

public class CarCopSwingMeasurementsFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener {

    private TextView txtCarNumber;
    private EditText edtSwingPanelWidth, edtSwingPanelHeight, edtAFFValue;

    public static CarCopSwingMeasurementsFragment newInstance(){
        CarCopSwingMeasurementsFragment fragment = new CarCopSwingMeasurementsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_cop_swing_measurements, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtSwingPanelWidth = (EditText) parent.findViewById(R.id.edtSwingPanelWidth);
        edtSwingPanelHeight = (EditText) parent.findViewById(R.id.edtSwingPanelHeight);
        edtAFFValue = (EditText) parent.findViewById(R.id.edtAFFValue);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtSwingPanelWidth.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtSwingPanelWidth);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.cops), getString(R.string.sub_title_cop_swing_measurements), true, true);
    }

    private void updateUIs(){

        edtSwingPanelWidth.setText("");
        edtSwingPanelHeight.setText("");
        edtAFFValue.setText("");

        CarData carData = MADSurveyApp.getInstance().getCarData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
        }
        txtCarNumber.setText(getString(R.string.cop_n_title, MADSurveyApp.getInstance().getCopNum() + 1, MADSurveyApp.getInstance().getCarData().getNumberOfCops(), MADSurveyApp.getInstance().getCarData().getCarNumber()));
        CopData copData = MADSurveyApp.getInstance().getCopData();
        if(copData.getSwingPanelHeight()>=0)
            edtSwingPanelHeight.setText(copData.getSwingPanelHeight()+"");
        if(copData.getSwingPanelWidth()>=0)
            edtSwingPanelWidth.setText(copData.getSwingPanelWidth()+"");
        if(copData.getCoverAff()>=0)
            edtAFFValue.setText(copData.getCoverAff()+"");


    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double swingPanelHeight = ConversionUtils.getDoubleFromEditText(edtSwingPanelHeight);
        double swingPanelWidth = ConversionUtils.getDoubleFromEditText(edtSwingPanelWidth);
        double affValue = ConversionUtils.getDoubleFromEditText(edtAFFValue);
        if(swingPanelWidth<0){
            edtSwingPanelWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_cop_swing_panel_width), Toast.LENGTH_SHORT);
            return;
        }
        if(swingPanelHeight<0){
            edtSwingPanelHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_cop_swing_panel_height), Toast.LENGTH_SHORT);
            return;
        }
        if(affValue<0){
            edtAFFValue.requestFocus();
            showToast(getString(R.string.valid_msg_input_car_cop_aff_value), Toast.LENGTH_SHORT);
            return;
        }
        CopData copData = MADSurveyApp.getInstance().getCopData();
        copData.setSwingPanelWidth(swingPanelWidth);
        copData.setSwingPanelHeight(swingPanelHeight);
        copData.setCoverAff(affValue);
        MADSurveyApp.getInstance().setCopData(copData);
        copDataHandler.update(copData);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_COP_NOTES, "car_cop_notes");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_car_cop_measurements), R.drawable.img_help_31_car_cop_swing_measurements_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
