package com.kyberlabs.projetvbg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Denonce extends AppCompatActivity implements View.OnClickListener {

    public static final String REGISTER_URL = "http://smspace.esy.es/vbg/volleyRegisterDenonce.php";


    public static final String KEY_CATEGORIE = "categorie";
    public static final String KEY_QUARTIER = "quartier";
    public static final String KEY_VILLE = "ville";
    public static final String KEY_DEPARTEMENT = "departement";
    public static final String KEY_DESCRIPTION= "description";

    AutoCompleteTextView quartier, ville;
    Spinner spinner, spinner2;
    EditText description;

    Button denonce;

    ArrayList<String> Quartier= new ArrayList<String>();
    ArrayList<String> Ville= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denonce);

        quartier=(AutoCompleteTextView)findViewById(R.id.quartier);
        description=(EditText)findViewById(R.id.description);
        ville=(AutoCompleteTextView)findViewById(R.id.ville);

        denonce=(Button)findViewById(R.id.btn_denonce);
        denonce.setOnClickListener(this);


        Quartier.add("Sikècodji");
        Quartier.add("Zogbadjè");
        Quartier.add("Togoudo");
        Quartier.add("St Michel");
        Quartier.add("Ste Rita");
        Quartier.add("Agblangandan");

        Ville.add("Cotonou");
        Ville.add("Nikki");
        Ville.add("Abomey-Calavi");
        Ville.add("Parakou");
        Ville.add("Lokossa");
        Ville.add("Allada");

        ArrayAdapter<String> adp= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Quartier);
        quartier.setAdapter(adp);

        ArrayAdapter<String> adp2= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Ville);
        ville.setAdapter(adp2);

        spinner = (Spinner) findViewById(R.id.cat_denonce);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.cat_denonce, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner2 = (Spinner) findViewById(R.id.departement);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.departement, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
    }


    private void registerDenonce(){

        final String cat=this.spinner.getSelectedItem().toString().trim();
        final String depart=this.spinner2.getSelectedItem().toString().trim();
        final String quart=this.quartier.getText().toString().trim();
        final String vil=this.ville.getText().toString().trim();
        final String descript=this.description.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(Denonce.this);
                        dialogBuilder
                                .withTitle("Denonciation")
                                .withMessage(response)
                                .withTitleColor("#FFFFFF")
                                .withDividerColor("#000000")
                                .withMessageColor("#ffffff")
                                .withDialogColor("#E21B18")
                                .withDuration(100)
                                .withEffect(Effectstype.RotateBottom)
                                .withButton1Text("Fermer")
                                .isCancelableOnTouchOutside(true)
                                //.setCustomView(View or ResId,context)
                                .setButton1Click(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogBuilder.cancel();
                                    }
                                })
                                .show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Denonce.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_CATEGORIE,cat);
                params.put(KEY_QUARTIER,quart);
                params.put(KEY_VILLE, vil);
                params.put(KEY_DEPARTEMENT, depart);
                params.put(KEY_DESCRIPTION, descript);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_denonce){

            registerDenonce();

        }
    }

}
