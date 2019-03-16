package com.ptit.filmdictionary.data.source.remote;

import com.ptit.filmdictionary.data.model.Actor;
import com.ptit.filmdictionary.data.model.Movie;
import com.ptit.filmdictionary.data.source.remote.response.GenreResponse;
import com.ptit.filmdictionary.data.source.remote.response.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {
    @GET("genre/movie/list")
    Observable<GenreResponse> getGenres();

    @GET("trending/movie/day")
    Observable<MovieResponse> getMoviesTrendingByDay();

    @GET("movie/{type}")
    Observable<MovieResponse> getMoviesByCategory(@Path("type") String type,
                                                  @Query("page") int page);

    @GET("discover/movie")
    Observable<MovieResponse> getMoviesByGenre(@Query("with_genres") String idGenre,
                                               @Query("page") int page);

    @GET("discover/movie")
    Observable<MovieResponse> getMoviesByActor(@Query("with_cast") String idCast,
                                               @Query("page") int page);

    @GET("discover/movie")
    Observable<MovieResponse> getMoviesByCompany(@Query("with_companies") int idCompany,
                                                 @Query("page") int page);

    @GET("movie/{movie_id}?append_to_response=credits,videos")
    Observable<Movie> getMovieDetail(@Path("movie_id") int id);

    @GET("search/movie")
    Observable<MovieResponse> searchMovieByName(@Query("query") String key,
                                                @Query("page") int page);

    @GET("person/{actor_id}")
    Observable<Actor> getProfile(@Path("actor_id") String actorId);
}
