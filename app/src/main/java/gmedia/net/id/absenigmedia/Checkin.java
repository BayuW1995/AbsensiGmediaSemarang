package gmedia.net.id.absenigmedia;

import android.app.Activity;
import android.app.Dialog;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Bayu on 28/11/2017.
 */

public class Checkin extends Fragment {

    private Context context;
    private SessionManager session;
    private View view;
    public boolean posisi = true;
    public static LinearLayout layoutcheckin;
    public static TextView checkin;
    private RelativeLayout btnLogin;
    private WifiManager manager;
    private String infoSSID = "", infoBSSID = "", infoIpPublic = "";
    private Dialog dialogCheckIn;
    private String imei;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, final Bundle savedInstanceState) {
        view = inflater.inflate(gmedia.net.id.absenigmedia.R.layout.chekin, viewGroup, false);
        context = getContext();
        session = new SessionManager(context);
        imei = Arrays.toString(GetImei.getIMEI(context).toArray());
        final TextView dinotanggal = view.findViewById(gmedia.net.id.absenigmedia.R.id.dinotanggal);
        final TextView waktuJam = view.findViewById(gmedia.net.id.absenigmedia.R.id.waktuJam);
        final TextView waktuMenit = view.findViewById(gmedia.net.id.absenigmedia.R.id.waktuMenit);
//        final TextView titikdua = (TextView) view.findViewById(R.id.titikdua);
        layoutcheckin = view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutcheckin);
        btnLogin = (RelativeLayout) view.findViewById(R.id.tombolCheckIn);
        checkin = view.findViewById(gmedia.net.id.absenigmedia.R.id.checkin);
        if (DashboardBaru.tombol) {
            posisi = true;
            layoutcheckin.setBackgroundDrawable(getResources().getDrawable(gmedia.net.id.absenigmedia.R.drawable.baground));
            checkin.setText("CHECK IN");
            checkin.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Bold.otf"));
            btnLogin.setBackgroundDrawable(getResources().getDrawable(gmedia.net.id.absenigmedia.R.drawable.bundertombolin));
        } else {
            posisi = false;
            layoutcheckin.setBackgroundDrawable(getResources().getDrawable(gmedia.net.id.absenigmedia.R.drawable.baground_chek_out));
            checkin.setText("CHECK OUT");
            checkin.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Bold.otf"));
            btnLogin.setBackgroundDrawable(getResources().getDrawable(gmedia.net.id.absenigmedia.R.drawable.bundertombolout));
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                Window window = Checkin.this.getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(context.getResources().getColor(gmedia.net.id.absenigmedia.R.color.statusbarcheckout));
            }
        }
        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(10);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                TextView dinotanggal = (TextView)findViewById(R.id.dinotanggal);
//                                TextView waktu = (TextView)findViewById(R.id.waktu);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("EE, dd/MMM/yyy", Locale.US);
                                SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
                                SimpleDateFormat sdf3 = new SimpleDateFormat("mm");
                                String dateString = sdf.format(date);
                                String dateString2 = sdf2.format(date);
                                String dateString3 = sdf3.format(date);
                                dinotanggal.setText(dateString);
                                waktuJam.setText(dateString2);
                                waktuMenit.setText(dateString3);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        waktuJam.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/DS-DIGI.TTF"));
        waktuMenit.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/DS-DIGI.TTF"));
