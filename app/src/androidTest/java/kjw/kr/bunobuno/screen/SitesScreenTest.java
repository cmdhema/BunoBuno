package kjw.kr.bunobuno.screen;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kjw.kr.bunobuno.Injection;
import kjw.kr.bunobuno.R;
import kjw.kr.bunobuno.bunos.BunoActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by kjwook on 2017. 1. 23..
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SitesScreenTest {

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<BunoActivity> mBunoActivityTestRule = new ActivityTestRule<BunoActivity>(BunoActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();

            Injection.provideSitesRepository(InstrumentationRegistry.getTargetContext()).deleteAllSites();
        }
    };

    /**
     * A custom {@link Matcher} which matches an item in a {@link ListView} by its text.
     * <p>
     * View constraints:
     * <ul>
     * <li>View must be a child of a {@link ListView}
     * <ul>
     *
     * @param itemText the text to match
     * @return Matcher that matches text in the given view
     */
    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(
                        isDescendantOfA(isAssignableFrom(ListView.class)),
                        withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA LV with text " + itemText);
            }
        };
    }

    @Test
    public void clickAddSiteButton_opensAddSiteUi() {

        //Click on the add site button
        onView(withId(R.id.fab_add_buno)).perform(click());

        // Check if the add site screen is displayed
        onView(withId(R.id.add_site_title)).check(matches(isDisplayed()));
    }

    @Test
    public void addSiteToSitesList() throws Exception {
        String newSiteTitle = "google";
        String newSitePassword = "test";
        String editSiteTitle = "naver";
        String editSitePassword = "password";

        createSite(newSiteTitle, newSitePassword);

        // Click on the site on the list
        onView(withText(newSiteTitle)).perform(click());

        onView(withId(R.id.add_site_title)).perform(replaceText(editSiteTitle), closeSoftKeyboard());
        onView(withId(R.id.add_site_password)).perform(replaceText("password"), closeSoftKeyboard());

        onView(withId(R.id.fab_edit_task_done)).perform(click());

        onView(withItemText(editSiteTitle)).check(matches(isDisplayed()));

        onView(withItemText(newSiteTitle)).check(doesNotExist());

    }

    private void createSite(String title, String password) {
        onView(withId(R.id.fab_add_buno)).perform(click());

        onView(withId(R.id.add_site_title)).perform(typeText(title), closeSoftKeyboard());
        onView(withId(R.id.add_site_password)).perform(typeText(password), closeSoftKeyboard());
    }
}
