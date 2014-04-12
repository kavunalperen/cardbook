package com.cardbook.android.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;




import com.cardbook.android.CardbookApp;
import com.cardbook.android.R;
import com.cardbook.android.activities.AppMainTabActivity;
import com.cardbook.android.adapters.AlisVerisListAdapter;
import com.cardbook.android.adapters.AlisverisListener;
import com.cardbook.android.adapters.PagerAdapter;
import com.cardbook.android.common.AppConstants;
import com.cardbook.android.adapters.FragmentPageListener;
import com.cardbook.android.common.Font;
import com.cardbook.android.common.Log;
import com.cardbook.android.connectivity.ConnectionManager;
import com.cardbook.android.connectivity.RequestCallBack;
import com.cardbook.android.models.Shopping;

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

    @Override
    public void openShoppingDetail(String shoppingId) {

       getDetail(shoppingId);

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

    private void getDetail(String detailId){

        JSONObject param=new JSONObject();
        try{
            param.putOpt("shoppingId",detailId);
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
