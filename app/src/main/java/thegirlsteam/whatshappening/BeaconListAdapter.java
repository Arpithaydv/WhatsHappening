package thegirlsteam.whatshappening;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.onebeacon.api.Beacon;

/**
 * Created by Subin on 11/22/2016.
 */

public class BeaconListAdapter extends ArrayAdapter<Beacon> {

    private List<Beacon> beacons;
    private LayoutInflater layoutInflater;

    public BeaconListAdapter(Context context, int resource, List<Beacon> beacons) {
        super(context, resource);
        this.beacons = beacons;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return beacons.size();
    }

    @Nullable
    @Override
    public Beacon getItem(int position) {
        return beacons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.list_item_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.beaconAddress = (TextView)convertView.findViewById(R.id.txtv_beacon_address);
            viewHolder.beaconRange = (TextView)convertView.findViewById(R.id.txtv_beacon_range);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.beaconAddress.setText(beacons.get(position).getPrettyAddress());
        viewHolder.beaconRange.setText(String.valueOf(beacons.get(position).getAverageRssi()));

        return convertView;
    }

    class ViewHolder{
        TextView beaconAddress;
        TextView beaconRange;
    }
}
