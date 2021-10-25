package com.stevehechio.apps.mziiketrailers.data.local.dao

import androidx.room.*
import com.stevehechio.apps.mziiketrailers.data.local.entities.Actor
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.data.local.entities.Trailer
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Created by stevehechio on 10/23/21
 */

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(moviesEntity: MoviesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(moviesEntity: List<MoviesEntity>)

    @Query("UPDATE MOVIE_TABLE SET genres = :genre, plot = :plot, trailer = :trailer, actorList = :actorList WHERE id = :id")
    fun updateMovie(id: String,genre: String?,plot: String?,trailer: Trailer?,actorList: List<Actor>?): Int

    @Query("DELETE FROM MOVIE_TABLE")
    fun deleteAll()

    @Query("SELECT * FROM MOVIE_TABLE")
    fun getAllMovies(): Observable<List<MoviesEntity>>

    @Query("SELECT * FROM MOVIE_TABLE WHERE id = :movieId")
    fun getMovieById(movieId: String): Observable<MoviesEntity>
}