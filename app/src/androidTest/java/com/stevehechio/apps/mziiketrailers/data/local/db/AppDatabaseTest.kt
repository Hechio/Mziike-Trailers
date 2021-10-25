package com.stevehechio.apps.mziiketrailers.data.local.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.runner.AndroidJUnitRunner
import com.stevehechio.apps.mziiketrailers.data.local.dao.MovieDao
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import org.junit.*
import org.junit.Assert.*

import org.junit.runner.RunWith

/**
 * Created by stevehechio on 10/25/21
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
abstract class AppDatabaseTest {

    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: AppDatabase
    private lateinit var movieDao: MovieDao


    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        movieDao = database.movieDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndGetMovies(){
        val entities  = ArrayList<MoviesEntity>()
        val moviesEntity = MoviesEntity("hechio123","The Mathematician",
            "2021","https://github.com/Hechio","This is the plot",
            "Coding, Sci-Fi, Comedy",null,"7.7",null,"Coming Soon")
        entities.add(moviesEntity)
        movieDao.insertAll(entities)
        val resEntity = movieDao.getAllMovies().blockingFirst()
        assertEquals(moviesEntity.id, resEntity[0].id)
    }

    @Test
    fun testUpdateAndGetMovieById(){
        val entities  = ArrayList<MoviesEntity>()
        val id = "hechio123"
        val plot = "This is the plot"
        val moviesEntity = MoviesEntity("hechio123","The Mathematician",
            "2021","https://github.com/Hechio",null,
            null,null,"7.7",null,"Coming Soon")
        entities.add(moviesEntity)
        movieDao.insertAll(entities)
        movieDao.updateMovie(id,"Coding, Sci-Fi, Comedy","This is the plot",null,null)
        val resEntity = movieDao.getMovieById(id).blockingFirst()
        assertEquals(plot, resEntity.plot)
    }


}