package netease.com.weather.ui.biz.board;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.model.BoardBean;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.base.PageAdapter;

/**
 * Created by user on 16-4-21.
 */
public class BoardListAdapter extends PageAdapter<BoardBean> {

    private BaseActivity mActivity;

    public BoardListAdapter(BaseActivity activity) {
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

        String boardName = mData.get(position).getT();
        viewHolder.boardName.setText(patternBoardName(boardName));

        String id = mData.get(position).getId();
        if (!TextUtils.isEmpty(id)) {//id不为空则为版块分组
            viewHolder.boardArrow.setVisibility(View.VISIBLE);
        } else {
            viewHolder.boardArrow.setVisibility(View.GONE);
        }

    }

    private String patternBoardName(String t) {
        Pattern pattern = Pattern.compile(">([^<>]+)<");
        Matcher uMatcher = pattern.matcher(t);
        if (uMatcher.find()) {
            t = uMatcher.group(0);
            t = t.replaceAll("<", "").replaceAll(">", "");
        }
        return t;
    }


    class BoardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.board_name)
        TextView boardName;
        @BindView(R.id.board_arrow)
        ImageView boardArrow;

        public BoardViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    BoardBean boardBean = mData.get(position);
                    String id = boardBean.getId();
                    if (!TextUtils.isEmpty(id)) {//id不为空则为版块分组
                        Bundle args = new Bundle();
                        args.putString(BoardListFragment.PARAM_BOARD, id);
                        Intent i = new Intent(mActivity, BoardActivity.class);
                        i.putExtras(args);
                        mActivity.startActivity(i);
                    } else {

                    }
                }
            });

        }
    }
}
