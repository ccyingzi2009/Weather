package netease.com.weather.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import netease.com.weather.R;


/**
 * 分页Adapter，使用ProgressBar样式的Footer
 */
public abstract class PageAdapter<T> extends HeaderFooterRecyclerAdapter<T> {

    private boolean mUseFooter = true;

    public void showFooter(boolean show) {
        mUseFooter = show;
    }

    @Override
    public boolean useHeader() {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindHeaderView(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getBasicItemViewType(int position) {
        return 0;
    }

    @Override
    public boolean useFooter() {
        return mUseFooter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.adapter_footer, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new FooterHolder(view);
    }

    @Override
    public void onBindFooterView(RecyclerView.ViewHolder holder, int position) {

    }

    class FooterHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public FooterHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(android.R.id.progress);
        }
    }
}
