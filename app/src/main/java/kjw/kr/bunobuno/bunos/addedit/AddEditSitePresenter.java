package kjw.kr.bunobuno.bunos.addedit;

import android.support.annotation.NonNull;

import kjw.kr.bunobuno.BasePresenter;
import kjw.kr.bunobuno.BaseView;
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
        if ( !isNewTask() && mIsDataMissing ) {
            populateSite();
        }

    }

    @Override
    public void populateSite() {

        if (isNewTask()) {
            throw new RuntimeException("populateTask() was called but task is new.");
        }

        mSiteRepository.getSite(mSiteId, this);
    }

    @Override
    public void saveSite(String title, String password) {
        if ( isNewTask() )
            createTask(title, password);
        else
            updateTask(title, password);
    }

    private void updateTask(String title, String password) {

        if (isNewTask()) {
            throw new RuntimeException("updateTask() was called but task is new.");
        }

        Site site = new Site(title, password, mSiteId);
        mSiteRepository.saveSite(site);
        mAddSiteView.showSitesList();

    }

    private void createTask(String title, String password) {

        Site site = new Site(title, password);
        if ( site.isEmpty() )
            mAddSiteView.showEmptySiteError();
        else {
            mSiteRepository.saveSite(site);
            mAddSiteView.showSitesList();
        }

    }

    @Override
    public void onSiteLoaded(Site site) {

    }

    @Override
    public void onDataNotAvailable() {

    }

    private boolean isNewTask() {
        return mSiteId == null;
    }
}
