package com.example.logintest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class register extends AppCompatActivity {

    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private EditText etfirstname, etlastname, etemail, etmobileno;
    private Button btnNexttoSignup;
    private String URL = "http://192.168.1.4/oculus-v2/api/user/register.php";
    private String firstname, lastname, email, mobilenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //widget setting
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        //setting up the action bar
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);

        //register setup
        etfirstname = (EditText) findViewById(R.id.etfirstname);
        etlastname = (EditText) findViewById(R.id.etlastname);
        etemail = (EditText) findViewById(R.id.etemail);
        etmobileno = (EditText) findViewById(R.id.etmobile_no);
        btnNexttoSignup = (Button) findViewById(R.id.btnNexttoSignup);
        firstname = lastname = email = mobilenumber = "";

        btnNexttoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });
    }

    public void register(View view) {
        firstname = etfirstname.getText().toString().trim();
       lastname = etlastname.getText().toString().trim();
        email = etemail.getText().toString().trim();
        mobilenumber = etmobileno.getText().toString().trim();
        if (!firstname.equals("") && !lastname.equals("") && !email.equals("") && !mobilenumber.equals("")) {
            btnNexttoSignup.setClickable(false);
            Intent intent = new Intent(register.this, signup.class);
            intent.putExtra("first_name", firstname);
            intent.putExtra("last_name", lastname);
            intent.putExtra("email", email);
            intent.putExtra("mobile_number", mobilenumber);

            startActivity(intent);

            /*JSONObject object = new JSONObject();
            try {
                object.put("firstname", firstname);
                object.put("lastnamme", lastname);
                object.put("email", email);
                object.put("mobilenumber", mobilenumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, URL, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("status")) {
                                    Toast.makeText(register.this, "Successfully registered.", Toast.LENGTH_SHORT).show();

                                    finish();
                                } else if (response.equals("failure")) {
                                    Toast.makeText(register.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e(error.getMessage());
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);*/
        }
    }

}