package gmedia.net.id.absenigmedia.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gmedia.net.id.absenigmedia.R;
import gmedia.net.id.absenigmedia.Model.CustomHistoryIjinKeluarKantor;

import java.util.List;

/**
 * Created by Bayu on 05/04/2018.
 */

public class ListAdapterHistoryIjinKeluarKantor extends ArrayAdapter {
    private Context context;
    private List<CustomHistoryIjinKeluarKantor> historyIjin;

    public ListAdapterHistoryIjinKeluarKantor(Context context, List<CustomHistoryIjinKeluarKantor> historyIjin) {
        super(context, R.layout.listview_history_ijin_keluar_kantor, historyIjin);
        this.context = context;
        this.historyIjin = historyIjin;
    }

    private static class ViewHolder {
        private TextView tanggal, jamDari, jamSampai, alasan, status;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        int tipeViewList = getItemViewType(position);
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /*LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
            convertView = inflater.inflate(R.layout.listview_history_ijin_keluar_kantor, null);
            holder.tanggal = (TextView) convertView.findViewById(R.id.tanggalHistoryIjinKeluarKantor);
            holder.jamDari = (TextView) convertView.findViewById(R.id.jamDari);
            holder.jamSampai = (TextView) convertView.findViewById(R.id.jamSampai);
            holder.alasan = (TextView) convertView.findViewById(R.id.alasanHistoryIjinKeluarKantor);
            holder.status = convertView.findViewById(R.id.statusHistoryIjinKeluarKantor);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CustomHistoryIjinKeluarKantor absen = historyIjin.get(position);
        holder.tanggal.setText(absen.getTanggal());
        holder.jamDari.setText(absen.getJamDari());
        holder.jamSampai.setText(absen.getJamSampai());
        holder.alasan.setText(absen.getAlasan());
        holder.status.setText(absen.getStatus());
        if (tipeViewList == 0) {
            RelativeLayout a = convertView.findViewById(R.id.layoutnohistoryijin);
            RelativeLayout b = convertView.findViewById(R.id.layouttanggalhistoryijin);
            RelativeLayout c = convertView.findViewById(R.id.layoutalasanhistoryijin);
            RelativeLayout d = convertView.findViewById(R.id.layoutstatushistoryijin);
            a.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            b.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            c.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            d.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
        }
        TextView texta = convertView.findViewById(R.id.tanggalHistoryIjinKeluarKantor);
        String textb = texta.getText().toString();
        if (textb.contains("Sab")) {
            texta.setTextColor(Color.parseColor("#FF0000"));
        } else if (textb.contains("Min")) {
            texta.setTextColor(Color.parseColor("#FF0000"));
        } else {
            texta.setTextColor(context.getResources().getColor(R.color.textbiasa));
        }
        return convertView;
    }
}
