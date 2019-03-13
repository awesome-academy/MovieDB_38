package com.ptit.filmdictionary.ui.home.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.filmdictionary.R;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.databinding.ItemVericalMovieBinding;
import com.ptit.filmdictionary.ui.home.ItemMovieViewModel;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private MovieListener mListener;
    private ObservableList<Movie> mMovies;
    private Context mContext;

    public MovieAdapter(MovieListener listener) {
        mListener = listener;
        mMovies = new ObservableArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemVericalMovieBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_verical_movie, viewGroup, false);
        return new ViewHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mMovies.get(i));
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    public void update(ObservableList<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MovieListener mListener;
        private ItemVericalMovieBinding mBinding;

        public ViewHolder(ItemVericalMovieBinding binding, MovieListener listener) {
            super(binding.getRoot());
            mListener = listener;
            mBinding = binding;
        }

        public void bindData(Movie movie) {
            mBinding.setViewModel(new ItemMovieViewModel());
            mBinding.getViewModel().setMovie(movie);
            mBinding.itemMovie.setOnClickListener(this);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onMovieClickListener(mBinding.getViewModel().getMovie());
            }
        }
    }

    public interface MovieListener {
        void onMovieClickListener(Movie movie);
    }
}
