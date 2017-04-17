package avipatil.cricketstatus;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView listView;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.list);
       // Parse parse=new Parse();

        //listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        listView.setOnItemClickListener(this);
        new Parse().execute("http://cricapi.com/api/matches/5xuC4b7sGvRmCZSI62cLlJdT3Sm2");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Parse parse=new Parse();
        switch (item.getItemId())
        {
            case R.id.upcoming:
                a=1;
                parse.execute("http://cricapi.com/api/matches/5xuC4b7sGvRmCZSI62cLlJdT3Sm2");
                //
                return true;
            case R.id.oldmatch:
                a=2;
                parse.execute("http://cricapi.com/api/cricket/5xuC4b7sGvRmCZSI62cLlJdT3Sm2");
                return true;
            case R.id.timetable:
                a=3;
                parse.execute("http://cricapi.com/api/matchCalendar/5xuC4b7sGvRmCZSI62cLlJdT3Sm2");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    class Parse extends AsyncTask<String,Void,String>
    {



        @Override
        protected String doInBackground(String... strings) {
            URL url=null;
            try {
                url=new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
//                HttpsURLConnection urlConnection= (HttpsURLConnection) url.openConnection();
//                InputStream inputStream=urlConnection.getInputStream();
//                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
//                String s=bufferedReader.readLine();
//                Log.d("response++++++",s);
//                Log.i("response++++++",s);
//                bufferedReader.close();

                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                //urlConnection.setDoOutput(true);
                // geting input from connection
                InputStream inputStream=urlConnection.getInputStream();
                //Reading The Response

                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String s=bufferedReader.readLine();
                Log.d("ss",s);
                bufferedReader.close();
                //Return the Response Message to on post Excute Method
                return  s;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject;
            try {
                jsonObject=new JSONObject(s);
                if(a==2) {
                    ArrayList<Cricket_Pojo> cricket_list = new ArrayList<Cricket_Pojo>();

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    // JSONArray jsonArray1=jsonObject.getJSONArray("provider");
                    // JSONObject date= jsonArray1.getJSONObject(2);
                    Cricket_Pojo c = new Cricket_Pojo();
                    // c.setDate(date.getString("pubDate"));
                    // cricket_list.add(c);
                    JSONObject provider = (JSONObject) jsonObject.get("provider");
                    c.setDate(provider.getString("pubDate"));
                    cricket_list.add(c);

                    // provider.get("pubDate");
                    Log.d("jsonArray", jsonArray.toString());
                    Log.i("JsonArray", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Cricket_Pojo cricket_pojo = new Cricket_Pojo();
                        cricket_pojo.setUnique_id(object.getString("unique_id"));
                        cricket_pojo.setDescription(object.getString("title"));
                        cricket_list.add(cricket_pojo);
                        // String id=object.getString("unique_id");
                        //String title=object.getString("title");
                    }
                    Cricket_Adapter cricket_adapter = new Cricket_Adapter(MainActivity.this, R.layout.list_row_item, cricket_list);
                    listView.setAdapter(cricket_adapter);
                }
                else  if(a==1)
                {
                    ArrayList<Cricket_Pojo> cricket_list = new ArrayList<Cricket_Pojo>();

                    JSONArray jsonArray = jsonObject.getJSONArray("matches");

                    // JSONArray jsonArray1=jsonObject.getJSONArray("provider");
                    // JSONObject date= jsonArray1.getJSONObject(2);
                   // Cricket_Pojo c = new Cricket_Pojo();
                    // c.setDate(date.getString("pubDate"));
                    // cricket_list.add(c);
                    //JSONObject provider = (JSONObject) jsonObject.get("provider");
                    //c.setDate(provider.getString("pubDate"));
                    //cricket_list.add(c);

                    // provider.get("pubDate");
                    Log.d("jsonArray", jsonArray.toString());
                    Log.i("JsonArray", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Cricket_Pojo cricket_pojo = new Cricket_Pojo();
                        cricket_pojo.setUnique_id(object.getString("unique_id"));
                       // cricket_pojo.setDescription(object.getString("title"));
                        cricket_pojo.setDescription(object.getString("team-1")+" "+"VS"+" "+object.getString("team-2"));
                        //cricket_pojo.setTeam2Name(object.getString("team-2"));
                        //cricket_pojo.setMatchStarted(object.getBoolean("matchStarted"));
                        //cricket_pojo.setSquad(object.getBoolean("squad"));
                        cricket_pojo.setDate(object.getString("date"));
                        cricket_list.add(cricket_pojo);
                        // String id=object.getString("unique_id");
                        //String title=object.getString("title");
                    }
                    Cricket_Adapter cricket_adapter = new Cricket_Adapter(MainActivity.this, R.layout.list_row_item, cricket_list);
                    listView.setAdapter(cricket_adapter);


                }
                else
                {

                    ArrayList<Cricket_Pojo> cricket_list = new ArrayList<Cricket_Pojo>();

                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    // JSONArray jsonArray1=jsonObject.getJSONArray("provider");
                    // JSONObject date= jsonArray1.getJSONObject(2);


                    // provider.get("pubDate");
                    Log.d("jsonArray", jsonArray.toString());
                    Log.i("JsonArray", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Cricket_Pojo cricket_pojo = new Cricket_Pojo();
                        cricket_pojo.setUnique_id(object.getString("unique_id"));
                        cricket_pojo.setDescription(object.getString("name"));
                        cricket_pojo.setDate(object.getString("date"));
                        cricket_list.add(cricket_pojo);
                        // String id=object.getString("unique_id");
                        //String title=object.getString("title");
                    }
                    Cricket_Adapter cricket_adapter = new Cricket_Adapter(MainActivity.this, R.layout.list_row_item, cricket_list);
                    listView.setAdapter(cricket_adapter);




                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
