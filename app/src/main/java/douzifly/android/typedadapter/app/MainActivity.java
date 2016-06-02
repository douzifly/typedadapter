package douzifly.android.typedadapter.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import douzifly.android.typedadapter.TypedAdapter;
import douzifly.android.typedadapter.TypedAdapterItem;

public class MainActivity extends AppCompatActivity {

    private ListView mListeView;
    private TypedAdapter<ProductData> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListeView = new ListView(this);
        setContentView(mListeView);
        mAdapter = new TypedAdapter<>(this, 2, typeGetter, renderCallback);
        mListeView.setAdapter(mAdapter);

        List<ProductData> products = new ArrayList<>(100);
        Random r = new Random();
        // mock data
        for (int i = 0 ; i < 100; i++) {
            int type = ProductData.TYPE_BOOK;
            String title;
            if ((i % 3 == 0) || (i % 7 == 0)) {
                type = ProductData.TYPE_DIGITAL;
                title = "Digital:" + i;
            } else {
                title = "Book:" + i;
            }


            ProductData p = new ProductData(title, "$" + r.nextFloat() * 100, type);
            products.add(p);
        }

        mAdapter.setData(products);
    }


    TypedAdapter.ViewTypeGetter<ProductData> typeGetter = new TypedAdapter.ViewTypeGetter<ProductData>() {
        @Override
        public int getViewType(ProductData data) {
           return data.type;
        }
    };

    TypedAdapter.RenderCallback<ProductData> renderCallback = new TypedAdapter.RenderCallback<ProductData>() {
        @Override
        public TypedAdapterItem createItem(ProductData data) {
            if (data.type == ProductData.TYPE_BOOK) {
                return new BookItem();
            } else {
                return new DigitalItem();
            }
        }
    };

    static class ProductData {

        public static final int TYPE_BOOK = 0;
        public static final int TYPE_DIGITAL = 1;

        public String title;
        public String price;
        public int type = TYPE_BOOK;

        public ProductData(String title, String price, int type) {
            this.title = title;
            this.price = price;
            this.type = type;
        }
    }

    static class BookItem extends TypedAdapterItem<ProductData> {

        @Override
        public void bind(ProductData data) {
            ((TextView) getView()).setText(data.title + "    " + data.price);
        }

        @Override
        public View onCreateView(Context context, LayoutInflater inflater, ViewGroup parent) {
            TextView v = new TextView(context);
            v.setPadding(10, 20, 10, 20);
            v.setTextColor(Color.RED);
            return v;
        }
    }

    static class DigitalItem extends TypedAdapterItem<ProductData> {

        @Override
        public void bind(ProductData data) {
            ((TextView) getView()).setText(data.title + "\n" + data.price);
        }

        @Override
        public View onCreateView(Context context, LayoutInflater inflater, ViewGroup parent) {
            TextView v = new TextView(context);
            v.setPadding(10, 20, 10, 20);
            v.setTextColor(Color.GREEN);
            return v;
        }
    }

}
