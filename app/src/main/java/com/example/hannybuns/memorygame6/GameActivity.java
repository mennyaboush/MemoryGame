package com.example.hannybuns.memorygame6;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {
    int DURATION_GAME = 30 * 1000;
    Random rand = new Random();
    Integer[] ALL_IMG = {R.drawable.im1, R.drawable.im2, R.drawable.im3, R.drawable.im4,
            R.drawable.im5, R.drawable.im6, R.drawable.im7, R.drawable.im8,
            R.drawable.im9, R.drawable.im10};

    ArrayList<Card> cards = new ArrayList<Card>();
    GridView gvcards = null;
    CardListAdapterWithCache adaptercards;

    ArrayList<Integer> img_in_game = new ArrayList<>();
    int level, firstCard = -1, secondCard = -1, numOfCouples = 0;

    TextView textClock;
    long MillisecondTime, StartTime, TimeBuff = DURATION_GAME, UpdateTime = 0L;
    int Seconds, Minutes, MilliSeconds;
    Handler handler = new Handler();;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textClock = (TextView) findViewById(R.id.textClock);
        gvcards = (GridView) findViewById(R.id.grid_cards);
        StartTime = SystemClock.uptimeMillis();
        level = getIntent().getIntExtra("level", 2);
        handler.postDelayed(runnable, 0);

        resetGame(level);
    }

    public void resetGame(int level) {
        for (int i = 0; i < level * 2; i++)
            img_in_game.add(ALL_IMG[i / 2]);
        for (int i = 0; i < level * 2; i++) {
            int temp = rand.nextInt(img_in_game.size());
            Card c = new Card(img_in_game.get(temp));
            cards.add(c);
            img_in_game.remove(temp);
        }

        adaptercards = new CardListAdapterWithCache(this, cards);
        gvcards.setAdapter(adaptercards);

        gvcards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                setCardImageView((ImageView) gvcards.getChildAt(position).findViewById(R.id.image), position);
                gameLogic(position);
            }
        });
    }
    public void setCardImageView(ImageView iv, int i) {
        cards.get(i).setCard_iv(iv);
    }
    public void gameLogic(int i) {
        if (!cards.get(i).isDone() && !gameOver()) {
            if (firstCard == -1) {
                firstCard = i;
                cards.get(firstCard).getCard_iv().setImageResource(cards.get(firstCard).getIv_front());
            } else if (i != firstCard) {
                if (secondCard == -1) {
                    secondCard = i;
                    cards.get(secondCard).getCard_iv().setImageResource(cards.get(secondCard).getIv_front());
                    if (cards.get(firstCard).getIv_front() == cards.get(secondCard).getIv_front()) {
                        numOfCouples += 2;
                        cards.get(firstCard).setDone(true);
                        cards.get(secondCard).setDone(true);
                    }
                } else {
                    if (cards.get(firstCard).getIv_front() != cards.get(secondCard).getIv_front()) {
                        cards.get(firstCard).getCard_iv().setImageResource(cards.get(firstCard).getIv_back());
                        cards.get(secondCard).getCard_iv().setImageResource(cards.get(firstCard).getIv_back());
                    }
                    firstCard = secondCard = -1;
                    gameLogic(i);
                }
            }
        }
    }

    private boolean gameOver() {
        if (numOfCouples == cards.size() || UpdateTime < 0)
            return true;
        return false;
    }

    public void endGame() {
        handler.removeCallbacks(runnable);
        Intent startNewGame = new Intent(this, LevelActivity.class);
        startActivity(startNewGame);
        finish();
    }

    public Runnable runnable = new Runnable() {
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff - MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            if (!gameOver()) {
                textClock.setText("" + Minutes + ":"
                        + String.format("%02d", Seconds) + ":"
                        + String.format("%03d", MilliSeconds));
            } else {
                if (UpdateTime < 0) {
                    textClock.setText("END TIME!");
                    textClock.setTextColor(Color.RED);
                } if (numOfCouples == cards.size()){
                    textClock.setText("YOU WON!");
                    textClock.setTextColor(Color.GREEN);
                }
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        endGame();
                    }
                }, 3000);
            }
            handler.postDelayed(this, 0);
        }
    };
}




