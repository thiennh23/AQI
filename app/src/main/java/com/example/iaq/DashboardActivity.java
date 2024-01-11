package com.example.iaq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        TextView txt = findViewById(R.id.test);
        txt.setText(APIClient.token);
    }
}