package ir.example.vazheyaabapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vazheyaabapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class LoadingValidPermutationsActivity extends AppCompatActivity {
    private static ArrayList<String> allPermutations;
    private static ArrayList<String> dictionary;
    int number = 0;
    static ArrayList<String> validWords;
    static ArrayList<String> editedValidWords;
    String string = "";
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(this, "loading...", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_valid_permutations);
        /*for (int i = 0; i < 999999999; i++) {
            number=1;
        }*/
        ImageView imageViewMagnifier = findViewById(R.id.imageViewMagnifier);
        TextView textViewLoading=findViewById(R.id.textViewLoading);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);


        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                double i = (double) ((double)count / (double) allPermutations.size());
                                progressBar.setProgress((int)(i*100));
                                textViewLoading.setText(((int)(i*100)+"%"));
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


        new Thread(new Runnable() {
            @Override
            public void run() {
                imageViewMagnifier.animate().rotationBy(999999999).setDuration(99999990);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle extras = getIntent().getExtras();
                //string = "";
                assert extras != null;
                if (extras.containsKey("string")) {
                    string = extras.getString("string");
                }
                if (string != null && !string.equals("")) {
                    makePermutation(string);
                    validWords = new ArrayList<>();
                    for (String s : allPermutations) {
                        count++;
                        if (isInDictionary(s)) {
                            validWords.add(s);
                        }
                    }

                    Intent intent = new Intent(LoadingValidPermutationsActivity.this, ListValidPermutationsActivity.class);
                    //intent.putExtra("string", string);
                    intent.putStringArrayListExtra("array", validWords);
                    startActivity(intent);
                    //prepareData(string);
                    //refreshDisplay();
                }
            }
        }).start();


        //imageViewMagnifier.animate().yBy(-100).setDuration(1000);
        /*Handler handler = new Handler();
        handler.postDelayed(() -> {
            // yourMethod();
            Bundle extras = getIntent().getExtras();
            //string = "";
            assert extras != null;
            if (extras.containsKey("string")) {
                string = extras.getString("string");
            }

            if (string != null && !string.equals("")) {
                Intent intent = new Intent(LoadingValidPermutationsActivity.this, ListValidPermutationsActivity.class);
                intent.putExtra("string", string);
                startActivity(intent);
                //prepareData(string);
                //refreshDisplay();
            }
        }, 1000);   //5 seconds*/


    }

    @Override
    protected void onResume() {
        //Intent intent = new Intent(LoadingValidPermutationsActivity.this, ValidPermutationsActivity.class);
        //startActivity(intent);
        //finish();
        number++;
        if (number % 2 == 0) {
            //Intent intent = new Intent(LoadingValidPermutationsActivity.this, ValidPermutationsActivity.class);
            //startActivity(intent);
            finish();
        }
        //Toast.makeText(this, "RSEUME()", Toast.LENGTH_SHORT).show();
        super.onResume();
    }


    private void initDictionary() {
        dictionary = new ArrayList<>();
        String string = "";
        InputStream is = this.getResources().openRawResource(R.raw.edited_words);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                if ((string = reader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
            dictionary.add(string);
        }
        Collections.sort(dictionary);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * permutation function
     *
     * @param str string to calculate permutation for
     * @param l   starting index
     * @param r   end index
     */
    public void permute(String str, int l, int r, ArrayList<String> arrayList) {

        if (l == r) {
            //System.out.println(str);
            arrayList.add(str);
        } else {
            for (int i = l; i <= r; i++) {
                str = swap(str, l, i);
                permute(str, l + 1, r, arrayList);
                str = swap(str, l, i);
            }
        }
    }

    /**
     * Swap Characters at position
     *
     * @param a string value
     * @param i position 1
     * @param j position 2
     * @return swapped string
     */
    public String swap(String a, int i, int j) {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }

    public void prepareData(String str) {
        makePermutation(str);
        validWords = new ArrayList<>();
        for (String s : allPermutations) {
            if (isInDictionary(s)) {
                validWords.add(s);
            }
        }
        Set<String> s = new LinkedHashSet<>(validWords);
        //HashSet<String> myHashSet=new HashSet<>(validWords);
        //items.addAll(s);
        //items.addAll(validWords);
        Toast.makeText(this, validWords.size() + " عدد واژه معنادار یافت شد😎", Toast.LENGTH_SHORT).show();
    }

    private static boolean isInDictionary(String word) {
        /*int start=0;
        int end=382502;
        for (Letter letter : letters) {
            if (letter.getLetter() == word.charAt(0)) {
                start = letter.getStart();
                end = letter.getEnd();
            }
        }
        //declareLetters();
        //for (Letter letter : letters) {
        //    System.out.println(letter.getLetter() + " " + letter.getStart() + " " + letter.getEnd());
        //}
        System.out.println("start = " + start);
        System.out.println("end = " + end);
        for (int i = start; i <= end; i++) {
            if (dictionary.get(i).equals(word)){
                return true;
            }
        }*/
        for (String s : dictionary) {
            if (s.equals(word)) {
                return true;
            }
        }
        /*for (String s : dictionary) {
            if (s.equals(word)) {
                return true;
            }
        }*/
        /*String absolutePath = "C:\\Users\\mmpey\\Desktop\\totalDic.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(absolutePath))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.equals(word)) {
                    return true;
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            // Exception handling
        }*/
        return false;
    }

    private void makePermutation(String str) {
        //initializeLettersArray();
        initDictionary();
        int n = str.length();
        ArrayList<String> arrayList = new ArrayList<>();
        permute(str, 0, n - 1, arrayList);
        allPermutations = new ArrayList<>(arrayList);

        HashSet<String> hashSet = new HashSet<>();
        int num = n;
        int x = 1;
        while (num > 3) {
            for (String s : arrayList) {
                hashSet.add(s.substring(x));
            }
            allPermutations.addAll(hashSet);
            hashSet.clear();
            x++;
            num--;
        }
    }

}
/*class MakeWordsList extends Thread{
    @Override
    public void run() {
        Bundle extras = getIntent().getExtras();
        while (string == null && string.equals(""))
    }
}*/
