package gmedia.net.id.absenigmedia;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Adapter.ListAdapterHistoryCuti;
import gmedia.net.id.absenigmedia.Adapter.RecyclerViewAdapterHistory;
import gmedia.net.id.absenigmedia.Model.CustomHistory;
import gmedia.net.id.absenigmedia.Model.CustomRecycler;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryCuti extends AppCompatActivity {
    private ListView listView;
    private ListAdapterHistoryCuti adapterHistory;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CustomRecycler> dataSet;
    private ArrayList<CustomHistory> history;
    private Fragment fragment;
    private final String no[] =
            {
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7",
                    "8",
                    "9",
                    "10",
                    "11",
                    "12",
                    "13",
                    "14"
            };
    private final String mulaihistory[] =
            {
                    "Jum'at, 23-06-2017",
                    "Rabu, 20-06-2017",
                    "Senin, 27-06-2017",
                    "Minggu, 25-06-2017",
                    "Selasa, 23-06-2017",
                    "Kamis, 22-06-2017",
                    "Sabtu, 21-06-2017",
                    "Kamis, 22-06-2017",
                    "Senin, 20-06-2017",
                    "Selasa, 29-06-2017",
                    "Jumat, 31-06-2017",
                    "Sabtu, 02-06-2017",
                    "Minggu, 21-06-2017",
                    "Rabu, 15-06-2017",
            };
    private final String selesai[] =
            {
                    "Sabtu, 24-06-2017",
                    "Kamis, 21-06-2017",
                    "Senin, 27-06-2017",
                    "Minggu, 25-06-2017",
                    "Selasa, 23-06-2017",
                    "Kamis, 22-06-2017",
                    "Sabtu, 21-06-2017",
                    "Kamis, 22-06-2017",
                    "Senin, 20-06-2017",
                    "Selasa, 29-06-2017",
                    "Jumat, 31-06-2017",
                    "Sabtu, 02-06-2017",
                    "Minggu, 21-06-2017",
                    "Rabu, 15-06-2017"
            };
    private final String keteranganHistory[] =
            {
                    "Acara Keluarga",
                    "Sakit",
                    "Acara Keluarga",
                    "Sakit",
                    "Acara Keluarga",
                    "Sakit",
                    "Acara Keluarga",
                    "Sakit",
                    "Acara Keluarga",
                    "Sakit",
                    "Acara Keluarga",
                    "Sakit",
                    "Acara Keluarga",
                    "Sakit"
            };
    private final String mulai[] =
            {
                    "Selasa, 14-11-2017",
                    "Kamis, 16-11-2017",
                    "Senin, 20-11-2017",
                    "Selasa, 14-11-2017",
                    "Kamis, 16-11-2017",
                    "Senin, 20-11-2017",
                    "Selasa, 14-11-2017",
                    "Kamis, 16-11-2017",
                    "Senin, 20-11-2017"
            };
    private final String sampai[] =
            {
                    "Rabu, 15-11-2017",
                    "Jum'at, 17-11-2017",
                    "Selasa, 21-11-2017",
                    "Rabu, 15-11-2017",
                    "Jum'at, 17-11-2017",
                    "Selasa, 21-11-2017",
                    "Rabu, 15-11-2017",
                    "Jum'at, 17-11-2017",
                    "Selasa, 21-11-2017"
            };
    private final String keterangan[] =
            {
                    "Acara Keluarga",
                    "Sakit",
                    "Sakit",
                    "Acara Keluarga",
                    "Sakit",
                    "Sakit",
                    "Acara Keluarga",
                    "Sakit",
                    "Sakit"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(gmedia.net.id.absenigmedia.R.layout.historycuti);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(gmedia.net.id.absenigmedia.R.color.statusbar));
        }
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvView = findViewById(gmedia.net.id.absenigmedia.R.id.rv_main);
        rvView.setLayoutManager(layoutManager);
        parsingDataOnProgress();

//        ArrayList<CustomRecycler> dataSet = prepareData();
        listView = findViewById(gmedia.net.id.absenigmedia.R.id.lvCuti);
//        ArrayList<CustomHistory> historycuti = prepareDataHistory();
        parsingDataCutiHistory();
        LinearLayout back = findViewById(gmedia.net.id.absenigmedia.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void parsingDataOnProgress() {
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("flag", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(HistoryCuti.this, jBody, "POST", URL.urlHistory, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dataSet = new ArrayList<CustomRecycler>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONArray array = object.getJSONArray("response");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject onProgress = array.getJSONObject(i);
                            dataSet.add(new CustomRecycler(
                                    onProgress.getString("id_cuti"),
                                    onProgress.getString("mulai"),
                                    onProgress.getString("selesai"),
                                    onProgress.getString("alasan")
                            ));
                        }
                        rvView.setAdapter(null);
                        adapter = new RecyclerViewAdapterHistory(HistoryCuti.this, dataSet);
                        rvView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void parsingDataCutiHistory() {
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("flag", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(HistoryCuti.this, jBody, "POST", URL.urlHistory, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                history = new ArrayList<CustomHistory>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONArray array = object.getJSONArray("response");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject cutiHistory = array.getJSONObject(i);
                            history.add(new CustomHistory(
                                    cutiHistory.getString("status"),
                                    cutiHistory.getString("mulai"),
                                    cutiHistory.getString("selesai"),
                                    cutiHistory.getString("alasan")
                            ));
                        }
                        listView.setAdapter(null);
                        adapterHistory = new ListAdapterHistoryCuti(HistoryCuti.this, history);
                        listView.setAdapter(adapterHistory);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.isCuti = false;
        Intent intent = new Intent(HistoryCuti.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    /* private ArrayList<CustomHistory> prepareDataHistory() {
        ArrayList<CustomHistory> historycuti = new ArrayList<>();
        for (int a = 0; a < no.length; a++) {
            CustomHistory custom = new CustomHistory();
            custom.setNo(no[a]);
            custom.setMulai(mulaihistory[a]);
            custom.setSelesai(selesai[a]);
            custom.setKeteranganHistory(keteranganHistory[a]);
            historycuti.add(custom);
        }
        return historycuti;
    }*/

    /*private ArrayList<CustomRecycler> prepareData() {
        ArrayList<CustomRecycler> rvData = new ArrayList<>();
        for (int i = 0; i < mulai.length; i++) {
            CustomRecycler custom = new CustomRecycler();
            custom.setMulai(mulai[i]);
            custom.setSampai(sampai[i]);
            custom.setKeterangan(keterangan[i]);
            rvData.add(custom);
        }
        return rvData;
    }*/
}
