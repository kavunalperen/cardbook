package com.abdullah.cardbook.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.BitmapLruCache;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


/**
 * Created by abdullah on 11/12/13.
 */
public class Barcode extends Activity {

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    BitmapLruCache cache;

    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.barcode);
        imgView=(ImageView)findViewById(R.id.imgBarcode);

        requestQueue= CardbookApp.getInstance().getRequestQuee();
        int cacheSize= AppConstants.getCacheSize(this);

        this.cache=new BitmapLruCache(cacheSize);


        Bitmap bitmap = BitmapFactory.decodeFile(getExternalCacheDir().toString()+"/barcode.PNG");
        if(bitmap!=null)
            imgView.setImageBitmap(bitmap);
        else
            saveBarcode();


    }


    public void saveBarcode(){

        Log.i("Saveboarcode is started");
        imageLoader=new ImageLoader(requestQueue, this.cache);

        imageLoader.get(CardbookApp.getInstance().getUser().getBarcodeUrl(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Log.i("saveBarcode Response is done ");


                if(response.getBitmap()!=null){
//                    Bitmap result=AppConstants.addMask(Profil.this.getActivity(), response.getBitmap(), R.drawable.listview_photomask);
//
                    imgView.setImageBitmap(response.getBitmap());

                    Bitmap bbicon;

                    bbicon= response.getBitmap();
                    String extStorageDirectory = getExternalCacheDir().toString();
                    OutputStream outStream = null;
                    File file = new File(extStorageDirectory, "barcode.PNG");
                    try {
                        outStream = new FileOutputStream(file);
                        bbicon.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                        outStream.flush();
                        outStream.close();

                        Log.i(" Barcode Saving OK");
                    }
                    catch(Exception e)
                    {}
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Image Response Error");
            }
        });



    }


}
