package com.stephenelf.marvelwithkontacts

import android.net.Uri
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.stephenelf.marvelwithkontacts.util.People
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PeopleInstrumentedTest {

    private lateinit var people: People

    @Before
    fun createPeople() {
        people= People("Test user", Uri.parse("http://www.google.com"),null)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.stephenelf.marvelwithkontacts", appContext.packageName)
    }

    @Test
    fun people__notNull(){
        assertNotNull(people)
    }
}
