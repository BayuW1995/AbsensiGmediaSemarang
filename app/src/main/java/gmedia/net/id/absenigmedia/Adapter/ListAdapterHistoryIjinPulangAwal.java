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
import gmedia.net.id.absenigmedia.Model.CustomHistoryIjinPulangAwal;

import java.util.List;

/**
 * Created by Bayu on 21/02/2018.
 */

public class ListAdapterHistoryIjinPulangAwal extends ArrayAdapter{
    private Context context;
    private List<CustomHistoryIjinPulangAwal> historyIjin;
    public ListAdapterHistoryIjinPulangAwal(Context context, List<CustomHistoryIjinPulangAwal> historyIjin) {
        super(context, R.layout.listview_history_ijin_pulang_awal, historyIjin);
        this.context=context;
        this.historyIjin=historyIjin;
    }
    private static class ViewHolder {
        private TextView tanggal, jam, alasan, status;
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
        if(position % 2 == 1){
            hasil = 0;
        }else{
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
            convertView = inflater.inflate(R.layout.listview_history_ijin_pulang_awal, null);
            holder.tanggal = (TextView) convertView.findViewById(R.id.tanggalHistoryIjinPulangAwal);
            holder.jam = (TextView) convertView.findViewById(R.id.jamHistoryIjinPulangAwal);
            holder.alasan = (TextView) convertView.findViewById(R.id.alasanHistoryIjinPulangAwal);
            holder.status = convertView.findViewById(R.id.statusHistoryIjinPulangAwal);
            convertView.setTag(holder);
        }
        else {
            holder = (ListAdapterHistoryIjinPulangAwal.ViewHolder) convertView.getTag();
        }
        final CustomHistoryIjinPulangAwal absen = historyIjin.get(position);
        holder.tanggal.setText(absen.getTanggal());
        holder.jam.setText(absen.getJam());
        holder.alasan.setText(absen.getAlasan());
        holder.status.setText(absen.getStatus());
        if (tipeViewList==0) {
            RelativeLayout a = convertView.findViewById(R.id.layoutnohistoryijin);
            RelativeLayout b = convertView.findViewById(R.id.layouttanggalhistoryijin);
            RelativeLayout c = convertView.findViewById(R.id.layoutalasanhistoryijin);
            RelativeLayout d = convertView.findViewById(R.id.layoutstatushistoryijin);
            a.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            b.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            c.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            d.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
        }
        TextView texta = convertView.findViewById(R.id.tanggalHistoryIjinPulangAwal);
        String textb = texta.getText().toString();
        if (textb.contains("Sab")) {
            texta.setTextColor(Color.parseColor("#FF0000"));
        } else if (textb.contains("Min")) {
            texta.setTextColor(Color.parseColor("#FF0000"));
        }else {
            texta.setTextColor(context.getResources().getColor(R.color.textbiasa));
        }
        return convertView;
    }
}
