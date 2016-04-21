package netease.com.weather.ui.biz.article;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.model.Article;

/**
 * Created by user on 16-4-21.
 */
public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Article.ArticleEntity> mList = new ArrayList<>();


    public void addItem(List<Article.ArticleEntity> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_article_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindCommentHolder(mList.get(position), (CommentHolder) holder);
    }

    private void bindCommentHolder(Article.ArticleEntity article, CommentHolder holder) {
        holder.content.setText(article.getContent());
    }

    private Article.ArticleEntity getItem(int position) {
        return mList.get(position);
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    /* package */ static class CommentHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.content)
        TextView content;

        public CommentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
