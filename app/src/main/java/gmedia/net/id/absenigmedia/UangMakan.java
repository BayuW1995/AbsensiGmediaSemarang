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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Adapter.ListAdapterUangMakan;
import gmedia.net.id.absenigmedia.Model.ModelUangMakan;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Bayu on 29/11/2017.
 */

public class UangMakan extends Fragment {
    private SessionManager session;
    private TextView minggu1, minggu2, minggu3, minggu4, minggu5;
    //    private int pertama, kedua, ketiga, keempat, kelima;
    private String totalan;
    private TextView jumlahView;
    private ListView listView;
    private ArrayList<ModelUangMakan> listUangMakan;
    private Context context;
    private View view;
    private ListAdapterUangMakan adapter;
    private LinearLayout tableUangMakan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.uang_makan, viewGroup, false);
        context = getContext();
        initUI();
        initAction();
//        session = new SessionManager(getContext());

//        int jumlah = pertama + kedua + ketiga + keempat + kelima;
//        String total = String.valueOf(jumlah);

        return view;
    }

    private void initUI() {
        jumlahView = view.findViewById(gmedia.net.id.absenigmedia.R.id.text300);
        listView = (ListView) view.findViewById(R.id.lvUangMakan);
        tableUangMakan = (LinearLayout) view.findViewById(R.id.tableUangMakan);
    }

    private void initAction() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("type", "makan");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(getContext(), jBody, "POST", URL.urlUangMakan, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                listUangMakan = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    String message = object.getJSONObject("metadata").getString("message");
                    if (status.equals("1")) {
                        JSONArray array = object.getJSONArray("response");
                        double totalnominal = 0;
                        if (array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject isi = array.getJSONObject(i);
                                totalnominal += parseNullDouble(isi.getString("nominal"));
                                listUangMakan.add(new ModelUangMakan(
                                        isi.getString("minggu"),
                                        isi.getString("range"),
                                        isi.getString("nominal")
                                ));
                            }
                            listView.setAdapter(null);
                            adapter = new ListAdapterUangMakan(context, listUangMakan);
                            listView.setAdapter(adapter);
                            tableUangMakan.setVisibility(View.VISIBLE);
                            totalan = doubleToStringFull(totalnominal);
                            jumlahView.setText(ChangeToRupiahFormat(totalan));

                        }
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
                Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
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
/*if (i == 0) {
                                    minggu1 = view.findViewById(gmedia.net.id.absenigmedia.R.id.minggu1);
                                    minggu1.setText(isi.getString("minggu"));
                                    TextView tanggal1 = view.findViewById(gmedia.net.id.absenigmedia.R.id.tanggal1);
                                    tanggal1.setText(isi.getString("range"));
                                    TextView harga1 = view.findViewById(gmedia.net.id.absenigmedia.R.id.harga1);
                                    harga1.setText(isi.getString("nominal"));
//                                    String uang1 = harga1.getText().toString();
//                                    pertama = Integer.parseInt(uang1);
                                } else if (i == 1) {
                                    minggu2 = view.findViewById(gmedia.net.id.absenigmedia.R.id.minggu2);
                                    minggu2.setText(isi.getString("minggu"));
                                    TextView tanggal2 = view.findViewById(gmedia.net.id.absenigmedia.R.id.tanggal2);
                                    tanggal2.setText(isi.getString("range"));
                                    TextView harga2 = view.findViewById(gmedia.net.id.absenigmedia.R.id.harga2);
                                    harga2.setText(isi.getString("nominal"));
//                                    String uang2 = harga2.getText().toString();
//                                    kedua = Integer.parseInt(uang2);
                                } else if (i == 2) {
                                    minggu3 = view.findViewById(gmedia.net.id.absenigmedia.R.id.minggu3);
                                    minggu3.setText(isi.getString("minggu"));
                                    TextView tanggal3 = view.findViewById(gmedia.net.id.absenigmedia.R.id.tanggal3);
                                    tanggal3.setText(isi.getString("range"));
                                    TextView harga3 = view.findViewById(gmedia.net.id.absenigmedia.R.id.harga3);
                                    harga3.setText(isi.getString("nominal"));
//                                    String uang3 = harga3.getText().toString();
//                                    ketiga = Integer.parseInt(uang3);
                                } else if (i == 3) {
                                    minggu4 = view.findViewById(gmedia.net.id.absenigmedia.R.id.minggu4);
                                    minggu4.setText(isi.getString("minggu"));
                                    TextView tanggal4 = view.findViewById(gmedia.net.id.absenigmedia.R.id.tanggal4);
                                    tanggal4.setText(isi.getString("range"));
                                    TextView harga4 = view.findViewById(gmedia.net.id.absenigmedia.R.id.harga4);
                                    harga4.setText(isi.getString("nominal"));
//                                    String uang4 = harga4.getText().toString();
//                                    keempat = Integer.parseInt(uang4);
                                } else if (i == 4) {
                                    minggu5 = view.findViewById(gmedia.net.id.absenigmedia.R.id.minggu5);
                                    minggu5.setText(isi.getString("minggu"));
                                    TextView tanggal5 = view.findViewById(gmedia.net.id.absenigmedia.R.id.tanggal5);
                                    tanggal5.setText(isi.getString("range"));
                                    TextView harga5 = view.findViewById(gmedia.net.id.absenigmedia.R.id.harga5);
                                    harga5.setText(isi.getString("nominal"));
//                                    String uang5 = harga5.getText().toString();
//                                    kelima = Integer.parseInt(uang5);
                                }*/
