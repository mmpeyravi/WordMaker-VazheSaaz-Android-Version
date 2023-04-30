package ir.example.vazheyaabapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vazheyaabapplication.BuildConfig;
import com.example.vazheyaabapplication.R;

import java.util.Locale;

import util.IabHelper;
import util.IabResult;
import util.Purchase;


public class StoreValidPermutationsActivity extends AppCompatActivity {
    // The helper object
    IabHelper mHelper;
    static String SKU_FIFTY_COINS="50coins";
    static String SKU_A_HUNDRED_COINS="100coins";
    static String SKU_TWO_HUNDRED_COINS="200coins";
    static String SKU_FOUR_HUNDRED_COINS="400coins";
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener;
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener;
boolean isSetup=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_valid_permutations);

        Locale locale = new Locale("fa");

        TextView textViewNumOfCoins1=findViewById(R.id.textViewNumOfCoins1);
        TextView textViewNumOfCoins2=findViewById(R.id.textViewNumOfCoins2);
        TextView textViewNumOfCoins3=findViewById(R.id.textViewNumOfCoins3);
        TextView textViewNumOfCoins4=findViewById(R.id.textViewNumOfCoins4);

        TextView textViewPriceCoins1=findViewById(R.id.textViewPriceCoins1);
        TextView textViewPriceCoins2=findViewById(R.id.textViewPriceCoins2);
        TextView textViewPriceCoins3=findViewById(R.id.textViewPriceCoins3);
        TextView textViewPriceCoins4=findViewById(R.id.textViewPriceCoins4);

        textViewNumOfCoins1.setText(String.format(locale,"%d سکه",50));
        textViewNumOfCoins2.setText(String.format(locale,"%d سکه",100));
        textViewNumOfCoins3.setText(String.format(locale,"%d سکه",200));
        textViewNumOfCoins4.setText(String.format(locale,"%d سکه",400));

        textViewPriceCoins1.setText(String.format(locale,"%d تومان",2000));
        textViewPriceCoins2.setText(String.format(locale,"%d تومان",3500));
        textViewPriceCoins3.setText(String.format(locale,"%d تومان",6500));
        textViewPriceCoins4.setText(String.format(locale,"%d تومان",12500));


        //String base64EncodedPublicKey="MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDkYSTBIMKFrLFzhiKtlQk6pFmQzu8s+smeihm3oFecEEPIEVVZRaSsvZIb7q1qv2Xg1vHlc7+vy2OICU1OHHlgIow6KWikVeIPASFHTf4gLBVYLCynGnWhEIWdbuJOW8PkNz3kC+NuoDSYYAgU4TxdaJzz4taNCsbNW93f8TjzsJqUJ050xYm1YnSlrguRqc+AHBDoawdH7xNiBsDVwTM5NZ0Vd6mbJFUUs/JW0Y8CAwEAAQ==";
        sharedPreferences = getApplicationContext().getSharedPreferences("PREFS_NAME", 0);
        //getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(this,/*base64EncodedPublicKey*/ BuildConfig.IAB_PUBLIC_KEY);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (mHelper==null){
                    return;
                }
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    isSetup=true;
                    Log.d("TAG", "Problem setting up In-app Billing: " + result);
                }
                // Hooray, IAB is fully set up!
                //mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });

        /*mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                Log.d("TAG", "Query inventory finished.");
                if (result.isFailure()) {
                    Log.d("TAG", "Failed to query inventory: " + result);
                    return;
                }
                else {
                    Log.d("TAG", "Query inventory was successful.");
                    // does the user have the premium upgrade?
                    //mIsPremium = inventory.hasPurchase(SKU_PREMIUM);
                    *//*if (inventory.hasPurchase(SKU_EIGHTY_GEMS_TEST)) {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_EIGHTY_GEMS_TEST), mConsumeFinishedListener);
                    }else{
                        System.out.println("Not Purchased!");
                    }*//*
                    // update UI accordingly

                    //Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
                }

                Log.d("TAG", "Initial inventory query finished; enabling main UI.");
            }
        };*/

        mConsumeFinishedListener =
                new IabHelper.OnConsumeFinishedListener() {
                    public void onConsumeFinished(Purchase purchase, IabResult result) {
                        if (!isSetup){
                            return;
                        }
                        if (result.isSuccess()) {
                            // provision the in-app purchase to the user
                            // (for example, credit 50 gold coins to player's character)
                            if (purchase.getSku().equals(SKU_FIFTY_COINS)) {
                                Toast.makeText(StoreValidPermutationsActivity.this,"50 سکه خدمت شما، به خوشی و سلامتی استفاده کنید😍", Toast.LENGTH_LONG).show();
                                Toast.makeText(StoreValidPermutationsActivity.this,"توجه! حذف برنامه موجب پریدن سکه ها و الماس های خریداری شده خواهدشد. لطفا دقت فرمایید⚠️", Toast.LENGTH_LONG).show();
                                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                                editor.putInt("coins", sharedPreferences.getInt("coins",-5)+50);
                                editor.apply();
                                // consume the gas and update the UI
                            }
                            else if (purchase.getSku().equals(SKU_A_HUNDRED_COINS)) {
                                // give user access to premium content and update the UI
                                Toast.makeText(StoreValidPermutationsActivity.this,"100 سکه خدمت شما، به خوشی و سلامتی استفاده کنید😍", Toast.LENGTH_LONG).show();
                                Toast.makeText(StoreValidPermutationsActivity.this,"توجه! حذف برنامه موجب پریدن سکه ها و الماس های خریداری شده خواهدشد. لطفا دقت فرمایید⚠️", Toast.LENGTH_LONG).show();
                                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                                editor.putInt("coins", sharedPreferences.getInt("coins",-5)+100);
                                editor.apply();
                            }
                            else if (purchase.getSku().equals(SKU_TWO_HUNDRED_COINS)) {
                                // give user access to premium content and update the UI
                                Toast.makeText(StoreValidPermutationsActivity.this,"200 سکه خدمت شما، به خوشی و سلامتی استفاده کنید😍", Toast.LENGTH_LONG).show();
                                Toast.makeText(StoreValidPermutationsActivity.this,"توجه! حذف برنامه موجب پریدن سکه ها و الماس های خریداری شده خواهدشد. لطفا دقت فرمایید⚠️", Toast.LENGTH_LONG).show();
                                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                                editor.putInt("coins", sharedPreferences.getInt("coins",-5)+200);
                                editor.apply();
                            }
                            else if (purchase.getSku().equals(SKU_FOUR_HUNDRED_COINS)) {
                                // give user access to premium content and update the UI
                                Toast.makeText(StoreValidPermutationsActivity.this,"400 سکه خدمت شما، به خوشی و سلامتی استفاده کنید😍", Toast.LENGTH_LONG).show();
                                Toast.makeText(StoreValidPermutationsActivity.this,"توجه! حذف برنامه موجب پریدن سکه ها و الماس های خریداری شده خواهدشد. لطفا دقت فرمایید⚠️", Toast.LENGTH_LONG).show();
                                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                                editor.putInt("coins", sharedPreferences.getInt("coins",-5)+400);
                                editor.apply();
                            }
                        }
                        else {
                            // handle error
                        }
                    }
                };


        mPurchaseFinishedListener
                = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result, Purchase purchase)
            {
                if (!isSetup){
                    return;
                }
                if (result.isFailure()) {
                    Log.d("TAG", "Error purchasing: " + result);
                    return;
                }
                else{
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                }


            }
        };
    }

    public void purchase50Coins(View view) {
        if (!isSetup){
            return;
        }
        mHelper.launchPurchaseFlow(this, SKU_FIFTY_COINS, 10001,
                mPurchaseFinishedListener, "");
    }
    public void purchase100Coins(View view) {
        if (!isSetup){
            return;
        }
        mHelper.launchPurchaseFlow(this, SKU_A_HUNDRED_COINS, 10001,
                mPurchaseFinishedListener, "");
    }
    public void purchase200Coins(View view) {
        if (!isSetup){
            return;
        }
        mHelper.launchPurchaseFlow(this, SKU_TWO_HUNDRED_COINS, 10001,
                mPurchaseFinishedListener, "");
    }
    public void purchase400Coins(View view) {
        if (!isSetup){
            return;
        }
        mHelper.launchPurchaseFlow(this, SKU_FOUR_HUNDRED_COINS, 10001,
                mPurchaseFinishedListener, "");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("TAG", "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper==null){
            return;
        }
        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d("TAG", "onActivityResult handled by IABUtil.");
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }
}