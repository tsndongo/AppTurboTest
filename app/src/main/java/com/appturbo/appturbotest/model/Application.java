package com.appturbo.appturbotest.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Application implements Parcelable {


    private String name;
    private String description;
    private String logo;
    private String screenshot;

    //Blank constructor
    public Application(){
        name = "";
        description = "";
        logo = "";
        screenshot = "";
    }

    //Constructor - takes in each parameter separately
    public Application(String n, String d, String l, String s){
        name = n;
        description = d;
        logo = l;
        screenshot = s;
    }

    //Constructor - takes in JSON object
    public Application(JSONObject json) throws JSONException{
        name = json.getString("name");
        description = json.getString("description");
        logo = json.getString("logo");
        screenshot = json.getString("screenshot");
    }

    //Constructor - takes in parcel
    public Application(Parcel in){
        this();
        readFromParcel(in);
    }

    //Constructor - takes in application object
    public Application(Application app){
        name = app.getName();
        description = app.getDescription();
        logo = app.getLogo();
        screenshot = app.getScreenshot();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    //Parcelable
    public static final Creator<Application> CREATOR = new Creator<Application>() {
        @Override
        public Application createFromParcel(Parcel source) {
                return new Application(source);
        }

        @Override
        public Application[] newArray(int size) {
            return new Application[size];
        }
    };

    private void readFromParcel(Parcel in){
        this.name = in.readString();
        this.description = in.readString();
        this.logo = in.readString();
        this.screenshot = in.readString();
    }

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(logo);
        parcel.writeString(screenshot);
    }


}