//        titikdua.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/DS-DIGI.TTF"));
//        TextView output= (TextView)view.findViewById(R.id.msg2);
//        output.setText("Fragment Two");
//        String textButton = checkin.getText().toString();
//        final RelativeLayout popupmenu = view.findViewById(R.id.popupmenu);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bundle = getArguments().getString("Check");
                if (bundle != null) {
                    if (bundle.equals("in")) {
                        Log.d("Check", "in");
                        getIpPublic();
                    } else if (bundle.equals("out")) {
                        Log.d("Check", "out");
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.popupcheckout);
                        Button ya = dialog.findViewById(gmedia.net.id.absenigmedia.R.id.yacheckout);
                        ya.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                getIpPublic();
                            }
                        });
                        Button tidak = dialog.findViewById(gmedia.net.id.absenigmedia.R.id.tidakcheckout);
                        tidak.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
						Window window = dialog.getWindow();
						window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    }
                }
            }
        });
        return view;
    }

    /*@Override
    public void onResume() {
        super.onResume();
        manager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        infoSSID = info.getSSID();
        infoBSSID = info.getBSSID();
        imei = Arrays.toString(GetImei.getIMEI(context).toArray());
    }*/

    private void getIpPublic() {
        dialogCheckIn = new Dialog(context);
        dialogCheckIn.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialogCheckIn.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCheckIn.setCanceledOnTouchOutside(false);
        dialogCheckIn.show();
        ApiVolley request = new ApiVolley(context, new JSONObject(), "GET", URL.urlIpPublic, "", "", 3, new ApiVolley.VolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(String result) {
                dialogCheckIn.dismiss();
                infoIpPublic = Jsoup.parse(result).text();
                manager = (WifiManager) context.getSystemService(WIFI_SERVICE);
                WifiInfo info = manager.getConnectionInfo();
                infoSSID = info.getSSID();
                infoBSSID = info.getBSSID();
                /*if (infoBSSID.contains("02:00:00:")){
                    DeviceAdminReceiver admin = new DeviceAdminReceiver();
                    DevicePolicyManager devicepolicymanager = admin.getManager(context);
                    ComponentName name1 = admin.getWho(context);
                    String mac_address = devicepolicymanager.getWifiMacAddress(name1);
                    Log.e("macAddress",""+mac_address);
                }*/
                checkin();
            }

            @Override
            public void onError(String result) {
                dialogCheckIn.dismiss();
                Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkin() {
        if (posisi) {
            final Dialog dialogLoading = new Dialog(context);
            dialogLoading.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCanceledOnTouchOutside(false);
            dialogLoading.show();
            final JSONObject jBody = new JSONObject();
            try {
                jBody.put("imei", imei);
                jBody.put("ssid", infoSSID);
                jBody.put("macaddress", infoBSSID);
                jBody.put("ippublic", infoIpPublic);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ApiVolley request1 = new ApiVolley(context, jBody, "POST", URL.urlCheckIn, "", "", 0, new ApiVolley.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    dialogLoading.dismiss();
                    try {
                        JSONObject object = new JSONObject(result);
                        String status = object.getJSONObject("metadata").getString("status");
                        if (status.equals("1")) {
                            final Dialog dialog = new Dialog(context);
                            dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.popupsuksescheckin);
                            RelativeLayout close = dialog.findViewById(gmedia.net.id.absenigmedia.R.id.closesukseslogin);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                            final Handler handler = new Handler();
                            final Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }
                            };

                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    handler.removeCallbacks(runnable);
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                    ((Activity) context).finish();
                                }
                            });

                            handler.postDelayed(runnable, 2000);

                           /* layoutcheckin.setBackgroundDrawable(getResources().getDrawable(gmedia.net.id.absenigmedia.R.drawable.baground_chek_out));
                            checkin.setText("CHECK OUT");
                            checkin.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Bold.otf"));
                            btnLogin.setBackgroundDrawable(getResources().getDrawable(gmedia.net.id.absenigmedia.R.drawable.bundertombolout));
                            if (android.os.Build.VERSION.SDK_INT >= 21) {
                                Window window = Checkin.this.getActivity().getWindow();
                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                window.setStatusBarColor(context.getResources().getColor(gmedia.net.id.absenigmedia.R.color.statusbarcheckout));
                            }
                            posisi = false;*/

                        } else {
                            String warning = object.getJSONObject("metadata").getString("message");
                            Toast.makeText(context, warning, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String result) {
                    dialogLoading.dismiss();
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            final Dialog dialogLoading = new Dialog(context);
            dialogLoading.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
            dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCanceledOnTouchOutside(false);
            dialogLoading.show();
            final JSONObject jBody = new JSONObject();
            try {
                jBody.put("imei", imei);
                jBody.put("flag", "1");
                jBody.put("ssid", infoSSID);
                jBody.put("macaddress", infoBSSID);
                jBody.put("ippublic", infoIpPublic);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ApiVolley request1 = new ApiVolley(context, jBody, "POST", URL.urlCheckIn, "", "", 0, new ApiVolley.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    dialogLoading.dismiss();
                    try {
                        JSONObject object = new JSONObject(result);
                        String status = object.getJSONObject("metadata").getString("status");
                        if (status.equals("1")) {
                            final Dialog dialog = new Dialog(context);
                            dialog.setContentView(R.layout.popupsuksescheckout);
                            RelativeLayout close = dialog.findViewById(gmedia.net.id.absenigmedia.R.id.closesukseslogin);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                            final Handler handler = new Handler();
                            final Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }
                            };

                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    handler.removeCallbacks(runnable);
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                    ((Activity) context).finish();
                                }
                            });

                            handler.postDelayed(runnable, 2000);
                                    /*layoutcheckin.setBackgroundDrawable(getResources().getDrawable(gmedia.net.id.absenigmedia.R.drawable.baground));
                                    checkin.setText("CHECK IN");
                                    checkin.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Bold.otf"));
                                    btnLogin.setBackgroundDrawable(getResources().getDrawable(gmedia.net.id.absenigmedia.R.drawable.bundertombolin));
                                    if (android.os.Build.VERSION.SDK_INT >= 21) {
                                        Window window = Checkin.this.getActivity().getWindow();
                                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                        window.setStatusBarColor(context.getResources().getColor(gmedia.net.id.absenigmedia.R.color.statusbar));
                                    }
                                    posisi = true;*/
                        } else {
                            String warning = object.getJSONObject("metadata").getString("message");
                            Toast.makeText(context, warning, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String result) {
                    dialogLoading.dismiss();
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                }
            });
            dialogLoading.dismiss();
        }
    }
}
/*if (textButton.contains("CHECK IN")) {
            checkin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutcheckin.setBackgroundDrawable(getResources().getDrawable(R.drawable.baground_chek_out));
                    checkin.setText("CHECK OUT");
                    checkin.setBackgroundDrawable(getResources().getDrawable(R.drawable.bundertombolout));

                }
            });
        }
        else if (textButton.contains("CHECK OUT")){
            checkin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutcheckin.setBackgroundDrawable(getResources().getDrawable(R.drawable.baground));
                    checkin.setText("CHECK IN");
                    checkin.setBackgroundDrawable(getResources().getDrawable(R.drawable.bundertombolin));
                }
            });
        }*/
 /*layoutcheckin.setAlpha(0.1f);
                progressBar.setVisibility(View.VISIBLE);
                Thread thread1 = new Thread() {
                    @Override
                    public void run() {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    while (progressStatus < 1000000) {
                                        progressStatus += 1;
                                        progressBar.setProgress(progressStatus);
                                    }
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } finally {
                                    if (progressStatus >= 1000000) {
                                        progressBar.setVisibility(View.GONE);
                                        layoutcheckout.setVisibility(VISIBLE);
                                    }

                                }

                            }
                        });
                    }

                };
                thread1.start();*/