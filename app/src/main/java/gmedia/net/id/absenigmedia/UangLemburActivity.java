package gmedia.net.id.absenigmedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Adapter.ExpandableListViewAdapter;
import gmedia.net.id.absenigmedia.Model.Child;
import gmedia.net.id.absenigmedia.Model.Parent;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bayu on 11/01/2018.
 */

public class UangLemburActivity extends AppCompatActivity {
    ExpandableListViewAdapter listViewAdapter;
    ExpandableListView listView;
    List<Parent> header;
    HashMap<String, List<Child>> child;
    public static ImageView dropdown;
    String url = "http://appsmg.gmedia.net.id:2180/hrd/rest/uang";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(gmedia.net.id.absenigmedia.R.layout.uang_lembur);
        dropdown = findViewById(gmedia.net.id.absenigmedia.R.id.dropdown);
        listView = (ExpandableListView) findViewById(gmedia.net.id.absenigmedia.R.id.expanded_menu);
//        prepareData();
        parsingdata();
    }

    private void prepareData() {
        header = new ArrayList<Parent>();
        child = new HashMap<String, List<Child>>();
        header.add(new Parent("Minggu Ke-1","21/01/2017","24/01/2017","Rp 40.000"));
        header.add(new Parent("Minggu Ke-2","25/01/2017","01/02/2017","-"));
        header.add(new Parent("Minggu Ke-3","02/02/2017","08/02/2017","-"));
        header.add(new Parent("Minggu Ke-4","09/02/2017","15/02/2017","Rp 40.000"));
        header.add(new Parent("Minggu Ke-5","09/02/2017","15/02/2017","-"));
        List<Child>childminggu1=new ArrayList<>();
        childminggu1.add(new Child("Sen, 21/01/2017","-"));
        childminggu1.add(new Child("Sel, 22/01/2017","-"));
        childminggu1.add(new Child("Sel, 23/01/2017","Rp 40.000"));
        childminggu1.add(new Child("Sel, 24/01/2017","-"));
        childminggu1.add(new Child("Sel, 25/01/2017","-"));
        childminggu1.add(new Child("Sel, 26/01/2017","-"));
        childminggu1.add(new Child("Sel, 27/01/2017","-"));
        List<Child>childminggu2=new ArrayList<>();
        childminggu2.add(new Child("Sen, 21/01/2017","-"));
        childminggu2.add(new Child("Sel, 22/01/2017","-"));
        childminggu2.add(new Child("Sel, 23/01/2017","Rp 40.000"));
        childminggu2.add(new Child("Sel, 24/01/2017","-"));
        childminggu2.add(new Child("Sel, 25/01/2017","-"));
        childminggu2.add(new Child("Sel, 26/01/2017","-"));
        childminggu2.add(new Child("Sel, 27/01/2017","-"));
        List<Child>childminggu3=new ArrayList<>();
        childminggu3.add(new Child("Sen, 21/01/2017","-"));
        childminggu3.add(new Child("Sel, 22/01/2017","-"));
        childminggu3.add(new Child("Sel, 23/01/2017","Rp 40.000"));
        childminggu3.add(new Child("Sel, 24/01/2017","-"));
        childminggu3.add(new Child("Sel, 25/01/2017","-"));
        childminggu3.add(new Child("Sel, 26/01/2017","-"));
        childminggu3.add(new Child("Sel, 27/01/2017","-"));
        List<Child>childminggu4=new ArrayList<>();
        childminggu4.add(new Child("Sen, 21/01/2017","-"));
        childminggu4.add(new Child("Sel, 22/01/2017","-"));
        childminggu4.add(new Child("Sel, 23/01/2017","Rp 40.000"));
        childminggu4.add(new Child("Sel, 24/01/2017","-"));
        childminggu4.add(new Child("Sel, 25/01/2017","-"));
        childminggu4.add(new Child("Sel, 26/01/2017","-"));
        childminggu4.add(new Child("Sel, 27/01/2017","-"));
        List<Child>childminggu5=new ArrayList<>();
        childminggu5.add(new Child("Sen, 21/01/2017","-"));
        childminggu5.add(new Child("Sel, 22/01/2017","-"));
        childminggu5.add(new Child("Sel, 23/01/2017","Rp 40.000"));
        childminggu5.add(new Child("Sel, 24/01/2017","-"));
        childminggu5.add(new Child("Sel, 25/01/2017","-"));
        childminggu5.add(new Child("Sel, 26/01/2017","-"));
        childminggu5.add(new Child("Sel, 27/01/2017","-"));

        child.put(header.get(0).getMinggu(),childminggu1);
        child.put(header.get(1).getMinggu(),childminggu2);
        child.put(header.get(2).getMinggu(),childminggu3);
        child.put(header.get(3).getMinggu(),childminggu4);
        child.put(header.get(4).getMinggu(),childminggu5);
    }
    private void parsingdata() {
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("type", "lembur");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(UangLemburActivity.this, jBody, "POST", url, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                header = new ArrayList<Parent>();
                child = new HashMap<String, List<Child>>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONArray array = object.getJSONArray("response");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject parent = array.getJSONObject(i);
                            header.add(new Parent(
                                    parent.getString("minggu"),
                                    parent.getString("tgl_awal"),
                                    parent.getString("tgl_akhir"),
                                    parent.getString("nominal")));
                            JSONArray cilikan = parent.getJSONArray("detail");
                            if (cilikan.length() > 0) {
                                List<Child> childminggu1 = new ArrayList<>();
                                for (int a = 0; a < cilikan.length(); a++) {
                                    JSONObject isicilikan = cilikan.getJSONObject(a);
                                    childminggu1.add(new Child(
                                            isicilikan.getString("hari"),
                                            isicilikan.getString("nominal")
                                    ));


                                }
                                child.put(header.get(i).getMinggu(), childminggu1);

                            }

                        }
                        listViewAdapter = new ExpandableListViewAdapter(UangLemburActivity.this, header, child);
                        listView.setAdapter(listViewAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(getApplicationContext(), "Error Brooh", Toast.LENGTH_LONG).show();

            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(UangLemburActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
