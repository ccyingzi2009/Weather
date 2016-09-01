package netease.com.weather.ui.biz.update;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;

/**
 * Created by liu_shuai on 16/9/1.
 */
public class VersionUpdateService extends Service {
    private DownloadManager mDownloadManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle args = intent.getExtras();
        String url = args.getString(VersionUpdateModel.UPDATE_URL);
        initDownload(url);
        return super.onStartCommand(intent, flags, startId);
    }

    private void initDownload(String url) {
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "smth.apk");
        mDownloadManager.enqueue(request);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
}
