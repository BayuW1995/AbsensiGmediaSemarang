package gmedia.net.id.absenigmedia.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import gmedia.net.id.absenigmedia.Approval;
import gmedia.net.id.absenigmedia.R;
import gmedia.net.id.absenigmedia.Model.CustomApproval;
import gmedia.net.id.absenigmedia.URL;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

public class ListAdapterApproval extends ArrayAdapter {
    private Context context;
    private List<CustomApproval> approval;
    private Boolean accept = false;
    private Boolean reject = false;

    public ListAdapterApproval(Context context, List<CustomApproval> approval) {
        super(context, R.layout.lv_approval, approval);
        this.context = context;
        this.approval = approval;
    }

    private static class ViewHolder {
        private TextView alasan, nama, jam, tanggal, keterangan;
        private LinearLayout background, layoutJam;
        private RelativeLayout layoutAccept, layoutReject;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        CardView cv;
        convertView = null;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.lv_approval, null);
            holder.nama = convertView.findViewById(R.id.tv_nama);
            holder.tanggal = convertView.findViewById(R.id.tv_tanggal);
            holder.keterangan = convertView.findViewById(R.id.tv_keterangan);
            holder.jam = convertView.findViewById(R.id.tv_jam);
            holder.alasan = convertView.findViewById(R.id.tv_alasan);
            holder.background = convertView.findViewById(R.id.clickApproval);
            holder.layoutAccept = convertView.findViewById(R.id.approvalAccept);
            holder.layoutReject = convertView.findViewById(R.id.approvalReject);
            holder.layoutJam = convertView.findViewById(R.id.layoutJamApproval);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CustomApproval custom = approval.get(position);
        holder.nama.setText(custom.getNama());
        holder.tanggal.setText(custom.getTanggal());
        holder.keterangan.setText(custom.getKeterangan());
        holder.alasan.setText(custom.getAlasan());
        holder.jam.setText(custom.getJam());
        cv = convertView.findViewById(R.id.cardViewApproval);
        /*Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        cv.startAnimation(animation);
        lastPosition = position;*/
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.animasi);
        cv.startAnimation(animation);
        final ViewHolder finalHolder = holder;
        if (Approval.tipe.equals("cuti")) {
            holder.layoutJam.setVisibility(View.GONE);
        } else if (Approval.tipe.equals("pulang")) {
            holder.layoutJam.setVisibility(View.VISIBLE);
        } else if (Approval.tipe.equals("keluar")) {
            holder.layoutJam.setVisibility(View.VISIBLE);
        }
        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogYesNO = new Dialog(context);
                dialogYesNO.setContentView(R.layout.popup_approval);
                dialogYesNO.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                RelativeLayout cancel = dialogYesNO.findViewById(R.id.cancelPopupApproval);
                Button Accept = dialogYesNO.findViewById(R.id.btnAcc);
                Button Reject = dialogYesNO.findViewById(R.id.btnRjj);
                TextView jenisApproval = dialogYesNO.findViewById(R.id.txtJenisApproval);
                if (Approval.tipe.equals("cuti")) {
                    jenisApproval.setText("Cuti?");
                } else if (Approval.tipe.equals("pulang")) {
                    jenisApproval.setText("Ijin Pulang Awal?");
                } else if (Approval.tipe.equals("keluar")) {
                    jenisApproval.setText("Ijin Keluar Kantor?");
                }
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogYesNO.dismiss();
                    }
                });
                Accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        /*accept = true;
                        reject = false;
                        finalHolder.layoutAccept.setVisibility(View.VISIBLE);
                        finalHolder.layoutReject.setVisibility(View.GONE);
                        finalHolder.background.setClickable(false);*/
                        final JSONObject jBody = new JSONObject();
                        try {
                            jBody.put("tipe", Approval.tipe);
                            jBody.put("approval", "1");
                            jBody.put("id", approval.get(position).getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlKonfirmasiApproval, "", "", 0, new ApiVolley.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                dialog.dismiss();
                                dialogYesNO.dismiss();
                                try {
                                    JSONObject object = new JSONObject(result);
                                    String status = object.getJSONObject("metadata").getString("status");
                                    String message = object.getJSONObject("metadata").getString("message");
                                    if (status.equals("1")) {
                                        approval.remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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
                                dialogYesNO.dismiss();
                                Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                Reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*reject = true;
                        accept = false;
                        finalHolder.layoutReject.setVisibility(View.VISIBLE);
                        finalHolder.layoutAccept.setVisibility(View.GONE);
                        finalHolder.background.setClickable(false);*/
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        final JSONObject jBody = new JSONObject();
                        try {
                            jBody.put("tipe", Approval.tipe);
                            jBody.put("approval", "0");
                            jBody.put("id", approval.get(position).getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlKonfirmasiApproval, "", "", 0, new ApiVolley.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                dialog.dismiss();
                                dialogYesNO.dismiss();
                                try {
                                    JSONObject object = new JSONObject(result);
                                    String status = object.getJSONObject("metadata").getString("status");
                                    String message = object.getJSONObject("metadata").getString("message");
                                    if (status.equals("1")) {
                                        approval.remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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
                                dialogYesNO.dismiss();
                                Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                dialogYesNO.setCancelable(false);
                dialogYesNO.show();
            }
        });
        return convertView;
    }
}
