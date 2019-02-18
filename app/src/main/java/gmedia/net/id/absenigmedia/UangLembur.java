package gmedia.net.id.absenigmedia;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Adapter.ExpandableListViewAdapter;
import gmedia.net.id.absenigmedia.Model.Child;
import gmedia.net.id.absenigmedia.Model.Parent;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UangLembur extends Fragment {
    ExpandableListViewAdapter listViewAdapter;
    ExpandableListView listView;
    List<Parent> header;
    HashMap<String, List<Child>> child;
    private SessionManager session;
    public static ImageView dropdown;
    //    ArrayList<Parent>items;
    private Context context;
    private TextView jumlah;
    private int pertama, kedua, ketiga, keempat, kelima, abc;
    public int total;
    private String totalan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(gmedia.net.id.absenigmedia.R.layout.uang_lembur, viewGroup, false);
        context = getContext();
        dropdown = view.findViewById(gmedia.net.id.absenigmedia.R.id.dropdown);
        listView = (ExpandableListView) view.findViewById(gmedia.net.id.absenigmedia.R.id.expanded_menu);
//        prepareData();
        jumlah = view.findViewById(gmedia.net.id.absenigmedia.R.id.text80);
        parsingdata();
//        listViewAdapter = new ExpandableListViewAdapter(context, header, child);
//        listView.setAdapter(listViewAdapter);
        return view;
    }

    private void parsingdata() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("type", "lembur");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlUangLembur, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                header = new ArrayList<Parent>();
                child = new HashMap<String, List<Child>>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONArray array = object.getJSONArray("response");
                        double totalnominal = 0;
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject parent = array.getJSONObject(i);
//                            String nominal = parent.getString("nominal");
                            header.add(new Parent(
                                    parent.getString("minggu"),
                                    parent.getString("tgl_awal"),
                                    parent.getString("tgl_akhir"),
                                    parent.getString("nominal")
                            ));
                            totalnominal+= parseNullDouble(parent.getString("nominal"));
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
//                        total = pertama+kedua+ketiga+keempat+kelima;
//                        total = total + totalnominal;
                        totalan = doubleToStringFull(totalnominal);
                        jumlah.setText(ChangeToRupiahFormat(totalan));
                        listViewAdapter = new ExpandableListViewAdapter(context, header, child);
                        listView.setAdapter(listViewAdapter);
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
    public Double parseNullDouble(String s){
        double result = 0;
        if(s != null && s.length() > 0){
            try {
                result = Double.parseDouble(s);
            }catch (Exception e){
                e.printStackTrace();

            }
        }
        return result;
    }
    public String doubleToStringFull(Double number){
        return String.format("%s", number).replace(",",".");
    }
    public String ChangeToRupiahFormat(String number){

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
/* private void prepareData() {
        header = new ArrayList<Parent>();
        child = new HashMap<String, List<Child>>();
        header.add(new Parent("Minggu Ke-1", "21/01/2017", "24/01/2017", "Rp 40.000"));
        header.add(new Parent("Minggu Ke-2", "25/01/2017", "01/02/2017", "-"));
        header.add(new Parent("Minggu Ke-3", "02/02/2017", "08/02/2017", "-"));
        header.add(new Parent("Minggu Ke-4", "09/02/2017", "15/02/2017", "Rp 40.000"));
        header.add(new Parent("Minggu Ke-5", "09/02/2017", "15/02/2017", "-"));
        List<Child> childminggu1 = new ArrayList<>();
        childminggu1.add(new Child("Sen, 21/01/2017", "-"));
        childminggu1.add(new Child("Sel, 22/01/2017", "-"));
        childminggu1.add(new Child("Sel, 23/01/2017", "Rp 10.000"));
        childminggu1.add(new Child("Sel, 24/01/2017", "-"));
        childminggu1.add(new Child("Sel, 25/01/2017", "-"));
        childminggu1.add(new Child("Sel, 26/01/2017", "-"));
        childminggu1.add(new Child("Sel, 27/01/2017", "-"));
        List<Child> childminggu2 = new ArrayList<>();
        childminggu2.add(new Child("Sen, 21/01/2017", "-"));
        childminggu2.add(new Child("Sel, 22/01/2017", "-"));
        childminggu2.add(new Child("Sel, 23/01/2017", "Rp 10.000"));
        childminggu2.add(new Child("Sel, 24/01/2017", "-"));
        childminggu2.add(new Child("Sel, 25/01/2017", "-"));
        childminggu2.add(new Child("Sel, 26/01/2017", "-"));
        childminggu2.add(new Child("Sel, 27/01/2017", "-"));
        List<Child> childminggu3 = new ArrayList<>();
        childminggu3.add(new Child("Sen, 21/01/2017", "-"));
        childminggu3.add(new Child("Sel, 22/01/2017", "-"));
        childminggu3.add(new Child("Sel, 23/01/2017", "Rp 10.000"));
        childminggu3.add(new Child("Sel, 24/01/2017", "-"));
        childminggu3.add(new Child("Sel, 25/01/2017", "-"));
        childminggu3.add(new Child("Sel, 26/01/2017", "-"));
        childminggu3.add(new Child("Sel, 27/01/2017", "-"));
        List<Child> childminggu4 = new ArrayList<>();
        childminggu4.add(new Child("Sen, 21/01/2017", "-"));
        childminggu4.add(new Child("Sel, 22/01/2017", "-"));
        childminggu4.add(new Child("Sel, 23/01/2017", "Rp 10.000"));
        childminggu4.add(new Child("Sel, 24/01/2017", "-"));
        childminggu4.add(new Child("Sel, 25/01/2017", "-"));
        childminggu4.add(new Child("Sel, 26/01/2017", "-"));
        childminggu4.add(new Child("Sel, 27/01/2017", "-"));
        List<Child> childminggu5 = new ArrayList<>();
        childminggu5.add(new Child("Sen, 21/01/2017", "-"));
        childminggu5.add(new Child("Sel, 22/01/2017", "-"));
        childminggu5.add(new Child("Sel, 23/01/2017", "Rp 10.000"));
        childminggu5.add(new Child("Sel, 24/01/2017", "-"));
        childminggu5.add(new Child("Sel, 25/01/2017", "-"));
        childminggu5.add(new Child("Sel, 26/01/2017", "-"));
        childminggu5.add(new Child("Sel, 27/01/2017", "-"));

        child.put(header.get(0).getMinggu(), childminggu1);
        child.put(header.get(1).getMinggu(), childminggu2);
        child.put(header.get(2).getMinggu(), childminggu3);
        child.put(header.get(3).getMinggu(), childminggu4);
        child.put(header.get(4).getMinggu(), childminggu5);
    }*/
//                            Parent parent1 = new Parent("","","","");
//                            parent1.getJumlah();
//                            String nominal = ""+parent1;
//                            abc = Integer.parseInt(nominal);
                            /*if (i==0){
                                String uang1 = parent.getString("nominal");
                                pertama = Integer.parseInt(uang1);
                            }
                            else if (i==1){
                                String uang2 = parent.getString("nominal");
                                kedua = Integer.parseInt(uang2);
                            }
                            else if (i==2){
                                String uang3 = parent.getString("nominal");
                                ketiga = Integer.parseInt(uang3);
                            }
                            else if (i==3){
                                String uang4 = parent.getString("nominal");
                                keempat = Integer.parseInt(uang4);
                            }
                            else if (i==4){
                                String uang5 = parent.getString("nominal");
                                kelima = Integer.parseInt(uang5);
                            }*/