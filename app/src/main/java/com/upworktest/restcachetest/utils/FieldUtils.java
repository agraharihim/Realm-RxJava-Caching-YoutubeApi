package com.upworktest.restcachetest.utils;

import android.databinding.Observable.OnPropertyChangedCallback;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

public class FieldUtils {
    @NonNull
    public static <T> Observable<T> toObservable(@NonNull final ObservableField<T> field) {

        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(final ObservableEmitter<T> e) throws Exception {
                T initialValue = field.get();
                if (initialValue != null) {
                    e.onNext(initialValue);
                }
                final OnPropertyChangedCallback callback = new OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(android.databinding.Observable observable, int i) {
                        e.onNext(field.get());
                    }
                };
                field.addOnPropertyChangedCallback(callback);
                e.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        field.removeOnPropertyChangedCallback(callback);
                    }
                });
            }
        });
    }
}
