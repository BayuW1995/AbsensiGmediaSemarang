package gmedia.net.id.absenigmedia;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import gmedia.net.id.absenigmedia.Adapter.ListAdapterDashboard;
import gmedia.net.id.absenigmedia.Adapter.RecyclerDashboard;
import gmedia.net.id.absenigmedia.Model.CustomDashboard;
import gmedia.net.id.absenigmedia.Model.CustomLvDashboard;

import java.util.ArrayList;

/**
 * Created by Bayu on 03/01/2018.
 */

public class Dashboard extends Fragment {
    private Context context;
    private View view;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ListView listView;
    private ListAdapterDashboard adapterDashboard;
    private final String isi[] =
            {
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " ",
                    " "
            };
    private final String judul[] =
            {
                    "Sisa Cuti",
                    "Total Terlambat",
                    "Total Uang Makan",
                    "Total Uang Lembur"
            };
    private final String hari[] =
            {
                    "5 Hari",
                    "5 Hari",
                    "400 Ribu",
                    "300 Ribu"
            };

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        view = inflater.inflate(gmedia.net.id.absenigmedia.R.layout.dashboard, viewGroup, false);
        context = getContext();
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvView = view.findViewById(gmedia.net.id.absenigmedia.R.id.rv_Dashboard);
        rvView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        rvView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
        rvView.setLayoutManager(layoutManager);
        ArrayList<CustomDashboard> dashboard = prepareDataDashboard();
        adapter = new RecyclerDashboard(context, dashboard);
        rvView.setAdapter(adapter);
        listView = view.findViewById(gmedia.net.id.absenigmedia.R.id.lv_dashboard);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        ArrayList<CustomLvDashboard> lvdashboard = prepareLvDashboard();
        adapterDashboard = new ListAdapterDashboard(context,lvdashboard);
        listView.setAdapter(adapterDashboard);
//        MainActivity.title.setText("Dashboard");
//        MainActivity.title.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Bold.otf"));
        MainActivity.posisi = true;
        RelativeLayout absen = view.findViewById(gmedia.net.id.absenigmedia.R.id.absen);
        absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Checkin();
                callFragment(fragment);
                MainActivity.title.setText("Absen");
                MainActivity.title.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Bold.otf"));
                MainActivity.drawer.closeDrawer(GravityCompat.START);
                MainActivity.posisi = false;
            }
        });
        return view;
    }

    private ArrayList<CustomLvDashboard> prepareLvDashboard() {
        ArrayList<CustomLvDashboard> lvdashboard = new ArrayList<>();
        for (int a = 0; a < isi.length; a++){
            CustomLvDashboard custom = new CustomLvDashboard();
            custom.setIsi(isi[a]);
            lvdashboard.add(custom);
        }
        return lvdashboard;
    }

    private ArrayList<CustomDashboard> prepareDataDashboard() {
        ArrayList<CustomDashboard> dashboard = new ArrayList<>();
        for (int i = 0; i < judul.length; i++) {
            CustomDashboard custom = new CustomDashboard();
            custom.setJudul(judul[i]);
            custom.setHari(hari[i]);
            dashboard.add(custom);
        }
        return dashboard;
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
