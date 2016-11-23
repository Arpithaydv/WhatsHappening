package thegirlsteam.whatshappening;

import android.content.Context;
import android.util.Log;

import io.onebeacon.api.Beacon;
import io.onebeacon.api.BeaconsMonitor;
import io.onebeacon.api.Rangeable;

/** Example subclass for a BeaconsMonitor **/
class MyBeaconsMonitor extends BeaconsMonitor {
    MainActivity mainActivity;
    public MyBeaconsMonitor(Context context, MainActivity mainActivity) {
        super(context);
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onBeaconChangedRange(Rangeable rangeable) {
        super.onBeaconChangedRange(rangeable);
        log("something has been changed");
        log(String.format("Range changed to %s for %s", rangeable.getRange(), rangeable));
        mainActivity.updateRange((Beacon)rangeable,rangeable.getRange());
    }

    @Override
    protected void onBeaconAdded(Beacon beacon) {
        super.onBeaconAdded(beacon);
        log("Name: "+beacon.getName()+" pretty address: "+beacon.getPrettyAddress()+" address: "+beacon.getAddress()+" data "+beacon.getData());

        mainActivity.addBeacon(beacon);
        // see Beacon.Type.* for more types, and io.onebeacon.api.spec.* for beacon type interfaces
    }

    // checkout the other available callbacks in the BeaconsManager base class

    private void log(String msg) {
        Log.d("MonitorService", msg);
    }
}