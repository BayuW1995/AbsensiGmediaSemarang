package gmedia.net.id.absenigmedia;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import gmedia.net.id.absenigmedia.Adapter.ListAdapterApproval;
import gmedia.net.id.absenigmedia.Model.CustomApproval;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

public class Approval extends Fragment {
    private Context context;
    private ListAdapterApproval adapterApproval;
    private ArrayList<CustomApproval> approval;
    private ListView listView;
    private String jenis[] =
            {
                    "Cuti",
                    "Ijin Pulang Awal",
                    "Ijin Keluar Kantor",
                    "Cuti",
                    "Ijin Pulang Awal",
                    "Ijin Keluar Kantor"
            };
    private String nama[] =
            {
                    "Gusat",
                    "Victor",
                    "Maulana",
                    "Gusat",
                    "Victor",
                    "Maulana"
            };
    private String tanggal[] =
            {
                    "25-06-2018",
                    "26-06-2018",
                    "27-06-2018",
                    "25-06-2018",
                    "26-06-2018",
                    "27-06-2018"
            };
    private String keterangan[] =
            {
                    "Sakit",
                    "Ijin Pulang",
                    "Ijin Keluar Sebentar",
                    "Sakit",
                    "Ijin Pulang",
                    "Ijin Keluar Sebentar"
            };
    private Spinner dropdown;
    public static String tipe = "";
    private LinearLayout layoutJam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.approval, viewGroup, false);
        context = getContext();
        listView = (ListView) view.findViewById(R.id.listApprovalIjin);
        dropdown = (Spinner) view.findViewById(R.id.dropdownApproval);
        layoutJam = (LinearLayout) view.findViewById(R.id.layoutJamApproval);
        String[] items = new String[]{"Jenis Approval: ", "Cuti", "Ijin Pulang Awal", "Ijin Keluar Kantor"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    dropdown.setAlpha(0.5f);
                    return false;
                } else {
                    dropdown.setAlpha(1);
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {

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
        dropdown.setAdapter(adapter);
//        approval = prepareData();
        if (MainActivity.cuti) {
            dropdown.setSelection(1);
            tipe = "cuti";
            prepareData();
        } else if (MainActivity.pulang_awal) {
            dropdown.setSelection(2);
            tipe = "pulang";
            prepareData();
        } else if (MainActivity.keluar_kantor) {
            dropdown.setSelection(3);
            tipe = "keluar";
            prepareData();
        }
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 1) {
                    tipe = "cuti";
                    prepareData();
                } else if (position == 2) {
                    tipe = "pulang";
                    prepareData();
                } else if (position == 3) {
                    tipe = "keluar";
                    prepareData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void prepareData() {
        MainActivity.cuti = false;
        MainActivity.pulang_awal = false;
        MainActivity.keluar_kantor = false;
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("tipe", tipe);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlApproval, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                listView.setAdapter(null);
                dialog.dismiss();
                approval = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    String message = object.getJSONObject("metadata").getString("message");
                    if (status.equals("1")) {
                        JSONArray response = object.getJSONArray("response");
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject isi = response.getJSONObject(i);
                            if (tipe.equals("cuti")) {
                                approval.add(new CustomApproval(
                                        isi.getString("id"),
                                        isi.getString("nama"),
                                        isi.getString("tgl_awal") + " - " + isi.getString("tgl_akhir"),
                                        isi.getString("keterangan"),
                                        isi.getString("alasan_cuti"), ""
                                ));
                            } else if (tipe.equals("pulang")) {
                                approval.add(new CustomApproval(
                                        isi.getString("id"),
                                        isi.getString("nama"),
                                        isi.getString("tgl"),
                                        isi.getString("keterangan"),
                                        isi.getString("alasan"),
                                        isi.getString("jam")
                                ));
                            } else if (tipe.equals("keluar")) {
                                approval.add(new CustomApproval(
                                        isi.getString("id"),
                                        isi.getString("nama"),
                                        isi.getString("tgl"),
                                        isi.getString("keterangan"),
                                        isi.getString("alasan"),
                                        isi.getString("dari") + " - " + isi.getString("sampai")
                                ));
                            }
                        }
                        adapterApproval = new ListAdapterApproval(context, approval);
                        listView.setAdapter(adapterApproval);
                        listView.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                dialog.dismiss();
                Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }

    /*private ArrayList<CustomApproval> prepareData() {
        ArrayList<CustomApproval> approval = new ArrayList<>();
        for (int a = 0; a < jenis.length; a++) {
            CustomApproval custom = new CustomApproval();
            custom.setJenis(jenis[a]);
            custom.setNama(nama[a]);
            custom.setTanggal(tanggal[a]);
            custom.setKeterangan(keterangan[a]);
            approval.add(custom);
        }
        return approval;
    }*/
}
