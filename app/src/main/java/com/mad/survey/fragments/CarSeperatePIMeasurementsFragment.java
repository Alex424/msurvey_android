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
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

public class CarSeperatePIMeasurementsFragment extends BaseFragment implements View.OnClickListener {

    private TextView txtCarNumber;
    private EditText edtQuantityPerCar, edtCoverWidth, edtCoverHeight, edtCoverDepth;
    private EditText edtCoverScrewCenterWidth, edtCoverScrewCenterHeight, edtSpaceAvailableWidth, edtSpaceAvailableHeight;

    public static CarSeperatePIMeasurementsFragment newInstance(){
        CarSeperatePIMeasurementsFragment fragment = new CarSeperatePIMeasurementsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_car_seperatepi_measurements, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        edtQuantityPerCar = (EditText) parent.findViewById(R.id.edtQuantityPerCar);
        edtCoverWidth = (EditText) parent.findViewById(R.id.edtCoverWidth);
        edtCoverHeight = (EditText) parent.findViewById(R.id.edtCoverHeight);
        edtCoverScrewCenterWidth = (EditText) parent.findViewById(R.id.edtCoverScrewCenterWidth);
        edtCoverScrewCenterHeight = (EditText) parent.findViewById(R.id.edtCoverScrewCenterHeight);
        edtSpaceAvailableWidth = (EditText) parent.findViewById(R.id.edtSpaceAvailableWidth);
        edtSpaceAvailableHeight = (EditText) parent.findViewById(R.id.edtSpaceAvailableHeight);
        edtCoverDepth = (EditText) parent.findViewById(R.id.edtCoverDepth);
        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtQuantityPerCar.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtQuantityPerCar);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.cops), getString(R.string.sub_title_car_seperatepi_measurements), true, true);
    }

    private void updateUIs(){
        edtQuantityPerCar.setText("");
        edtCoverDepth.setText("");
        edtCoverWidth.setText("");
        edtCoverHeight.setText("");
        edtCoverScrewCenterHeight.setText("");
        edtCoverScrewCenterWidth.setText("");
        edtSpaceAvailableWidth.setText("");
        edtSpaceAvailableHeight.setText("");

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_edit_title, MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtCarNumber.setText(getString(R.string.car_number_n_title, MADSurveyApp.getInstance().getCarNum()+1, bankData.getNumOfCar(),MADSurveyApp.getInstance().getCarData().getCarNumber()));
        }

        CarData carData = MADSurveyApp.getInstance().getCarData();
        if(carData.getNumberPerCabSPI()>0)
            edtQuantityPerCar.setText(carData.getNumberPerCabSPI()+"");
        if(carData.getDepthSPI()>=0)
            edtCoverDepth.setText(carData.getDepthSPI()+"");
        if(carData.getCoverWidthSPI()>=0)
            edtCoverWidth.setText(carData.getCoverWidthSPI()+"");
        if(carData.getCoverHeightSPI()>=0)
            edtCoverHeight.setText(carData.getCoverHeightSPI()+"");
        if(carData.getCoverScrewCenterWidthSPI()>=0)
            edtCoverScrewCenterWidth.setText(carData.getCoverScrewCenterWidthSPI()+"");
        if(carData.getCoverScrewCenterHeightSPI()>=0)
            edtCoverScrewCenterHeight.setText(carData.getCoverScrewCenterHeightSPI()+"");
        if(carData.getSpaceAvailableWidthSPI()>=0)
            edtSpaceAvailableWidth.setText(carData.getSpaceAvailableWidthSPI()+"");
        if(carData.getSpaceAvailableHeightSPI()>=0)
            edtSpaceAvailableHeight.setText(carData.getSpaceAvailableHeightSPI()+"");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        int numberPerCar = ConversionUtils.getIntegerFromEditText(edtQuantityPerCar);
        double coverDepth = ConversionUtils.getDoubleFromEditText(edtCoverDepth);
        double coverWidth = ConversionUtils.getDoubleFromEditText(edtCoverWidth);
        double coverHeight = ConversionUtils.getDoubleFromEditText(edtCoverHeight);
        double coverScrewCenterWidth = ConversionUtils.getDoubleFromEditText(edtCoverScrewCenterWidth);
        double coverScrewCenterHeight = ConversionUtils.getDoubleFromEditText(edtCoverScrewCenterHeight);
        double spaceAvailableWidth = ConversionUtils.getDoubleFromEditText(edtSpaceAvailableWidth);
        double spaceAvailableHeight = ConversionUtils.getDoubleFromEditText(edtSpaceAvailableHeight);

        if(numberPerCar<=0){
            edtQuantityPerCar.requestFocus();
            showToast(getString(R.string.valid_msg_input_quantity_per_car), Toast.LENGTH_SHORT);
            return;
        }
        if(coverWidth<0){
            edtCoverWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_cover_width), Toast.LENGTH_SHORT);
            return;
        }
        if(coverHeight<0){
            edtCoverHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_cover_height), Toast.LENGTH_SHORT);
            return;
        }
        if(coverDepth<0){
            edtCoverDepth.requestFocus();
            showToast(getString(R.string.valid_msg_input_cover_depth), Toast.LENGTH_SHORT);
            return;
        }

        if(coverScrewCenterWidth<0){
            edtCoverScrewCenterWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_screw_center_width), Toast.LENGTH_SHORT);
            return;
        }
        if(coverScrewCenterHeight<0){
            edtCoverScrewCenterHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_screw_center_height), Toast.LENGTH_SHORT);
            return;
        }
        if(spaceAvailableWidth<0){
            edtSpaceAvailableWidth.requestFocus();
            showToast(getString(R.string.valid_msg_input_space_available_width), Toast.LENGTH_SHORT);
            return;
        }
        if(spaceAvailableHeight<0){
            edtSpaceAvailableHeight.requestFocus();
            showToast(getString(R.string.valid_msg_input_space_available_height), Toast.LENGTH_SHORT);
            return;
        }

        CarData carData = MADSurveyApp.getInstance().getCarData();
        carData.setNumberPerCabSPI(numberPerCar);
        carData.setCoverWidthSPI(coverWidth);
        carData.setCoverHeightSPI(coverHeight);
        carData.setDepthSPI(coverDepth);
        carData.setCoverScrewCenterHeightSPI(coverScrewCenterHeight);
        carData.setCoverScrewCenterWidthSPI(coverScrewCenterWidth);
        carData.setSpaceAvailableWidthSPI(spaceAvailableWidth);
        carData.setSpaceAvailableHeightSPI(spaceAvailableHeight);
        MADSurveyApp.getInstance().setCarData(carData);
        carDataHandler.update(carData);

        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_SEPERATEPI_NOTES, "car_seperate_notes");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_car_seperatepi_measurements), R.drawable.img_help_37_car_cop_separatepi_measurements_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }
}
