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
import gmedia.net.id.absenigmedia.Model.CustomHistoryIjin;

import java.util.List;

/**
 * Created by Bayu on 21/02/2018.
 */

public class ListAdapterHistoryIjin extends ArrayAdapter{
    private Context context;
    private List<CustomHistoryIjin> historyIjin;
    public ListAdapterHistoryIjin(Context context, List<CustomHistoryIjin> historyIjin) {
        super(context, R.layout.listview_historyijin, historyIjin);
        this.context=context;
        this.historyIjin=historyIjin;
    }
    private static class ViewHolder {
        private TextView no, tanggal, alasan, status;
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
        ListAdapterHistoryIjin.ViewHolder holder = new ListAdapterHistoryIjin.ViewHolder();
        int tipeViewList = getItemViewType(position);
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /*LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
            convertView = inflater.inflate(R.layout.listview_historyijin, null);
            holder.no = (TextView) convertView.findViewById(R.id.nohistoryijin);
            holder.tanggal = (TextView) convertView.findViewById(R.id.tanggalhistoryijin);
            holder.alasan = (TextView) convertView.findViewById(R.id.alasanhistoryijin);
            holder.status = convertView.findViewById(R.id.statushistoryijin);
            convertView.setTag(holder);
        }
        else {
            holder = (ListAdapterHistoryIjin.ViewHolder) convertView.getTag();
        }
        final CustomHistoryIjin absen = historyIjin.get(position);
        holder.no.setText(absen.getNo());
        holder.tanggal.setText(absen.getTanggal());
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
        TextView texta = convertView.findViewById(R.id.tanggalhistoryijin);
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
