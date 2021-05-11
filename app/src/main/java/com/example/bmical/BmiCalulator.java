package com.example.bmical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BmiCalulator extends AppCompatActivity {
    android.widget.Button mrecalculatebmi;
    TextView mbmidisplay, mbmicategory, mgender;
    Intent intent;
    ImageView mimageview;
    String mbmi;
    float intbmi;
    String height;
    String weight;
    float intheight, intweight;
    RelativeLayout mbackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calulator);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\"><font"));
        getSupportActionBar().setTitle("Result: ");
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#1E1D1D"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        intent = getIntent();

        mbmidisplay = findViewById(R.id.bmidisplay);
        mbmicategory = findViewById(R.id.bmicategory);
        mgender = findViewById(R.id.genderdisplay);
        mbackground = findViewById(R.id.contentlayout);
        mimageview = findViewById(R.id.imageview);
        mrecalculatebmi=findViewById(R.id.recalculatebmi);

        height = intent.getStringExtra("height");
        weight = intent.getStringExtra("weight");

        intheight = Float.parseFloat(height);
        intweight = Float.parseFloat(weight);
        intheight = intheight/100;

        intbmi = intweight/(intheight*intheight);

        mbmi = Float.toString(intbmi);

        if(intbmi<16)
        {
            mbmicategory.setText("You Have to Loose your Weight");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource((R.drawable.crosss));
        }else if(intbmi<16.9 && intbmi>16){
            mbmicategory.setText("you Are In A Good Position");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource((R.drawable.warning));
        }else if(intbmi<18.4 && intbmi>27){
            mbmicategory.setText("You Are to Increase your Weight");
            mbackground.setBackgroundColor(Color.YELLOW);
            mimageview.setImageResource((R.drawable.crosss));
        }else if(intbmi<25 && intbmi>18.4){
            mbmicategory.setText("You are Normal");
           // mbackground.setBackgroundColor(Color.YELLOW);
            mimageview.setImageResource((R.drawable.ok));
        }else if(intbmi<29.4 && intbmi>25){
            mbmicategory.setText("You are Over Weight");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource((R.drawable.warning));
        }else{
            mbmicategory.setText("You Want to Die!!");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource((R.drawable.warning));
        }

        mgender.setText(intent.getStringExtra("gender"));
        mbmidisplay.setText(mbmi);

        mrecalculatebmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BmiCalulator.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}