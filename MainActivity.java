package ir.example.vazheyaabapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.vazheyaabapplication.R;

public class MainActivity extends AppCompatActivity {
    TextView textViewBesmel;
    TextView textViewPresents;
    TextView textViewVajesaz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewBesmel=findViewById(R.id.textViewBesmel);
        textViewPresents=findViewById(R.id.textViewPresents);
        textViewVajesaz=findViewById(R.id.textViewVajesaz);

        textViewBesmel.animate().alpha(1).setStartDelay(500).setDuration(500);
        textViewPresents.animate().alpha(1).setStartDelay(1000).setDuration(500);
        textViewVajesaz.animate().alpha(1).setStartDelay(1500).setDuration(500);
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            /*new ShowcaseView.Builder(this)
                    .setTarget(new ActionViewTarget(this, ActionViewTarget.Type.HOME))
                    .setContentTitle("ShowcaseView")
                    .setContentText("This is highlighting the Home button")
                    .hideOnTouchOutside()
                    .build();*/
        /*AccountManager am = AccountManager.get(this); // "this" references the current Context

        Account[] accounts = am.getAccountsByType("com.google");
        Account account;am.setUserData();*/
                Intent intent = new Intent(MainActivity.this, ValidPermutationsActivity.class);
                startActivity(intent);
                finish();
                //prepareData(string);
                //refreshDisplay();

        }, 3000);   //5 seconds
    }
    /*@Override
    protected void onResume() {
        //Intent intent = new Intent(LoadingValidPermutationsActivity.this, ValidPermutationsActivity.class);
        //startActivity(intent);
        //finish();
        number++;
        if (number%2==0){
            //Intent intent = new Intent(LoadingValidPermutationsActivity.this, ValidPermutationsActivity.class);
            //startActivity(intent);
            finish();
        }
        //Toast.makeText(this, "RSEUME()", Toast.LENGTH_SHORT).show();
        super.onResume();
    }*/
}
