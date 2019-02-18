package gmedia.net.id.absenigmedia;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gmedia.net.id.absenigmedia.Volley.ApiVolley;

public class About extends Fragment {
    private Context context;
    private RelativeLayout btnChangeLog;
    private TextView version;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.about, viewGroup, false);
        context = getContext();
        initUI();
        initAction();

        return view;
    }

    private void initUI() {
        btnChangeLog = (RelativeLayout) view.findViewById(R.id.btnChangeLog);
        version = (TextView) view.findViewById(R.id.version);
    }

    private void initAction() {
        prepareDataVersion();
        btnChangeLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChangeLog.class);
                ((Activity) context).startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }

    private void prepareDataVersion() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        ApiVolley request = new ApiVolley(context, new JSONObject(), "POST", URL.urlChangeLog, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONArray response = object.getJSONArray("response");
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject isi = response.getJSONObject(i);
                                if (i == 0) {
                                    version.setText("Versi "+isi.getString("version"));
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
                dialog.dismiss();
            }
        });
    }
}
