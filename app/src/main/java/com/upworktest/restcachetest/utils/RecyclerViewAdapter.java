package com.upworktest.restcachetest.utils;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.upworktest.restcachetest.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataBindingViewHolder> {
    private @NonNull
    List<ViewModel> latestViewModels = new ArrayList<>();
    private final @NonNull
    ViewProvider viewProvider;
    private final @NonNull
    ViewModelBinder binder;
    private final @NonNull
    Observable<List<ViewModel>> source;
    private final @NonNull
    HashMap<RecyclerView.AdapterDataObserver, Disposable> subscriptions = new HashMap<>();

    public RecyclerViewAdapter(@NonNull Observable<List<ViewModel>> viewModels,
                               @NonNull ViewProvider viewProvider,
                               @NonNull ViewModelBinder viewModelBinder) {
        this.viewProvider = viewProvider;
        this.binder = viewModelBinder;
        source = viewModels
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<ViewModel>>() {
                    @Override
                    public void accept(List<ViewModel> viewModels) throws Exception {
                        latestViewModels = viewModels != null ? viewModels : new ArrayList<ViewModel>();
                        notifyDataSetChanged();
                    }
                })
                .share();
    }

    @Override
    public int getItemViewType(int position) {
        return viewProvider.getView(latestViewModels.get(position));
    }

    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new DataBindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, int position) {
        binder.bind(holder.viewBinding, latestViewModels.get(position));
        holder.viewBinding.executePendingBindings();
    }

    @Override
    public void onViewRecycled(DataBindingViewHolder holder) {
        binder.bind(holder.viewBinding, null);
        holder.viewBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return latestViewModels.size();
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        subscriptions.put(observer, source.subscribe());
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        Disposable subscription = subscriptions.remove(observer);
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    public static class DataBindingViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        final ViewDataBinding viewBinding;

        public DataBindingViewHolder(@NonNull ViewDataBinding viewBinding) {
            super(viewBinding.getRoot());
            this.viewBinding = viewBinding;
        }
    }
}
