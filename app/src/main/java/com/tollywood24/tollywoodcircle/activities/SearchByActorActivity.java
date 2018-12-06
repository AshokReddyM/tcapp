package com.tollywood24.tollywoodcircle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tollywood24.tollywoodcircle.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchByActorActivity extends AppCompatActivity {


    private static final int VOICE_INPUT_REQUEST_CODE = 99;
    @BindView(R.id.search_box)
    EditText searchView;


    @BindView(R.id.iv_search_mic)
    ImageView searchMic;

    @BindView(R.id.iv_search_back)
    ImageView back;

    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_actor);

        ButterKnife.bind(this);

        //to open keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        searchView.requestFocus();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent=new Intent(SearchByActorActivity.this,ActorNewsActivity.class);
                    intent.putExtra("SearchName",searchView.getText().toString());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        searchMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchByActorActivity.this, "Clicked mic", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Search");
                startActivityForResult(intent, VOICE_INPUT_REQUEST_CODE);
            }
        });


        AdView mAdView = (AdView) findViewById(R.id.adView_player);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_INPUT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> matches = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                int matchSize = matches.size();
                for (int index = 0; index < matchSize; index++) {
                    Log.i(TAG, String.valueOf(index) + ": " + matches.get(index));
                    if (index == 0) {

                        searchView.setText(matches.get(index));
                        searchView.setSelection(matches.get(index).length());

                    }
                }
            }
        }
    }

}
