package com.appturbo.appturbotest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * This class implements an AsyncTask to load the picture from the network onto to ImageViews
 */
public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imgView;

    public DownloadImage(ImageView iv){
        imgView = iv;
    }

    protected Bitmap doInBackground(String ... address){
        String url = address[0];

        Bitmap bitmap = null;

        try{
            InputStream is = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result){
        imgView.setImageBitmap(result);
    }
}
