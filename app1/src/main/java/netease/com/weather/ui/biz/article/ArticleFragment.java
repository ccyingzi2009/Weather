package netease.com.weather.ui.biz.article;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import netease.com.weather.R;
import netease.com.weather.ui.base.BaseFragment;

/**
 * Created by user on 16-4-20.
 */
public class ArticleFragment extends BaseFragment {

    @Bind(R.id.send)
    Button mSend;

    LopperThread lopperThread;
    public static ArticleFragment newInstance() {
        Bundle args = new Bundle();
        ArticleFragment top10Fragment = new ArticleFragment();
        top10Fragment.setArguments(args);
        return top10Fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lopperThread = new LopperThread(getContext());
        lopperThread.start();
        String url = "www.baidu.com?param=first";
        mSend.setText(Uri.parse(url).getQueryParameter("param"));

    }



    @OnClick(R.id.send)
    void send(){
        lopperThread.getHandler().obtainMessage(1).sendToTarget();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class LopperThread extends Thread {
        public Handler mHandler;
        public Context mContext;

        public Handler getHandler(){
            return mHandler;
        }

        LopperThread(Context context){
            mContext = context;
        }


        @Override
        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    // process incoming messages here
                    System.out.println(msg.what);
                    if (getView() != null) {
                        Toast.makeText(mContext, msg.what + "", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            Looper.loop();
        }


    }
}
