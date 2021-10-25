package com.stevehechio.apps.mziiketrailers.utils

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by stevehechio on 10/25/21
 */
class RatingConvertorTest{

    @Test
    fun `test if rating conversion works correctly`(){
        val rating = "8.0"
        val exp = "4.0"
        assertEquals(exp,RatingConvertor.getRating(rating))
    }
}