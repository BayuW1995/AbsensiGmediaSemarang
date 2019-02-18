package gmedia.net.id.absenigmedia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Adapter.ListAdapterTotalTerlambat;
import gmedia.net.id.absenigmedia.Model.CustomTotalTerlambat;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Bayu on 15/02/2018.
 */

public class TotalTerlambat extends AppCompatActivity {
    private ListView listView;
    private ListAdapterTotalTerlambat adapter;
    private TextView tanggalPeriode, hari, menit;
    public final String tanggal[] =
            {
                    "Rab, 21/01/2017",
                    "Kam, 22/01/2017",
                    "Rab, 23/01/2017",
                    "Kam, 24/01/2017",
                    "Jum, 25/01/2017",
                    "Sab, 26/01/2017",
                    "Ming, 27/01/2017",
                    "Sen, 28/01/2017",
                    "sel, 29/01/2017",
                    "Rab, 30/01/2017",
                    "Kam, 31/01/2017",
                    "Jum, 01/01/2017",
                    "Sab, 02/01/2017"
            };
    public final String jammasuk[] =
            {
                    "08.10",
                    "-",
                    "08.15",
                    "08.00",
                    "09.00",
                    "-",
                    "-",
                    "07.15",
                    "07.15",
                    "07.15",
                    "-",
                    "-",
                    "-"
            };
    public final String scanmasuk[] =
            {
                    "17.40",
                    "-",
                    "18.20",
                    "17.45",
                    "14.00",
                    "-",
                    "-",
                    "14.00",
                    "14.00",
                    "14.00",
                    "-",
                    "-",
                    "-"
            };
    public final String telat[] =
            {
                    "17.40",
                    "-",
                    "18.20",
                    "17.45",
                    "14.00",
                    "-",
                    "-",
                    "14.00",
                    "14.00",
                    "14.00",
                    "-",
                    "-",
                    "-"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(gmedia.net.id.absenigmedia.R.layout.total_terlambat);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(gmedia.net.id.absenigmedia.R.color.statusbar));
        }
        tanggalPeriode = findViewById(gmedia.net.id.absenigmedia.R.id.tanggalPeriode);
        hari = findViewById(gmedia.net.id.absenigmedia.R.id.totalHari);
        menit = findViewById(gmedia.net.id.absenigmedia.R.id.totalMenit);
        RelativeLayout back = findViewById(gmedia.net.id.absenigmedia.R.id.backTotalTerlambat);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        prepareDataTotalTerlambat();
        prepareDataDetailTotalTerlambat();
        listView = (ListView) findViewById(gmedia.net.id.absenigmedia.R.id.listTotalTerlambat);
    }

    private void prepareDataTotalTerlambat() {
        ApiVolley request = new ApiVolley(TotalTerlambat.this, new JSONObject(), "POST", URL.urlTotalTerlambat, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONObject totalTerlambat = object.getJSONObject("response");
                        tanggalPeriode.setText(totalTerlambat.getString("periode"));
                        hari.setText(totalTerlambat.getString("total_hari"));
                        menit.setText(totalTerlambat.getString("total_menit"));
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
    private void prepareDataDetailTotalTerlambat() {
        ApiVolley request = new ApiVolley(TotalTerlambat.this, new JSONObject(), "POST", URL.urlTotalTerlambat, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                ArrayList<CustomTotalTerlambat>totalTerlambat = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONObject response = object.getJSONObject("response");
                        JSONArray detail = response.getJSONArray("detail");
                        for (int i=0;i<detail.length();i++){
                            JSONObject onProgress = detail.getJSONObject(i);
                            totalTerlambat.add(new CustomTotalTerlambat(
                                    onProgress.getString("tanggal"),
                                    onProgress.getString("jam_masuk"),
                                    onProgress.getString("scan_masuk"),
                                    onProgress.getString("total_menit")
                            ));
                        }
                        listView.setAdapter(null);
                        adapter = new ListAdapterTotalTerlambat(TotalTerlambat.this, totalTerlambat);
                        listView.setAdapter(adapter);
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
    /*private ArrayList<CustomTotalTerlambat> isiList() {
        ArrayList<CustomTotalTerlambat> rvData = new ArrayList<>();
        for (int i = 0; i < tanggal.length; i++) {
            CustomTotalTerlambat custom = new CustomTotalTerlambat();
            custom.setTanggal(tanggal[i]);
            custom.setJammasuk(jammasuk[i]);
            custom.setScanmasuk(scanmasuk[i]);
            custom.setTelat(telat[i]);
            rvData.add(custom);
        }
        return rvData;
    }*/
}
