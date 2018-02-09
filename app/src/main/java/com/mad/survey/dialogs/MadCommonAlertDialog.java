package com.mad.survey.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mad.survey.R;

public class MadCommonAlertDialog extends Dialog {
	protected Context mContext;

    private String title, subTitle1, subTitle2, content;
    private TextView txtMainTitle, txtSubTitle1, txtSubTitle2, txtContent;
    private View.OnClickListener clickListener = null;

	public MadCommonAlertDialog(final Context context, String _title, String _subTitle1, String _subTitle2, String _content, View.OnClickListener _clickListener) {
		super(context, R.style.MADAlertDialogStyle);
		this.mContext = context;
        this.title = _title;
        this.subTitle1 = _subTitle1;
        this.subTitle2 = _subTitle2;
        this.content = _content;
        this.clickListener = _clickListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dlg_mad_common_alert);

        txtMainTitle = (TextView) findViewById(R.id.txtMainTitle);
        txtSubTitle1 = (TextView) findViewById(R.id.txtSubTitle1);
        txtSubTitle2 = (TextView) findViewById(R.id.txtSubTitle2);
        txtContent = (TextView) findViewById(R.id.txtContent);

        findViewById(R.id.btnClose).setOnClickListener(mClickListener);
        if (this.clickListener != null){
            findViewById(R.id.btnOK).setOnClickListener(clickListener);
        }else {
            findViewById(R.id.btnOK).setOnClickListener(mClickListener);
        }

        txtMainTitle.setText(title);
        txtSubTitle1.setText(subTitle1);
        txtSubTitle2.setText(subTitle2);
        txtContent.setText(content);

        if (subTitle1.equals("")){
            txtSubTitle1.setVisibility(View.GONE);
        }

        if (subTitle2.equals("")){
            txtSubTitle2.setVisibility(View.GONE);
        }

        if (content.equals("")){
            txtContent.setVisibility(View.GONE);
        }
	}

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };
}
