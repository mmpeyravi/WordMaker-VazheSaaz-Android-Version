package ir.example.vazheyaabapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.example.vazheyaabapplication.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ValidPermutationsActivity extends AppCompatActivity {
    ImageButton imageButton;
    ImageView imageView;
    TextView textViewAlterValidPermutation;
    int coinsGot;
    int gemsGot;
    long chestTime;
    long startTime;
    int chestOpenClose;
    TextView textViewCoinsValidPermutations;
    TextView textViewGemsValidPermutations;
    TextView textViewTimeChest;
    TextView textViewStringPermutations;
    ImageView imageViewAddCoinsValidPermutations;
    ImageView imageViewAddGemsValidPermutations;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    Button lastWord;
    Button btnReset;
    String showingText;
    int requiredGemsToOpenTheChest;
    EditText editText;
    int minu;
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    MyColor srcColor;
    MyColor dstColor;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid_permutations);
        srcColor = new MyColor(0, 0, 255);
        dstColor = new MyColor(0, 0, 255);
        random = new Random();
        Locale locale = new Locale("fa");

        lastWord = findViewById(R.id.buttonLastWord);
//btnReset=findViewById(R.id.rs)
        lastWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ValidPermutationsActivity.this, ListValidPermutationsActivity.class);
                intent.putExtra("string", sharedPreferences.getString("lastWord", null));
                //intent.putExtra("string",showPrivateData("lastWord"));
                startActivity(intent);
            }
        });
