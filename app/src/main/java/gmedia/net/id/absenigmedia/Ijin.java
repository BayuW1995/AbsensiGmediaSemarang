package gmedia.net.id.absenigmedia;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.TimePicker;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Model.CustomMasterApproval;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Bayu on 20/02/2018.
 */

public class Ijin extends Fragment implements AdapterView.OnItemSelectedListener {
    private Context context;
    private Spinner dropdownMenu, dropdownApproval;
    private RelativeLayout layoutTimePixer, kirim, layouttglmulaiijin, layoutJamMulaiKeluarKantor, layoutJamSelesaiKeluarKantor;
    private TextView textTimePixer, texttglmulaiijin, textJamMulaiKeluarKantor, textJamSelesaiKeluarKantor;
    private EditText keterangan;
    private int posisiDropdown;
    ArrayAdapter<CustomMasterApproval> arrayAdapterMasterApproval;
    private List<CustomMasterApproval> approval;
    private String posisiDropdownApproval;
    private CustomMasterApproval custom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(gmedia.net.id.absenigmedia.R.layout.ijin, viewGroup, false);
        context = getContext();
        RelativeLayout layoutijin = view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutijin);
        layoutTimePixer = view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutTimePixer);
//        layoutInputan = view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutInputKeluarKantor);
        layoutJamMulaiKeluarKantor = view.findViewById(R.id.layoutInputMulaiKeluarKantor);
        textJamMulaiKeluarKantor = view.findViewById(R.id.textMulai);
        layoutJamSelesaiKeluarKantor = view.findViewById(R.id.layoutInputSelesaiKeluarKantor);
        textJamSelesaiKeluarKantor = view.findViewById(R.id.textSelesai);
        textTimePixer = view.findViewById(gmedia.net.id.absenigmedia.R.id.textTimePixer);
//        isianKeluarKantor = view.findViewById(gmedia.net.id.absenigmedia.R.id.textInputKeluarKantor);
        kirim = view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutkirimhistoryijin);
        layouttglmulaiijin = view.findViewById(gmedia.net.id.absenigmedia.R.id.layouttglmulaiijin);
        texttglmulaiijin = view.findViewById(gmedia.net.id.absenigmedia.R.id.texttglmulaiijin);
        keterangan = view.findViewById(gmedia.net.id.absenigmedia.R.id.edit_text_keterangan_historyijin);
        dropdownMenu = view.findViewById(R.id.menuDropdownJenisIjin);
        dropdownApproval = view.findViewById(R.id.menuDropdownApproval);
        layoutijin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideKeyboard.hideSoftKeyboard(getActivity());

            }
        });
        dropdownMenu.setOnItemSelectedListener(this);
        String[] items = new String[]{"Jenis Ijin: ", "Ijin Pulang Awal", "Ijin Keluar Kantor"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items) {
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
        dropdownMenu.setAdapter(adapter);
        layouttglmulaiijin.setOnClickListener(new View.OnClickListener() {
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
                        texttglmulaiijin.setText(sdFormat.format(customDate.getTime()));
                        texttglmulaiijin.setAlpha(1);

                    }
                };
                new DatePickerDialog(context, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
                /*Intent i = new Intent(context, Calendar.class);
                i.putExtra("tombol","dari");
                i.putExtra("flag","cuti");
                startActivity(i);*/
            }
        });
        layoutTimePixer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Calendar mcurrentTime = java.util.Calendar.getInstance();
                int hour = mcurrentTime.get(java.util.Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(java.util.Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String jam = String.valueOf(selectedHour);
                        String menit = String.valueOf(selectedMinute);
                        jam = jam.length() < 2 ? "0" + jam : jam;
                        menit = menit.length() < 2 ? "0" + menit : menit;
                        textTimePixer.setText(jam + ":" + menit);
                        textTimePixer.setAlpha(1);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        layoutJamMulaiKeluarKantor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Calendar mcurrentTime = java.util.Calendar.getInstance();
                int hour = mcurrentTime.get(java.util.Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(java.util.Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String jam = String.valueOf(selectedHour);
                        String menit = String.valueOf(selectedMinute);
                        jam = jam.length() < 2 ? "0" + jam : jam;
                        menit = menit.length() < 2 ? "0" + menit : menit;
                        textJamMulaiKeluarKantor.setText(jam + ":" + menit);
                        textJamMulaiKeluarKantor.setAlpha(1);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        layoutJamSelesaiKeluarKantor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Calendar mcurrentTime = java.util.Calendar.getInstance();
                int hour = mcurrentTime.get(java.util.Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(java.util.Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String jam = String.valueOf(selectedHour);
                        String menit = String.valueOf(selectedMinute);
                        jam = jam.length() < 2 ? "0" + jam : jam;
                        menit = menit.length() < 2 ? "0" + menit : menit;
                        textJamSelesaiKeluarKantor.setText(jam + ":" + menit);
                        textJamSelesaiKeluarKantor.setAlpha(1);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        ApiVolley request = new ApiVolley(context, new JSONObject(), "POST", URL.urlMasterApproval, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                approval = new ArrayList<>();
                approval.add(new CustomMasterApproval("0", "Pilih Approval: "));
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
                        arrayAdapterMasterApproval = new ArrayAdapter<CustomMasterApproval>(context, android.R.layout.simple_spinner_item, approval) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    // Disable the first item from Spinner
                                    // First item will be use for hint
                                    dropdownApproval.setAlpha(0.5f);
                                    return false;
                                } else {
                                    dropdownApproval.setAlpha(1);
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
                        dropdownApproval.setAdapter(arrayAdapterMasterApproval);
                        dropdownApproval.setSelection(1);
                        dropdownApproval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                custom = approval.get(position);
                                posisiDropdownApproval = custom.getKode();
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
        final TextView ijin = view.findViewById(gmedia.net.id.absenigmedia.R.id.ijin);
        ijin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.isIjin = false;
                Intent i = new Intent(context, HistoryIjin.class);
                startActivity(i);
            }
        });
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posisiDropdown == 0) {
                    Toast.makeText(context, "Silahkan Pilih Jenis Ijin Anda", Toast.LENGTH_LONG).show();
                }
                if (posisiDropdown == 1) {
                    if (texttglmulaiijin.getText().toString().equals("Tanggal Mulai")) {
                        Toast.makeText(context, "Silahkan Isi Tanggal Ijin Anda", Toast.LENGTH_LONG).show();
                    } /*else if (posisiDropdownApproval.equals("0")) {
                        Toast.makeText(context, "Silahkan Pilih Siapa Approval Anda", Toast.LENGTH_LONG).show();
                    }*/ else if (textTimePixer.getText().toString().equals("Jam")) {
                        Toast.makeText(context, "Silahkan Isi Jam Ijin Anda", Toast.LENGTH_LONG).show();
                    } else if (keterangan.getText().toString().isEmpty()) {
                        Toast.makeText(context, "Silahkan Isi Keterangan Ijin Anda", Toast.LENGTH_LONG).show();
                    } else {
                        /*dropdownApproval.setSelection(1);
                        posisiDropdownApproval = String.valueOf(dropdownApproval.getSelectedItemPosition());*/
                        prepareDataPulangAwal();
                    }
                } else if (posisiDropdown == 2) {
                    if (texttglmulaiijin.getText().toString().equals("Tanggal Mulai")) {
                        Toast.makeText(context, "Silahkan Isi Tanggal Ijin Anda", Toast.LENGTH_LONG).show();
                    } /*else if (posisiDropdownApproval.equals("0")) {
                        Toast.makeText(context, "Silahkan Pilih Siapa Approval Anda", Toast.LENGTH_LONG).show();
                    }*/ /*else if (isianKeluarKantor.getText().toString().isEmpty()) {
                        Toast.makeText(context, "Silahkan Isi Lama Ijin Anda", Toast.LENGTH_LONG).show();
                    }*/ else if (textJamMulaiKeluarKantor.getText().toString().equals("Jam Mulai")) {
                        Toast.makeText(context, "Silahkan Isi Jam Mulai Anda", Toast.LENGTH_LONG).show();
                    } else if (textJamSelesaiKeluarKantor.getText().toString().equals("Jam Selesai")) {
                        Toast.makeText(context, "Silahkan Isi Jam Selesai Anda", Toast.LENGTH_LONG).show();
                    } else if (keterangan.getText().toString().isEmpty()) {
                        Toast.makeText(context, "Silahkan Isi Keterangan Ijin Anda", Toast.LENGTH_LONG).show();
                    } else {
                        /*dropdownApproval.setSelection(1);
                        posisiDropdownApproval = String.valueOf(dropdownApproval.getSelectedItemPosition());*/
                        prepareDataKeluarKantor();
                    }
                }
            }
        });
        return view;
    }

    private void prepareDataKeluarKantor() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("tanggal", texttglmulaiijin.getText());
            jBody.put("dari", textJamMulaiKeluarKantor.getText());
            jBody.put("sampai", textJamSelesaiKeluarKantor.getText());
