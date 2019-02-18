package gmedia.net.id.absenigmedia;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Calendar;

import gmedia.net.id.absenigmedia.Volley.ApiVolley;

public class Potongan extends Fragment {

    private Context context;
    private View view;
    private RelativeLayout btnProses;
    private TextView tgl;
    String[] bulan = new String[]
            {
                    "Pilih Bulan : ",
                    "Januari",
                    "Februari",
                    "Maret",
                    "April",
                    "Mei",
                    "Juni",
                    "Juli",
                    "Agustus",
                    "September",
                    "Oktober",
                    "November",
                    "Desember"
            };
    String[] tahun = new String[]
            {
                    "" + String.valueOf(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) - 1),
                    "" + String.valueOf(java.util.Calendar.getInstance().get(Calendar.YEAR))
            };
    private Spinner menuBulan, menuTahun;
    private String posisiMenuBulan, posisiMenuTahun;
    private LinearLayout table;
    private TextView uangAsuransi, uangCicilHutang, totalPotongan;
    private Double nominalAsuransi = null, nominalCicilHutang = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.potongan, viewGroup, false);
        context = getContext();
        initUI();
        initAction();
        return view;
    }

    private void initUI() {
        menuBulan = (Spinner) view.findViewById(R.id.menuBulanPotGaji);
        menuTahun = (Spinner) view.findViewById(R.id.menuTahunPotGaji);
        btnProses = (RelativeLayout) view.findViewById(R.id.btnProsesPotGaji);
        table = (LinearLayout) view.findViewById(R.id.tablePotonganGaji);
        uangAsuransi = (TextView) view.findViewById(R.id.nomPotAsuransi);
        uangCicilHutang = (TextView) view.findViewById(R.id.nomPotCicilHutang);
        totalPotongan = (TextView) view.findViewById(R.id.totalPotongan);
    }

    private void initAction() {
        ArrayAdapter<String> spinnerAdapterBulan = new ArrayAdapter<String>(context, R.layout.spinner_items, bulan) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
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
        spinnerAdapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuBulan.setAdapter(spinnerAdapterBulan);
        menuBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posisiMenuBulan = String.valueOf(menuBulan.getSelectedItemPosition());
                Log.d("bulan", posisiMenuBulan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> spinnerAdapterTahun = new ArrayAdapter<String>(context, R.layout.spinner_items, tahun) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
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
        spinnerAdapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuTahun.setAdapter(spinnerAdapterTahun);
        menuTahun.setSelection(1);
        menuTahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posisiMenuTahun = String.valueOf(menuTahun.getSelectedItem().toString());
                Log.d("tahun", posisiMenuTahun);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posisiMenuBulan.equals("0")) {
                    Toast.makeText(context, "Silahkan pilih bulan terlebih dahulu", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    prepareDataPotonganAsuransi();
                }
//                Toast.makeText(context, posisiMenuBulan + " " + posisiMenuTahun, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void prepareDataPotonganAsuransi() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("bulan", posisiMenuBulan);
            jBody.put("tahun", posisiMenuTahun);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlAsuransi, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    String message = object.getJSONObject("metadata").getString("message");
                    if (status.equals("1")) {
                        JSONObject response = object.getJSONObject("response");
                        uangAsuransi.setText(ChangeToRupiahFormat(response.getString("nominal")));
                        nominalAsuransi = parseNullDouble(response.getString("nominal"));
                        prepareDataCicilHutang();
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
                Toast.makeText(context, "terjadi kesalahan di potongan asuransi", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void prepareDataCicilHutang() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("bulan", posisiMenuBulan);
            jBody.put("tahun", posisiMenuTahun);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlCicilHutang, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    String message = object.getJSONObject("metadata").getString("message");
                    if (status.equals("1")) {
                        JSONObject response = object.getJSONObject("response");
                        uangCicilHutang.setText(ChangeToRupiahFormat(response.getString("nominal")));
                        nominalCicilHutang = parseNullDouble(response.getString("nominal"));
                        Double total = nominalAsuransi + nominalCicilHutang;
                        String nomninalTotal = ChangeToRupiahFormat(doubleToStringFull(total));
                        totalPotongan.setText(nomninalTotal);
                        table.setVisibility(View.VISIBLE);
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
                Toast.makeText(context, "terjadi kesalahan di potongan cicil hutang", Toast.LENGTH_LONG).show();
            }
        });
    }

    public Double parseNullDouble(String s) {
        double result = 0;
        if (s != null && s.length() > 0) {
            try {
                result = Double.parseDouble(s);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return result;
    }

    public String doubleToStringFull(Double number) {
        return String.format("%s", number).replace(",", ".");
    }

    public String ChangeToRupiahFormat(String number) {

        double value = parseNullDouble(number);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols symbols = ((DecimalFormat) format).getDecimalFormatSymbols();

        symbols.setCurrencySymbol("Rp ");
        ((DecimalFormat) format).setDecimalFormatSymbols(symbols);
        format.setMaximumFractionDigits(0);

        String hasil = String.valueOf(format.format(value));

        return hasil;
    }
}