/*btnReset.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        editor.putInt("coins",100);
        editor.putInt("gems",20);
        textViewCoinsValidPermutations.setText(sharedPreferences.getInt("coins",-5)+"");
        textViewGemsValidPermutations.setText(sharedPreferences.getInt("gems",-5)+"");
    }
});*/
        //Calendar calendar=Calendar.getInstance();
        textViewTimeChest = findViewById(R.id.textViewTimeChest);
        textViewCoinsValidPermutations = findViewById(R.id.textViewCoinsValidPermutations);
        textViewGemsValidPermutations = findViewById(R.id.textViewGemsValidPermutations);
        textViewStringPermutations=findViewById(R.id.textViewStringPermutation);
        imageViewAddCoinsValidPermutations = findViewById(R.id.imageViewCoinsAddValidPermutations);
        imageViewAddGemsValidPermutations = findViewById(R.id.imageViewGemsAddValidPermutations);
        sharedPreferences = getApplicationContext().getSharedPreferences("PREFS_NAME", 0);
        //getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        coinsGot = 0;
        gemsGot = 0;
        /*editor.putInt("coins",100);
        editor.apply();
        editor.putInt("gems",20);
        editor.apply();*/
        //textViewCoinsValidPermutations.setText(sharedPreferences.getInt("coins",-5)+"");
        //textViewGemsValidPermutations.setText(sharedPreferences.getInt("gems",-5)+"");
        //if(showPublicData("coins")==null){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textViewStringPermutations.setText(Build.getSerial());
        }
        if (sharedPreferences.getInt("coins", -5) == -5) {
            editor.putInt("coins", 100);
            editor.apply();
            //savePublicly("coins",100);
        }
        /*if(showPublicData("coins")==null){
            System.out.println("Hello!");
        }*/
        //coinsGot=Integer.parseInt(showPublicData("coins"));
        //coinsGot = sharedPreferences.getInt("coins", -5);
        //textViewCoinsValidPermutations.setText(coinsGot + "");
        textViewCoinsValidPermutations.setText(String.format(locale, "%d", sharedPreferences.getInt("coins", -5)));

        //if(showPublicData("gems")==null){
        if (sharedPreferences.getInt("gems", -5) == -5) {
            editor.putInt("gems", 10);
            editor.apply();
            //savePublicly("gems",10);
        }
        //gemsGot=Integer.parseInt(showPublicData("gems"));
        //gemsGot = sharedPreferences.getInt("gems", -5);
        //textViewGemsValidPermutations.setText(gemsGot + "");
        textViewGemsValidPermutations.setText(String.format(locale, "%d", sharedPreferences.getInt("gems", -5)));

        //if (showPrivateData("chestOpenClose")==null){
        if (sharedPreferences.getInt("chestOpenClose", -5) == -5) {
            editor.putInt("chestOpenClose", 0);
            editor.apply();
            //savePrivately("chestOpenClose",0);
        } else {
            chestTime = sharedPreferences.getInt("chestOpenClose", -5);
            //chestTime=Integer.parseInt(showPrivateData("chestOpenClose"));
            //textViewTimeChest.setText(chestTime + "");
        }
        //if (showPrivateData("lastWord")==null){
        if (sharedPreferences.getString("lastWord", null) == null) {
            lastWord.setVisibility(View.INVISIBLE);
        } else {
            lastWord.setVisibility(View.VISIBLE);
            lastWord.setText("آخرین واژه:" + "\n" + sharedPreferences.getString("lastWord", null));
            //lastWord.setText("آخرین واژه:" + "\n" + Integer.parseInt(showPrivateData("lastWord")));
        }
        /*Handler mHandler = new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                textViewTimeChest.setText(showingText);
                return true;
            }
        };*/


        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                changeColor();
                                textViewCoinsValidPermutations.setText(String.format(locale, "%d", sharedPreferences.getInt("coins", -5)));
                                textViewGemsValidPermutations.setText(String.format(locale, "%d", sharedPreferences.getInt("gems", -5)));
                                //if (showPrivateData("lastWord")==null){
                                if (sharedPreferences.getString("lastWord", null) == null) {
                                    lastWord.setVisibility(View.INVISIBLE);
                                } else {
                                    lastWord.setVisibility(View.VISIBLE);
                                    lastWord.setText("آخرین واژه:" + "\n" + sharedPreferences.getString("lastWord", null));
                                }
                                //long chestTimeStart =Long.parseLong(showPrivateData("timeChestStart"));
                                //int chestOpenClose =Integer.parseInt(showPrivateData("chestOpenClose"));
                                long chestTimeStart = sharedPreferences.getLong("timeChestStart", -5);
                                int chestOpenClose = sharedPreferences.getInt("chestOpenClose", -5);
                                if (chestOpenClose == 0) {
                                    showingText = "آماده\nواژه سازی!🥳";
                                    //textViewTimeChest.setText("آماده جستجو!🥳");
                                } else {
                                    Date date = new Date();
                                    long chestTime = chestTimeStart + 1800000L - date.getTime();
                                    if (chestTime > 0) {
                                        //chestTime-=1000;
                                        int time = (int) chestTime / 1000;
                                        int minutes = (int) (time / 60);
                                        minu = minutes;
                                        int seconds = (int) (time % 60);
                                        String m = minutes + "";
                                        String s = seconds + "";
                                        if (minutes < 10) {
                                            m = "0" + m;
                                        }
                                        if (seconds < 10) {
                                            s = "0" + s;
                                        }
                                        requiredGemsToOpenTheChest = (((int) minutes / 10) + 1);
                                        //NumberFormat nf=NumberFormat.getInstance(new Locale("fa")).f;
                                        showingText = String.format(locale, "%02d:%02d", minutes, seconds) + "\n" + "یا " + String.format(locale, "%d", requiredGemsToOpenTheChest) + "💎";
                                        //textViewTimeChest.setText(showingText);
                                    } else {
                                        //savePrivately("chestOpenClose",0);
                                        editor.putInt("chestOpenClose", 0);
                                        editor.apply();
                                    }
                                }
                                textViewTimeChest.setText(showingText);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                super.run();
            }
        };
        thread.start();





        Thread thread2 = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                changeColor();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                super.run();
            }
        };
        thread2.start();



        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                long chestTimeStart = sharedPreferences.getLong("timeChestStart", -5);
                int chestOpenClose = sharedPreferences.getInt("chestOpenClose", -5);
                if (chestOpenClose == 0) {
                    showingText="آماده جستجو!🥳";
                    //textViewTimeChest.setText("آماده جستجو!🥳");
                } else {
                    Date date = new Date();
                    long chestTime = date.getTime() - chestTimeStart;
                    if (chestTime > 0) {
                        //chestTime-=1000;
                        int minutes = (int) (chestTime / 60);
                        int seconds = (int) (chestTime % 60);
                        String m = minutes + "";
                        String s = seconds + "";
                        if (minutes < 10) {
                            m = "0" + m;
                        }
                        if (seconds < 10) {
                            s = "0" + s;
                        }
                        showingText = m + ":" + s + "\n" + "یا " + ((int) minutes / 10) + "💎";
                        //textViewTimeChest.setText(showingText);
                    } else {
                        editor.putInt("chestOpenClose", 0);
                        editor.apply();
                    }
                }
                textViewTimeChest.setText(showingText);
            }
        },1000);