//            jBody.put("jml_menit", isianKeluarKantor.getText());
            jBody.put("keterangan", keterangan.getText().toString());
            jBody.put("approval", posisiDropdownApproval);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(getContext(), jBody, "POST", URL.urlIjinKeluarKantor, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    String message = object.getJSONObject("metadata").getString("message");
                    if (status.equals("1")) {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        MainActivity.isIjin = false;
                        Intent intent = new Intent(context, MainActivity.class);
                        ((Activity) context).startActivity(intent);
                        ((Activity) context).finish();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
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

    private void prepareDataPulangAwal() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("tanggal", texttglmulaiijin.getText());
            jBody.put("jam", textTimePixer.getText());
            jBody.put("keterangan", keterangan.getText().toString());
            jBody.put("approval", posisiDropdownApproval);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(getContext(), jBody, "POST", URL.urlIjinPulangAwal, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    String message = object.getJSONObject("metadata").getString("message");
                    if (status.equals("1")) {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        MainActivity.isIjin = false;
                        Intent intent = new Intent(context, MainActivity.class);
                        ((Activity) context).startActivity(intent);
                        ((Activity) context).finish();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        posisiDropdown = parent.getSelectedItemPosition();
        if (position == 1) {
            layoutTimePixer.setVisibility(View.VISIBLE);
            layoutJamMulaiKeluarKantor.setVisibility(View.GONE);
            layoutJamSelesaiKeluarKantor.setVisibility(View.GONE);
            textTimePixer.setText("Jam");
            textTimePixer.setAlpha(0.5f);
        } else if (position == 2) {
            layoutJamMulaiKeluarKantor.setVisibility(View.VISIBLE);
            layoutJamSelesaiKeluarKantor.setVisibility(View.VISIBLE);
            layoutTimePixer.setVisibility(View.GONE);
//            isianKeluarKantor.setText("");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
