package kim.uno.simpleapp.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kim.uno.simpleapp.R
import kim.uno.simpleapp.first
import kim.uno.simpleapp.ui.MainActivity
import kim.uno.simpleapp.waitUntilVisible
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSearch() {
        onView(withId(R.id.search)).perform(replaceText("kakao"))
        onView(withId(R.id.image).first()).waitUntilVisible(5)
            .check(matches(isDisplayed()))
    }

}