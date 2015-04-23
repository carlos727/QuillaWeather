package com.uninorte.quillaweather;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;


public class MainActivity extends ActionBarActivity {

    private ProgressDialog pDialog;
    private static String url = "http://api.openweathermap.org/data/2.5/weather?q=Barranquilla,co&units=metric";
    JSONArray weathers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Toast.makeText(this, "Network not available ", Toast.LENGTH_LONG) .show();
        }
        return isNetworkAvaible;
    }

    /*private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    usuarios = jsonObj.getJSONArray("results");
                    Log.d("Response length: ", "> " + usuarios.length());

                    for (int i = 0; i < usuarios.length(); i++) {
                        JSONObject c = usuarios.getJSONObject(i).getJSONObject("user");

                        DataEntry dataEntry = new DataEntry();

                        dataEntry.setGender(c.getString("gender"));

                        JSONObject name = c.getJSONObject("name");

                        dataEntry.setFistName(name.getString("first"));
                        dataEntry.setLastName(name.getString("last"));

                        JSONObject imageObject = c.getJSONObject("picture");

                        dataEntry.setPicture(imageObject.getString("large"));

                        listaUsuarios.add(dataEntry);

                    }
                } catch (JSONException e) {
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
            /**
             * Updating parsed JSON data into ListView
             *
            CustomAdapter adapter = new CustomAdapter(MainActivity.this, listaUsuarios);
            listView.setAdapter(adapter);
        }

    }*/

}
