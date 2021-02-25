package kim.uno.simpleapp.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kim.uno.simpleapp.R
import kim.uno.simpleapp.first
import kim.uno.simpleapp.ui.MainActivity
import kim.uno.simpleapp.waitUntilVisible
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class DocumentFragmentTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testDocument() {
        onView(withId(R.id.search)).perform(replaceText("kakao"))
        onView(withId(R.id.image).first())
            .waitUntilVisible(5)
            .perform(click())

        sleep(1000L)
        onView(withId(R.id.image)).check(matches(isDisplayed()))
    }

    @Test
    fun testDocumentFavorite() {
        onView(withId(R.id.search)).perform(replaceText("kakao"))
        onView(withId(R.id.image).first())
            .waitUntilVisible(5)
            .perform(click())

        sleep(1000L)
        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.favorite)).check(matches(isSelected()))
    }

    @Test
    fun testDocumentFavoriteSync() {
        onView(withId(R.id.search)).perform(replaceText("kakao"))
        onView(withId(R.id.image).first())
            .waitUntilVisible(5)
            .perform(click())

        sleep(1000L)
        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.favorite)).check(matches(isSelected()))

        onView(withId(R.id.back)).perform(click())
        sleep(1000L)
        onView(withId(R.id.favorite).first()).check(matches(isSelected()))
    }

}