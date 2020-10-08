package com.example.android.trainingplanner


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.endsWith
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun moveAtTestedFragment() {
        onView(withId(R.id.training_templates_button)).perform(click())
    }

    @Test
    fun templateName_isEntered() {
        val testTemplateName = "Test Name"

        onView(withId(R.id.create_button)).perform(click())
        onView(withId(R.id.tNameForRedaction)).perform(typeText(testTemplateName))
        onView(withId(R.id.okNameButton)).perform(click())

        onView(withId(R.id.tNameText)).check(matches(withText(testTemplateName)))

    }

}
