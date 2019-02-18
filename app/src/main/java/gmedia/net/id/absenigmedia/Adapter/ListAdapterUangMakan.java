package gmedia.net.id.absenigmedia.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import gmedia.net.id.absenigmedia.Model.ModelUangMakan;
import gmedia.net.id.absenigmedia.R;

public class ListAdapterUangMakan extends ArrayAdapter {
    private Context context;
    private ArrayList<ModelUangMakan> listUangMakan;

    public ListAdapterUangMakan(Context context, ArrayList<ModelUangMakan> listUangMakan) {
        super(context, R.layout.lv_uang_makan, listUangMakan);
        this.context = context;
        this.listUangMakan = listUangMakan;
    }

    private static class ViewHolder {
        private TextView minggu, tanggal, jumlah;
        private LinearLayout layout;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        int hasil = 0;
        if (position % 2 == 1) {
            hasil = 0;
        } else {
            hasil = 1;
        }
        return hasil;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        int tipeViewList = getItemViewType(position);
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.lv_uang_makan, null);
            holder.minggu = (TextView) convertView.findViewById(R.id.txtMingguKeUangMakan);
            holder.tanggal = (TextView) convertView.findViewById(R.id.txtTanggalUangMakan);
            holder.jumlah = (TextView) convertView.findViewById(R.id.txtHargaUangMakan);
            holder.layout = convertView.findViewById(R.id.layoutIsiUangMakan);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ModelUangMakan isi = listUangMakan.get(position);
        holder.minggu.setText(isi.getMinggu());
        holder.tanggal.setText(isi.getTanggal());
        holder.jumlah.setText(isi.getJumlah());
        if (tipeViewList == 0) {
            holder.layout.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        return convertView;
    }
}
