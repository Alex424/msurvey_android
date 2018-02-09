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

public class InteriorCarBackWallFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtSubTitle;
    private EditText edtWidth;
    private EditText edtHeight;

    public static InteriorCarBackWallFragment newInstance(){
        InteriorCarBackWallFragment fragment = new InteriorCarBackWallFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_interior_car_back_wall, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);

        edtWidth = (EditText) parent.findViewById(R.id.edtWidth);
        edtHeight = (EditText) parent.findViewById(R.id.edtHeight);

        parent.findViewById(R.id.btnHelp).setOnClickListener(this);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtHeight.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtHeight);
            }
        });
    }

    private void updateUIs(){
        edtHeight.setText("");
        edtWidth.setText("");
        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getInteriorCarNum()+1, bankData.getNumOfInteriorCar(),MADSurveyApp.getInstance().getInteriorCarData().getCarDescription()));
        }
        InteriorCarData interiorCarData = MADSurveyApp.getInstance().getInteriorCarData();
        double height = interiorCarData.getRearWallHeight();
        double width = interiorCarData.getRearWallWidth();
        if(height >= 0)
            edtHeight.setText(height+"");
        if(width >= 0)
            edtWidth.setText(width+"");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double height = ConversionUtils.getDoubleFromEditText(edtHeight);
        double width = ConversionUtils.getDoubleFromEditText(edtWidth);

        if(height<0){
            edtHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_height), Toast.LENGTH_SHORT);
            return;
        }
        if(width<0){
            edtWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_width), Toast.LENGTH_SHORT);
            return;
        }
        MADSurveyApp.getInstance().getInteriorCarData().setRearWallHeight(height);
        MADSurveyApp.getInstance().getInteriorCarData().setRearWallWidth(width);
        interiorCarDataHandler.update(MADSurveyApp.getInstance().getInteriorCarData());

        if(MADSurveyApp.getInstance().isEditMode()){
            ((BaseActivity) getActivity()).backToSpecificFragment("edit_interior_car_list");
        }
        else{
            int interiorCarCount = MADSurveyApp.getInstance().getBankData().getNumOfInteriorCar();
            int currentNum = MADSurveyApp.getInstance().getInteriorCarNum();


            if(interiorCarCount > currentNum + 1){
                MADSurveyApp.getInstance().setInteriorCarNum(currentNum + 1);
                ((BaseActivity) getActivity()).backToSpecificFragment("interior_car_description");
            }
            else {

                if(MADSurveyApp.getInstance().getProjectData().getHallEntrances() == 1)
                    ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_FLOOR_IDENTIFIER, "hall_entrance_floor_description");
                else{
                    int numBank = MADSurveyApp.getInstance().getProjectData().getNumBanks();
                    int currentBank = MADSurveyApp.getInstance().getBankNum();
                    if(numBank > currentBank + 1){
                        MADSurveyApp.getInstance().setInteriorCarNum(0);
                        MADSurveyApp.getInstance().setBankNum(currentBank + 1);
                        ((BaseActivity) getActivity()).backToSpecificFragment("bank_name");
                    }
                    else{
                        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_PROJECT_ALL_ENTERED, "project_all_entered");
                    }
                }
            }
        }


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
                showHelpDialog(getActivity(), getString(R.string.help_title_car_interior_measurements), R.drawable.img_help_52_interior_car_back_wall_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
