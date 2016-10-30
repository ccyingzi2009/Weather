package netease.com.weather.ui.biz.board;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.DataLoadingSubject;
import netease.com.weather.data.model.ArticleSingleBean;
import netease.com.weather.data.model.BoardBean;
import netease.com.weather.ui.base.PageAdapter;
import netease.com.weather.ui.biz.article.ArticleActivity;
import netease.com.weather.ui.biz.pics.PicShowActivity;
import netease.com.weather.util.StringUtils;

/**
 * Created by user on 16-4-21.
 */
public class BoardListAdapter extends PageAdapter<BoardBean> {

    private BoardActivity mActivity;

    public BoardListAdapter(BoardActivity activity) {
        super(null);
        mActivity = activity;
    }

    @Override
    public boolean useFooter() {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        return new BoardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_board_item, parent, false));
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        BoardViewHolder viewHolder = (BoardViewHolder) holder;
        viewHolder.boardName.setText(mData.get(position).getT());

    }


    /* package */ static class BoardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.board_name)
        TextView boardName;

        public BoardViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
