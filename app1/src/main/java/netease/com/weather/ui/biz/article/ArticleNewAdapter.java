package netease.com.weather.ui.biz.article;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.DataLoadingSubject;
import netease.com.weather.data.model.ArticleBean;
import netease.com.weather.ui.CustomTextView;
import netease.com.weather.ui.base.PageAdapter;
import netease.com.weather.util.string.StringUtils;

/**
 * Created by user on 16-4-21.
 */
public class ArticleNewAdapter extends PageAdapter<ArticleBean> implements DataLoadingSubject.DataLoadingCallbacks {

    private Activity mActivity;

    public ArticleNewAdapter(Activity activity, OnFooterViewCallback callback) {
        super(callback);
        mActivity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_article_item, parent, false));
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        bindCommentHolder(mData.get(position), (CommentHolder) holder);

    }

    private void bindCommentHolder(ArticleBean article, CommentHolder holder) {
        //holder.content.setText(article.getContent());
        holder.userName.setText(article.getUser());

        String content = article.getContent();
        String[] contentSec = content.split(StringUtils.IMG_NODE);
        List<String> mImgUrls = article.getImgs();
        int position = 0;

        holder.contentContainer.removeAllViews();
        for (int i = 0; i < contentSec.length; i++) {
            String line = contentSec[i];
            //绑定图片
            if (TextUtils.isEmpty(line) && mImgUrls != null && !mImgUrls.isEmpty() && mImgUrls.size() > position) {
                addImg(holder, mImgUrls, position++);
                continue;
            }
            View imgItem = LayoutInflater.from(mActivity).inflate(R.layout.activity_article_item_content, null, false);
            holder.contentContainer.addView(imgItem);
            TextView contentView = (TextView) imgItem.findViewById(R.id.content);
            contentView.setText(Html.fromHtml(line));
            addImg(holder, mImgUrls, position++);
        }
    }

    private void addImg(CommentHolder holder, List<String> mImgUrls, int position) {
        if (mImgUrls.size() <= position) {
            return;
        }
        View imgItem = LayoutInflater.from(mActivity).inflate(R.layout.activity_article_item_img, null, false);
        holder.contentContainer.addView(imgItem);
        ImageView imageView = (ImageView) imgItem.findViewById(R.id.article_img);
        Glide.with(mActivity).load(mImgUrls.get(position)).into(imageView);
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
        CustomTextView content;
        @Bind(R.id.article_user_name)
        TextView userName;
        @Bind(R.id.content_container)
        LinearLayout contentContainer;

        public CommentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
