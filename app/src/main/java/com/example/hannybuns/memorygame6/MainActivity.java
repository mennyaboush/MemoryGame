package com.example.hannybuns.memorygame6;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button playButton ;
    private Button buttonCalander ;
    private TextView dateView;
    private TextView nameView;
    private TextView editName;

    //    Bundle bundle = new Bundle();
    private int d,m,y; /* for calander*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        playButtonOnClick();
        buttonCalanderOnClick();
    }


    private void findView() {
        playButton = (Button)findViewById(R.id.playButton);
        buttonCalander = (Button)findViewById(R.id.buttonCalander);
        dateView = (TextView)findViewById(R.id.viewDate);
        nameView = (TextView)findViewById(R.id.viewName);
        editName= (TextView)findViewById(R.id.editName);

    }

    private void buttonCalanderOnClick() {
        buttonCalander.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();

            @Override
            public void onClick(View view) {
                d = calendar.get(Calendar.DAY_OF_MONTH);
                m = calendar.get(Calendar.MONTH);
                y = calendar.get(Calendar.YEAR);

                DatePickerDialog pickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dateView.setText("Date:"+i2+"/"+i1+"/"+i);
                        y=i;m=i1;d=i2;
                    }
                }, y, m, d);
                Toast.makeText(MainActivity.this, d+"/"+m+"/"+y, Toast.LENGTH_LONG).show();
                pickerDialog.show();
            }
        });
    }
    private void playButtonOnClick() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , LevelActivity.class);
                intent.putExtra("day",d);
                intent.putExtra("month",m);
                intent.putExtra("year",y);
                intent.putExtra("name",editName.getText());
                Toast.makeText(MainActivity.this,nameView.getText(),Toast.LENGTH_LONG);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this, nameView.getText().toString(), Toast.LENGTH_LONG).show();
    }

}
