package com.mad.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mad.survey.MADSurveyApp;
import com.mad.survey.R;
import com.mad.survey.activities.BaseActivity;
import com.mad.survey.adapters.EditLobbyListAdapter;
import com.mad.survey.adapters.HallStationsListAdapter;
import com.mad.survey.dialogs.ItemDeleteConfirmDialog;
import com.mad.survey.dialogs.ItemDeleteDialog;
import com.mad.survey.listeners.OnFragmentResumedListener;
import com.mad.survey.models.HallStationData;
import com.mad.survey.models.LobbyData;
import com.mad.survey.models.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class EditLobbyListFragment extends BaseFragment implements View.OnClickListener,OnFragmentResumedListener{

    private TextView txtSubTitle;
    private ListView lstLobby;
    private EditLobbyListAdapter lstAdapter;
    private LayoutInflater inflater;
    private List<LobbyData> dataList = new ArrayList<>();
    private LobbyData selectedItem;

    public static EditLobbyListFragment newInstance(){
        EditLobbyListFragment fragment = new EditLobbyListFragment();
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
        lstLobby = (ListView) parent.findViewById(R.id.listView);
        lstAdapter = new EditLobbyListAdapter(getActivity(), inflater, this);
        lstLobby.setAdapter(lstAdapter);

        parent.findViewById(R.id.btnBack).setOnClickListener(this);
        parent.findViewById(R.id.btnAdd).setOnClickListener(this);
    }

    private void updateUIs(){
        txtSubTitle.setText(getString(R.string.edit_lobby_panels));
        loadData();
    }

    private void goToNext(int lobbyNum){
        MADSurveyApp.getInstance().setLobbyNum(lobbyNum);
        ((BaseActivity) getActivity()).replaceFragment(BaseActivity.FRAGMENT_ID_LOBBY_LOCATION, "lobby_location");
    }

    ItemDeleteDialog deleteDlg;
    ItemDeleteConfirmDialog deleteConfirmDlg;
    int nPosition;

    private void doOpenDeleteDialog(){
        deleteDlg = new ItemDeleteDialog(getActivity(), getString(R.string.delete_lobby, nPosition + 1),selectedItem.getLocation(),"", this);
        deleteDlg.show();
    }
    private void doOpenDeleteConfirmDialog(){
        deleteConfirmDlg = new ItemDeleteConfirmDialog(getActivity(),getString(R.string.delete_lobby, nPosition + 1),selectedItem.getLocation(),"",this);
        deleteConfirmDlg.show();
    }
    private void loadData(){
        dataList.clear();
        dataList.addAll(lobbyDataHandler.getAll("projectId = " + MADSurveyApp.getInstance().getProjectData().getId(), null, null, null, null));
        lstAdapter.setData(dataList);
    }
    private void deleteItem(LobbyData data){
        deleteDlg.dismiss();
        lobbyDataHandler.delete(data);
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
                LobbyData data = (LobbyData)v.getTag();
                goToNext(data.getLobbyNum());
                break;
            case R.id.btnAdd:
                int maxLobbyNum = lobbyDataHandler.getMaxLobbyNum(MADSurveyApp.getInstance().getProjectData().getId());
                goToNext(maxLobbyNum + 1);
                break;
            case R.id.btnDelete:
                selectedItem = (LobbyData)((View)(v.getParent())).getTag();
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
