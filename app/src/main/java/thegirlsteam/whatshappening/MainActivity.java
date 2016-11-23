package thegirlsteam.whatshappening;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.onebeacon.api.Beacon;
import io.onebeacon.api.OneBeacon;
import io.onebeacon.api.ScanStrategy;

public class MainActivity extends AppCompatActivity implements ServiceConnection,updateListviewCallback{

    private ListView lst_beacons;

    private BeaconListAdapter beaconListAdapter;
    private MonitorService mService = null;

    private List<Beacon> beacons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!bindService(new Intent(this, MonitorService.class), this, BIND_AUTO_CREATE)) {
            setTitle("Bind failed! Manifest?");
        }

        lst_beacons = (ListView)findViewById(R.id.lst_beacons_list);
        beaconListAdapter = new BeaconListAdapter(this,R.layout.list_item_layout,beacons);
        lst_beacons.setAdapter(beaconListAdapter);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mService = ((MonitorService.LocalServiceBinder) iBinder).getService();
        mService.setMainActivity(this);
        setTitle("Service connected");

        // make the service to stick around by actually starting it
        startService(new Intent(this, MonitorService.class));
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mService = null;
        setTitle("Service disconnected");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Activity is visible, scan with most reliable results
        OneBeacon.setScanStrategy(ScanStrategy.LOW_LATENCY);
    }

    @Override
    protected void onPause() {
        // Activity is not in foreground, make a trade-off between battery usage and scan latency
        OneBeacon.setScanStrategy(ScanStrategy.BALANCED);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // Activity is gone, set scan mode to use lowest possible power usage
        OneBeacon.setScanStrategy(ScanStrategy.LOW_POWER);
        if (null != mService) {
            // optionally stop the service if running in background is not desired
//            stopService(new Intent(this, MonitorService.class));
            unbindService(this);
            mService = null;
        }
        super.onDestroy();
    }

    @Override
    public void addBeacon(Beacon beacon) {
        beacons.add(beacon);
        lst_beacons.setAdapter(beaconListAdapter);
        beaconListAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeBeacon(Beacon beacon) {

    }

    @Override
    public void updateRange(Beacon beacon, int newRange) {

    }
}
