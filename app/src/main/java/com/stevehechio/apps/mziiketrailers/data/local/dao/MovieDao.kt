package com.stevehechio.apps.mziiketrailers.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
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

    @Query("DELETE FROM MOVIE_TABLE")
    fun deleteAll()

    @Query("SELECT * FROM MOVIE_TABLE")
    fun getAllMovies(): Observable<List<MoviesEntity>>

    @Query("SELECT * FROM MOVIE_TABLE WHERE id = :movieId")
    fun getMovieById(movieId: String): Flowable<MoviesEntity>
}