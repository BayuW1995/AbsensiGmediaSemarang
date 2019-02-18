package gmedia.net.id.absenigmedia;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import gmedia.net.id.absenigmedia.Adapter.CustomLinearLayoutManager;
import gmedia.net.id.absenigmedia.Adapter.RecyclerViewDashboardBaru;
import gmedia.net.id.absenigmedia.Model.CustomRecyclerViewDashboardBaru;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;
import gmedia.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class DashboardBaru extends Fragment {

    private View view;
    private Context context;
    private SlidingUpPanelLayout slidingPaneLayout;
    public static boolean tombol = true;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CustomRecyclerViewDashboardBaru> dataSet;
    private final String tanggal[] =
            {
                    "10 OKT 2017",
                    "10 OKT 2017",
                    "10 OKT 2017",
                    "10 OKT 2017",
                    "10 OKT 2017",
                    "10 OKT 2017"
            };
    private final String teks1[] =
            {
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry."

            };
    private final String teks2[] =
            {
                    "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested",
                    "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested",
                    "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested",
                    "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested",
                    "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested",
                    "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested",
            };
    private final String id[] =
            {
                    "4",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6"
            };
    private SessionManager session;
    private TelephonyManager telephonyManager;
    private static final String TAG = "MyFirebaseIIDService";
    private String token;
    private Bundle bundle = new Bundle();

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup viewGroup, Bundle savedInstanceState) {
        view = inflater.inflate(gmedia.net.id.absenigmedia.R.layout.dashboard_baru, viewGroup, false);
        context = getContext();
        session = new SessionManager(context);
        FirebaseApp.initializeApp(context);
        FirebaseMessaging.getInstance().subscribeToTopic("gmedia_news");
        token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "tokenmu: " + token);
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        Toast.makeText(context,imei,Toast.LENGTH_LONG).show();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(gmedia.net.id.absenigmedia.R.color.statusbar));
        }
        rvView = view.findViewById(gmedia.net.id.absenigmedia.R.id.rv_DashboardBaru);
        CustomLinearLayoutManager customLayoutManager = new CustomLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvView.setLayoutManager(customLayoutManager);
        /*rvView.setAdapter(null);
        dataSet = prepareData();
        adapter = new RecyclerViewDashboardBaru(context, dataSet);
        rvView.setAdapter(adapter);*/
        final RelativeLayout slidinglayout = view.findViewById(gmedia.net.id.absenigmedia.R.id.slidingLayout);
        final RelativeLayout layoutnews = view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutnews);
        final ImageView panah = view.findViewById(gmedia.net.id.absenigmedia.R.id.panah);
        Animation animation = AnimationUtils.loadAnimation(context, gmedia.net.id.absenigmedia.R.anim.animasi);
        LinearLayout layoutdaftar = view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutdaftar);
        layoutdaftar.startAnimation(animation);
        slidingPaneLayout = view.findViewById(gmedia.net.id.absenigmedia.R.id.sliding);
        slidingPaneLayout.setDragView(gmedia.net.id.absenigmedia.R.id.layoutnews);
        slidingPaneLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layout.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    layoutnews.setLayoutParams(layout);
                    layoutnews.setBackgroundDrawable(getResources().getDrawable(gmedia.net.id.absenigmedia.R.drawable.backgroundnewsslidinglayout));
                    panah.setImageResource(gmedia.net.id.absenigmedia.R.drawable.panah_keatas_putih);
                    CustomLinearLayoutManager customLayoutManager = new CustomLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvView.setLayoutManager(customLayoutManager);
                } else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    layoutnews.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                    layoutnews.setBackgroundColor(Color.parseColor("#1a6a9d"));
                    panah.setImageResource(gmedia.net.id.absenigmedia.R.drawable.panah_kebawah_putih);
                    layoutManager = new LinearLayoutManager(context);
                    rvView.setLayoutManager(layoutManager);
                }
            }
        });
        slidingPaneLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingPaneLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        dashboard();
        newsDashboard();
        final Button checkin = view.findViewById(gmedia.net.id.absenigmedia.R.id.tombolCheckinDashboard);
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Checkin();
                bundle.putString("Check", "in");
                fragment.setArguments(bundle);
                callFragment(fragment);
                MainActivity.title.setText("Absen");
                MainActivity.title.setTypeface(Typeface.createFromAsset(DashboardBaru.this.getActivity().getAssets(), "fonts/Helvetica-Bold.otf"));
//                finalDrawer.closeDrawer(GravityCompat.START);
                MainActivity.posisi = false;
                tombol = true;

                /*Checkin.layoutcheckin.setBackgroundDrawable(getResources().getDrawable(R.drawable.baground));
                Checkin.checkin.setText("CHECK IN");
                Checkin.checkin.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Bold.otf"));
                Checkin.checkin.setBackgroundDrawable(getResources().getDrawable(R.drawable.bundertombolin));*/
            }
        });
        Button checkout = view.findViewById(gmedia.net.id.absenigmedia.R.id.tombolCheckoutDashboard);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Checkin();
                bundle.putString("Check", "out");
                fragment.setArguments(bundle);
                callFragment(fragment);
                MainActivity.title.setText("Absen");
                MainActivity.title.setTypeface(Typeface.createFromAsset(DashboardBaru.this.getActivity().getAssets(), "fonts/Helvetica-Bold.otf"));
