package kjw.kr.bunobuno.bunos.sites.addedit;

import android.support.annotation.NonNull;

import kjw.kr.bunobuno.data.Site;
import kjw.kr.bunobuno.data.source.SitesDataSource;
import kjw.kr.bunobuno.data.source.SitesRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public class AddEditSitePresenter implements AddEditSiteContract.Presenter, SitesDataSource.GetSiteCallback{

    @NonNull
    private final SitesRepository mSiteRepository;

    @NonNull
    private final AddEditSiteContract.View mAddSiteView;

    private final String mSiteId;
    private final boolean mIsDataMissing;

    public AddEditSitePresenter(String siteId, SitesRepository sitesRepository, AddEditSiteContract.View addEditSiteView, boolean shouldLoadDataFromRepo) {
        mSiteId = siteId;
        mSiteRepository = checkNotNull(sitesRepository);
        mAddSiteView = checkNotNull(addEditSiteView);
        mIsDataMissing = shouldLoadDataFromRepo;
    }

    @Override
    public void start() {
        if ( !isNewSite() && mIsDataMissing ) {
            populateSite();
        }

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void populateSite() {

        if (isNewSite()) {
            throw new RuntimeException("populateTask() was called but task is new.");
        }

        mSiteRepository.getSite(mSiteId, this);
    }

    @Override
    public void saveSite(String title, String id, String password) {
        if ( isNewSite() )
            createSite(title, id, password);
        else
            updateTask(title, id, password);
    }

    private void updateTask(String title, String id, String password) {

        if (isNewSite()) {
            throw new RuntimeException("updateTask() was called but task is new.");
        }

        Site site = new Site(title, id, password, mSiteId);
        mSiteRepository.saveSite(site);
        mAddSiteView.showSitesList();

    }

    private void createSite(String title, String id, String password) {

        Site site = new Site(title, id, password);
        if ( site.isEmpty() )
            mAddSiteView.showEmptySiteError();
        else {
            mSiteRepository.saveSite(site);
            mAddSiteView.showSitesList();
        }

    }

    @Override
    public void onSiteLoaded(Site site) {
        mAddSiteView.setTitle(site.getTitle());
        mAddSiteView.setPassword(site.getPassword());
        mAddSiteView.setSiteId(site.getSiteId());
    }

    @Override
    public void onDataNotAvailable() {
    }

    private boolean isNewSite() {
        return mSiteId == null;
    }
}
