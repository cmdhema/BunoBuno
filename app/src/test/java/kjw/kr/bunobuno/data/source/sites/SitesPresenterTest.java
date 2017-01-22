package kjw.kr.bunobuno.data.source.sites;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import kjw.kr.bunobuno.bunos.sites.SitesContract;
import kjw.kr.bunobuno.bunos.sites.SitesPresenter;
import kjw.kr.bunobuno.data.Site;
import kjw.kr.bunobuno.data.source.SitesDataSource;
import kjw.kr.bunobuno.data.source.SitesRepository;

import static org.mockito.Mockito.verify;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public class SitesPresenterTest {

    private static List<Site> SITES;

    @Mock
    private SitesRepository mSitesRepository;
    @Mock
    private SitesContract.View mSitesView;

    @Captor
    private ArgumentCaptor<SitesDataSource.LoadSitesCallback> mLoadSitesCallbackCaptor;

    private SitesPresenter mSitesPresenter;

    @Before
    public void setupSitesPresenter() {
        MockitoAnnotations.initMocks(this);
        mSitesPresenter = new SitesPresenter(mSitesRepository, mSitesView);
    }

    @Test
    public void clickOnFab_ShowsAddsSiteUi() {

        //When addming a new site
        mSitesPresenter.addNewSite();

        //Then add site UI is shown
        verify(mSitesView).showAddSite();
    }

    @Test
    public void loadSitesFromRepositoryAndLoadIntoView() {

        mSitesPresenter.loadSites(true);

        verify(mSitesRepository).getSites(mLoadSitesCallbackCaptor.capture());
        mLoadSitesCallbackCaptor.getValue().onSitesLoaded(SITES);

        verify(mSitesView).setLoadingIndicator(false);
        verify(mSitesView).showSites(SITES);
    }

}
