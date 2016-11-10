package netease.com.weather.ui.biz.board;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import netease.com.weather.data.model.BoardBean;
import netease.com.weather.ui.MainActivity;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.base.BaseLoadFragment;
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
    public final static String PARAM_BOARD = "param_board";
    private String mBoardId = "list-section";
    public final static String DEFAULT_ACCOUNT = "guest";

    @Override
    protected PageAdapter<BoardBean> createAdapter() {
        return new BoardListAdapter((BaseActivity) getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            if (!TextUtils.isEmpty(args.getString(PARAM_BOARD))) {
                mBoardId = args.getString(PARAM_BOARD);
            }
        }
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

    @Override
    public void onNetResponse(RefreshMode mode, List<BoardBean> response) {
        super.onNetResponse(mode, response);
        if (getAdapter() != null) {
            getAdapter().showFooter(false);
        }
    }
}
