package com.dangerous.roran.keno;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Random;

public class PlayBoard extends AppCompatActivity {

    private int pick;
    private int[] picks;
    private boolean played = false;
    private int matches =0;
    private int[] ids = {R.id.num1,R.id.num2,R.id.num3,R.id.num4,R.id.num5,R.id.num6,R.id.num7,R.id.num8,R.id.num9,R.id.num10,
            R.id.num11,R.id.num12,R.id.num13,R.id.num14,R.id.num15,R.id.num16,R.id.num17,R.id.num18,R.id.num19,R.id.num20,
            R.id.num21,R.id.num22,R.id.num23,R.id.num24,R.id.num25,R.id.num26,R.id.num27,R.id.num28,R.id.num29,R.id.num30,
            R.id.num31,R.id.num32,R.id.num33,R.id.num34,R.id.num35,R.id.num36,R.id.num37,R.id.num38,R.id.num39,R.id.num40,
            R.id.num41,R.id.num42,R.id.num43,R.id.num44,R.id.num45,R.id.num46,R.id.num47,R.id.num48,R.id.num49,R.id.num50,
            R.id.num51,R.id.num52,R.id.num53,R.id.num54,R.id.num55,R.id.num56,R.id.num57,R.id.num58,R.id.num59,R.id.num60,
            R.id.num61,R.id.num62,R.id.num63,R.id.num64,R.id.num65,R.id.num66,R.id.num67,R.id.num68,R.id.num69,R.id.num70,
            R.id.num71,R.id.num72,R.id.num73,R.id.num74,R.id.num75,R.id.num76,R.id.num77,R.id.num78,R.id.num79,R.id.num80};
    private JSONObject j = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_board);

        pick = getIntent().getExtras().getInt("pick");
        try {
            switch (pick) {
                case 1:
                    j.put("1", "2");
                    break;
                case 2:
                    j.put("2","10");
                    break;
                case 3:
                    j.put("3","25");
                    j.put("2","2");
                    break;
                case 4:
                    j.put("4","50");
                    j.put("3","5");
                    j.put("2","1");
                    break;
                case 5:
                    j.put("5","500");
                    j.put("4","15");
                    j.put("3","2");
                    break;
                case 6:
                    j.put("6","1500");
                    j.put("5","50");
                    j.put("4","5");
                    j.put("3","1");
                    break;
                case 7:
                    j.put("7","5000");
                    j.put("6","150");
                    j.put("5","15");
                    j.put("4","2");
                    j.put("3","1");
                    break;
                case 8:
                    j.put("8","15,000");
                    j.put("7","400");
                    j.put("6","50");
                    j.put("5","10");
                    j.put("4","2");
                    break;
                case 9:
                    j.put("9","25000");
                    j.put("8","2500");
                    j.put("7","200");
                    j.put("6","25");
                    j.put("5","4");
                    j.put("4","1");
                    break;
                case 10:
                    j.put("10","200000");
                    j.put("9","10000");
                    j.put("8","500");
                    j.put("7","50");
                    j.put("6","10");
                    j.put("5","3");
                    j.put("0","3");
                    break;
                default:
                    break;
            }
        }catch(JSONException je){
            System.out.println(je.toString());
        }
        picks = new int[80];
        for(int i = 0;i<ids.length;i++){
            TextView tv = (TextView)findViewById(ids[i]);
            tv.setTag(i);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = Integer.parseInt(v.getTag().toString());
                    if(picks[pos]==0) {
                        if (pick > 0) {
                            v.setBackgroundColor(Color.CYAN);
                            pick--;
                            picks[pos] = 1;
                        } else {
                            (Toast.makeText(PlayBoard.this, "You are out of picks", Toast.LENGTH_LONG)).show();
                        }
                    }
                    else{
                        v.setBackgroundColor(0x00000000);
                        pick++;
                        picks[pos] = 0;
                    }
                }
            });
        }

        final Button playB = (Button)findViewById(R.id.playbtn);
        final Random rand = new Random();
        playB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!played) {
                    if (pick == 0) {
                        for (int i = 0; i < 20; i++) {
                            int tempPosition;
                            do {
                                tempPosition = rand.nextInt(80);
                            } while (picks[tempPosition] == 2);

                            TextView tempTV = (TextView) findViewById(ids[tempPosition]);
                            if (picks[tempPosition] == 0) {
                                tempTV.setBackgroundColor(Color.RED);
                            } else {
                                tempTV.setBackgroundColor(Color.GREEN);
                                matches++;
                            }
                            picks[tempPosition] = 2;
                        }
                        playB.setText("Play again");
                        TextView result = (TextView)findViewById(R.id.resulttv);
                        try {
                            result.setText("Matches: " + matches + "   Prize: " + (j.has((matches + "")) ? j.get((matches + "")) : 0));
                        }catch(JSONException je){
                            System.out.println(je.toString());}
                        played = true;
                    } else
                        (Toast.makeText(PlayBoard.this, "You didn't use all the picks", Toast.LENGTH_LONG)).show();

                } else finish();
            }
        });
    }
}
