package com.mad.survey.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.adapters.EditBankListAdapter;
import com.mad.survey.adapters.EditLobbyListAdapter;
import com.mad.survey.dialogs.ItemDeleteConfirmDialog;
import com.mad.survey.dialogs.ItemDeleteDialog;
import com.mad.survey.dialogs.MadCommonAlertDialog;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.BankData;
import com.mad.survey.models.CarData;
import com.mad.survey.models.InteriorCarData;
import com.mad.survey.models.LobbyData;
import com.mad.survey.models.ProjectData;
import com.mad.survey.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class EditBankListFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener{

    private TextView txtSubTitle;
    private ListView listView;
    private EditBankListAdapter lstAdapter;
    private LayoutInflater inflater;
    private List<BankData> dataList = new ArrayList<>();
    private static int flag = 0;
    private BankData selectedItem = null;

    public static EditBankListFragment newInstance(int _flag){
        EditBankListFragment fragment = new EditBankListFragment();
        flag = _flag;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_edit_list, container, false);

        this.inflater = inflater;
        initView(parent);
        updateUIs();

        return parent;
    }

    private void initView(View parent){
        setHeaderTitle(parent, MADSurveyApp.getInstance().getProjectData().getName());

        txtSubTitle = (TextView) parent.findViewById(R.id.txtSubTitle);
        listView = (ListView) parent.findViewById(R.id.listView);
        lstAdapter = new EditBankListAdapter(getActivity(), inflater, this);
        listView.setAdapter(lstAdapter);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnAdd).setOnClickListener(this);

        if (flag > 0) openInstructionDlg();
    }

    private MadCommonAlertDialog dlg;
    private void openInstructionDlg(){
        dlg = new MadCommonAlertDialog(getActivity(), getString(R.string.instruction), "", "", getString(R.string.instruction_select_bank_for_edit), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btnOK) {
                    dlg.dismiss();
                }
            }
        });
        dlg.show();
    }

    private void updateUIs(){
        txtSubTitle.setText(getString(R.string.edit_elevator_banks));
        loadData();
    }

    private void goToNext(int bankNum){
        MADSurveyApp.getInstance().setBankNum(bankNum);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_BANK_NAME, "bank_name");

    }
    private BankData setBankData(int bankNum){
        MADSurveyApp.getInstance().setBankNum(bankNum);
        BankData bankData = bankDataHandler.get(MADSurveyApp.getInstance().getProjectData().getId(),bankNum);
        MADSurveyApp.getInstance().setBankData(bankData);
        return bankData;
    }
    private void gotoNewHallEntrance(int bankNum){
        setBankData(bankNum);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_HALL_ENTRANCE_FLOOR_IDENTIFIER, "hall_entrance_floor_description");
    }
    private void gotoNewHallStationORLantern(int bankNum,boolean flag){
        setBankData(bankNum);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_BANK_FLOOR_DESCRIPTION, "floor_description", flag);
    }
    private void gotoNewCar(int bankNum){
        setBankData(bankNum);
        /*
        int cntPerBank = carDataHandler.getAll(MADSurveyApp.getInstance().getProjectData().getId(),bankNum).size();
        int numbOfCar = bankData.getNumOfCar();
        if(cntPerBank >= numbOfCar){
            bankData.setNumOfCar(cntPerBank + 1);
            MADSurveyApp.getInstance().setBankData(bankData);
            bankDataHandler.update(bankData);
        }
        */
        int carNum = carDataHandler.getMaxCarNum(MADSurveyApp.getInstance().getProjectData().getId(),bankNum);
        MADSurveyApp.getInstance().setCarNum(carNum + 1);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_CAR_DESCRIPTION, "car_description");
    }
    private void gotoNewInteriorCar(int bankNum){
        setBankData(bankNum);
        /*
        int cntPerBank = interiorCarDataHandler.getAll(MADSurveyApp.getInstance().getProjectData().getId(), bankNum).size();
        int numbOfInteriorCar = bankData.getNumOfInteriorCar();
        if(cntPerBank >= numbOfInteriorCar){
            bankData.setNumOfInteriorCar(cntPerBank + 1);
            MADSurveyApp.getInstance().setBankData(bankData);
            bankDataHandler.update(bankData);
        }
        */
        int interiorCarNum = interiorCarDataHandler.getMaxInteriorCarNum(MADSurveyApp.getInstance().getProjectData().getId(),bankNum);
        MADSurveyApp.getInstance().setInteriorCarNum(interiorCarNum + 1);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_INTERIOR_CAR_DESCRIPTION, "interior_car_description");
    }
    ItemDeleteDialog deleteDlg;
    ItemDeleteConfirmDialog deleteConfirmDlg;
    int nPosition;

    private void doOpenDeleteDialog(){
        deleteDlg = new ItemDeleteDialog(getActivity(), getString(R.string.delete_bank, nPosition + 1),selectedItem.getName(),"", this);
        deleteDlg.show();
    }
    private void doOpenDeleteConfirmDialog(){
        deleteConfirmDlg = new ItemDeleteConfirmDialog(getActivity(), getString(R.string.delete_bank, nPosition + 1),selectedItem.getName(),"", this);
        deleteConfirmDlg.show();
    }
    private void loadData(){
        dataList.clear();
        dataList.addAll(bankDataHandler.getAll("projectId = " + MADSurveyApp.getInstance().getProjectData().getId(), null, null, null, null));
        lstAdapter.setData(dataList);
    }
    private void deleteItem(BankData data){
        deleteDlg.dismiss();
        //HallStation
        hallStationDataHandler.delete("projectId = "+MADSurveyApp.getInstance().getProjectData().getId()+" AND bankId = "+data.getBankNum(),null);
        //lantern
        lanternDataHandler.delete("projectId = "+MADSurveyApp.getInstance().getProjectData().getId()+" AND bankId = "+data.getBankNum(),null);
        //car
        carDataHandler.delete("projectId = "+MADSurveyApp.getInstance().getProjectData().getId()+" AND bankId = "+data.getBankNum(),null);
        //interior
        interiorCarDataHandler.delete("projectId = "+MADSurveyApp.getInstance().getProjectData().getId()+" AND bankId = "+data.getBankNum(),null);
        //hallEntrance
        hallEntranceDataHandler.delete("projectId = "+MADSurveyApp.getInstance().getProjectData().getId()+" AND bankId = "+data.getBankNum(),null);

        bankDataHandler.delete(data);
        loadData();
        doOpenDeleteConfirmDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.layout:
                BankData data = (BankData)v.getTag();
                if(flag == 1 || flag == 2){
                    gotoNewHallStationORLantern(data.getBankNum(), flag == 1);
                }
                else if(flag == 3){
                    gotoNewCar(data.getBankNum());
                }
                else if(flag == 4){
                    gotoNewInteriorCar(data.getBankNum());
                }
                else if(flag == 5){
                    gotoNewHallEntrance(data.getBankNum());
                }
                else{
                    goToNext(data.getBankNum());
                }
                break;

            case R.id.btnAdd:
                int cntBank = bankDataHandler.getMaxBankNum(MADSurveyApp.getInstance().getProjectData().getId());
                goToNext(cntBank + 1);
                break;
            case R.id.btnDelete:
                selectedItem = (BankData)((View)v.getParent()).getTag();
                nPosition = Integer.parseInt(v.getTag().toString());
                doOpenDeleteDialog();
                break;
            case R.id.btnYes:
                deleteItem(selectedItem);
                break;
            case R.id.btnNo:
                deleteDlg.dismiss();
                break;
            case R.id.btnOK:
                deleteConfirmDlg.dismiss();
                break;

        }
    }

    @Override
    public void onFragmentResumed() {
        updateUIs();
    }
}
