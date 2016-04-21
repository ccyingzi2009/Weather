package netease.com.weather.data;

/**
 * Created by user on 16-4-20.
 */
public abstract class BaseDataManager<T> implements DataLoadingSubject {


    public abstract void onDataLoaded(T data);


    @Override
    public boolean isDataLoading() {
        return false;
    }

    @Override
    public void registerCallback(DataLoadingCallbacks callbacks) {

    }

    @Override
    public void unregisterCallback(DataLoadingCallbacks callbacks) {

    }
}
