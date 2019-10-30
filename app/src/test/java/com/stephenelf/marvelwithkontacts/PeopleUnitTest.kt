package com.stephenelf.marvelwithkontacts

import com.stephenelf.marvelwithkontacts.util.People
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PeopleUnitTest {
    @Test
    fun people_isNull() {
       val people:People?=null

        assertNull(people)
    }
}
