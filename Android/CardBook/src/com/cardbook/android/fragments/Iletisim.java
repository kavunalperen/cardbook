package com.cardbook.android.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cardbook.android.CardbookApp;
import com.cardbook.android.R;
import com.cardbook.android.adapters.FragmentPageListener;
import com.cardbook.android.common.AppConstants;
import com.cardbook.android.common.Font;
import com.cardbook.android.common.Log;
import com.cardbook.android.connectivity.ConnectionManager;
import com.cardbook.android.connectivity.RequestCallBack;
import com.cardbook.android.models.CardBookUser;
import com.cardbook.android.models.Company;
import com.cardbook.android.models.CompanyInfo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Iletisim extends BaseFragment implements View.OnClickListener, RequestCallBack{

    private TextView tvCompanyAddress, tvCompanyMail, tvCompanyPhone, tvCompanyName;
    private EditText etSubject, etText;
    private Button btnSend;
    private ProgressDialog dialog;
    private Company company;
    private CompanyInfo info;

    public Iletisim(){

    }

    public Iletisim(FragmentPageListener listener) {
        pageListener = listener;
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        info=(CompanyInfo)getArguments().getSerializable(CompanyInfo.COMPANY_INFO);


        View view = inflater.inflate(R.layout.iletisim, container, false);

//        LinearLayout layout = (LinearLayout) view.findViewById(R.id.il);
//        layout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);

        Typeface font=Font.getFont(this.getActivity(), Font.ROBOTO_REGULAR);

//        tvCompanyName=(TextView)view.findViewById(R.id.tvCompanyName);
        tvCompanyAddress=(TextView) view.findViewById(R.id.tvCompanyAddress);
        tvCompanyMail=(TextView) view.findViewById(R.id.tvCompanyMail);
        tvCompanyMail.setOnClickListener(this);
        tvCompanyPhone=(TextView) view.findViewById(R.id.tvCompanyPhone);
        tvCompanyPhone.setOnClickListener(this);

        etSubject=(EditText)view.findViewById(R.id.etMailSubject);

        etText=(EditText)view.findViewById(R.id.etMailText);

        btnSend=(Button)view.findViewById(R.id.btnMailSend);
        btnSend.setOnClickListener(this);
        setNavBarItemsStyle(view);



        company=((Company)getArguments().getSerializable(Company.COMPANY));

        TextView navBarText=(TextView)view.findViewById(R.id.navBarTxt);
        navBarText.setText(company.getCompanyName()+" - Ýletiþim");

        dialog = new ProgressDialog(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

//        tvCompanyName.setText(company.getCompanyName());

        if(info==null){
           LinearLayout ly=(LinearLayout)getView().findViewById(R.id.companyInfoLayout);
            ly.setVisibility(View.GONE);

            ImageView sUp=(ImageView)getView().findViewById(R.id.seperatorUpInfo);
            ImageView sDown=(ImageView)getView().findViewById(R.id.seperatorInfoDown);
            sUp.setVisibility(View.GONE);
            sDown.setVisibility(View.GONE);
            return;
        }

        if(info.getPhone()!=null)
            tvCompanyPhone.setText(info.getPhone());
        else
            tvCompanyPhone.setText("");

        if(info.getMail()!=null)
            tvCompanyMail.setText(info.getMail());
        else
            tvCompanyMail.setText("");

        if(info.getMail()!=null)
        tvCompanyAddress.setText(info.getAddressLine()+"\n"+info.getCountyName()+" "+info.getCityName());
        else
            tvCompanyAddress.setText("");
    }


    private void sendMessage(Company company){


        /*
        companyId=1 (Hangi firmaya mesaj gÃ¶nderileceÄŸi bilgisi)
ContactEmail=test@contact.com (Mesaj gÃ¶nderen kullanÄ±cÄ±nÄ±n e-posta adresi)
ContactFullname=Test ContactName (Mesaj gÃ¶nderen kullanÄ±cÄ±nÄ±n ad soyad bilgisi)
Subject=Test Konu (MesajÄ±n konusu)
Message=test mesajÄ± (Mesaj)
*/
        CardbookApp app=CardbookApp.getInstance();
        CardBookUser user=app.getUser();

        Map<String, String> list=new HashMap<String, String>();
        list.put("ContactEmail", user.getEmail());
        list.put("ContactFullname",user.getName());
        list.put("companyId", String.valueOf(company.getCompanyId()));
        list.put("Subject", etSubject.getText().toString());
        list.put("Message", etText.getText().toString());
        ConnectionManager.postData(getActivity(), this, AppConstants.SM_SEND_MESSAGE_TO_COMPANY, list);

    }

    @Override
    public void onRequestStart() {

        super.onRequestStart();
        dialog.setMessage("Mesaj gönderiliyor...");
        dialog.show();
    }

    @Override
    public void onRequestComplete(JSONObject result) {
        super.onRequestComplete(result);
        Log.i("onResponseComplete: "+result);
        dialog.dismiss();
        Toast.makeText(getActivity(),"Mesajýnýz gönderildi.", Toast.LENGTH_LONG).show();
        etText.setText("");
        etSubject.setText("");

    }

    @Override
    public void onRequestError() {
        super.onRequestError();
        Toast.makeText(getActivity(),"Mesajýnýz gönderilemedi; lütfen tekrar deneyin.", Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }

    @Override
    public void backPressed() {
        pageListener.onSwitchBeforeFragment(AppConstants.KARTLARIM);
    }

    @Override
    public void onClick(View view) {
        if(view==btnSend)
            sendMessage(company);
        else if(view==tvCompanyMail){
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{info.getMail()});

            try {
                startActivity(Intent.createChooser(i, "Mesaj Gönder..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getActivity(), "Cihazýnýzda mail gönderme uygulamasý bulunmamaktadýr.", Toast.LENGTH_SHORT).show();
            }
        }
        else if(view==tvCompanyPhone){
            String number = "tel:" + info.getPhone().trim();
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
            startActivity(callIntent);
        }
    }
}
