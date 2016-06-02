package douzifly.android.typedadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by douzifly on 5/26/16.
 */
public class TypedAdapter<T> extends BaseAdapter {

    private static final String TAG = "TypedAdapter";

    public interface ViewTypeGetter<T> {
        int getViewType(T data);
    }

    public interface RenderCallback<T> {
        TypedAdapterItem createItem(T data);
    }

    private List<T> mDatas;
    private Context mContext;
    private int mViewTypeCount;
    private ViewTypeGetter<T> mViewTypeGetter;
    private RenderCallback<T> mRenderCallback;

    public TypedAdapter(Context context,
                        int viewTypeCount,
                        ViewTypeGetter<T> typeGetter,
                        RenderCallback<T> renderCallback) {
        mContext = context;
        mViewTypeGetter = typeGetter;
        if (viewTypeCount <= 0) {
            mViewTypeCount = 1;
        } else {
            mViewTypeCount = viewTypeCount;
        }
        mRenderCallback = renderCallback;
        if (mRenderCallback == null) {
            throw new IllegalStateException("RenderCallback can't be null");
        }
    }

    public void setData(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return mViewTypeCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mViewTypeGetter == null) {
            return 0;
        }
        return mViewTypeGetter.getViewType(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        T data = getItem(position);

        TypedAdapterItem item = null;

        if (v == null) {
            // create view now
            item = mRenderCallback.createItem(data);
            v = item.createView(mContext, LayoutInflater.from(mContext), parent);
            v.setTag(item);
        } else {
            item = (TypedAdapterItem) v.getTag();
        }

        item.setPosition(position);

        // bind data
        item.bind(data);
        return v;
    }

}
