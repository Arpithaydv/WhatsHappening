package thegirlsteam.whatshappening;

import io.onebeacon.api.Beacon;

/**
 * Created by Subin on 11/22/2016.
 */

public interface updateListviewCallback {

    void addBeacon(Beacon beacon);
    void removeBeacon(Beacon beacon);
    void updateRange(Beacon beacon,int newRange);
}
