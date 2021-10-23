package com.stevehechio.apps.mziiketrailers.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stevehechio.apps.mziiketrailers.data.local.converters.ActorsListTypeConvertor
import com.stevehechio.apps.mziiketrailers.data.local.converters.TrailerTypeConverter
import com.stevehechio.apps.mziiketrailers.data.local.dao.MovieDao
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.utils.AppConstants

/**
 * Created by stevehechio on 10/23/21
 */
@Database(entities = [MoviesEntity::class],version = AppConstants.DB_VERSION,exportSchema = false)
@TypeConverters(ActorsListTypeConvertor::class,TrailerTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}