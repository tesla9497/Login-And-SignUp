package com.example.logintest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class signup extends AppCompatActivity {

    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private EditText etusername, etPassword;
    private Button btnSignup;
    private String URL = "http://192.168.1.4/oculus-v2/api/user/signup.php";
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        //setting up the action bar
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);

        //signup setting
        etusername = (EditText) findViewById(R.id.etusername);
        etPassword = (EditText) findViewById(R.id.etpassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        username = password = "";

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });
    }

    public void save(View view) {
        //get data from intent
        String firstName, lastName, emailId, mobileNo;
        Intent intent=getIntent();
        firstName = intent.getStringExtra("first_name");
        lastName = intent.getStringExtra("last_name");
        emailId = intent.getStringExtra("email");
        mobileNo = intent.getStringExtra("mobile_number");

        username = etusername.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (!username.equals("") && !password.equals("")) {
            JSONObject object = new JSONObject();
            try {
                object.put("username", username);
                object.put("password", password);
                object.put("firstname", firstName);
                object.put("lastname", lastName);
                object.put("email", emailId);
                object.put("mobilenumber", mobileNo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, URL, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("status")) {
                                    Toast.makeText(signup.this, "Successfully registered.", Toast.LENGTH_SHORT).show();
                                    btnSignup.setClickable(false);
                                    Intent intent = new Intent(signup.this, home.class);
                                    startActivity(intent);
                                    finish();
                                } else if (response.equals("failure")) {
                                    Toast.makeText(signup.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
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
            requestQueue.add(stringRequest);
        }
    }

}