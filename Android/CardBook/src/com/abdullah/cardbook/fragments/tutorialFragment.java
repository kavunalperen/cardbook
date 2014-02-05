package com.abdullah.cardbook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.abdullah.cardbook.R;
import com.abdullah.cardbook.activities.FirstActivity;
import com.abdullah.cardbook.activities.TutorialActivity;

/**
 * Created by abdullah on 05.02.2014.
 */

public class TutorialFragment extends Fragment {

    private int img;
    public TutorialFragment(int img){
        this.img=img;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.tutorial, container, false);

        ImageView imgView=(ImageView)rootView.findViewById(R.id.imgTutorial);
        Button btnDone=(Button)rootView.findViewById(R.id.btnDone);
        if(img!=0){

            imgView.setImageResource(this.img);
            btnDone.setVisibility(View.GONE);
        }
        else{
            imgView.setVisibility(View.GONE);
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), FirstActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }

        return rootView;
    }
}