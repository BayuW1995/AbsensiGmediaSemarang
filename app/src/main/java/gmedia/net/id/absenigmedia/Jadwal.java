package gmedia.net.id.absenigmedia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Adapter.ListAdapterJadwal;
import gmedia.net.id.absenigmedia.Model.CustomJadwal;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bayu on 27/02/2018.
 */

public class Jadwal extends Fragment {
    private Context context;
    //    ListAdapterExpandJadwal listViewAdapter;
    ListView listView;
    private List<CustomJadwal> jadwal;
    //    HashMap<String, List<ChildJadwal>> child;
    //    private ListView listView;
    private ListAdapterJadwal adapter;
    private static TextView tglAwal, tglAkhir;
    private RelativeLayout dataJadwal, proses;
    public final String tanggal[] =
            {
                    "Rab, 21/01/2017",
                    "Kam, 22/01/2017",
                    "Rab, 23/01/2017",
                    "Kam, 24/01/2017",
                    "Jum, 25/01/2017",
                    "Rab, 23/01/2017"
            };
    public final String shift[] =
            {
                    "R",
                    "PM",
                    "R",
                    "PM",
                    "R",
                    "PM"
            };
    public final String jammasuk[] =
            {
                    "08.20",
                    "08.30",
                    "08.10",
                    "08.15",
                    "08.29",
                    "08.31"
            };
    public final String jamkeluar[] =
            {
                    "17.30",
                    "17.40",
                    "17.35",
                    "17.45",
                    "18.00",
                    "18.30"
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(gmedia.net.id.absenigmedia.R.layout.jadwal, viewGroup, false);
        context = getContext();
        RelativeLayout tombolAwal = view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutAwalJadwal);
        RelativeLayout tombolAkhir = view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutAkhirJadwal);
        tglAwal = view.findViewById(gmedia.net.id.absenigmedia.R.id.tglAwalJadwal);
        tglAkhir = view.findViewById(gmedia.net.id.absenigmedia.R.id.tglAkhirJadwal);
        dataJadwal = view.findViewById(gmedia.net.id.absenigmedia.R.id.dataJadwal);
        listView = view.findViewById(gmedia.net.id.absenigmedia.R.id.listJadwalExpand);
        proses = view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutProsesJadwal);
        tombolAwal.setOnClickListener(new View.OnClickListener() {
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
                        tglAwal.setText(sdFormat.format(customDate.getTime()));
                        tglAwal.setAlpha(1);

                    }
                };
                new DatePickerDialog(context, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
            }
        });
        tombolAkhir.setOnClickListener(new View.OnClickListener() {
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
                        tglAkhir.setText(sdFormat.format(customDate.getTime()));
                        tglAkhir.setAlpha(1);

                    }
                };
                new DatePickerDialog(context, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
            }
        });
        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tglAwal.getText().toString().equals("Tanggal Mulai")) {
                    Toast.makeText(context, "Silahkan Pilih Tanggal Mulai", Toast.LENGTH_LONG).show();
                    return;
                } else if (tglAkhir.getText().toString().equals("Tanggal Selesai")) {
                    Toast.makeText(context, "Silahkan Pilih Tanggal Akhir", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    prepareData();
                }
            }
        });
        return view;
    }

    private void prepareData() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("startdate", tglAwal.getText());
            jBody.put("enddate", tglAkhir.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlJadwal, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                jadwal = new ArrayList<CustomJadwal>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    String message = object.getJSONObject("metadata").getString("message");
                    if (status.equals("1")) {
                        JSONObject response = object.getJSONObject("response");
                        JSONArray detail = response.getJSONArray("jadwal");
                        for (int i = 0; i < detail.length(); i++) {
                            JSONObject isiDetail = detail.getJSONObject(i);
                            jadwal.add(new CustomJadwal(
                                    isiDetail.getString("tanggal"),
                                    isiDetail.getString("shift"),
                                    isiDetail.getString("jam_masuk"),
                                    isiDetail.getString("jam_pulang"),
                                    isiDetail.getString("flag_libur")
                            ));
                        }
                        listView.setAdapter(null);
                        adapter = new ListAdapterJadwal(context, jadwal);
                        listView.setAdapter(adapter);
                        dataJadwal.setVisibility(View.VISIBLE);
                    }
                    else if (status.equals("0")){
                        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                dialog.dismiss();
                Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }
}

    /*private void prepareDataExpand() {
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("startdate", tglAwal.getText());
            jBody.put("enddate", tglAkhir.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlJadwal, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                jadwal = new ArrayList<CustomJadwal>();
                child = new HashMap<String, List<ChildJadwal>>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONObject response = object.getJSONObject("response");
                        JSONArray isiJadwal = response.getJSONArray("jadwal");
                        double totalnominal = 0;
                        for (int i = 0; i < isiJadwal.length(); i++) {
                            JSONObject parent = isiJadwal.getJSONObject(i);
                            String nominal = parent.getString("nominal");
                            jadwal.add(new CustomJadwal(
                                    parent.getString("tanggal"),
                                    parent.getString("shift"),
                                    parent.getString("jam_masuk"),
                                    parent.getString("jam_pulang")
                            ));
                            List<ChildJadwal> childminggu1 = new ArrayList<>();
                            childminggu1.add(new ChildJadwal(
                                    parent.getString("keterangan")
                            ));
                            child.put(jadwal.get(i).getTanggal(),childminggu1);
                        }
//                        total = pertama+kedua+ketiga+keempat+kelima;
//                        total = total + totalnominal;
                        *//*listViewAdapter = new ListAdapterExpandJadwal(context, jadwal, child);
                        listView.setAdapter(listViewAdapter);
                        dataJadwal.setVisibility(View.VISIBLE);*//*

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(String result) {
                Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }*/



/* private ArrayList<CustomJadwal> isiList() {
        ArrayList<CustomJadwal> rvData = new ArrayList<>();
        for (int i = 0; i < tanggal.length; i++) {
            CustomJadwal custom = new CustomJadwal();
            custom.setTanggal(tanggal[i]);
            custom.setShift(shift[i]);
            custom.setJammasuk(jammasuk[i]);
            custom.setJamkeluar(jamkeluar[i]);
            rvData.add(custom);
        }
        return rvData;
    }*/