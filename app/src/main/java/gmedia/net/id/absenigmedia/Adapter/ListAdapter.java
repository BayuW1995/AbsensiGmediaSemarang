package gmedia.net.id.absenigmedia.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gmedia.net.id.absenigmedia.SessionManager;
import gmedia.net.id.absenigmedia.Model.CustomItem;
import gmedia.net.id.absenigmedia.R;

import java.util.List;

/**
 * Created by Bayu on 24/11/2017.
 */

public class ListAdapter extends ArrayAdapter {
    Activity context;
    private List<CustomItem> items;
    private SessionManager session;

    public ListAdapter(Activity context, List<CustomItem> items) {
        super(context, R.layout.listview_drawer, items);
        this.context = context;
        this.items = items;
    }

    private static class ViewHolder {
        private ImageView icon;
        private TextView textIcon;
        private RelativeLayout menuApproval;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        session = new SessionManager(context);
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.listview_drawer, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.gambar);
            holder.textIcon = (TextView) convertView.findViewById(R.id.textDrawer);
            holder.menuApproval = (RelativeLayout) convertView.findViewById(R.id.layoutMenuUtama);
//            holder.garis = (RelativeLayout)convertView.findViewById(R.id.layout2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CustomItem itemSelected = items.get(position);
        holder.icon.setImageDrawable(convertView.getResources().getDrawable(itemSelected.getIcon()));
        holder.textIcon.setText(itemSelected.getTextIcon());
        if (session.getApproval().equals("1")) {
            holder.menuApproval.setVisibility(View.VISIBLE);
        } else {
            if (position == 2) {
                holder.menuApproval.setVisibility(View.GONE);
            } else {
                holder.menuApproval.setVisibility(View.VISIBLE);
            }
        }
        /*items.remove(2);
        notifyDataSetChanged();*/
        return convertView;
    }
}
