package com.example.iaq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.iaq.Models.Status;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txt1 = findViewById(R.id.textview1);
        TextView txt2 = findViewById(R.id.textview2);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

       /* apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Status> call = apiInterface.getInfo();
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                txt1.setText(status.version);
                txt2.setText(status.authServerUrl);
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                txt1.setText("error");
                txt2.setText("error");
            }
        });*/
    }
}