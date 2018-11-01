package ca.mcgill.ecse321.passengerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import ca.mcgill.ecse321.passengerapp.util.HttpUtils;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    private TextView errorTxt;
    private EditText usernameTbx;
    private EditText passwordTbx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        errorTxt = (TextView)findViewById(R.id.errorTxt);
        usernameTbx = (EditText) findViewById(R.id.usernameTbx);
        passwordTbx = (EditText) findViewById(R.id.passwordTbx);
    }

    //Make sure input is ok before trying to login and try to login
    public void loginBtnClick(View view){
        String pass = passwordTbx.getText().toString();
        String un = usernameTbx.getText().toString();

        if(pass == ""){
            errorTxt.setText("Unable to login: Please input a password");
        }
        else if (un == ""){
            errorTxt.setText("Unable to login: Please input a username");
        }
        else if(!userExists(un)){
            errorTxt.setText("Unable to login: Username does not exist");
        }
        else {
            login(un, pass);
        }
    }



    //Make sure the user does not exist and try to log in
    public void signUpBtnClick(View view){
        String pass = passwordTbx.getText().toString();
        String un = usernameTbx.getText().toString();

        if(pass == ""){
            errorTxt.setText("Please input a password");
        }
        else if (un == ""){
            errorTxt.setText("Please input a username");
        }
        else {
            if(saveUser(un, pass)){
                login(un, pass);
            }
        }
    }

    public boolean userExists(String username){
        if(username.compareTo("admin") ==  0){
            return true;
        }
        return false;
    }

    public boolean saveUser(String username, String password){

        boolean result = false;
        try {
            result = signupUser(username, password);
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_exception_thrown) + e.getMessage(), Snackbar.LENGTH_LONG).show();
        }

        return result;
    }

    public boolean signupUser(String username, String password) throws JSONException, UnsupportedEncodingException {
        // Check if the network is available
        if (!HttpUtils.isNetworkAvailable(this)) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_no_internet), Snackbar.LENGTH_LONG).show();

            return false;
        }

        /**
         * Example request:
         * {
         * 	   "name": "Alex",
         *     "username": "Bshizzl",
         *     "password": "123123"
         * }
         */

        JSONObject jsonParams = new JSONObject();
        jsonParams.put("name", "test");
        jsonParams.put("username", username);
        jsonParams.put("password", password);

        System.out.println(jsonParams.toString());

        HttpUtils.post(this, getString(R.string.signup_url), jsonParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                errorTxt.setText(response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("ERROR STATUS: " + statusCode);
                if (errorResponse != null) {
                    errorTxt.setText(errorResponse.toString());
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Service is down!", Snackbar.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                errorTxt.setText(responseString);
            }
        });

        return true;
    }



    public boolean login(String username, String password) {

        if (!getAccessToken(username, password)) {
            return false;
        }

        if(username.compareTo("admin") == 0 && password.compareTo("password") == 0){
            //Changes view to main view
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);

            //Prevents user from pressing back to return to sign in page
            finish();
            return true;
        }
        return false;
    }


    public boolean getAccessToken(String username, String password) {



    }

}

