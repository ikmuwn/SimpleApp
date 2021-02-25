package kim.uno.simpleapp

import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

fun ViewInteraction.waitUntilVisible(second: Int): ViewInteraction {
    val start = System.currentTimeMillis()
    val end = start + second * 1000L

    do {
        try {
            check(matches(isDisplayed()))
            return this
        } catch (e: NoMatchingViewException) {
            Thread.sleep(1000L)
        }
    } while (System.currentTimeMillis() < end)

    throw TimeoutException()
}

fun <T> Matcher<T>.first() = indexOf(0)

fun <T> Matcher<T>.indexOf(index: Int): Matcher<T> {
    return object : BaseMatcher<T>() {
        var count = 0
        lateinit var obj: Any

        override fun matches(item: Any): Boolean {
            if (this@indexOf.matches(item)) {
                if (!::obj.isInitialized) {
                    if (count++ != index) return false
                    obj = item
                }

                return obj == item
            }
            return false
        }

        override fun describeTo(description: Description) {
            description.appendText("should return first matching item")
        }
    }
}