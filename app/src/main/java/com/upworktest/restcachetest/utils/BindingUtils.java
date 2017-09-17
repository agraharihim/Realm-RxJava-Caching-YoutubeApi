package com.upworktest.restcachetest.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.upworktest.restcachetest.BR;
import com.upworktest.restcachetest.TestApplication;
import com.upworktest.restcachetest.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

@SuppressWarnings("unused")
public class BindingUtils {

    static ViewModelBinder binder;

    @Nullable
    public static ViewModelBinder getBinder() {
        if (binder == null) binder = new ViewModelBinder() {
            @Override
            public void bind(ViewDataBinding viewDataBinding, ViewModel viewModel) {
                viewDataBinding.setVariable(BR.vm, viewModel);
            }
        };
        return binder;
    }


    @BindingAdapter({"items", "view_provider"})
    public static void bindAdapterWithDefaultBinder(@NonNull RecyclerView recyclerView, @Nullable Observable<List<ViewModel>> items, @Nullable ViewProvider viewProvider) {
        recyclerView.setAdapter(new RecyclerViewAdapter(items, viewProvider, binder));
    }

    @BindingConversion
    @Nullable
    public static <T extends ViewModel> Observable<List<ViewModel>> toGenericList(@Nullable Observable<List<T>> specificList) {
        return specificList == null ? null : specificList.map(new Function<List<T>, List<ViewModel>>() {
            @Override
            public List<ViewModel> apply(List<T> ts) throws Exception {
                return new ArrayList<ViewModel>(ts);
            }
        });
    }

    @BindingConversion
    @Nullable
    public static <T extends ViewModel> Observable<List<ViewModel>> toListObservable(@Nullable List<T> specificList) {
        return specificList == null ? null :
                Observable.just((List<ViewModel>) new ArrayList<ViewModel>(specificList));
    }

    @BindingAdapter("layout_vertical")
    public static void bindLayoutManager(@NonNull RecyclerView recyclerView, boolean vertical) {
        int orientation = vertical ? RecyclerView.VERTICAL : RecyclerView.HORIZONTAL;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), orientation, false));
    }

    @BindingAdapter(value = {"imageUrl", "placeHolder", "errorUrl"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String url, int placeHolder, int errorUrl) {
        if (TextUtils.isEmpty(url) && placeHolder == 0) return;
        Picasso picasso = Picasso.with(imageView.getContext());
        picasso.setLoggingEnabled(false);
        RequestCreator requestCreator;
        requestCreator = picasso.load(url);
        if (placeHolder != 0) {
            requestCreator = requestCreator.placeholder(placeHolder);
        }
        if (errorUrl != 0) {
            requestCreator.error(errorUrl);
        }
        requestCreator.centerCrop()
                .fit().into(imageView);

    }

    @BindingAdapter(value = {"font"})
    public static void setTypeface(TextView textView, String fontName) {
        TestApplication app = TestApplication.getInstance();
        if (app.getFont(fontName) == null)
            app.putFontCache(fontName, fontName);
        textView.setTypeface(app.getFont(fontName));
    }
}
