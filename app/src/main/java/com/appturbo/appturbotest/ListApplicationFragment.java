package com.appturbo.appturbotest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RunnableFuture;
import java.util.zip.Inflater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appturbo.appturbotest.model.Application;

import org.json.JSONArray;

public class ListApplicationFragment extends ListFragment {
	private final List<Application> list = new ArrayList<Application>();
    public static final String EXTRA_APPLICATIONS = "extra_application";
    public static final String EXTRA_APPLICATION_ID = "extra_application_id";

    /*
     * TODO: Load the view of the Fragment, this need to be a listview with the android standard list id.
     */

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo= cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnected()) {
            new loadData().execute("http://mobile-team.appturbo.net/exerciceAndroid.json");
        } else {
            Toast.makeText(getActivity(), "Cannot display. \nYou are not connected to the network", Toast.LENGTH_LONG).show();
        }

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), DetailPagerActivity.class);
                i.putParcelableArrayListExtra(DetailPagerActivity.EXTRA_APPLICATIONS, (ArrayList<Application>)list);
                i.putExtra(DetailPagerActivity.EXTRA_APPLICATION_ID, position);
                startActivity(i);
            }
        });
	}


    //AsyncTask that loads data from network and store it in list of Application objects
    class loadData extends AsyncTask<String, Integer, List<Application>>{

            @Override
            protected List<Application> doInBackground(String... urlString){
                InputStream is = null;
                String input = "";

                //Connect to network and read data into a string
                try{
                    URL url = new URL(urlString[0]);
                    URLConnection con = url.openConnection();
                    HttpURLConnection httpCon = (HttpURLConnection)con;
                    httpCon.setAllowUserInteraction(false);
                    httpCon.setInstanceFollowRedirects(true);
                    httpCon.setRequestMethod("GET");
                    httpCon.connect();

                    is = httpCon.getInputStream();
                    input = convertStreamToString(is);

                    //Parses the string into application objects
                    JSONArray array = new JSONArray(input);
                    for(int i = 0; i < array.length(); i++){
                        list.add(new Application(array.getJSONObject(i)));
                    }
                    return list;
                } catch (Exception e){
                    e.printStackTrace();
                }
                return list;
            }

            //Once the data is fetched, the UI on the main thread is updated
            @Override
            protected void onPostExecute(List<Application> result){
                super.onPostExecute(result);

                getActivity().runOnUiThread(new Runnable() {
                    public void run(){
                        AppListAdapter adapter = new AppListAdapter(list);
                        setListAdapter(adapter);
                    }
                });
            }

            //Method that converts the InputStream into a string
            public String convertStreamToString(InputStream stream) throws IOException{
                if(stream != null){
                    StringBuilder sb = new StringBuilder();
                    String line;

                    try{
                        BufferedReader bf = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                        while ((line = bf.readLine()) != null){
                            sb.append(line).append("\n");
                        }
                    } finally {
                        stream.close();
                    }
                    return sb.toString();
                } else {
                    return "";
                }
            }
        }


    //Function that launches the DetailPagerActivity when a item on the list is selected with the appropriate info
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id){
        Intent i = new Intent(getActivity(), DetailPagerActivity.class);
        i.putParcelableArrayListExtra(DetailPagerActivity.EXTRA_APPLICATIONS, (ArrayList<Application>)list);
        i.putExtra(DetailPagerActivity.EXTRA_APPLICATION_ID, pos);
        startActivity(i);
    }

    //List adapter that implements the application list behavior
    private class AppListAdapter extends ArrayAdapter<Application>{
        public AppListAdapter(List<Application> apps){
            super(getActivity(), 0, apps);
        }

        //Inflate and populate the view
        @Override
        public View getView(int pos, View v, ViewGroup parent) {
            if (v == null){
                v = getActivity().getLayoutInflater().inflate(R.layout.list_application_item, null);
            }

            ImageView icon = (ImageView) v.findViewById(R.id.logo);
            new DownloadImage(icon).execute(getItem(pos).getLogo());

            TextView name = (TextView)v.findViewById(R.id.app_name);
            name.setText(getItem(pos).getName());

            TextView desc = (TextView)v.findViewById(R.id.app_desc);
            desc.setText(getItem(pos).getDescription());

            return v;
        }

    }


}
