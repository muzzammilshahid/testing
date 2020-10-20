package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import pk.codebase.requests.HttpError;
import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class Edit extends AppCompatActivity {
    EditText tv,tv1,tv2,tv3,tv4;
    Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        tv = (EditText) findViewById(R.id.tv);
        tv1 = (EditText) findViewById(R.id.tv1);
        tv2 = (EditText) findViewById(R.id.tv2);
        tv3 = (EditText) findViewById(R.id.tv3);
        tv4 = (EditText) findViewById(R.id.tv4);
        tv.setText(getIntent().getStringExtra("firstname"));
        tv1.setText(getIntent().getStringExtra("lastname"));
        tv2.setText(getIntent().getStringExtra("email"));
        tv3.setText(getIntent().getStringExtra("number"));
        tv4.setText(getIntent().getStringExtra("username"));
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String first = tv.getEditableText().toString();
                String last = tv1.getEditableText().toString();
                //String pass = password.getEditText().getText().toString();
                //String cpass = confirmpassword.getEditText().getText().toString();
                String emaill = tv2.getEditableText().toString();
                String uname = tv4.getEditableText().toString();
                String number = tv3.getEditableText().toString();
                HttpRequest request = new HttpRequest();
                request.setOnResponseListener(new HttpRequest.OnResponseListener() {
                    @Override
                    public void onResponse(HttpResponse response) {
                        if (response.code == HttpResponse.HTTP_OK) {
                            System.out.println(response.toJSONObject());
                            String first = tv.getText().toString();
                            String last = tv1.getText().toString();
                            String uname = tv4.getText().toString();
                            String mobile = tv3.getText().toString();
                            String email = tv2.getText().toString();
                            Intent intent = new Intent(Edit.this,AfterLogin.class);
                            intent.putExtra("firstname",first);
                            intent.putExtra("lastname",last);
                            intent.putExtra("username",uname);
                            intent.putExtra("mobile",mobile);
                            intent.putExtra("email",email);
                            startActivity(intent);

                        }
                    }
                });
                request.setOnErrorListener(new HttpRequest.OnErrorListener() {
                    @Override
                    public void onError(HttpError error) {
                        // There was an error, deal with it
                    }
                });

                JSONObject json;
                try {
                    json = new JSONObject();
                    json.put("firstname", first);
                    json.put("lastname", last);
                    //json.put("password",pass);
                    json.put("email",emaill);
                    json.put("username",uname);
                    json.put("mobile",number);
                } catch (JSONException ignore) {
                    return;
                }
                request.put("http://codebase.pk:7000/api/users/", json);
            }
        });
    }
}
