package gmedia.net.id.absenigmedia.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.EditCuti;
import gmedia.net.id.absenigmedia.Model.CustomRecycler;
import gmedia.net.id.absenigmedia.R;
import gmedia.net.id.absenigmedia.URL;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Bayu on 14/12/2017.
 */

public class RecyclerViewAdapterHistory extends RecyclerView.Adapter<RecyclerViewAdapterHistory.ViewHolder> {
    private ArrayList<CustomRecycler> rvData;
    private Context context;
    private int lastPosition = -2;

    public RecyclerViewAdapterHistory(Context context, ArrayList<CustomRecycler> rvData) {
        this.context = context;
        this.rvData = rvData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mulai, sampai, keterangan;
        private Button cancelHistory, editHistory;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            mulai = itemView.findViewById(R.id.textmulaicuti);
            sampai = itemView.findViewById(R.id.textsampaicuti);
            keterangan = itemView.findViewById(R.id.textketeranganhistory);
            cancelHistory = itemView.findViewById(R.id.cancelHistory);
            editHistory = (Button) itemView.findViewById(R.id.editHistory);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_history_cuti, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mulai.setText(rvData.get(position).getMulai());
        holder.sampai.setText(rvData.get(position).getSampai());
        holder.keterangan.setText(rvData.get(position).getKeterangan());
        setAnimation(holder.itemView, position);
        holder.cancelHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.popupcancelhistory);
                Button yacancel = dialog.findViewById(R.id.yacancel);
                yacancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final JSONObject jBody = new JSONObject();
                        try {
                            jBody.put("id_cuti", rvData.get(position).getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlBatalCuti, "", "", 0, new ApiVolley.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                try {
                                    JSONObject object = new JSONObject(result);
                                    String status = object.getJSONObject("metadata").getString("status");
                                    if (status.equals("1")) {
                                        Toast.makeText(context, "berhasil", Toast.LENGTH_LONG).show();
                                        rvData.remove(position);
                                        notifyDataSetChanged();
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
                        dialog.dismiss();
                    }
                });
                Button tidakcancel = dialog.findViewById(R.id.tidakcancel);
                tidakcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        holder.editHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final JSONObject jBody = new JSONObject();
                try {
                    jBody.put("flag", "1");
                    jBody.put("id", rvData.get(position).getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlHistory, "", "", 0, new ApiVolley.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            final String status = object.getJSONObject("metadata").getString("status");
//                            String message = object.getJSONObject("metadata").getString("message");
                            if (status.equals("1")) {
                                JSONArray response = object.getJSONArray("response");
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject isi = response.getJSONObject(i);
                                    Intent intent = new Intent(context, EditCuti.class);
                                    intent.putExtra("id",isi.getString("id_cuti"));
                                    intent.putExtra("awal",isi.getString("tgl_mulai"));
                                    intent.putExtra("akhir",isi.getString("tgl_selesai"));
                                    intent.putExtra("alasan",isi.getString("alasan"));
                                    intent.putExtra("approval",isi.getString("approval"));
                                    ((Activity) context).startActivity(intent);
                                }
                            } else {

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
        });
    }

    private void setAnimation(View viewToAnimate, int position) {
            /*Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.right_from_left
                    : R.anim.left_form_right);*/
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.animasi);
        viewToAnimate.startAnimation(animation);
//            lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }


}
