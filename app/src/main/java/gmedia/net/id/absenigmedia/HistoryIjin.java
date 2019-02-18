package gmedia.net.id.absenigmedia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Adapter.ListAdapterHistoryIjinKeluarKantor;
import gmedia.net.id.absenigmedia.Adapter.ListAdapterHistoryIjinPulangAwal;
import gmedia.net.id.absenigmedia.Model.CustomHistoryIjinKeluarKantor;
import gmedia.net.id.absenigmedia.Model.CustomHistoryIjinPulangAwal;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bayu on 21/02/2018.
 */

public class HistoryIjin extends AppCompatActivity {
    private ListView listViewHistoryIjinPulangAwal, listViewHistoryIjinKeluarKantor;
    private ListAdapterHistoryIjinPulangAwal adapterHistoryIjinPulangAwal;
    private ListAdapterHistoryIjinKeluarKantor adapterHistoryIjinKeluarKantor;
    /*public final String tanggal[] =
            {
                    "Rab, 21/01/2017",
                    "Kam, 22/01/2017",
                    "Rab, 23/01/2017",
                    "Kam, 24/01/2017",
                    "Jum, 25/01/2017",
                    "Rab, 23/01/2017",
                    "Rab, 21/01/2017",
                    "Kam, 22/01/2017",
                    "Rab, 23/01/2017",
                    "Kam, 24/01/2017",
                    "Jum, 25/01/2017",
                    "Rab, 23/01/2017"
            };
    public final String alasan[] =
            {
                    "Sakit",
                    "Ijin",
                    "Sakit",
                    "Ijin",
                    "Sakit",
                    "Ijin",
                    "Sakit",
                    "Ijin",
                    "Sakit",
                    "Ijin",
                    "Sakit",
                    "Ijin"
            };
    public final String status[] =
            {
                    "Accept",
                    "Ignore",
                    "Accept",
                    "Ignore",
                    "Accept",
                    "Ignore",
                    "Accept",
                    "Ignore",
                    "Accept",
                    "Ignore",
                    "Accept",
                    "Ignore"
            };
    public final String tanggal2[] =
            {
                    "Jum, 25/01/2017",
                    "Rab, 23/01/2017",
                    "Rab, 21/01/2017",
                    "Kam, 22/01/2017",
                    "Rab, 23/01/2017",
                    "Kam, 24/01/2017",
                    "Rab, 21/01/2017",
                    "Kam, 22/01/2017",
                    "Rab, 23/01/2017",
                    "Kam, 24/01/2017",
                    "Jum, 25/01/2017",
                    "Rab, 23/01/2017"
            };
    public final String alasan2[] =
            {
                    "Ijin",
                    "Sakit",
                    "Sakit",
                    "Ijin",
                    "Sakit",
                    "Ijin",
                    "Sakit",
                    "Ijin",
                    "Sakit",
                    "Ijin",
                    "Sakit",
                    "Ijin"
            };
    public final String status2[] =
            {
                    "Accept",
                    "Ignore",
                    "Accept",
                    "Ignore",
                    "Accept",
                    "Ignore",
                    "Accept",
                    "Ignore",
                    "Accept",
                    "Ignore",
                    "Accept",
                    "Ignore"
            };*/
    private Spinner dropdownMenu;
    private LinearLayout pulangAwal, keluarKantor;
    private RelativeLayout tglMulai, tglSelesai, kirim;
    private TextView textMulai, textSelesai;
    private int posisi;
    private List<CustomHistoryIjinPulangAwal> historyIjinPulangAwal;
    private List<CustomHistoryIjinKeluarKantor> historyIjinKeluarKantor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(gmedia.net.id.absenigmedia.R.layout.history_ijin);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(gmedia.net.id.absenigmedia.R.color.statusbar));
        }
        RelativeLayout back = findViewById(gmedia.net.id.absenigmedia.R.id.backHistoryIjin);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tglMulai = (RelativeLayout) findViewById(gmedia.net.id.absenigmedia.R.id.layoutTglMulaiHistoryIjin);
        tglSelesai = (RelativeLayout) findViewById(gmedia.net.id.absenigmedia.R.id.layoutTglSelesaiHistoryIjin);
        kirim = (RelativeLayout) findViewById(gmedia.net.id.absenigmedia.R.id.layoutkirimhistoryijin);
        textMulai = (TextView) findViewById(gmedia.net.id.absenigmedia.R.id.textTglMulaiHistoryIjin);
        textSelesai = (TextView) findViewById(gmedia.net.id.absenigmedia.R.id.textTglSelesaiHistoryIjin);
        listViewHistoryIjinPulangAwal = (ListView) findViewById(gmedia.net.id.absenigmedia.R.id.listHistoryIjinPulangAwal);
