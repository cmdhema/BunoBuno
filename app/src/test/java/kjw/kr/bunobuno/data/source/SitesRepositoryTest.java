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

    private SitesRepository mSItesRepository;

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
    private ArgumentCaptor<SitesDataSource.GetSiteCallback> mSIteCallbackCaptor;

    @Before
    public void setupSitesRepository() {
        MockitoAnnotations.initMocks(this);

        mSItesRepository = SitesRepository.getInstance(mSitesRemoteDataSource, mSitesLocalDataSource);
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
        mSItesRepository.getSites(mLoadSitesCallback);
        verify(mSitesLocalDataSource).getSites(any(SitesDataSource.LoadSitesCallback.class));
    }

    @Test
    public void saveSite_savesSiteToServiceAPI() {

        Site newSite = new Site(SITE_TITLE, "password");

        mSItesRepository.saveSite(newSite);
    }

    private void twoTasksLoadCallsToRepository(SitesDataSource.LoadSitesCallback callback) {

    }
}
