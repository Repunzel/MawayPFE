package com.strive.maway.maway;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Winsido on 12/04/2018.
 */



    public class GetDirectionsData extends AsyncTask<Object,String,String> {

        GoogleMap mMap;
        private String url;
        String googleDirectionsData;
        protected String duration;
        protected String distance;
         LatLng latLng;

        @Override
        protected String doInBackground(Object... objects) {

            mMap = (GoogleMap) objects[0];
            url = (String) objects[1];
            latLng  = (LatLng) objects[2]; //contains end_lat and end_lng
            DownloadURL downloadURL = new DownloadURL();

            try {
                googleDirectionsData = downloadURL.readUrl(url);
            }
            catch (IOException e) {
                e.printStackTrace();
            }


            return googleDirectionsData;
        }
        //s represents jsonData
        @Override
        protected void onPostExecute(String s) {

            //we need hashmap :
            HashMap<String,String> directionsList = null;
            DataParser parser = new DataParser();
            directionsList = parser.parseDirections(s);
            duration = directionsList.get("duration");
            String[] distanceParts = directionsList.get("distance").split(" ");
            distance = distanceParts[0];

            //i need somewhat to return this in getNearbyPlacesData
        }
        public HashMap<String,String> getDurationAndDistance(){

            HashMap<String,String> durationAndDistance = new HashMap<>();;
            durationAndDistance.put("duration",duration);
            durationAndDistance.put("distance",distance);

            return durationAndDistance;

        }


    }


