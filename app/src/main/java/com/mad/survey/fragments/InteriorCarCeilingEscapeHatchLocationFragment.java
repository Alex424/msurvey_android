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
import com.mad.survey.models.BankData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class InteriorCarCeilingEscapeHatchLocationFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtSubTitle;
    private TextView txtCarNumber;
    private EditText edtEscapeHatchLocation;
    private EditText edtHaToLeftWall;
    private EditText edtHaToBackWall;
    private EditText edtHaWidth;
    private EditText edtHaLength;

    public static InteriorCarCeilingEscapeHatchLocationFragment newInstance(){
        InteriorCarCeilingEscapeHatchLocationFragment fragment = new InteriorCarCeilingEscapeHatchLocationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_ceiling_escape_hatch_location, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtEscapeHatchLocation = (EditText) parent.findViewById(R.id.edtEscapeHatchLocation);
        edtHaToLeftWall = (EditText) parent.findViewById(R.id.edtHatchToLeftWall);
        edtHaToBackWall = (EditText) parent.findViewById(R.id.edtHatchToBackWall);
        edtHaWidth = (EditText) parent.findViewById(R.id.edtHatchWidth);
        edtHaLength = (EditText) parent.findViewById(R.id.edtHatchLength);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtEscapeHatchLocation.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtEscapeHatchLocation);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.sub_title_cab_interior), getString(R.string.sub_title_car_interior_escape_hatch_location), true, true);
    }

    private void updateUIs(){
        edtEscapeHatchLocation.setText("");
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();
        edtEscapeHatchLocation.setText(interiorCarData.getEscapeHatchLocation());

        if (interiorCarData.getHaToLeftWall() >= 0)
            edtHaToLeftWall.setText(interiorCarData.getHaToLeftWall() + "");
        if (interiorCarData.getHaToBackWall() >= 0)
            edtHaToBackWall.setText(interiorCarData.getHaToBackWall() + "");
        if (interiorCarData.getHaWidth() >= 0)
            edtHaWidth.setText(interiorCarData.getHaWidth() + "");
        if (interiorCarData.getHaLength() >= 0)
            edtHaLength.setText(interiorCarData.getHaLength() + "");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        String escapeHatchLocation = edtEscapeHatchLocation.getText().toString();
        if(escapeHatchLocation.equals("")){
            edtEscapeHatchLocation.requestFocus();
            showToast(getString(R.string.valid_msg_input_escape_hatch_location), Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().getInteriorCarData().setEscapeHatchLocation(escapeHatchLocation);

        MADSurveyApp.getInstance().getInteriorCarData().setHaToLeftWall(ConversionUtils.getDoubleFromEditText(edtHaToLeftWall));
        MADSurveyApp.getInstance().getInteriorCarData().setHaToBackWall(ConversionUtils.getDoubleFromEditText(edtHaToBackWall));
        MADSurveyApp.getInstance().getInteriorCarData().setHaWidth(ConversionUtils.getDoubleFromEditText(edtHaWidth));
        MADSurveyApp.getInstance().getInteriorCarData().setHaLength(ConversionUtils.getDoubleFromEditText(edtHaLength));

        interiorCarDataHandler.update(MADSurveyApp.getInstance().getInteriorCarData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_TYPE, "interior_car_ceiling_frame_type");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_escape_hatch_location), R.drawable.img_help_interior_car_ceiling_escape_hatch_location, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