//                finalDrawer.closeDrawer(GravityCompat.START);
                MainActivity.posisi = false;
                tombol = false;
                /*Checkin.layoutcheckin.setBackgroundDrawable(getResources().getDrawable(R.drawable.baground_chek_out));
                Checkin.checkin.setText("CHECK OUT");
                Checkin.checkin.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Bold.otf"));
                Checkin.checkin.setBackgroundDrawable(getResources().getDrawable(R.drawable.bundertombolout));*/
            }
        });
        RelativeLayout menuSisaCuti = view.findViewById(gmedia.net.id.absenigmedia.R.id.menuSisaCuti);
        menuSisaCuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Cuti();
                callFragment(fragment);
                MainActivity.title.setText("Cuti");
                MainActivity.posisi = false;
            }
        });
        RelativeLayout menuUangLembur = view.findViewById(gmedia.net.id.absenigmedia.R.id.menuUangLembur);
        menuUangLembur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new UangLembur();
                callFragment(fragment);
                MainActivity.title.setText("Uang Lembur");
                MainActivity.posisi = false;
            }
        });
        RelativeLayout menuUangMakan = view.findViewById(gmedia.net.id.absenigmedia.R.id.menuUangMakan);
        menuUangMakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new UangMakan();
                callFragment(fragment);
                MainActivity.title.setText("Uang Makan");
                MainActivity.posisi = false;
            }
        });
        RelativeLayout menuTotalTerlambat = view.findViewById(gmedia.net.id.absenigmedia.R.id.menuTotalTerlambat);
        menuTotalTerlambat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TotalTerlambat.class);
                startActivity(i);
            }
        });
        return view;
    }

    private void newsDashboard() {
        ApiVolley request = new ApiVolley(context, new JSONObject(), "POST", URL.urlNews, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dataSet = new ArrayList<CustomRecyclerViewDashboardBaru>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONArray array = object.getJSONArray("response");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject objectNews = array.getJSONObject(i);
                            dataSet.add(new CustomRecyclerViewDashboardBaru(
                                    objectNews.getString("id"),
                                    objectNews.getString("judul"),
                                    objectNews.getString("berita"),
                                    objectNews.getString("tanggal"),
                                    objectNews.getString("gambar")
                            ));
                        }

                        rvView.setAdapter(null);
                        adapter = new RecyclerViewDashboardBaru(context, dataSet);
                        rvView.setAdapter(adapter);
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

    /*private ArrayList<CustomRecyclerViewDashboardBaru> prepareData() {
        ArrayList<CustomRecyclerViewDashboardBaru> historycuti = new ArrayList<>();
        for (int a = 0; a < id.length; a++) {
            CustomRecyclerViewDashboardBaru custom = new CustomRecyclerViewDashboardBaru();
            custom.setId(id[a]);
            custom.setJudul(teks1[a]);
            custom.setBerita(teks2[a]);
            custom.setTanggal(tanggal[a]);
            historycuti.add(custom);
        }
        return historycuti;
    }*/

    @Override
    public void onResume() {
        super.onResume();
        dashboard();
        /*rvView.setAdapter(null);
        dataSet = prepareData();
        adapter = new RecyclerViewDashboardBaru(context, dataSet);
        rvView.setAdapter(adapter);*/
        newsDashboard();
    }

    private void dashboard() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        String imei = telephonyManager.getDeviceId();
        String imei = Arrays.toString(GetImei.getIMEI(context).toArray());
//        Toast.makeText(context,imei,Toast.LENGTH_LONG).show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("imei", imei);
            jBody.put("nip", session.getNip());
            jBody.put("fcm_id", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(getContext(), jBody, "POST", URL.urlDashboard, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONArray array = object.getJSONArray("response");
                        if (array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject isi = array.getJSONObject(i);
                                if (i == 0) {
                                    TextView sisaCutiDashboard = view.findViewById(gmedia.net.id.absenigmedia.R.id.sisaCutiDashboard);
                                    sisaCutiDashboard.setText(isi.getString("label"));
                                    TextView contentSisaCutiDashboard = view.findViewById(gmedia.net.id.absenigmedia.R.id.contentSisaCutiDashboard);
                                    contentSisaCutiDashboard.setText(isi.getString("value"));
                                } else if (i == 1) {
                                    TextView totalTerlambatDashboard = view.findViewById(gmedia.net.id.absenigmedia.R.id.totalTerlambatDashboard);
                                    totalTerlambatDashboard.setText(isi.getString("label"));
                                    TextView contentTotalTerlambatDashboard = view.findViewById(gmedia.net.id.absenigmedia.R.id.contentTotalTerlambatDashboard);
                                    contentTotalTerlambatDashboard.setText(isi.getString("value"));
                                } else if (i == 2) {
                                    TextView uangMakanDashboard = view.findViewById(gmedia.net.id.absenigmedia.R.id.uangMakanDashboard);
                                    uangMakanDashboard.setText(isi.getString("label"));
                                    TextView contentUangMakanDashboard = view.findViewById(gmedia.net.id.absenigmedia.R.id.contentUangMakanDashboard);
                                    contentUangMakanDashboard.setText(isi.getString("value"));
                                } else if (i == 3) {
                                    TextView uangLemburDashboard = view.findViewById(gmedia.net.id.absenigmedia.R.id.uangLemburDashboard);
                                    uangLemburDashboard.setText(isi.getString("label"));
                                    TextView contentUangLemburDashboard = view.findViewById(gmedia.net.id.absenigmedia.R.id.contentUangLemburDashboard);
                                    contentUangLemburDashboard.setText(isi.getString("value"));
                                }
                            }
                        }
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
    }

    private Fragment fragment;

    private void callFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(gmedia.net.id.absenigmedia.R.id.mainLayout, fragment, fragment.getClass().getSimpleName())
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .addToBackStack(null)
                .commit();
    }

}
