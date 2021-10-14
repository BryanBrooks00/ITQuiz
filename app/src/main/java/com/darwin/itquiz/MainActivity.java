package com.darwin.itquiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


public class MainActivity extends AppCompatActivity {

    private RewardedAd mRewardedAd;
    FragmentTransaction transaction;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    final static String TAG = "LOG";
    private long backPressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadAd();
        loadFragment();
    }

    public void loadFragment(){
        Button btn_ad = findViewById(R.id.btn_ad);
        TextView ad_tv = findViewById(R.id.ad_tv);


        transaction = getSupportFragmentManager().beginTransaction();

        sp = getSharedPreferences(TAG, MODE_PRIVATE);
        int lvl = sp.getInt(TAG, 1);
        Log.i(TAG, "lvl = " + lvl);

        switch (lvl) {
            case 1:
                transaction.replace(R.id.frameLayout,Fragment1.class, null);
                btn_ad.setVisibility(View.VISIBLE);
                ad_tv.setVisibility(View.VISIBLE);
                break;
            case 2:
                transaction.replace(R.id.frameLayout,Fragment2.class, null);
                break;
            case 3:
                transaction.replace(R.id.frameLayout,Fragment3.class, null);
                break;
            case 4:
                transaction.replace(R.id.frameLayout,Fragment4.class, null);
                break;
            case 5:
                transaction.replace(R.id.frameLayout,Fragment5.class, null);
                break;
            case 6:
                transaction.replace(R.id.frameLayout,Fragment6.class, null);
                break;
                case 7:
                transaction.replace(R.id.frameLayout,Fragment7.class, null);
                break;
            case 8:
                transaction.replace(R.id.frameLayout,Fragment8.class, null);
                break;
            case 9:
                transaction.replace(R.id.frameLayout,Fragment9.class, null);
                break;
            case 10:
                transaction.replace(R.id.frameLayout,Fragment10.class, null);
                break;
            case 11:
                transaction.replace(R.id.frameLayout,FragmentFinal.class, null);
                btn_ad.setVisibility(View.INVISIBLE);
                ad_tv.setVisibility(View.INVISIBLE);
                break;

            default:
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();

        //ad
        btn_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAd();

            }
        });
        //
    }

    public void savePrefs(int i){
        Log.i(TAG, "save preferences:" + i);
        sp = getSharedPreferences(TAG, MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt(TAG, i);
        editor.apply();
        loadFragment();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        } else{
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.exit),
                    Toast.LENGTH_SHORT).show();

        }
        backPressedTime = System.currentTimeMillis();
    }

    public void loadAd(){
        // ad
        AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();

        RewardedAd.load(this,"ca-app-pub-2382402581294867/2653755019",
                adRequest,new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad (@NonNull LoadAdError loadAdError){
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        Log.i(TAG, "Failed to load ad.");
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded (@NonNull RewardedAd rewardedAd){
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });
        //
    }

    @SuppressLint("SetTextI18n")
    public void showAd(){

        try {
            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d(TAG, "Ad was shown.");
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when ad fails to show.
                    Log.d(TAG, "Ad failed to show.");
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    Log.d(TAG, "Ad was dismissed.");
                    mRewardedAd = null;
                }

            });
        } catch (Exception e){
            Log.i(TAG, "showAd error");
        }

        if (mRewardedAd!= null) {
            Activity activityContext = MainActivity.this;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d(TAG, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();

                    sp = getSharedPreferences(TAG, MODE_PRIVATE);
                    int lvl = sp.getInt(TAG, 1);
                    savePrefs(lvl+1);
                    loadFragment();
                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.tryLater),
                    Toast.LENGTH_SHORT).show();
        }
    }
    }
