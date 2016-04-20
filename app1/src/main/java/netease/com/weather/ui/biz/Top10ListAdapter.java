package netease.com.weather.ui.biz;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.model.TopTen;
import netease.com.weather.ui.MainActivity;
import netease.com.weather.util.FrescoUtil;

/**
 * Created by user on 16-3-17.
 */
public class Top10ListAdapter extends RecyclerView.Adapter<Top10ListAdapter.MyViewHolder> {
    private LayoutInflater mInflater;
    private List<TopTen.ArticleEntity> mList;
    private MainActivity mActivity;

    public Top10ListAdapter(MainActivity activity
            , List<TopTen.ArticleEntity> list) {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.adapter_main_list_item, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TopTen.ArticleEntity article = mList.get(position);
        //holder.img.setImageURI(Uri.parse(coverUrl));
        FrescoUtil.loadImage(holder.img, Uri.parse(article.getUser().getFace_url()));

        holder.title.setText(article.getTitle());
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon)
        SimpleDraweeView img;
        @Bind(R.id.title)
        TextView title;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }
}
