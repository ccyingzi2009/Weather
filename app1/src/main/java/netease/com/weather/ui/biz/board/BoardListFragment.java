package netease.com.weather.ui.biz.board;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import netease.com.weather.data.model.BoardBean;
import netease.com.weather.ui.base.BaseLoadListFragment;
import netease.com.weather.ui.base.PageAdapter;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.ui.biz.pc.AccountModel;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.JsonRequest;

/**
 * Created by user on 16-10-28.
 */

public class BoardListFragment extends BaseLoadListFragment<BoardBean> {
    private String mBoardId = "list-section";
    public final static String DEFAULT_ACCOUNT = "guest";

    @Override
    protected PageAdapter<BoardBean> createAdapter() {
        return new BoardListAdapter((BoardActivity) getActivity());
    }

    @Override
    protected BaseRequest<List<BoardBean>> onCreateNet(RefreshMode refreshMode) {
        String account = AccountModel.isLogin() ?
                (!TextUtils.isEmpty(AccountModel.getUserId()) ? AccountModel.getUserId() : DEFAULT_ACCOUNT) : DEFAULT_ACCOUNT;
        String url = String.format(Constants.URL_BOARD, account, mBoardId);

        JsonRequest r = new JsonRequest<List<BoardBean>>(url, new TypeToken<List<BoardBean>>() {}, null);
        r.setEncode(JsonRequest.ENCODE_GBK);
        return r;
    }
}
