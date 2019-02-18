package gmedia.net.id.absenigmedia;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gmedia.net.id.absenigmedia.Adapter.ListAdapterChangeLog;
import gmedia.net.id.absenigmedia.Model.CustomChangeLog;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

public class ChangeLog extends AppCompatActivity {
    private LinearLayout btnback;
    private ListView listView;
    private ListAdapterChangeLog adapter;
    private List<CustomChangeLog> changeLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_log);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(gmedia.net.id.absenigmedia.R.color.statusbar));
        }
        listView = findViewById(R.id.listChangeLog);
        btnback = findViewById(R.id.backChangeLog);
        prepareDataChangeLog();
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void prepareDataChangeLog() {
        final Dialog dialog = new Dialog(ChangeLog.this);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        ApiVolley request = new ApiVolley(ChangeLog.this, new JSONObject(), "POST", URL.urlChangeLog, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                changeLog = new ArrayList<>();
                listView.setAdapter(null);
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    String message = object.getJSONObject("metadata").getString("message");
                    if (status.equals("1")) {
                        JSONArray response = object.getJSONArray("response");
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject isi = response.getJSONObject(i);
                            changeLog.add(new CustomChangeLog(
                                    isi.getString("version"),
                                    isi.getString("tgl"),
                                    isi.getString("text")
                            ));
                        }
                        adapter = new ListAdapterChangeLog(ChangeLog.this,changeLog);
                        listView.setAdapter(adapter);
                    } else {
                        Toast.makeText(ChangeLog.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                dialog.dismiss();
                Toast.makeText(ChangeLog.this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.changelog = true;
        Intent intent = new Intent(ChangeLog.this, MainActivity.class);
        intent.putExtra("About", "data");
        startActivity(intent);
        finish();
    }
}
