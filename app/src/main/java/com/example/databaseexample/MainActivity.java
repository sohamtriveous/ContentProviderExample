package com.example.databaseexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.databaseexample.model.musicMatchResponse.Collection1;
import com.example.databaseexample.model.MusicmatchResponse;
import com.example.databaseexample.network.Music;
import com.example.databaseexample.ui.MusicAdapter;
import com.example.databaseexample.utils.ValidationUtils;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {
    private ListView listView;

    private MusicAdapter musicAdapter;

    public List<Collection1> collection1List;

    private EditText email;
    private EditText password;
    private Button submit;
    private ProgressBar progressBar;
    private boolean loginFailure = false;

    private View loginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.activity_main_listview);
        email = (EditText) findViewById(R.id.activity_main_email);
        password = (EditText) findViewById(R.id.activity_main_password);
        submit = (Button) findViewById(R.id.activity_main_submit);
        progressBar = (ProgressBar) findViewById(R.id.activity_main_progress);
        loginLayout = findViewById(R.id.activity_main_loginlayout);

        listView.setDivider(null);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideLoginLayout();
                showProgress();
                if (ValidationUtils.isValidEmail(email.getText().toString())) {
                    if (ValidationUtils.isPasswordValid(password.getText().toString())) {
                        if (Music.checkLogin(email.getText().toString(), password.getText().toString())) {
                            getMusicFromWeb();
                        } else {
                            setLoginFailure(true);
                            hideProgress();
                            showLoginLayout();
                            Toast.makeText(MainActivity.this, "Could not login", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        setLoginFailure(true);
                        hideProgress();
                        showLoginLayout();
                        Toast.makeText(MainActivity.this, "Password is not valid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    setLoginFailure(true);
                    hideProgress();
                    showLoginLayout();
                    Toast.makeText(MainActivity.this, "Email is not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isLoginFailure() {
        return loginFailure;
    }

    public void setLoginFailure(boolean loginFailure) {
        this.loginFailure = loginFailure;
    }

    private void getMusicFromWeb() {
        Music.getLatestMusic(new Callback<MusicmatchResponse>() {
            @Override
            public void success(MusicmatchResponse musicmatchResponse, Response response) {
                hideProgress();
                showList();
                collection1List = musicmatchResponse.getResults().getCollection1();
                musicAdapter = new MusicAdapter(MainActivity.this, collection1List);
                listView.setAdapter(musicAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                setLoginFailure(true);
                hideList();
                hideProgress();
                showLoginLayout();
                Log.e("MusicMatch", "error is " + error.getMessage());
            }
        });
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private void showLoginLayout() {
        loginLayout.setVisibility(View.VISIBLE);
    }

    private void hideLoginLayout() {
        loginLayout.setVisibility(View.GONE);
    }

    private void showList() {
        listView.setVisibility(View.VISIBLE);
    }

    private void hideList() {
        listView.setVisibility(View.GONE);
    }
}
