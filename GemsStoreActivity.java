package ir.example.vazheyaabapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vazheyaabapplication.BuildConfig;
import com.example.vazheyaabapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import util.IabHelper;
import util.IabResult;
import util.Purchase;

public class GemsStoreActivity extends AppCompatActivity {
    IabHelper mHelper;
    static String SKU_TEN_GEMS = "10gems";
    static String SKU_TWENTY_GEMS = "20gems";
    static String SKU_FORTY_GEMS = "40gems";
    static String SKU_EIGHTY_GEMS = "80gems";
    static String SKU_EIGHTY_GEMS_TEST = "80gemsTest";
    boolean isSetup = false;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener;
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gems_store);

        Locale locale = new Locale("fa");

        TextView textViewNumOfGems1 = findViewById(R.id.textViewNumOfGems1);
        TextView textViewNumOfGems2 = findViewById(R.id.textViewNumOfGems2);
        TextView textViewNumOfGems3 = findViewById(R.id.textViewNumOfGems3);
        TextView textViewNumOfGems4 = findViewById(R.id.textViewNumOfGems4);

        TextView textViewPriceGems1 = findViewById(R.id.textViewPriceGems1);
        TextView textViewPriceGems2 = findViewById(R.id.textViewPriceGems2);
        TextView textViewPriceGems3 = findViewById(R.id.textViewPriceGems3);
        TextView textViewPriceGems4 = findViewById(R.id.textViewPriceGems4);

        textViewNumOfGems1.setText(String.format(locale, "%d الماس", 10));
        textViewNumOfGems2.setText(String.format(locale, "%d الماس", 20));
        textViewNumOfGems3.setText(String.format(locale, "%d الماس", 40));
        textViewNumOfGems4.setText(String.format(locale, "%d الماس", 80));

        textViewPriceGems1.setText(String.format(locale, "%d تومان", 2000));
        textViewPriceGems2.setText(String.format(locale, "%d تومان", 3500));
        textViewPriceGems3.setText(String.format(locale, "%d تومان", 6500));
        textViewPriceGems4.setText(String.format(locale, "%d تومان", 12500));

        //String base64EncodedPublicKey="MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwCwSE0DaAFfvk+qAolUca6ihpAzdXky1cj9jhtzmBJmd/ACLmmtc3Q8h79S5EmXiP0RSc/PrBcwwyK7kYtipw7pjj8BBDyYlftMqAlpdkrUx4etUzp+A09/eDf/2wk3k47mIa4HtI4CZTQFvimozPpaKkgHynwI7tdWGeGd7gaQzpfzgfjo+kz/7+bW14MrJiCgIBkJBRwTa9XjSP5V+rSNa4TOLJ8EG9zrMIYD5kMCAwEAAQ==";
        sharedPreferences = getApplicationContext().getSharedPreferences("PREFS_NAME", 0);
        //getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //editor.putInt("gems", sharedPreferences.getInt("gems",-5)+80);
        //editor.apply();
        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(GemsStoreActivity.this, BuildConfig.IAB_PUBLIC_KEY);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d("TAG", "Problem setting up In-app Billing: " + result);
                }else {
                    isSetup = true;
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
                    if (inventory.hasPurchase(SKU_EIGHTY_GEMS_TEST)) {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_EIGHTY_GEMS_TEST), mConsumeFinishedListener);
                    }else{
                        System.out.println("Not Purchased!");
                    }
                    // update UI accordingly

                    //Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
                }

                Log.d("TAG", "Initial inventory query finished; enabling main UI.");
            }
        };*/

        mConsumeFinishedListener =
                new IabHelper.OnConsumeFinishedListener() {
                    public void onConsumeFinished(Purchase purchase, IabResult result) {
                        if (!isSetup) {
                            return;
                        }
                        if (result.isSuccess()) {
                            // provision the in-app purchase to the user
                            // (for example, credit 50 gold coins to player's character)
                            if (purchase.getSku().equals(SKU_TEN_GEMS)) {
                                Toast.makeText(GemsStoreActivity.this, "10 الماس خدمت شما، به خوشی و سلامتی استفاده کنید😍", Toast.LENGTH_LONG).show();
                                Toast.makeText(GemsStoreActivity.this, "توجه! حذف برنامه موجب پریدن سکه ها و الماس های خریداری شده خواهدشد. لطفا دقت فرمایید⚠️", Toast.LENGTH_LONG).show();
                                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                                editor.putInt("gems", sharedPreferences.getInt("gems", -5) + 10);
                                editor.apply();
                                // consume the gas and update the UI
                            } else if (purchase.getSku().equals(SKU_TWENTY_GEMS)) {
                                // give user access to premium content and update the UI
                                Toast.makeText(GemsStoreActivity.this, "20 الماس خدمت شما، به خوشی و سلامتی استفاده کنید😍", Toast.LENGTH_LONG).show();
                                Toast.makeText(GemsStoreActivity.this, "توجه! حذف برنامه موجب پریدن سکه ها و الماس های خریداری شده خواهدشد. لطفا دقت فرمایید⚠️", Toast.LENGTH_LONG).show();
                                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                                editor.putInt("gems", sharedPreferences.getInt("gems", -5) + 20);
                                editor.apply();
                            } else if (purchase.getSku().equals(SKU_FORTY_GEMS)) {
                                // give user access to premium content and update the UI
                                Toast.makeText(GemsStoreActivity.this, "40 الماس خدمت شما، به خوشی و سلامتی استفاده کنید😍", Toast.LENGTH_LONG).show();
                                Toast.makeText(GemsStoreActivity.this, "توجه! حذف برنامه موجب پریدن سکه ها و الماس های خریداری شده خواهدشد. لطفا دقت فرمایید⚠️", Toast.LENGTH_LONG).show();
                                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                                editor.putInt("gems", sharedPreferences.getInt("gems", -5) + 40);
                                editor.apply();
                            } else if (purchase.getSku().equals(SKU_EIGHTY_GEMS)) {
                                // give user access to premium content and update the UI
                                Toast.makeText(GemsStoreActivity.this, "80 الماس خدمت شما، به خوشی و سلامتی استفاده کنید😍", Toast.LENGTH_LONG).show();
                                Toast.makeText(GemsStoreActivity.this, "توجه! حذف برنامه موجب پریدن سکه ها و الماس های خریداری شده خواهدشد. لطفا دقت فرمایید⚠️", Toast.LENGTH_LONG).show();
                                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                                editor.putInt("gems", sharedPreferences.getInt("gems", -5) + 80);
                                editor.apply();
                            }
                        } else {
                            // handle error
                        }
                    }
                };


        mPurchaseFinishedListener
                = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                if (!isSetup) {
                    return;
                }
                if (result.isFailure()) {
                    Log.d("TAG", "Error purchasing: " + result);
                    return;
                } else {
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                }


            }
        };
        /*List<String> additionalSkuList = new ArrayList<>();
        additionalSkuList.add(SKU_TEN_GEMS);
        additionalSkuList.add(SKU_TWENTY_GEMS);
        additionalSkuList.add(SKU_FORTY_GEMS);
        additionalSkuList.add(SKU_EIGHTY_GEMS_TEST);*/

        /*mHelper.queryInventoryAsync(new IabHelper.QueryInventoryFinishedListener() {
            @Override
            public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                if (result.isFailure()) {
                    // handle error here
                }
                else {

                    mHelper.consumeAsync(inv.getPurchase(SKU_EIGHTY_GEMS_TEST), new IabHelper.OnConsumeFinishedListener() {
                        @Override
                        public void onConsumeFinished(Purchase purchase, IabResult result) {
                            if (result.isSuccess()) {
                                System.out.println("Consumed!");
                            }else{
                                System.out.println("NotConsumed");
                            }
                        }
                    });
                    // update UI accordingly
                }
            }
        });*/


    }


    public void purchase10Gems(View view) {
        if (!isSetup) {
            return;
        }
        mHelper.launchPurchaseFlow(GemsStoreActivity.this, SKU_TEN_GEMS, 10001,
                mPurchaseFinishedListener, "");

    }

    public void purchase20Gems(View view) {
        if (isSetup) {
            mHelper.launchPurchaseFlow(this, SKU_TWENTY_GEMS, 10001,
                    mPurchaseFinishedListener, "");
        }
    }

    public void purchase40Gems(View view) {
        if (!isSetup) {
            return;
        }
        mHelper.launchPurchaseFlow(this, SKU_FORTY_GEMS, 10001,
                mPurchaseFinishedListener, "");
    }

    public void purchase80Gems(View view) {
        if (!isSetup) {
            return;
        }
        mHelper.launchPurchaseFlow(GemsStoreActivity.this, SKU_EIGHTY_GEMS, 10001,
                mPurchaseFinishedListener, "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("TAG", "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (!isSetup) {
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