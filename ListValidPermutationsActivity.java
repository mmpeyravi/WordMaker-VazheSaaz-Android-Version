package ir.example.vazheyaabapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vazheyaabapplication.R;
import com.plattysoft.leonids.ParticleSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class ListValidPermutationsActivity extends AppCompatActivity implements TextWatcher {

    private static ArrayList<String> allPermutations;
    //private static ArrayList<Letter> letters;
    public static ArrayList<String> dictionary;
    private static ArrayList<EditText> editTexts;
    private EditText editTextNumberOfChars;
    //private static ArrayList<Letter> letters;
    List<String> items;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    //int number = 1;
    static ArrayList<String> validWords;
    static ArrayList<String> editedValidWords;
    String string;
    Boolean flag = false;
    Boolean firstTime = true;
    static int[] starts;
    static int[] ends;
    static ArrayList<Character> lets;
    Node node;
    ImageView imageViewGear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(this, "List...", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_valid_permutations);
        initEditTexts();
        validWords = new ArrayList<>();
        imageViewGear = findViewById(R.id.imageViewGear);
        listView = findViewById(R.id.listViewValidPermutations);
        items = new ArrayList<>();
        final Bundle extras = getIntent().getExtras();
        assert extras != null;
        refreshDisplay();
        TextView textViewLoading = findViewById(R.id.textViewLoadingList);
        //initializeLettersArray();
        node = new Node();
        /*for (int i = 0; i < 32; i++) {
            node.children.pu(new Node(lets.get(i)));
        }*/
        /*if (extras.containsKey("string")) {
            string = extras.getString("string");
        }*/
        /*if (extras.containsKey("array")) {
            validWords = extras.getStringArrayList("array");
        }*/
        //Date date=new Date();

        Random random = new Random();

//new Thread
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(500);
                        runOnUiThread(() -> {
                            //double i = (double) ((double)count / (double) allPermutations.size());
                            //count++;
                            //System.out.println("flag = " + flag);
                            if (flag) {
                                if (firstTime) {
                                    /*set.clear();
                                    set.addAll(validWords);*/
                                    items.clear();
                                    items.addAll(validWords);
                                    arrayAdapter.notifyDataSetChanged();
                                    firstTime = false;
                                    Toast.makeText(ListValidPermutationsActivity.this, validWords.size() + " عدد واژه معنادار ساخته شد😎", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(ListValidPermutationsActivity.this, count + " پیمایش😎", Toast.LENGTH_SHORT).show();
                                    //long l=date.getTime()-new Date().getTime();
                                    //System.out.println("count = " + l);
                                    new ParticleSystem(ListValidPermutationsActivity.this, 80, R.drawable.star_pink, 3000L)
                                            .setSpeedRange(0.2f, 0.5f)
                                            .oneShot(textViewLoading, 80);
                                    textViewLoading.setVisibility(View.GONE);
                                }
                                imageViewGear.animate().alpha(0).setDuration(500);
                                //thread.stop();
                                //arrayAdapter.notifyDataSetChanged();
                            } else {
                                /*set.clear();
                                set.addAll(validWords);*/
                                if (validWords.size() > 0) {
                                    textViewLoading.setVisibility(View.GONE);
                                }
                                items.clear();
                                items.addAll(validWords);
                                arrayAdapter.notifyDataSetChanged();
                                imageViewGear.animate().rotationBy(30f).setDuration(500);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    textViewLoading.setTextColor(Color.argb(1, random.nextFloat(), random.nextFloat(), random.nextFloat()));
                                }
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


        new Thread(() -> {
            Bundle extras1 = getIntent().getExtras();
            //string = "";
            assert extras1 != null;
            if (extras1.containsKey("string")) {
                string = extras1.getString("string");
            }
            if (string != null && !string.equals("")) {
                makePermutation(string);
                //System.out.println("makePermutation");
                //declareStartEnds();
                initTree();
                //System.out.println("initializeLettersArray");
                for (int i = 0; i < allPermutations.size(); i++) {
                    /*if (isInDictionary(allPermutations.get(i))) {
                        validWords.add(allPermutations.get(i));
                        //set.add(allPermutations.get(i));
                    }*/
                    if (allPermutations.get(i).charAt(0) == 'ا') {
                        System.out.println("آ" + allPermutations.get(i).substring(1));
                        if (searchInTree("آ" + allPermutations.get(i).substring(1))) {
                            validWords.add("آ" + allPermutations.get(i).substring(1));
                        }
                    }
                    if (searchInTree(allPermutations.get(i))) {
                        validWords.add(allPermutations.get(i));
                    }

                    //validWords.add(i + "-" + dictionary.get(i));
                    //Collections.reverse(validWords);
                    //validWords=validWords.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
                    //Collections.reverse(validWords);
                    //System.out.println(validWords);
                    //set.clear();
                    //set.addAll(validWords);
                    /*for (int j = 0; j < validWords.size(); j++) {
                        set.add(validWords.get(j));
                    }
                    if (validWords.size()>0) {
                        for (int j = validWords.size() - 1; j <= 0; j++) {
                            set.add(validWords.get(j));
                        }
                    }*/
                }

                /*for (int i = 0; i < validWords.size(); i++) {
                    if (validWords.get(i).charAt(0)=='ا'){
                        for (int j = 0; j < dictionary.size(); j++) {
                            if (dictionary.get(j).)
                        }
                    }
                }*/
                /*for (String s : allPermutations) {
                    //count++;
                    if (isInDictionary(s)) {
                        validWords.add(s);
                        set.add(s);
                    }
                }*/
                /*for (int i = 0; i < 32; i++) {
                    System.out.println(lets.get(i) + " " + starts[i] + " " + ends[i]);
                }*/

                /*for (int i = 400000; i < 401000; i++) {
                    System.out.println(dictionary.get(i));

                }*/
                assert validWords != null;
                editedValidWords = new ArrayList<>(validWords);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    validWords = validWords.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
                }
                //Collections.sort(validWords);
                flag = true;
                //Toast.makeText(getApplicationContext(), validWords.size() + " عدد واژه معنادار ساخته شد😎", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(LoadingValidPermutationsActivity.this, ListValidPermutationsActivity.class);
                //intent.putExtra("string", string);
                //intent.putStringArrayListExtra("array", validWords);
                //startActivity(intent);
                //prepareData(string);
                //refreshDisplay();
            }
        }).start();

        //Toast.makeText(this, "🪄در حال ساخت واژگان معنی دار...", Toast.LENGTH_SHORT).show();


        /*if (string != null && !string.equals("")) {
            prepareData(string);
            refreshDisplay();
        }*/
        /*if (validWords != null) {
            Set<String> set = new LinkedHashSet<>(validWords);
            items.addAll(set);
            Toast.makeText(this, validWords.size() + " عدد واژه معنادار ساخته شد😎", Toast.LENGTH_SHORT).show();
            refreshDisplay();
        }*/

        //editTexts.get(2).setText("ر");

    }

    /*public void prepareData() {
//        Toast.makeText(this, items.get(0), Toast.LENGTH_SHORT).show();
//        items.addAll(validWords);
       // Toast.makeText(this,validWords.size()+" عدد واژه معنادار یافت شد😎", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
    }*/
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }*/

    private void refreshDisplay() {
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(ListValidPermutationsActivity.this, items.get(position), Toast.LENGTH_SHORT).show();
            Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("https://abadis.ir/fatofa/" + items.get(position)));
            startActivity(browse);
            //items.set(position, "* " + items.get(position) + " *");
            //Random random=new Random();
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                view.setBackgroundColor(Color.argb(0.4f,random.nextFloat(),random.nextFloat(),random.nextFloat()));
            }*/
        });
        arrayAdapter.notifyDataSetChanged();
    }

    public void initEditTexts() {
        editTexts = new ArrayList<>();
        editTexts.add(this.findViewById(R.id.editTextListValidPermutations1));
        editTexts.add(this.findViewById(R.id.editTextListValidPermutations2));
        editTexts.add(this.findViewById(R.id.editTextListValidPermutations3));
        editTexts.add(this.findViewById(R.id.editTextListValidPermutations4));
        editTexts.add(this.findViewById(R.id.editTextListValidPermutations5));
        editTexts.add(this.findViewById(R.id.editTextListValidPermutations6));
        editTexts.add(this.findViewById(R.id.editTextListValidPermutations7));
        editTexts.add(this.findViewById(R.id.editTextListValidPermutations8));
        editTextNumberOfChars = findViewById(R.id.editTextListValidPermutationsNumber);
        for (int i = 0; i < 8; i++) {
            editTexts.get(i).addTextChangedListener(this);
        }
        editTextNumberOfChars.addTextChangedListener(this);
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
        items.addAll(s);
        //items.addAll(validWords);
        Toast.makeText(this, validWords.size() + " عدد واژه معنادار یافت شد😎", Toast.LENGTH_SHORT).show();
    }

    private boolean isInDictionary(String word) {
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
        /*int index = lets.indexOf(word.charAt(0));
        for (int i = starts[index]; i < ends[index]; i++) {
            if (dictionary.get(i).replace('آ','ا').equals(word)) {
                return true;
            }
        }*/
        for (String s : dictionary) {
            //count++;
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
                /*if (searchInTree(s.substring(x))){
                    validWords.add(s.substring(x));
                }*/
            }

            allPermutations.addAll(hashSet);
            hashSet.clear();
            x++;
            num--;
        }
    }

    /*private static void initializeLettersArray() {
        letters = new ArrayList<>();
        letters.add(new Letter('ا', 1, 46477));
        letters.add(new Letter('ب', 46478, 79377));
        letters.add(new Letter('پ', 79378, 96911));
        letters.add(new Letter('ت', 96912, 121912));
        letters.add(new Letter('ث', 121913, 122596));
        letters.add(new Letter('ج', 122597, 132926));
        letters.add(new Letter('چ', 132927, 138519));
        letters.add(new Letter('ح', 138520, 143472));
        letters.add(new Letter('خ', 143473, 160446));
        letters.add(new Letter('د', 160447, 178641));
        letters.add(new Letter('ذ', 178642, 179499));
        letters.add(new Letter('ر', 179499, 191920));
        letters.add(new Letter('ز', 191921, 198191));
        letters.add(new Letter('ژ', 198192, 199332));
        letters.add(new Letter('س', 199333, 219752));
        letters.add(new Letter('ش', 219753, 230619));
        letters.add(new Letter('ص', 230620, 233567));
        letters.add(new Letter('ض', 233568, 234424));
        letters.add(new Letter('ط', 234425, 236845));
        letters.add(new Letter('ظ', 236846, 237317));
        letters.add(new Letter('ع', 237318, 243657));
        letters.add(new Letter('غ', 243658, 247774));
        letters.add(new Letter('ف', 247775, 259064));
        letters.add(new Letter('ق', 259065, 265993));
        letters.add(new Letter('ک', 265994, 282766));
        letters.add(new Letter('گ', 282767, 295366));
        letters.add(new Letter('ل', 295367, 301521));
        letters.add(new Letter('م', 301522, 343695));
        letters.add(new Letter('ن', 343696, 364244));
        letters.add(new Letter('ه', 364245, 371808));
        letters.add(new Letter('و', 371809, 379074));
        letters.add(new Letter('ی', 379075, 382504));
    }*/
    private static void initializeLettersArray() {
        //char[] lets=new char[31];
        lets = new ArrayList<>();
        lets.add('ا');
        lets.add('ب');
        lets.add('پ');
        lets.add('ت');
        lets.add('ث');
        lets.add('ج');
        lets.add('چ');
        lets.add('ح');
        lets.add('خ');
        lets.add('د');
        lets.add('ذ');
        lets.add('ر');
        lets.add('ز');
        lets.add('ژ');
        lets.add('س');
        lets.add('ش');
        lets.add('ص');
        lets.add('ض');
        lets.add('ط');
        lets.add('ظ');
        lets.add('ع');
        lets.add('غ');
        lets.add('ف');
        lets.add('ق');
        lets.add('ک');
        lets.add('گ');
        lets.add('ل');
        lets.add('م');
        lets.add('ن');
        lets.add('و');
        lets.add('ه');
        lets.add('ی');



        /*letters = new ArrayList<>();
        letters.add(new Letter('ا', 0,dictionary));
        letters.add(new Letter('ب', 46478, 79377));
        letters.add(new Letter('پ', 79378, 96911));
        letters.add(new Letter('ت', 96912, 121912));
        letters.add(new Letter('ث', 121913, 122596));
        letters.add(new Letter('ج', 122597, 132926));
        letters.add(new Letter('چ', 132927, 138519));
        letters.add(new Letter('ح', 138520, 143472));
        letters.add(new Letter('خ', 143473, 160446));
        letters.add(new Letter('د', 160447, 178641));
        letters.add(new Letter('ذ', 178642, 179499));
        letters.add(new Letter('ر', 179499, 191920));
        letters.add(new Letter('ز', 191921, 198191));
        letters.add(new Letter('ژ', 198192, 199332));
        letters.add(new Letter('س', 199333, 219752));
        letters.add(new Letter('ش', 219753, 230619));
        letters.add(new Letter('ص', 230620, 233567));
        letters.add(new Letter('ض', 233568, 234424));
        letters.add(new Letter('ط', 234425, 236845));
        letters.add(new Letter('ظ', 236846, 237317));
        letters.add(new Letter('ع', 237318, 243657));
        letters.add(new Letter('غ', 243658, 247774));
        letters.add(new Letter('ف', 247775, 259064));
        letters.add(new Letter('ق', 259065, 265993));
        letters.add(new Letter('ک', 265994, 282766));
        letters.add(new Letter('گ', 282767, 295366));
        letters.add(new Letter('ل', 295367, 301521));
        letters.add(new Letter('م', 301522, 343695));
        letters.add(new Letter('ن', 343696, 364244));
        letters.add(new Letter('ه', 364245, 371808));
        letters.add(new Letter('و', 371809, 379074));
        letters.add(new Letter('ی', 379075, 382504));*/
    }

    public void declareStartEnds() {
        starts = new int[32];
        ends = new int[32];
        starts[0] = 0;
        ends[0] = 0;

        while (dictionary.get(ends[0]).charAt(0) == lets.get(0) || dictionary.get(ends[0]).charAt(0) == 'آ') {
            ends[0]++;
        }
        for (int i = 1; i < 32; i++) {
            starts[i] = ends[i - 1];
            ends[i] = starts[i];
            while (dictionary.get(ends[i]).charAt(0) == lets.get(i)) {
                ends[i]++;
            }
        }
    }

    private static void findWordsWith(char ch, int index) {
        ArrayList<String> temp = new ArrayList<>();
        for (String allPermutation : editedValidWords) {
            if (index < allPermutation.length()) {
                if (allPermutation.charAt(index) == ch) {
                    temp.add(allPermutation);
                    //System.out.println(allPermutation);
                }
            }
        }
        editedValidWords.clear();
        editedValidWords.addAll(temp);
    }

    private void initDictionary() {
        dictionary = new ArrayList<>();
        String string = "";
        InputStream is;
        BufferedReader reader;
        is = this.getResources().openRawResource(R.raw.edited_words);
        reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                string = reader.readLine();
                if ((string) == null) break;
                if (string.charAt(0) == 'ء') {
                    string = string.substring(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
            if (!string.equals("") && string.length() < 15 && string.charAt(0) != 'ئ') {
                dictionary.add(string);
            }
        }
        System.out.println("dictionary = " + dictionary.size());


        /*is = this.getResources().openRawResource(R.raw.distinct_words);
        reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                string = reader.readLine();
                if ((string) == null) break;
                if (string.charAt(0) == 'ء') {
                    string = string.substring(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
            assert string != null;
            if (!string.equals("") && string.length() < 15 && string.charAt(0) != 'ئ') {
                dictionary.add(string);
            }
        }*/


        is = this.getResources().openRawResource(R.raw.empty);
        reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                string = reader.readLine();
                if ((string) == null) break;
                if (string.charAt(0) == 'ء') {
                    string = string.substring(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
            assert string != null;
            if (!string.equals("") && string.length() < 15 && string.charAt(0) != 'ئ') {
                dictionary.add(string/*.replace('آ','ا')*/);
            }
        }



        /*is = this.getResources().openRawResource(R.raw.big);
        reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                string = reader.readLine();
                if ((string) == null) break;
                if (string.length()>0 && string.charAt(0) == 'ء') {
                    string = string.substring(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
            assert string != null;
            if (!string.equals("") && !string.equals("\n") && string.length() < 15 && string.charAt(0) != 'ئ') {
                dictionary.add(string);
            }
        }*/


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dictionary = dictionary.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        }
        Collections.sort(dictionary);
        //write();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        editedValidWords.clear();
        editedValidWords.addAll(validWords);
        ArrayList<String> temp = new ArrayList<>();
        int numberOfChars = 0;
        if (!editTextNumberOfChars.getText().toString().equals("")) {
            numberOfChars = Integer.parseInt(editTextNumberOfChars.getText().toString());
        }
        if (numberOfChars == 0) {
            for (int i = 0; i < 8; i++) {
                if (!editTexts.get(i).getText().toString().equals("")) {
                    findWordsWith(editTexts.get(i).getText().toString().charAt(0), i);
                }
            }
        } else {
            for (int i = 0; i < editedValidWords.size(); i++) {
                if (editedValidWords.get(i).length() == numberOfChars) {
                    temp.add(editedValidWords.get(i));
                }
            }
            editedValidWords.clear();
            editedValidWords.addAll(temp);
            System.out.println(editedValidWords);
            for (int i = 0; i < 8; i++) {
                if (!editTexts.get(i).getText().toString().equals("")) {
                    findWordsWith(editTexts.get(i).getText().toString().charAt(0), i);
                }
            }
        }
        // Set<String> set = new LinkedHashSet<>(editedValidWords);
        items.clear();
        items.addAll(editedValidWords);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*public void write() {
        try {
            //OutputStream os = this.getResources().openRawResource(R.raw.empty);
           // FileWriter myWriter = new FileWriter(is);
            for (int i = 0; i < dictionary.size(); i++) {
               // os.write(dictionary.get(i)+"\n");
            }
            //myWriter.write("Files in Java might be tricky, but it is fun enough!");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }*/
    public void initTree() {
        for (int i = 0; i < dictionary.size(); i++) {
            Node tempNode = node;
            for (int j = 0; j < dictionary.get(i).length(); j++) {
                char ch = dictionary.get(i).charAt(j);
                if (findNode(tempNode, ch) == null) {
                    tempNode.children.put(ch, new Node(ch));
                }
                tempNode = findNode(tempNode, ch);
            }
            tempNode.value = dictionary.get(i);
        }
    }

    public boolean searchInTree(String word) {
        Node tempNode = node;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (findNode(tempNode, ch) == null) {
                return false;
            } else {
                tempNode = findNode(tempNode, ch);
            }
        }
        if (tempNode.value == null) {
            return false;
        }
        return tempNode.value.equals(word);
    }

    public Node findNode(Node node, char ch) {
        return node.children.get(ch);
    }


}

class Letter {
    private char letter;
    private int start, end;

    char getLetter() {
        return letter;
    }

    int getStart() {
        return start;
    }

    int getEnd() {
        return end;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    Letter(char letter, int start, int end) {
        this.letter = letter;
        this.start = start;
        this.end = end;
    }

    Letter(char letter, int start, ArrayList<String> dictionary) {
        this.letter = letter;
        int end = start;
        while (dictionary.get(end).charAt(0) == letter) {
            end++;
        }
        this.start = start;
        this.end = end;
    }

}

class Node {
    char name;
    String value;
    HashMap<Character, Node> children;

    public Node() {
        this.children = new HashMap<>();
    }

    public Node(char name) {
        this.children = new HashMap<>();
        this.name = name;
    }
}