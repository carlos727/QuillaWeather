package com.uninorte.quillaweather;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ProgressDialog pDialog;
    private static String url_current = "http://api.openweathermap.org/data/2.5/weather?q=Barranquilla,co&units=metric";
    private static String url_next = "http://api.openweathermap.org/data/2.5/forecast/daily?id=3689147";
    JSONArray weathers = null;
    ArrayList<DataEntry> listWeathers;
    private ListView listView;
    private TextView TEMP, TEMP_MIN, TEMP_MAX;
    private String temp, temp_min, temp_max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TEMP = (TextView)findViewById(R.id.temp);
        TEMP_MIN = (TextView)findViewById(R.id.temp_min);
        TEMP_MAX = (TextView)findViewById(R.id.temp_max);

        listWeathers = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isNetworkAvaible = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isNetworkAvaible = true;
            Toast.makeText(this, "Network is available ", Toast.LENGTH_LONG) .show();
        } else {
            Toast.makeText(this, "Network isn't available ", Toast.LENGTH_LONG) .show();
        }
        return isNetworkAvaible;
    }

    public void requestData(View view) {

        new GetData().execute();


    }

    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            isNetworkAvailable();
            temp = "";
            temp_min = "";
            temp_max = "";
            listWeathers.clear();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStrCurrent = sh.makeServiceCall(url_current, ServiceHandler.GET);
            String jsonStrNext = sh.makeServiceCall(url_next, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStrCurrent);
            Log.d("Response: ", "> " + jsonStrNext);

            if (jsonStrCurrent != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStrCurrent).getJSONObject("main");
                    temp = jsonObj.getString("temp") + "°C";
                    temp_min = "Min: " + jsonObj.getString("temp_min") + "°C";
                    temp_max = "Max: " + jsonObj.getString("temp_max") + "°C";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            if (jsonStrNext != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStrNext);
                    weathers = jsonObj.getJSONArray("list");

                    Log.d("Response length: ", "> " + weathers.length());

                    DataEntry dataentry = new DataEntry();
                    dataentry.setDay("Day");
                    dataentry.setMin("Min");
                    dataentry.setMax("Max");
                    dataentry.setNight("Night");
                    listWeathers.add(dataentry);

                    for (int i = 0; i < weathers.length(); i++) {
                        JSONObject j = weathers.getJSONObject(i).getJSONObject("temp");

                        DataEntry dataEntry = new DataEntry();
                        dataEntry.setDay(j.getString("day") + "°K");
                        dataEntry.setMin(j.getString("min") + "°K");
                        dataEntry.setMax(j.getString("max") + "°K");
                        dataEntry.setNight(j.getString("night") + "°K");

                        listWeathers.add(dataEntry);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            TEMP.setText(temp);
            TEMP_MIN.setText(temp_min);
            TEMP_MAX.setText(temp_max);

            CustomAdapter adapter = new CustomAdapter(MainActivity.this, listWeathers);
            listView.setAdapter(adapter);
            /**
             * Updating parsed JSON data into ListView

            CustomAdapter adapter = new CustomAdapter(MainActivity.this, listaUsuarios);
            listView.setAdapter(adapter);*/
        }

    }

}
