package com.appturbo.appturbotest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.appturbo.appturbotest.model.Application;
import java.util.List;

/**
 * This activity implements a PageViewer to allow navigation between
 * the detail views of each application through swipes
 */


public class DetailPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private static int numPages = 0;
    private static int currentApp = 0;
    private List<Application> apps;
    public static final String EXTRA_APPLICATIONS = "extra_application";
    public static final String EXTRA_APPLICATION_ID = "extra_application_id";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_pager);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Extract list of applications and position of the item selected in the list
        Bundle bundle = getIntent().getExtras();
        apps = bundle.getParcelableArrayList(EXTRA_APPLICATIONS);
        numPages = apps.size();
        currentApp = bundle.getInt(EXTRA_APPLICATION_ID);

        //Initialises the page viewer and its adapter
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(new DetailPagerAdapter(getSupportFragmentManager()));

        //Loops through the application list and displays the application that was selected
        for(int i = 0; i < numPages; i++){
            if(i == currentApp){
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }

    //Back navigation in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    //Pager adapter for the detail fragments
    private class DetailPagerAdapter extends FragmentStatePagerAdapter{

        public DetailPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new DetailFragment().newInstance(apps.get(position));
        }

        @Override
        public int getCount() {
            return numPages;
        }

    }
}
