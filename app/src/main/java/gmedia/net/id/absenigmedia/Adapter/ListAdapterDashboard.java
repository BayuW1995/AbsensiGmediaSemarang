package gmedia.net.id.absenigmedia.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import gmedia.net.id.absenigmedia.Model.CustomLvDashboard;
import gmedia.net.id.absenigmedia.R;

import java.util.List;

/**
 * Created by Bayu on 03/01/2018.
 */

public class ListAdapterDashboard extends ArrayAdapter {
    private Context context;
    public List<CustomLvDashboard> items;
    private int lastPosition = -1;

    public ListAdapterDashboard(Context context, List<CustomLvDashboard>items) {
        super(context, R.layout.listview_dashboard,items);
        this.context=context;
        this.items=items;

    }
    private static class ViewHolder {
        private TextView isi;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        ViewHolder holder = new ViewHolder();
        CardView cv;
        convertView=null;
        if (convertView==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.listview_dashboard,null);
            holder.isi = convertView.findViewById(R.id.textlistdashboard);
            convertView.setTag(holder);
        }
        else {
          holder = (ViewHolder) convertView.getTag();
        }
        final CustomLvDashboard custom = items.get(position);
        holder.isi.setText(custom.getIsi());
        cv = convertView.findViewById(R.id.cardViewDashboard);
        /*Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        cv.startAnimation(animation);
        lastPosition = position;*/
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.animasi);
        cv.startAnimation(animation);
        return convertView;
    }
}
