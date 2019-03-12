package com.ptit.filmdictionary.ui.actor.info;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.ptit.filmdictionary.data.model.Actor;
import com.ptit.filmdictionary.data.source.MovieRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActorInfoViewModel {
    private static final String FEMALE_CODE = "1";
    private static final String FEMALE = "Female";
    private static final String MALE = "Male";
    private MovieRepository mMovieRepository;
    private CompositeDisposable mCompositeDisposable;
    public final ObservableField<String> mName = new ObservableField<>();
    public final ObservableField<String> mGender = new ObservableField<>();
    public final ObservableField<String> mBirthday = new ObservableField<>();
    public final ObservableField<String> mPlaceOfBirth = new ObservableField<>();
    public final ObservableField<String> mDepartment = new ObservableField<>();
    public final ObservableField<String> mBiography = new ObservableField<>();
    public final ObservableField<String> mPopularity = new ObservableField<>();
    public final ObservableInt mProogress = new ObservableInt();
    private ActorInfoNavigator mNavigator;

    public ActorInfoViewModel(MovieRepository movieRepository, ActorInfoNavigator navigator) {
        mMovieRepository = movieRepository;
        mCompositeDisposable = new CompositeDisposable();
        mNavigator = navigator;
    }

    public void loadProfile(String actorId) {
        Disposable disposable = mMovieRepository.getProfile(actorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actor -> {
                    bindData(actor);
                    mNavigator.showContentView();
                });
        mCompositeDisposable.add(disposable);
    }

    private void bindData(Actor actor) {
        mName.set(actor.getName());
        mGender.set(actor.getGender().equals(FEMALE_CODE) ? FEMALE : MALE);
        mBirthday.set(actor.getBirthday());
        mPlaceOfBirth.set(actor.getPlace());
        mDepartment.set(actor.getDepartment());
        mBiography.set(actor.getBiography());
        mPopularity.set(String.valueOf((int) Double.parseDouble(actor.getPopularity())));
        mProogress.set((int) Double.parseDouble(actor.getPopularity()));
    }

    public void destroy() {
        mCompositeDisposable.dispose();
    }

}
