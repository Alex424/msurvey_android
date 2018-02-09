package com.mad.survey.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.media.session.MediaController;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.mad.survey.R;
import com.mad.survey.preferences.AppPreference;

public class HelpPhotoDialog extends Dialog implements View.OnClickListener{
	protected Context mContext;

    private int displayWidth;

    private TextView txtHelpTitle;
    private ImageView imgHelp;
    private RelativeLayout pinchLayout;

    private String helpTitle;
    private double imgRate = 1.0;
    private int imgResource;

    private View.OnClickListener mClickListener;

	public HelpPhotoDialog(final Context context, int _displayWidth, String _helpTitle, int _imgResource, double _imgRate) {
		super(context, R.style.MADHelpDialogStyle);
		this.mContext = context;
        this.helpTitle = _helpTitle;
        this.imgResource = _imgResource;
        this.imgRate = _imgRate;
        this.displayWidth = _displayWidth;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dlg_help_photo);

        txtHelpTitle = (TextView) findViewById(R.id.txtHelpTitle);
        imgHelp = (ImageView) findViewById(R.id.imgHelp);
        pinchLayout = (RelativeLayout) findViewById(R.id.layoutPinchHelp);

        if(AppPreference.getBooleanPrefValue(getContext(), AppPreference.PREF_KEY_PINCH_FIRST_TIME))
            pinchLayout.setVisibility(View.GONE);


        RelativeLayout layoutBody = (RelativeLayout) findViewById(R.id.layoutBody);
        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) layoutBody.getLayoutParams();
        p.height = (int)((displayWidth - mContext.getResources().getDimensionPixelSize(R.dimen.dp_10) * 2) * imgRate);
        layoutBody.setLayoutParams(p);

        /*imgHelp.post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imgHelp.getLayoutParams();
                params.height = (int) (imgHelp.getWidth() * imgRate);
                imgHelp.setLayoutParams(params);


                RelativeLayout.LayoutParams params_pinch = (RelativeLayout.LayoutParams) pinchLayout.getLayoutParams();
                params_pinch.height = params.height;
                pinchLayout.setLayoutParams(params_pinch);
            }
        });*/

        imgHelp.setImageResource(imgResource);
        txtHelpTitle.setText(helpTitle);


        findViewById(R.id.btnClose).setOnClickListener(this);
        findViewById(R.id.layoutRoot).setOnClickListener(this);
        findViewById(R.id.layoutPinchHelp).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnClose:case R.id.layoutRoot:
                dismiss();
                break;
            case R.id.layoutPinchHelp:
                AppPreference.setSharedPrefValue(getContext(), AppPreference.PREF_KEY_PINCH_FIRST_TIME, true);
                pinchLayout.setVisibility(View.GONE);
                break;
        }
    }
}
