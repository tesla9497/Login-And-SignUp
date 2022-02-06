package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    private TextView signuptxt;
    private EditText etusername, etpassword;
    private Button btnlogin;
    private String username, password;
    private final String URL = "http://192.168.1.4/oculus-v2/api/user/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etusername = findViewById(R.id.etusername);
        etpassword = findViewById(R.id.etpassword);
        btnlogin = findViewById(R.id.btnlogin);
        signuptxt = findViewById(R.id.signup_txt);
        username = password = "";

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerview(view);
            }
        });
    }

    public void login(View view) {
        username = etusername.getText().toString().trim();
        password = etpassword.getText().toString().trim();
        if (!username.equals("") && !password.equals("")) {
            JSONObject object = new JSONObject();
            try {
                object.put("username", username);
                object.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, URL, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("status")) {
                                    btnlogin.setClickable(false);
                                    Intent intent = new Intent(login.this, home.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    String message =  response.getString("message");
                                    Toast.makeText(login.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse.statusCode == 401) {
                        Toast.makeText(login.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerview(View view) {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
        finish();
    }
}