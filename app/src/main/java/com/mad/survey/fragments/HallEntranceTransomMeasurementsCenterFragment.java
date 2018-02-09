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
import com.mad.survey.models.HallEntranceData;
import com.mad.survey.utils.ConversionUtils;
import com.mad.survey.utils.Utils;

import org.w3c.dom.Text;

public class HallEntranceTransomMeasurementsCenterFragment extends BaseFragment implements View.OnClickListener , OnFragmentResumedListener{

    private TextView txtCarNumber;
    private TextView txtFloorIdentifier;
    private EditText edtA, edtB, edtC, edtD, edtE, edtF, edtG, edtH, edtI;

    public static HallEntranceTransomMeasurementsCenterFragment newInstance(){
        HallEntranceTransomMeasurementsCenterFragment fragment = new HallEntranceTransomMeasurementsCenterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_hall_entrance_transom_measurements_center, container, false);

        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtCarNumber = (TextView) parent.findViewById(R.id.txtCarNumber);
        txtFloorIdentifier = (TextView) parent.findViewById(R.id.txtFloorIdentifier);
        edtA = (EditText) parent.findViewById(R.id.edtA);
        edtB = (EditText) parent.findViewById(R.id.edtB);
        edtC = (EditText) parent.findViewById(R.id.edtC);
        edtD = (EditText) parent.findViewById(R.id.edtD);
        edtE = (EditText) parent.findViewById(R.id.edtE);
        edtF = (EditText) parent.findViewById(R.id.edtF);
        edtG = (EditText) parent.findViewById(R.id.edtG);
        edtH = (EditText) parent.findViewById(R.id.edtH);
        edtI = (EditText) parent.findViewById(R.id.edtI);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnNext).setOnClickListener(this);
        parent.findViewById(R.id.btnHelp).setOnClickListener(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                edtA.requestFocus();
                Utils.showKeyboard(getActivity(), true, edtA);
            }
        });

        setHeaderScrollConfiguration(parent, getString(R.string.hall_entrance), getString(R.string.sub_title_transom_measurements_center), true, true);
    }

    private void updateUIs(){
        edtA.setText("");
        edtB.setText("");
        edtC.setText("");
        edtD.setText("");
        edtE.setText("");
        edtF.setText("");
        edtG.setText("");
        edtH.setText("");
        edtI.setText("");

        BankData bankData = MADSurveyApp.getInstance().getBankData();
        if (MADSurveyApp.getInstance().isEditMode()){
            txtSubTitle.setText(getString(R.string.bank_description_n_edit_title, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_edit_title, MADSurveyApp.getInstance().getFloorDescriptor()));
        }else{
            txtSubTitle.setText(getString(R.string.bank_description_n_title, MADSurveyApp.getInstance().getBankNum() + 1, MADSurveyApp.getInstance().getBankData().getName()));
            txtFloorIdentifier.setText(getString(R.string.floor_n_identifier_title, MADSurveyApp.getInstance().getFloorNum() + 1, MADSurveyApp.getInstance().getProjectData().getNumFloors(), MADSurveyApp.getInstance().getFloorDescriptor()));
            txtCarNumber.setText(getString(R.string.car_n_title, MADSurveyApp.getInstance().getHallEntranceCarNum()+1, MADSurveyApp.getInstance().getBankData().getNumOfCar()));
        }
        HallEntranceData hallEntranceData = MADSurveyApp.getInstance().getHallEntranceData();

        double A = hallEntranceData.getTransomMeasurementsA();
        double B = hallEntranceData.getTransomMeasurementsB();
        double C = hallEntranceData.getTransomMeasurementsC();
        double D = hallEntranceData.getTransomMeasurementsD();
        double E = hallEntranceData.getTransomMeasurementsE();
        double F = hallEntranceData.getTransomMeasurementsF();
        double G = hallEntranceData.getTransomMeasurementsG();
        double H = hallEntranceData.getTransomMeasurementsH();
        double I = hallEntranceData.getTransomMeasurementsI();

        if(A>=0)
            edtA.setText(A+"");
        if(B>=0)
            edtB.setText(B+"");
        if(C>=0)
            edtC.setText(C+"");
        if(D>=0)
            edtD.setText(D+"");
        if(E>=0)
            edtE.setText(E+"");
        if(F>=0)
            edtF.setText(F+"");
        if(G>=0)
            edtG.setText(G+"");
        if(H>=0)
            edtH.setText(H+"");
        if(I>=0)
            edtI.setText(I+"");
    }

    private void goToNext(){
        if (!isLastFocused()) return;

        double A = ConversionUtils.getDoubleFromEditText(edtA);
        double B = ConversionUtils.getDoubleFromEditText(edtB);
        double C = ConversionUtils.getDoubleFromEditText(edtC);
        double D = ConversionUtils.getDoubleFromEditText(edtD);
        double E = ConversionUtils.getDoubleFromEditText(edtE);
        double F = ConversionUtils.getDoubleFromEditText(edtF);
        double G = ConversionUtils.getDoubleFromEditText(edtG);
        double H = ConversionUtils.getDoubleFromEditText(edtH);
        double I = ConversionUtils.getDoubleFromEditText(edtI);

        if (A < 0) {
            edtA.requestFocus();
            showToast(getString(R.string.valid_msg_input_A), Toast.LENGTH_SHORT);
            return;
        }
        if (B < 0) {
            edtB.requestFocus();
            showToast(getString(R.string.valid_msg_input_B), Toast.LENGTH_SHORT);
            return;
        }
        if (C < 0) {
            edtC.requestFocus();
            showToast(getString(R.string.valid_msg_input_C), Toast.LENGTH_SHORT);
            return;
        }
        if (D < 0) {
            edtD.requestFocus();
            showToast(getString(R.string.valid_msg_input_D), Toast.LENGTH_SHORT);
            return;
        }
        if (E < 0) {
            edtE.requestFocus();
            showToast(getString(R.string.valid_msg_input_E), Toast.LENGTH_SHORT);
            return;
        }
        if (F < 0) {
            edtF.requestFocus();
            showToast(getString(R.string.valid_msg_input_F), Toast.LENGTH_SHORT);
            return;
        }
        if (G < 0) {
            edtG.requestFocus();
            showToast(getString(R.string.valid_msg_input_G), Toast.LENGTH_SHORT);
            return;
        }
        if (H < 0) {
            edtH.requestFocus();
            showToast(getString(R.string.valid_msg_input_H), Toast.LENGTH_SHORT);
            return;
        }
        if (I < 0) {
            edtI.requestFocus();
            showToast(getString(R.string.valid_msg_input_I), Toast.LENGTH_SHORT);
            return;
        }

        MADSurveyApp.getInstance().getHallEntranceData().setTransomMeasurementsA(A);
        MADSurveyApp.getInstance().getHallEntranceData().setTransomMeasurementsB(B);
        MADSurveyApp.getInstance().getHallEntranceData().setTransomMeasurementsC(C);
        MADSurveyApp.getInstance().getHallEntranceData().setTransomMeasurementsD(D);
        MADSurveyApp.getInstance().getHallEntranceData().setTransomMeasurementsE(E);
        MADSurveyApp.getInstance().getHallEntranceData().setTransomMeasurementsF(F);
        MADSurveyApp.getInstance().getHallEntranceData().setTransomMeasurementsG(G);
        MADSurveyApp.getInstance().getHallEntranceData().setTransomMeasurementsH(H);
        MADSurveyApp.getInstance().getHallEntranceData().setTransomMeasurementsI(I);

        hallEntranceDataHandler.update(MADSurveyApp.getInstance().getHallEntranceData());
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_NOTES, "hall_entrance_notes");
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
                showHelpDialog(getActivity(), getString(R.string.help_title_hall_entrance), R.drawable.img_help_54_hall_entrance_front_transom_center_measurements_help, GlobalConstant.HELP_PHOTO_SIZE_RATE);
                break;
        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
