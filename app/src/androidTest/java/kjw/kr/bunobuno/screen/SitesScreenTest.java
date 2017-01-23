package kjw.kr.bunobuno.screen;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kjw.kr.bunobuno.Injection;
import kjw.kr.bunobuno.R;
import kjw.kr.bunobuno.bunos.BunoActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by kjwook on 2017. 1. 23..
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SitesScreenTest {

    @Rule
    public ActivityTestRule<BunoActivity> mBunoActivityTestRule = new ActivityTestRule<BunoActivity>(BunoActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();

            Injection.provideSitesRepository(InstrumentationRegistry.getTargetContext()).deleteAllSites();
        }
    };
    @Test
    public void clickAddSiteButton_opensAddSiteUi() {

        //Click on the add site button
        onView(withId(R.id.fab_add_buno)).perform(click());

        // Check if the add site screen is displayed
        onView(withId(R.id.add_site_title)).check(matches(isDisplayed()));
    }

    @Test
    public void addSiteToSitesList() throws Exception {

    }
}
