package kjw.kr.bunobuno.data.source;

import android.content.Context;

import com.google.common.collect.Lists;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import kjw.kr.bunobuno.data.Site;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public class SitesRepositoryTest {

    private final static String SITE_TITLE = "naver";
    private final static String SITE_TITLE2 = "kakao";
    private final static String SITE_TITLE3 = "title";

    private static List<Site> SITES = Lists.newArrayList(new Site("Title1", "Password"), new Site("Title2", "Password"));

    private SitesRepository mSitesRepository;

    @Mock
    private SitesDataSource mSitesRemoteDataSource;

    @Mock
    private SitesDataSource mSitesLocalDataSource;

    @Mock
    private Context context;

    @Mock
    private SitesDataSource.GetSiteCallback mGetSiteCallback;

    @Mock
    private SitesDataSource.LoadSitesCallback mLoadSitesCallback;

    @Captor
    private ArgumentCaptor<SitesDataSource.LoadSitesCallback> mSitesCallbackCaptor;

    @Captor
    private ArgumentCaptor<SitesDataSource.GetSiteCallback> mSiteCallbackCaptor;

    @Before
    public void setupSitesRepository() {
        MockitoAnnotations.initMocks(this);

        mSitesRepository = SitesRepository.getInstance(mSitesRemoteDataSource, mSitesLocalDataSource);
    }

    @After
    public void destroyRepositoryInstance() {
        SitesRepository.destroyInstance();
    }

    @Test
    public void getSites_repositoryCachesAfterFirstAPiCall() {
        twoTasksLoadCallsToRepository(mLoadSitesCallback);
    }

    @Test
    public void getSites_requestAllSitesFromLocalDataSource() {
        mSitesRepository.getSites(mLoadSitesCallback);
        verify(mSitesLocalDataSource).getSites(any(SitesDataSource.LoadSitesCallback.class));
    }

    @Test
    public void saveSite_savesSiteToServiceAPI() {

        Site newSite = new Site(SITE_TITLE, "password");

        // When a task is saved to the tasks repository
        mSitesRepository.saveSite(newSite);

        // Then the service API and persistent repository are called and the cache is updated
        verify(mSitesLocalDataSource).saveSite(newSite);
        verify(mSitesRemoteDataSource).saveSite(newSite);
        assertThat(mSitesRepository.mCachedSites.size(), is(1));
    }

    /**
     * Convenience method that issues two calls to the tasks repository
     */
    private void twoTasksLoadCallsToRepository(SitesDataSource.LoadSitesCallback callback) {

        // When tasks are requested from repository
        mSitesRepository.getSites(callback);

        // Use the Mockito Captor to capture the callback
        verify(mSitesLocalDataSource).getSites(mSitesCallbackCaptor.capture());

        // Local data source doesn't have data yet
        mSitesCallbackCaptor.getValue().onDataNotAvailable();

        // Verify the remote data source is queried
        verify(mSitesRemoteDataSource).getSites(mSitesCallbackCaptor.capture());

        // Trigger callback so tasks are cached
        mSitesCallbackCaptor.getValue().onSitesLoaded(SITES);

        mSitesRepository.getSites(callback);

    }
}
