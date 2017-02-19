package kjw.kr.bunobuno.bunos.sites;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.List;

import kjw.kr.bunobuno.bunos.addedit.AddEditSiteActivity;
import kjw.kr.bunobuno.data.Site;
import kjw.kr.bunobuno.data.source.SitesDataSource;
import kjw.kr.bunobuno.data.source.SitesRepository;
import kjw.kr.bunobuno.util.EspressoIdlingResource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public class SitesPresenter implements SitesContract.Presenter {

    private final SitesRepository mSitesRepository;
    private final SitesContract.View mSitesView;

    private boolean mFirstLoad = true;

    public SitesPresenter(@NonNull SitesRepository sitesRepository, @NonNull SitesContract.View sitesView) {
        mSitesRepository = checkNotNull(sitesRepository, "SItesRepository can not be null");
        mSitesView = checkNotNull(sitesView, "SiteView can not be null");

        mSitesView.setPresenter(this);
    }


    @Override
    public void loadSites(boolean forceUpdate) {
        loadSites(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void addNewSite() {
        mSitesView.showAddSite();
    }

    @Override
    public void openSiteDetails(@NonNull Site requestedSite) {

    }

    @Override
    public void result(int requestCode, int resultCode) {
        // If a task was successfully added, show snackbar
        if (AddEditSiteActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
            mSitesView.showSuccessfullySavedMessage();
        }
    }

    @Override
    public void start() {

    }

    private void loadSites(boolean forceUpdate, final boolean showLoadingUI) {
        if ( showLoadingUI )
            mSitesView.setLoadingIndicator(true);

        if ( forceUpdate )
            mSitesRepository.refreshSites();

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        mSitesRepository.getSites(new SitesDataSource.LoadSitesCallback() {
            @Override
            public void onSitesLoaded(List<Site> sites) {

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                mSitesView.setLoadingIndicator(false);
//                mSitesView.showSites(sites);

                processSites(sites);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void processSites(List<Site> sites) {
        if ( sites.isEmpty() ) {
        } else {
            mSitesView.showSites(sites);
        }
    }

}
