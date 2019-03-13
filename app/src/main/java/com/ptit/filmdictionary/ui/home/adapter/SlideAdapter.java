package com.ptit.filmdictionary.ui.home.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.databinding.ItemSlideBinding;
import com.ptit.filmdictionary.ui.home.ItemMovieViewModel;

public class SlideAdapter extends PagerAdapter implements View.OnClickListener {
    private ObservableList<Movie> mMovies;
    private SlideListener mListener;
    private ItemSlideBinding mBinding;

    public SlideAdapter(SlideListener listener) {
        mListener = listener;
        mMovies = new ObservableArrayList<>();
    }

    @Override
    public int getCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    public void update(ObservableList<Movie> movies){
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_slide, container,
                true);
        if(mBinding.getViewModel() == null){
            mBinding.setViewModel(new ItemMovieViewModel());
        }
        mBinding.getViewModel().setMovie(mMovies.get(position));
        mBinding.imageSlide.setOnClickListener(this);
        mBinding.executePendingBindings();
        return mBinding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public void onClick(View v) {
        if(mListener!= null){
            mListener.onSlideClickListener(mBinding.getViewModel().getMovie());
        }
    }

    public interface SlideListener {
        void onSlideClickListener(Movie movie);
    }

}
