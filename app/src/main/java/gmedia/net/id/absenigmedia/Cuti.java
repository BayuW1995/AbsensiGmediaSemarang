package gmedia.net.id.absenigmedia;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Model.CustomMasterApproval;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bayu on 05/12/2017.
 */

public class Cuti extends Fragment {
    private Context context;
    private static TextView tglmulai, tglselesai, sisacuti;
    private EditText keterangan;
    private Spinner dropdownCuti;
    private List<CustomMasterApproval> approval;
    private ArrayAdapter<CustomMasterApproval> adapterMasterApproval;
    private CustomMasterApproval custom;
    private String posisiDropdown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(gmedia.net.id.absenigmedia.R.layout.cuti, viewGroup, false);
        context = getContext();
        RelativeLayout tombolmulai = (RelativeLayout) view.findViewById(gmedia.net.id.absenigmedia.R.id.layouttglmulai);
        RelativeLayout tombolselesai = (RelativeLayout) view.findViewById(gmedia.net.id.absenigmedia.R.id.layouttglselesai);
        RelativeLayout tombolkirim = (RelativeLayout) view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutkirim);
        tglmulai = (TextView) view.findViewById(gmedia.net.id.absenigmedia.R.id.texttglmulai);
        tglselesai = (TextView) view.findViewById(gmedia.net.id.absenigmedia.R.id.texttglselesai);
        keterangan = (EditText) view.findViewById(gmedia.net.id.absenigmedia.R.id.edit_text_keterangan);
        dropdownCuti = (Spinner) view.findViewById(R.id.menuDropdownCuti);
        RelativeLayout layoutcuti = view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutcuti);
        layoutcuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideKeyboard.hideSoftKeyboard((Activity) context);
            }
        });
        sisacuti = (TextView) view.findViewById(gmedia.net.id.absenigmedia.R.id.sisacuti);
        sisaCuti();
        tombolmulai.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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
                        tglmulai.setText(sdFormat.format(customDate.getTime()));
                        tglmulai.setAlpha(1);

                    }
                };
                new DatePickerDialog(context, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
                /*Intent i = new Intent(context, Calendar.class);
                i.putExtra("tombol","dari");
                i.putExtra("flag","cuti");
                startActivity(i);*/
            }
        });
        tombolselesai.setOnClickListener(new View.OnClickListener() {
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
                        tglselesai.setText(sdFormat.format(customDate.getTime()));
                        tglselesai.setAlpha(1);

                    }
                };
                new DatePickerDialog(context, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
                /*Intent i = new Intent(context, Calendar.class);
                i.putExtra("tombol","sampai");
                i.putExtra("flag","cuti");
                startActivity(i);*/
            }
        });
        ApiVolley request = new ApiVolley(context, new JSONObject(), "POST", URL.urlMasterApproval, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                approval = new ArrayList<>();
                approval.add(new CustomMasterApproval("0", "Pilih Approval: "));
//                posisiDropdown = custom.getKode();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONArray response = object.getJSONArray("response");
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject isi = response.getJSONObject(i);
                            approval.add(new CustomMasterApproval(
                                    isi.getString("kode"),
                                    isi.getString("nama")
                            ));
                        }
                        adapterMasterApproval = new ArrayAdapter<CustomMasterApproval>(context, android.R.layout.simple_spinner_item, approval) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    // Disable the first item from Spinner
                                    // First item will be use for hint
                                    dropdownCuti.setAlpha(0.5f);
                                    return false;
                                } else {
                                    dropdownCuti.setAlpha(1);
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
                        dropdownCuti.setAdapter(adapterMasterApproval);
                        dropdownCuti.setSelection(1);
                        dropdownCuti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                custom = approval.get(position);
                                posisiDropdown = String.valueOf(custom.getKode());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
        tombolkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tglmulai.getText().toString().equals("Tanggal Mulai")) {
                    Toast.makeText(context, "Silahkan Pilih Tanggal Mulai", Toast.LENGTH_LONG).show();
                    return;
                } else if (tglselesai.getText().toString().equals("Tanggal Selesai")) {
                    Toast.makeText(context, "Silahkan Pilih Tanggal Selesai", Toast.LENGTH_LONG).show();
                    return;
                } /*else if (posisiDropdown.equals("0")) {
                    Toast.makeText(context, "Silahkan Pilih Siapa Approval Anda", Toast.LENGTH_LONG).show();
                    return;
                }*/ else if (keterangan.getText().toString().equals("")) {
                    Toast.makeText(context, "Silahkan Isi Keterangan Anda", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    /*dropdownCuti.setSelection(1);
                    dropdownCuti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            custom = approval.get(position);
                            posisiDropdown = String.valueOf(custom.getKode());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });*/
                    prepareData();
                }

            }
        });
        TextView history = view.findViewById(gmedia.net.id.absenigmedia.R.id.history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.isCuti = false;
                Intent i = new Intent(context, HistoryCuti.class);
                ((Activity) context).startActivity(i);
            }
        });
        return view;
    }


    private void sisaCuti() {
        ApiVolley request = new ApiVolley(context, new JSONObject(), "POST", URL.urlSisaCuti, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONObject isi = object.getJSONObject("response");
                        sisacuti.setText(isi.getString("sisa_cuti"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void setDate(String date) {
        tglmulai.setText(date);
        tglmulai.setTextSize(20);
        tglmulai.setAlpha(1);
    }

    public static void setDate2(String date) {
        tglselesai.setText(date);
        tglselesai.setTextSize(20);
        tglselesai.setAlpha(1);
    }

    private void prepareData() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("startdate", tglmulai.getText());
            jBody.put("enddate", tglselesai.getText());
            jBody.put("keterangan", keterangan.getText());
            jBody.put("approval", posisiDropdown);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlCuti, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    String message = object.getJSONObject("metadata").getString("message");
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    tglmulai.setText("Tanggal Mulai");
                    tglmulai.setTextColor(Color.parseColor("#ffffff"));
                    tglmulai.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(gmedia.net.id.absenigmedia.R.dimen.textperiode));
                    tglselesai.setText("Tanggal Selesai");
                    tglselesai.setTextColor(Color.parseColor("#ffffff"));
                    tglselesai.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(gmedia.net.id.absenigmedia.R.dimen.textperiode));
                    keterangan.setText("");

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
}
