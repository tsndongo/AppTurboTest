package com.appturbo.appturbotest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appturbo.appturbotest.model.Application;

import java.io.InputStream;
import java.net.URL;

/**
 * This class implements the fragment that is displayed a click on
 * the list and is populated by the application that was selected
 */
public class DetailFragment extends Fragment {

    public static final String EXTRA_APPLICATION = "extra_application";
    private Application application;

    /* Constructor that creates an instance of DetailFragment
    loaded with the application object that was selected */
    public static DetailFragment newInstance(Application app){
        DetailFragment frag = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_APPLICATION, app);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Selected application is retrieved from the bundle
        application = getArguments().getParcelable(EXTRA_APPLICATION);
    }

    //Population of fragment view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_detail, parent, false);

        ImageView logo = (ImageView)v.findViewById(R.id.logo);
        new DownloadImage(logo).execute(application.getLogo());

        TextView name = (TextView)v.findViewById(R.id.name);
        name.setText(application.getName());

        TextView description = (TextView)v.findViewById(R.id.description);
        description.setText(application.getDescription());

        ImageView screenshot = (ImageView)v.findViewById(R.id.screenshot);
        new DownloadImage(screenshot).execute(application.getScreenshot());

        return v;
    }

}
