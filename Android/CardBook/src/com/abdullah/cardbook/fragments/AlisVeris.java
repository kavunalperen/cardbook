package com.abdullah.cardbook.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;




import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.activities.AppMainTabActivity;
import com.abdullah.cardbook.adapters.AlisVerisListAdapter;
import com.abdullah.cardbook.adapters.AlisverisListener;
import com.abdullah.cardbook.adapters.PagerAdapter;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.connectivity.RequestCallBack;
import com.abdullah.cardbook.models.Campaign;
import com.abdullah.cardbook.models.Shopping;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Stack;


public class AlisVeris extends BaseFragment implements OnItemClickListener, AlisverisListener, RequestCallBack {


    private ListView listView;
    private ProgressDialog dialog;
    AlisVerisListAdapter adapter;


    public AlisVeris(){

    }

    public AlisVeris(FragmentPageListener listener) {
        pageListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alis_veris, container, false);

//        LinearLayout layout = (LinearLayout) view.findViewById(R.id.alisVerisLayoutLinear);
//        layout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);

        Typeface font= Font.getFont(this.getActivity(), Font.ROBOTO_REGULAR);

        listView= (ListView) view.findViewById(R.id.lvAlisVeris);
        listView.setDivider(null);
        listView.setEnabled(true);
        listView.setClickable(true);
        listView.setFocusable(true);
        listView.setOnItemClickListener(this);

        Bundle bundle=getArguments();

        if(bundle!=null){
            Log.i("BUndle dolu");
            int companyId=bundle.getInt("companyId",0);
            JSONObject object=new JSONObject();
            try {
                object.put("companyId",companyId);
                object.put("userId",CardbookApp.getInstance().getUser().getId());
                object= ConnectionManager.addDefaultParameters(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Shopping>allList=CardbookApp.getInstance().getShoppings();
            ArrayList<Shopping>list=new ArrayList<Shopping>();

            if(allList!=null && allList.size()>0){
                for(Shopping cp:allList){
                    if(cp.getCompanyId()==companyId)
                        list.add(cp);

                }
                setList(list);
            }
            else
                Toast.makeText(getActivity(),"Alışverişiniz bulunmamaktadır.", Toast.LENGTH_SHORT).show();

        }else{

            if(CardbookApp.getInstance().getShoppings()!=null)
                setList(CardbookApp.getInstance().getShoppings());
//            else{
//                Button button=new Button(getActivity());
//                button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                button.setText("Güncelle");
//
//                layout.addView(button);
//            }
        }

            setNavBarItemsStyle(view);
        dialog = new ProgressDialog(getActivity());
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((AppMainTabActivity)this.getActivity()).alisverisListener=this;
    }

    public void setList(ArrayList<Shopping> item){
        adapter=new AlisVerisListAdapter(this.getActivity(),R.layout.alis_veris_list_template, item);
        listView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Toast.makeText(getActivity(),"onItemClicck : "+i,Toast.LENGTH_SHORT).show();

        Shopping shoping=adapter.getItem(i);
        getDetail(shoping);
    }

    @Override
    public void openShopping(int companyId) {
        Log.i("openShopping");
        Bundle data=new Bundle();
        data.putInt("companyId", companyId);
        AlisVeris detail=new AlisVeris();
        detail.setArguments(data);
        pageListener.onSwitchToNextFragment(AppConstants.ALIS_VERIS,detail, this);
    }

    private void getDetail(Shopping shopping){

        JSONObject param=new JSONObject();
        try{
        param.putOpt("shoppingId",shopping.getId());
        ConnectionManager.postData(getActivity(), this, AppConstants.SM_GET_SHOPPING_DETAIL_CONTENT,param);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestStart() {
        super.onRequestStart();
        dialog.setMessage("Alışveris detayları yükleniyor...");
        dialog.show();
    }

    @Override
    public void onRequestComplete(JSONObject result) {
        super.onRequestComplete(result);

        Shopping shopping=new Shopping(result.optJSONObject(AppConstants.POST_DATA));

        Bundle data=new Bundle();
        data.putSerializable("shopping", shopping);
        AlisverisDetail detail=new AlisverisDetail();
        detail.setArguments(data);
        pageListener.onSwitchToNextFragment(AppConstants.ALIS_VERIS,detail, this);
        dialog.dismiss();


    }

    @Override
    public void onRequestError() {
        super.onRequestError();
        dialog.dismiss();
        AppConstants.ErrorToast(getActivity());
    }

    @Override
    public void backPressed() {

        Stack stack= PagerAdapter.fragments.get(AppConstants.ALIS_VERIS);
        if(stack.size()>1)
            pageListener.onSwitchBeforeFragment(AppConstants.ALIS_VERIS);
        else
        getActivity().moveTaskToBack(true);
    }
}
