package netease.com.weather.ui.biz.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.model.MainSlider;
import netease.com.weather.ui.MainActivity;
import netease.com.weather.ui.biz.article.ArticleActivity;
import netease.com.weather.ui.biz.article.ArticleModel;
import netease.com.weather.ui.biz.article.ArticleNewFragment;

/**
 * Created by user on 16-8-3.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MyViewHolder> implements View.OnClickListener, StickyRecyclerHeadersAdapter<MainListAdapter.HeaderViewHolder> {
    private LayoutInflater mInflater;
    private List<MainSlider> mList;
    private MainActivity mActivity;

    public MainListAdapter(MainActivity activity
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
        holder.title.setText(article.getTitle());
    }


    private MainSlider getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getHeaderId(int position) {
        String section = getItem(position).getSection();
        return section.hashCode();
    }


    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View v = mInflater.inflate(R.layout.adapter_main_list_item_header, null);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int position) {
        holder.title.setText(getItem(position).getSection());
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
        @BindView(R.id.title)
        TextView title;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    String articleUrl = mList.get(getLayoutPosition()).getArticle_url();
                    articleUrl = articleUrl.substring(articleUrl.indexOf("article"));
                    args.putString(ArticleNewFragment.ARTICLE_URL, articleUrl);
                    String articleId = mList.get(getLayoutPosition()).getArticleId();
                    String boardId = mList.get(getLayoutPosition()).getBoardId();
                    args.putString(ArticleModel.ARTICLE_ID, articleId);
                    args.putString(ArticleModel.ARTICLE_BOARDID, boardId);
                    Intent intent = new Intent(mActivity, ArticleActivity.class);
                    intent.putExtras(args);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
                    }else {
                        mActivity.startActivity(intent);
                    }
                }
            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        public HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

    }
}