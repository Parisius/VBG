package com.kyberlabs.projetvbg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.kyberlabs.projetvbg.classes.AppController;
import com.kyberlabs.projetvbg.classes.ClassDenonce;
import com.kyberlabs.projetvbg.classes.Config;
import com.kyberlabs.projetvbg.classes.CustomListDenonce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView lv;

    List<ClassDenonce> news = new ArrayList<ClassDenonce>();
    private CustomListDenonce adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupview(Config.SECRET);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Main = new Intent(getApplicationContext(), Denonce.class);
                startActivity(Main);
            }
        });
    }

    public void setupview(final String code) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.tous_denonce, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    String categorie, date, departement,  quartier, ville, description;
                    int id;

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("info_denonce");

                    for (int i = 0; i < result.length(); i++) {
                        JSONObject data = result.getJSONObject(i);
                        id = data.getInt("id");
                        categorie = data.getString("categorie");
                        departement = data.getString("departement");
                        quartier = data.getString("quartier");
                        date = data.getString("date");
                        ville = data.getString("ville");
                        description = data.getString("description");

                        ClassDenonce lyr = new ClassDenonce(id, categorie, quartier, ville, departement, description, date);
                        news.add(lyr);

                    }


                    lv = (ListView) findViewById(R.id.listlyric);
                    adapter = new CustomListDenonce(MainActivity.this, news);
                    lv.setAdapter(adapter);

                    /*
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View
                                view, int position, long id) {
                            Lyric zik = news.get(position);
                            Intent intent = new Intent(Lyrics.this, DetailsLyrics.class);
                            intent.putExtra("id_lyric", zik.getId_lyric());
                            intent.putExtra("titre", zik.getTitre());
                            intent.putExtra("artiste", zik.getArtiste());
                            intent.putExtra("contenu_lyric", zik.getContenu_lyric());

                            startActivity(intent);

                        }
                    }); */

                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error data can't be retrieve!!", Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Passing user parameters to our server
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("code", code);
                return params;
            }

        };

        int socketTimeout = 60000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
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
        if (id == R.id.action_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "With STOP VBG, you can denounce violence based on " +
                    " gender.\nLien : ";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "STOP VBG");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;
        }

        if (id == R.id.action_quitter) {
            final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(MainActivity.this);
            dialogBuilder
                    .withTitle("Quit?")
                    .withMessage("Do you want to quit this app")
                    .withTitleColor("#FFFFFF")
                    .withDividerColor("#000000")
                    .withMessageColor("#ffffff")
                    .withDialogColor("#E21B18")
                    .withDuration(100)
                    .withEffect(Effectstype.Slideright)                                         //def Effectstype.Slidetop
                    .withButton1Text("Yes")                                      //def gone
                    .withButton2Text("No")
                    .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                    //.setCustomView(View or ResId,context)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // if this button is clicked, close
                            // current activity
                            Intent i=new Intent(MainActivity.this,MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            MainActivity.this.finish();
                        }
                    })
                    .setButton2Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.cancel();
                        }
                    })
                    .show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
