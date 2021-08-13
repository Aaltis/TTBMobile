package fi.breakwaterworks.trackthatbarbellmobile;

/**
 * Created by Aaltis on 29.11.2017.
 */

/* https://medium.com/@manuelvicnt/android-wear-accessing-the-data-layer-api-d64fd55982e3 */
public class MobileWearableService /*extends WearableListenerService
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String PATH_DATA = "/count";
    private GoogleApiClient mGoogleApiClient;
    private boolean nodeConnected = false;
    private int count = 0;

    private static final String COUNT_KEY = "com.example.key.count";

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        nodeConnected = true;
        Wearable.DataApi.addListener(mGoogleApiClient, DataApi.DataListener);

    }

    @Override
    public void onConnectionSuspended(int i) {
        nodeConnected = false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        nodeConnected = false;
        Log.d("", "onConnectionFailed: " + connectionResult.getErrorMessage());
    }
    @Override
    public void onDataChanged(DataEventBuffer dataEvents)
    {
        final ArrayList<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);

        for (DataEvent event : events)
        {
            PutDataMapRequest putDataMapRequest =
                    PutDataMapRequest.createFromDataMapItem(DataMapItem.fromDataItem(event.getDataItem()));

            String path = event.getDataItem().getUri().getPath();
            if (event.getType() == DataEvent.TYPE_CHANGED)
            {
                if (PATH_DATA.equals(path))
                {
                    DataMap data = putDataMapRequest.getDataMap();
                    String data = data.getString("data");
                }
                else if (event.getType() == DataEvent.TYPE_DELETED)
                {

                }
            }
        }

    // Create a data map and put data in it
    private void increaseCounter() {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/count");
        putDataMapReq.getDataMap().putInt(COUNT_KEY, count++);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult =
                Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
    }*/
{
}