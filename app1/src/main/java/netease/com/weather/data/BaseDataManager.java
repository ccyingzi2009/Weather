package netease.com.weather.data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by user on 16-4-20.
 */
public abstract class BaseDataManager<T> implements DataLoadingSubject {

    private AtomicInteger mAtomicInteger;

    public BaseDataManager() {
        mAtomicInteger = new AtomicInteger(0);
    }

    public void onDataLoaded(T data, boolean isRefresh) {
    }


    @Override
    public boolean isDataLoading() {
        return mAtomicInteger.get() > 0;
    }

    protected void loadStarted(){
        mAtomicInteger.getAndIncrement();
    }
    protected void loadFinish(){
        mAtomicInteger.decrementAndGet();
    }

    @Override
    public void registerCallback(DataLoadingCallbacks callbacks) {

    }

    @Override
    public void unregisterCallback(DataLoadingCallbacks callbacks) {

    }
}
