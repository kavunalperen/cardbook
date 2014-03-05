package com.abdullah.cardbook.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.abdullah.cardbook.R;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by abdullah on 11/15/13.
 */
public class FirstActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        ImageButton login=(ImageButton)findViewById(R.id.btnLogin);
        login.setVisibility(View.INVISIBLE);


    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {



                        String  userInformation= AppConstants.getUserInformation(FirstActivity.this);

//                        boolean isTutorialShowed=AppConstants.isTutorialShowed(FirstActivity.this);
//                        if(!isTutorialShowed){
//                                AppConstants.setTutorialShow(FirstActivity.this);
//                            Intent intent=new Intent(FirstActivity.this, TutorialActivity.class);
//                            startActivity(intent);
//                        }
//                        else
                        if(userInformation==null){
                            Intent intent=new Intent(FirstActivity.this, MainActivity.class);
                            startActivity(intent);


                        }
                        else{
                            Intent intent=new Intent(FirstActivity.this, Barcode.class);
                            intent.putExtra("Navigation","MainActivity");
                            startActivity(intent);
                        }
                    }
                });
            }
        }).start();

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
