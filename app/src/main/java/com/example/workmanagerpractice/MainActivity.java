package com.example.workmanagerpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.workmanagerpractice.R.layout.*;

public class MainActivity extends AppCompatActivity {

    TextView statusMessage;

    Button btnWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusMessage=findViewById(R.id.message);


        final OneTimeWorkRequest oneTimeWorkRequest=new OneTimeWorkRequest.Builder(MyWorker.class).build();

        btnWork=findViewById(R.id.work);

        btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WorkManager.getInstance().enqueue(oneTimeWorkRequest);

            }
        });

        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {


                        String status=workInfo.getState().name();

                        btnWork.append(status + "\n");
                    }
                });
    }
}