//        ArrayList<CustomHistoryIjinPulangAwal> historyIjinPulangAwal = isiListPulangAwal();
        listViewHistoryIjinKeluarKantor = (ListView) findViewById(gmedia.net.id.absenigmedia.R.id.listHistoryIjinKeluarKantor);
//        ArrayList<CustomHistoryIjinKeluarKantor> historyIjinKeluarKantor = isiListKeluarKantor();
        pulangAwal = findViewById(gmedia.net.id.absenigmedia.R.id.layoutListPulangAwal);
        keluarKantor = findViewById(gmedia.net.id.absenigmedia.R.id.layoutListKeluarKantor);
        dropdownMenu = findViewById(gmedia.net.id.absenigmedia.R.id.menudropdownHistoryIjin);
        dropdownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posisi = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] items = new String[]{"Jenis Ijin ", "Ijin Pulang Awal", "Ijin Keluar Kantor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HistoryIjin.this, android.R.layout.simple_spinner_item, items) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    dropdownMenu.setAlpha(0.5f);
                    return false;
                } else {
                    dropdownMenu.setAlpha(1);
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.BLACK);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownMenu.setAdapter(adapter);
        tglMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar customDate = java.util.Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        customDate.set(java.util.Calendar.YEAR, year);
                        customDate.set(java.util.Calendar.MONTH, month);
                        customDate.set(java.util.Calendar.DATE, dayOfMonth);
                        SimpleDateFormat sdFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        textMulai.setText(sdFormat.format(customDate.getTime()));
                        textMulai.setAlpha(1);

                    }
                };
                new DatePickerDialog(HistoryIjin.this, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
            }
        });
        tglSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar customDate = java.util.Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        customDate.set(java.util.Calendar.YEAR, year);
                        customDate.set(java.util.Calendar.MONTH, month);
                        customDate.set(java.util.Calendar.DATE, dayOfMonth);
                        SimpleDateFormat sdFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        textSelesai.setText(sdFormat.format(customDate.getTime()));
                        textSelesai.setAlpha(1);

                    }
                };
                new DatePickerDialog(HistoryIjin.this, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
            }
        });
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posisi == 0) {
                    Toast.makeText(getApplicationContext(), "Silahkan Pilih Jenis Ijin Anda", Toast.LENGTH_LONG).show();
                }
                if (posisi == 1) {
                    if (textMulai.getText().toString().equals("Tanggal Mulai")) {
                        Toast.makeText(getApplicationContext(), "Silahkan Isi Tanggal Mulai", Toast.LENGTH_LONG).show();
                    } else if (textSelesai.getText().toString().equals("Tanggal Selesai")) {
                        Toast.makeText(getApplicationContext(), "Silahkan Isi Tanggal Selesai", Toast.LENGTH_LONG).show();
                    } else {
                        prepareViewIjinPulang();
                    }
                } else if (posisi == 2) {
                    if (textMulai.getText().toString().equals("Tanggal Mulai")) {
                        Toast.makeText(getApplicationContext(), "Silahkan Isi Tanggal Mulai", Toast.LENGTH_LONG).show();
                    } else if (textSelesai.getText().toString().equals("Tanggal Selesai")) {
                        Toast.makeText(getApplicationContext(), "Silahkan Isi Tanggal Selesai", Toast.LENGTH_LONG).show();
                    } else {
                        prepareViewKeluarKantor();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.isIjin = false;
        Intent intent = new Intent(HistoryIjin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void prepareViewIjinPulang() {
        final Dialog dialog = new Dialog(HistoryIjin.this);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("startdate", textMulai.getText());
            jBody.put("enddate", textSelesai.getText());
        } catch (JSONException e) {
            e.printStackTrace();

        }
        ApiVolley request = new ApiVolley(HistoryIjin.this, jBody, "POST", URL.urlViewPulangAwal, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                keluarKantor.setVisibility(View.GONE);
                historyIjinPulangAwal = new ArrayList<CustomHistoryIjinPulangAwal>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("0")){
                        Toast.makeText(getApplicationContext(),"Tidak Ada Data",Toast.LENGTH_LONG).show();
                    }
                    else if (status.equals("1")) {
                        JSONArray array = object.getJSONArray("response");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject isi = array.getJSONObject(i);
                            historyIjinPulangAwal.add(new CustomHistoryIjinPulangAwal(
                                    isi.getString("tgl"),
                                    isi.getString("jam"),
                                    isi.getString("alasan"),
                                    isi.getString("status")
                            ));
                        }

                        listViewHistoryIjinPulangAwal.setAdapter(null);
                        adapterHistoryIjinPulangAwal = new ListAdapterHistoryIjinPulangAwal(HistoryIjin.this, historyIjinPulangAwal);
                        listViewHistoryIjinPulangAwal.setAdapter(adapterHistoryIjinPulangAwal);
                        pulangAwal.setVisibility(View.VISIBLE);
                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void prepareViewKeluarKantor() {
        final Dialog dialog = new Dialog(HistoryIjin.this);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("startdate", textMulai.getText());
            jBody.put("enddate", textSelesai.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(HistoryIjin.this, jBody, "POST", URL.urlViewKeluarKantor, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                pulangAwal.setVisibility(View.GONE);
                historyIjinKeluarKantor = new ArrayList<CustomHistoryIjinKeluarKantor>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("0")){
                        Toast.makeText(getApplicationContext(),"Tidak Ada Data",Toast.LENGTH_LONG).show();
                    }
                    else if (status.equals("1")) {
                        JSONArray array = object.getJSONArray("response");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject isi = array.getJSONObject(i);
                            historyIjinKeluarKantor.add(new CustomHistoryIjinKeluarKantor(
                                    isi.getString("tgl"),
                                    isi.getString("dari"),
                                    isi.getString("sampai"),
                                    isi.getString("alasan"),
                                    isi.getString("status")
                            ));
                        }

                        listViewHistoryIjinKeluarKantor.setAdapter(null);
                        adapterHistoryIjinKeluarKantor = new ListAdapterHistoryIjinKeluarKantor(HistoryIjin.this, historyIjinKeluarKantor);
                        listViewHistoryIjinKeluarKantor.setAdapter(adapterHistoryIjinKeluarKantor);
                        keluarKantor.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }
   /* private ArrayList<CustomHistoryIjinKeluarKantor> isiListKeluarKantor() {
        ArrayList<CustomHistoryIjinKeluarKantor> rvData = new ArrayList<>();
        int nomer = 0;
        for (int i = 0; i < tanggal.length; i++) {
            CustomHistoryIjinKeluarKantor custom = new CustomHistoryIjinKeluarKantor();
            nomer++;
            custom.setNo("" + nomer);
            custom.setTanggal(tanggal2[i]);
            custom.setAlasan(alasan2[i]);
            custom.setStatus(status2[i]);
            rvData.add(custom);
        }
        return rvData;
    }
    private ArrayList<CustomHistoryIjinPulangAwal> isiListPulangAwal() {
        ArrayList<CustomHistoryIjinPulangAwal> rvData = new ArrayList<>();
        int nomer = 0;
        for (int i = 0; i < tanggal.length; i++) {
            CustomHistoryIjinPulangAwal custom = new CustomHistoryIjinPulangAwal();
            nomer++;
            custom.setNo("" + nomer);
            custom.setTanggal(tanggal[i]);
            custom.setAlasan(alasan[i]);
            custom.setStatus(status[i]);
            rvData.add(custom);
        }
        return rvData;
    }*/
}
