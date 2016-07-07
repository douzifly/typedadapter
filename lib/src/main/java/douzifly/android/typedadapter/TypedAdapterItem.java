package douzifly.android.typedadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by douzifly on 5/27/16.
 */
public abstract class TypedAdapterItem<T> {

    protected int mPosition;
    protected View mView;
    protected Context mContext;
    protected T mData;
    protected TypedAdapter<T> mAdapter;

    public void setPosition(int position) {
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }

    public View getView() {
        return mView;
    }

    public Context getContext() {
        return mContext;
    }


    public void bind(T data) {
        mData = data;
        onBind(data);
    }

    public abstract void onBind(T data);

    public abstract View onCreateView(Context context,
                                      LayoutInflater inflater,
                                      ViewGroup parent);

    public final View createView(Context context,
                                 LayoutInflater inflater,
                                 ViewGroup parent) {
        mContext = context;
        mView = onCreateView(context, inflater, parent);
        onViewCreated(mView);
        return mView;
    }

    /**
     * called after onCreateView
     */
    public void onViewCreated(View v) {};
}