*/
        /*new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long chestTimeStart = sharedPreferences.getLong("timeChestStart", -5);
                int chestOpenClose = sharedPreferences.getInt("chestOpenClose", -5);
                if (chestOpenClose == 0) {
                    showingText="آماده جستجو!🥳";
                    //textViewTimeChest.setText("آماده جستجو!🥳");
                } else {
                    Date date = new Date();
                    long chestTime = date.getTime() - chestTimeStart;
                    if (chestTime > 0) {
                        //chestTime-=1000;
                        int minutes = (int) (chestTime / 60);
                        int seconds = (int) (chestTime % 60);
                        String m = minutes + "";
                        String s = seconds + "";
                        if (minutes < 10) {
                            m = "0" + m;
                        }
                        if (seconds < 10) {
                            s = "0" + s;
                        }
                        showingText = m + ":" + s + "\n" + "یا " + ((int) minutes / 10) + "💎";
                        //textViewTimeChest.setText(showingText);
                    } else {
                        editor.putInt("chestOpenClose", 0);
                        editor.apply();
                    }
                }
                mHandler.obtainMessage(1).sendToTarget();
            }
        }, 0, 1000);
*/


        editText = findViewById(R.id.editTextValidPermutation);
        // imageButton=findViewById(R.id.imageButtonValidPermutation);
        imageView = findViewById(R.id.imageViewValidPermutations);
        textViewAlterValidPermutation = findViewById(R.id.textViewAlterValidPermutation);

        //editText.setText("عاشورا");
        //TextView textViewEnterValidPermutations = findViewById(R.id.textViewStringPermutation);
        //textViewEnterValidPermutations.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/iran_nastaliq.ttf"));
        //textViewAlterValidPermutation.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/iran_nastaliq.ttf"));
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    public void decCoins(int dec) {
        //int x=Integer.parseInt(showPublicData("coins"));
        int x = sharedPreferences.getInt("coins", -5);
        x -= dec;
        editor.putInt("coins", x);
        editor.apply();
        //savePublicly("coins",x);
        textViewCoinsValidPermutations.setText(sharedPreferences.getInt("coins", -5) + "");
        //textViewCoinsValidPermutations.setText(showPublicData("coins") + "");
    }

    public void decGems(int dec) {
        //int x=Integer.parseInt(showPublicData("gems"));
        int x = sharedPreferences.getInt("gems", -5);
        x -= dec;
        editor.putInt("gems", x);
        editor.apply();
        textViewGemsValidPermutations.setText(sharedPreferences.getInt("gems", -5) + "");
        //textViewGemsValidPermutations.setText(showPublicData("gems") + "");
    }

    public void showDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //Type 1
        alertDialog.setTitle("فعال کردن واژه ساز")
                .setMessage("آیا می خواهید با پرداخت " + requiredGemsToOpenTheChest + " الماس، واژه ساز را فعال نمایید؟")
                .setCancelable(false)
                .setIcon(R.drawable.gem)
                .setPositiveButton("بله😍", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putInt("chestOpenClose", 1);
                        editor.apply();
                        //savePrivately("chestOpenClose",1);
                        editor.putInt("gems", sharedPreferences.getInt("gems", -5) - requiredGemsToOpenTheChest);
                        editor.apply();
                        //savePublicly("gems",Integer.parseInt(showPublicData("gems"))-requiredGemsToOpenTheChest);
                        textViewGemsValidPermutations.setText(sharedPreferences.getInt("gems", -5) + "");
                        int dec = editText.getText().toString().trim().length();
                        decCoins(dec);
                        //textViewGemsValidPermutations.setText(showPublicData("gems")+"");
                        editor.putLong("timeChestStart", new Date().getTime());
                        editor.apply();
                        //savePrivately("timeChestStart",new Date().getTime());
                        editor.putString("lastWord", editText.getText().toString().trim());
                        editor.apply();
                        Toast.makeText(ValidPermutationsActivity.this, dec + "سکه برای راه اندازی واژه ساز خرج شد", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ValidPermutationsActivity.this, ListValidPermutationsActivity.class);
                        intent.putExtra("string", editText.getText().toString().trim());
                        startActivity(intent);
                    }
                })
                .setNegativeButton("نه، " + minu + " دقیقه صبر می کنم تا فعال شود😞", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.show();
        //.setNeutralButton("لغو",null);
    }


    public void showDialogNotEnoughGem() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //Type 1
        alertDialog.setTitle("الماس ناکافی")
                .setMessage("شما الماس کافی رو برای فعالسازی واژه ساز ندارید😢 آیا می خواهید الماس بگیرید؟")
                .setCancelable(false)
                .setIcon(R.drawable.gem)
                .setPositiveButton("بله😍", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ValidPermutationsActivity.this, GemsStoreActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("خیر😞", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.show();
        //.setNeutralButton("لغو",null);
    }

    public void showDialogNotEnoughCoin() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //Type 1
        alertDialog.setTitle("سکه ناکافی")
                .setMessage("شما سکه کافی رو برای راه اندازی واژه ساز ندارید😢 آیا می خواهید سکه بگیرید؟")
                .setCancelable(false)
                .setIcon(R.drawable.coin)
                .setPositiveButton("بله😍", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ValidPermutationsActivity.this, StoreValidPermutationsActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("خیر😞", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.show();
        //.setNeutralButton("لغو",null);
    }


    public void search(View view) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(ValidPermutationsActivity.this, "لطفا جعبه ورودی را خالی نگذارید", Toast.LENGTH_SHORT).show();
            //}else if(editText.getText().toString().equals(showPrivateData("lastWord"))){
        } else if (editText.getText().toString().equals(sharedPreferences.getString("lastWord", null))) {
            Toast.makeText(ValidPermutationsActivity.this, "این واژه رو تو قسمت آخرین واژه دارینش! سکه هاتون رو الکی خرج نکنید😊!", Toast.LENGTH_LONG).show();
        } else if (editText.getText().toString().trim().length() <= sharedPreferences.getInt("coins", -5)) {
            //}else if (editText.getText().toString().trim().length() <= Integer.parseInt(showPublicData("coins"))) {
            if (sharedPreferences.getInt("chestOpenClose", -5) == 0) {
                //if(Integer.parseInt(showPrivateData("chestOpenClose"))==0){
                editor.putInt("chestOpenClose", 1);
                editor.apply();
                //savePrivately("chestOpenClose",1);
                int dec = editText.getText().toString().trim().length();
                decCoins(dec);


                //savePrivately("timeChestStart",new Date().getTime());
                editor.putLong("timeChestStart", new Date().getTime());
                editor.apply();

                //////if (sharedPreferences.getString("lastWord", null) == null) {

                //savePrivately("lastWord",editText.getText().toString().trim());
                editor.putString("lastWord", editText.getText().toString().trim());
                editor.apply();
                Toast.makeText(ValidPermutationsActivity.this, dec + "سکه برای راه اندازی واژه ساز خرج شد", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ValidPermutationsActivity.this, ListValidPermutationsActivity.class);
                intent.putExtra("string", editText.getText().toString().trim());
                startActivity(intent);
            } else {
                //if(Integer.parseInt(showPublicData("gems"))>=requiredGemsToOpenTheChest){
                if (sharedPreferences.getInt("gems", -5) >= requiredGemsToOpenTheChest) {
                    showDialog();
                } else {
                    showDialogNotEnoughGem();
                }
            }

        } else if (editText.getText().toString().trim().length() > sharedPreferences.getInt("coins", -5)) {
            showDialogNotEnoughCoin();
        }
    }


    public void savePrivately(String fileName, int data) {

        // Creating folder with name GeekForGeeks
        File folder = getExternalFilesDir("WordMaker");
        // Creating file with name gfg.txt
        File file = new File(folder, fileName);
        writeTextData(file, Integer.toString(data));
    }

    public void savePrivately(String fileName, Long data) {

        // Creating folder with name GeekForGeeks
        File folder = getExternalFilesDir("WordMaker");

        // Creating file with name gfg.txt
        File file = new File(folder, fileName);
        writeTextData(file, Long.toString(data));
    }

    public void savePrivately(String fileName, String data) {

        // Creating folder with name GeekForGeeks
        File folder = getExternalFilesDir("WordMaker");

        // Creating file with name gfg.txt
        File file = new File(folder, fileName);
        writeTextData(file, data);
    }

    private void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            //Toast.makeText(this, "Done" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String showPrivateData(String fileName) {

        // GeeksForGeeks represent the folder name to access privately saved data
        File folder = getExternalFilesDir("WordMaker");

        // gft.txt is the file that is saved privately
        File file = new File(folder, fileName);
        String data = getData(file);
        return data;
    }

    private String getData(File myfile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(myfile);
            int i = -1;
            StringBuilder buffer = new StringBuilder();
            while ((i = fileInputStream.read()) != -1) {
                buffer.append((char) i);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void savePublicly(String fileName, int data) {
        // Requesting Permission to access External Storage
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);
        //String editTextData = editText.getText().toString();

        // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
        // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Storing the data in file with name as geeksData.txt
        File file = new File(folder, fileName);
        writeTextData(file, Integer.toString(data));
    }

    public String showPublicData(String fileName) {
        /*ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);*/
        // Accessing the saved data from the downloads folder
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // geeksData represent the file data that is saved publicly
        File file = new File(folder, fileName);
        String data = getData(file);
        return data;
    }

    public void goToGemsStore(View view) {
        startActivity(new Intent(ValidPermutationsActivity.this, GemsStoreActivity.class));
    }

    public void goToCoinsStore(View view) {
        startActivity(new Intent(ValidPermutationsActivity.this, StoreValidPermutationsActivity.class));
    }

    public boolean colorEqual() {
        return srcColor.r == dstColor.r && srcColor.g == dstColor.g && srcColor.b == dstColor.b;
    }

    public void changeColor() {
        if (colorEqual()) {
            dstColor.r = random.nextInt(255);
            dstColor.g = random.nextInt(255);
            dstColor.b = random.nextInt(255);
        }
        animateColor();
    }

    private void animateColor() {
        int speed=1;
        if (srcColor.r>dstColor.r){
            srcColor.r-=speed;
        }else if(srcColor.r<dstColor.r){
            srcColor.r+=speed;
        }

        if (srcColor.g>dstColor.g){
            srcColor.g-=speed;
        }else if(srcColor.g<dstColor.g){
            srcColor.g+=speed;
        }

        if (srcColor.b>dstColor.b){
            srcColor.b-=speed;
        }else if(srcColor.b<dstColor.b){
            srcColor.b+=speed;
        }

        lastWord.setBackgroundColor(Color.argb(255,srcColor.r,srcColor.g,srcColor.b));
        lastWord.setTextColor(srcColor.r + srcColor.g + srcColor.g > 380 ? Color.BLACK : Color.WHITE);
    }
    int abs(int x){
        return x > 0 ? x : -x;
    }

}

class MyColor {
    int r, g, b;

    public MyColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}