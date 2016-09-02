package netease.com.weather.ui.biz.update;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
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
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "smth.apk");
        request.setTitle("下载中");
        request.setDescription("升级中...");
        long downloadId = mDownloadManager.enqueue(request);
        registerReceiver(new DownloadBroadcastReceiver(downloadId), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private static class DownloadBroadcastReceiver extends BroadcastReceiver{

        long mDownloadId;

        public DownloadBroadcastReceiver(long downloadId) {
            mDownloadId = downloadId;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            long id = intent.getExtras().getLong(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (mDownloadId != id) {
                return;
            }
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(mDownloadId);
            Cursor c = dm.query(query);
            if (c != null && c.moveToFirst()) {
                int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                // 下载失败也会返回这个广播，所以要判断下是否真的下载成功
                if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                    // 获取下载好的 apk 路径
                    String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    // 提示用户安装
                    installAPP(Uri.parse(uriString), context);
                }
                c.close();
            }
        }

        private void installAPP(Uri data, Context context) {
            Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                    .setDataAndType(data, "application/vnd.android.package-archive");
            // FLAG_ACTIVITY_NEW_TASK 可以保证安装成功时可以正常打开 app
            promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(promptInstall);
        }
    }
}
