package com.appturbo.appturbotest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class AppturboTestActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appturbo_test);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        /*
        * TODO: Set the correct Fragment according to the position
        * TODO: DONE!!
        * Position 1 -> ListApplicationFragment
        * Position 2 -> AboutFragment
        *
        * Use the SupportFragmentManager and replace the Fragment in the @id/container
        */

        Fragment fragment = new Fragment();
        FragmentManager fm = getSupportFragmentManager();

        //Create the correct fragment based on the list item selected in drawer
        switch (position){
            case 0:
                fragment = new ListApplicationFragment();
                break;
            case 1:
                fragment = new AboutFragment();
                break;
        }

        //Replaces the fragment with the newly created one
        fm.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
