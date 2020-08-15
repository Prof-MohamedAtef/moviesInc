package mo.ed.movies.inc.ui.activities


import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import mo.ed.movies.inc.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MovieAppTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun movieTest() {
        try {
            val imageView = onView(
                allOf(
                    withId(R.id.moviePoster),
                    childAtPosition(
                        allOf(
                            withId(R.id.constraint_layout),
                            childAtPosition(
                                withId(R.id.recycler),
                                0
                            )
                        ),
                        0
                    ),
                    isDisplayed()
                )
            )
            imageView.check(matches(isDisplayed()))
        }catch (e: NoMatchingViewException){
            Log.e("notfound","imageView" )
        }

        val constraintLayout = onView(
            allOf(
                withId(R.id.constraint_layout),
                childAtPosition(
                    allOf(
                        withId(R.id.recycler),
                        childAtPosition(
                            withId(R.id.swipe_refresh_layout),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        constraintLayout.perform(click())

        val ratingBar = onView(
            allOf(
                withId(R.id.ratingBar),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                        1
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        ratingBar.check(matches(isDisplayed()))

        val viewGroup = onView(
            allOf(
                withId(R.id.actor_constraint_layout),
                childAtPosition(
                    allOf(
                        withId(R.id.recycler),
                        childAtPosition(
                            withId(R.id.swipe_refresh_layout),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))


        try {
            val imageView3 = onView(
                allOf(
                    withId(R.id.actorProfile),
                    childAtPosition(
                        allOf(
                            withId(R.id.actor_constraint_layout),
                            childAtPosition(
                                withId(R.id.recycler),
                                2
                            )
                        ),
                        0
                    ),
                    isDisplayed()
                )
            )
            imageView3.check(matches(isDisplayed()))
        }catch (e: NoMatchingViewException){
            Log.e("notfound","imageView3" )
        }


        try {
            val imageButton = onView(
                allOf(
                    withContentDescription("Navigate up"),
                    childAtPosition(
                        allOf(
                            withId(R.id.detail_toolbar),
                            childAtPosition(
                                withId(R.id.appbar),
                                0
                            )
                        ),
                        0
                    ),
                    isDisplayed()
                )
            )
            imageButton.check(matches(isDisplayed()))

        }catch (e: NoMatchingViewException){
            Log.e("notfound","imageButton" )
        }

        try {
            val linearLayout = onView(
                allOf(
                    withId(R.id.appbar),
                    childAtPosition(
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                            0
                        ),
                        0
                    ),
                    isDisplayed()
                )
            )
            linearLayout.check(matches(isDisplayed()))
        }catch (e: NoMatchingViewException){
            Log.e("notfound","linearLayout" )
        }

        try {
            val constraintLayout2 = onView(
                allOf(
                    withId(R.id.actor_constraint_layout),
                    childAtPosition(
                        allOf(
                            withId(R.id.recycler),
                            childAtPosition(
                                withId(R.id.swipe_refresh_layout),
                                0
                            )
                        ),
                        0
                    ),
                    isDisplayed()
                )
            )
            constraintLayout2.perform(click())
        }catch (e: NoMatchingViewException){
            Log.e("notfound","constraintLayout2" )
        }
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
