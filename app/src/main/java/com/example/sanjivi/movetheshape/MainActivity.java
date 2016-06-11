package com.example.sanjivi.movetheshape;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView txt;
    private Button btn;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    int flag=0;
    float w=0F,x=0F,y=100F,z=100F; //for square and rectangle
    float c1=50F,c2=50F,r=50F; //for circle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Forming the square
        Bitmap b = Bitmap.createBitmap(1000, 1500, Bitmap.Config.ARGB_8888); //allocates each alpha,red,green,blue 8 bits of storage and hence 32 bit graphics
        b.eraseColor(Color.WHITE);
        Canvas c = new Canvas(b);
        Paint p = new Paint();
        c.drawRect(w,x,y,z,p);
        p.setColor(Color.BLUE);
        ImageView imageview = (ImageView) findViewById(R.id.imageView);
        imageview.setImageBitmap(b);
        //Speech input
        txt = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       promptSpeechInput();
                                   }
                               }
        );

    }
    //Google speech input dialog
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Speech not supported", Toast.LENGTH_SHORT).show();


        }

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case REQ_CODE_SPEECH_INPUT:{if(resultCode==RESULT_OK && null!=data){
                ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                txt.setText(result.get(0));
                String string=result.get(0);
                Bitmap b = Bitmap.createBitmap(1000, 1500, Bitmap.Config.ARGB_8888);
                b.eraseColor(Color.WHITE);
                Canvas c = new Canvas(b);
                Paint p = new Paint();
                if(string.equalsIgnoreCase("square"))
                    flag=0;
                else if(string.equalsIgnoreCase("circle"))
                    flag = 1;
                else if(string.equalsIgnoreCase("rectangle")) {
                    flag = 0;
                    w=0F;
                    x=0F;
                    y=100F;
                    z=100F;
                    c.drawRect(w, x, y, z, p);
                }

                else if(string.equalsIgnoreCase("move right") ) {
                     if(flag==0 && y != 1000F){
                        w += 10F;
                        y += 10F;}
                    else if(flag==1 && c1+r!=1000F)
                     c1+=10F;
                    }
                else if (string.equalsIgnoreCase("move left") ) {
                    if(flag==0 && w != 0F){
                        w -= 10F;
                        y -= 10F;}
                    else if(flag==1 && c1-r!=0F)
                        c1-=10F;

                    }
                else if (string.equalsIgnoreCase("move up") ) {
                    if(flag==0 && x != 0F){
                        x -= 10F;
                        z -= 10F;}
                    else if(flag==1 && c2-r!=0F)
                        c2-=10F;
                    }
                else if (string.equalsIgnoreCase("move down")) {
                    if (flag == 0 && z != 1500F) {
                        x += 10F;
                        z += 10F;
                    } else if (flag == 1 && c2 + r != 1500F)
                        c2 += 10F;
                }
                else if (string.equalsIgnoreCase("bigger"))
                {    if(flag==0 && (y - w) != 1000F)  //checking if side of square is equal to width of bitmap
                    {

                            //going through each edge of the bitmap
                            if (w == 0) {
                                y += 10;
                                if (x == 0)
                                    z += 10;
                                else
                                    x -= 10;


                            } else if (x == 0) {
                                z += 10;
                                if (y == 1000)
                                    w -= 10;
                                else
                                    y += 10;

                            } else if (y == 1000) {
                                w -= 10;
                                if (z == 1500)
                                    x -= 10;
                                else
                                    z += 10;
                            } else if (z == 1500) {
                                x -= 10;
                                if (w == 0)
                                    y += 10;
                                else
                                    w -= 10;
                            } else {
                                y += 10;
                                z += 10;
                            }

                        }

                     else if(flag==1 && r!=500F) { //checking if diameter of circe equals width of bitmap
                    //going through the four edges of the screen
                         if (c1 - r == 0F) {
                             if (c2 - r == 0)
                                 c2 += 10F;
                             c1 += 10F;
                             r += 10F;
                         } else if (c2 - r == 0F) {
                             if (c1 + r == 1000F)
                                 c1 -= 10F;
                             c2 += 10F;
                             r += 10F;
                         } else if (c1 + r == 1000F) {
                             if (c2 + r == 1500F)
                                 c2 -= 10F;
                             c1 -= 10F;
                             r += 10F;
                         } else if (c2 + r == 1500F) {
                             if (c1 - r == 0F)
                                 c1 += 10F;
                             c2 -= 10F;
                             r += 10F;
                         }
                     }


                }

            else if (string.equalsIgnoreCase("smaller") ) {
                             if(flag==0 && z-x!=10F)
                             {
                                 y -= 10;
                                 z -= 10;
                             }
                             else if(flag==1 && r!=10F)
                                 r -= 10F;
                         }
                if(flag==0)


                    c.drawRect(w, x, y, z, p);

                else if(flag==1)
                   c.drawCircle(c1,c2,r,p);
                p.setColor(Color.BLUE);
                ImageView imageview = (ImageView) findViewById(R.id.imageView);
                imageview.setImageBitmap(b);
                break;
            }
            }
        }
}}
