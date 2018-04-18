package com.example.hannybuns.memorygame6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class LevelActivity extends AppCompatActivity {

    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;

    String level="level";
    final int EASY = 6;
    final int MEDYUM = 8;
    final int HARD = 10;

    private TextView textView;
    private  Bundle bundle;
    private ImageView imageView;
    private int d,m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        easyButton =(Button) findViewById(R.id.easyButton);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyGame();
            }
        });

        mediumButton =(Button) findViewById(R.id.mediumButton);
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediumGame();
            }
        });

        hardButton =(Button) findViewById(R.id.hardButton);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hardGame();
            }
        });

        textView = (TextView)findViewById(R.id.happyBirthdayView);
        imageView = (ImageView) findViewById(R.id.happyBirthdayImageView);
        bundle = getIntent().getExtras();
        setHappyBirthdayText();
    }
    public void easyGame(){
        Intent easyGame = new Intent(this, GameActivity.class);
        easyGame.putExtra(level, EASY);
        startActivity(easyGame);
    }
    public void mediumGame(){
        Intent mediumGame = new Intent(this, GameActivity.class);
        mediumGame.putExtra(level, MEDYUM);
        startActivity(mediumGame);
    }
    public void hardGame(){
        Intent hardGame = new Intent(this, GameActivity.class);
        hardGame.putExtra(level, HARD);
        startActivity(hardGame);
    }


    private void setHappyBirthdayText() {
        if(Birthday()){
            imageView.setVisibility(ImageView.VISIBLE);
            textView.setText(bundle.get("name") + " Happy Birthday!!");
        }
        else
            textView.setText("Hello "+ bundle.get("name"));
    }
    private boolean Birthday() {
        Calendar calendar = Calendar.getInstance();
        d = calendar.get(Calendar.DAY_OF_MONTH);
        m = calendar.get(Calendar.MONTH);
        if((int)bundle.get("day")!= d )
            return false;
        else if ((int)bundle.get("month") != m )
            return false;
        return true;
    }
}
