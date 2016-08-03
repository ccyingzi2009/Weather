package netease.com.weather.ui.biz.main;

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
import netease.com.weather.data.model.MainSlider;
import netease.com.weather.ui.base.BaseActivity;

/**
 * Created by user on 16-8-3.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MyViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    private List<MainSlider> mList;
    private BaseActivity mActivity;

    public MainListAdapter(BaseActivity activity
            , List<MainSlider> list) {
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
        MainSlider article = mList.get(position);
        //holder.img.setImageURI(Uri.parse(coverUrl));
        holder.title.setText(article.getTitle());
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        MainSlider article = mList.get(position);
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