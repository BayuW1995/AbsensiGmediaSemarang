package gmedia.net.id.absenigmedia;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gmedia.net.id.absenigmedia.Volley.ApiVolley;
import me.biubiubiu.justifytext.library.JustifyTextView;

public class DetailNews extends AppCompatActivity {
    private String idNews, gambarUrl;
    private TextView txtJudul, txtTanggal, txtBerita;
    private RelativeLayout btnBack;
    private ImageView gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(gmedia.net.id.absenigmedia.R.color.statusbar));
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idNews = bundle.getString("id", "");
            gambarUrl = bundle.getString("gambar", "");
        }
        initUI();
        initAction();
    }

    private void initUI() {
        txtJudul = (TextView) findViewById(R.id.txtJudul);
        txtTanggal = (TextView) findViewById(R.id.txtTanggal);
        txtBerita = (TextView) findViewById(R.id.txtBerita);
        btnBack = (RelativeLayout) findViewById(R.id.btnBackDetailNews);
        gambar = (ImageView) findViewById(R.id.picDetailNews);
    }

    private void initAction() {
        prepareDataDetailNews();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (!gambarUrl.equals("")) {
            Picasso.get().load(gambarUrl).into(gambar);
        }
    }

    private void prepareDataDetailNews() {
        final Dialog dialog = new Dialog(DetailNews.this);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("id", idNews);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(DetailNews.this, jBody, "POST", URL.urlNews, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    String message = object.getJSONObject("metadata").getString("message");
                    if (status.equals("1")) {
                        JSONObject response = object.getJSONObject("response");
                        txtJudul.setText(response.getString("judul"));
                        txtTanggal.setText(response.getString("tanggal"));
                        txtBerita.setText(response.getString("berita"));
                    } else {
                        Toast.makeText(DetailNews.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                dialog.dismiss();
                Toast.makeText(DetailNews.this, "terjadi kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
