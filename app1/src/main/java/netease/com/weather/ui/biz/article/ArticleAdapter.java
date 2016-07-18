package netease.com.weather.ui.biz.article;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.DataLoadingSubject;
import netease.com.weather.data.model.Article;
import netease.com.weather.ui.base.PageAdapter;

/**
 * Created by user on 16-4-21.
 */
public class ArticleAdapter extends PageAdapter<Article.ArticleEntity> implements DataLoadingSubject.DataLoadingCallbacks {

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_article_item, parent, false));
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        bindCommentHolder(mData.get(position), (CommentHolder) holder);

    }

    private void bindCommentHolder(Article.ArticleEntity article, CommentHolder holder) {
        holder.content.setText(article.getContent());
    }

    @Override
    public void dataStartedLoading() {
        showFooter(true);
    }

    @Override
    public void dataFinishedLoading() {
        showFooter(false);
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