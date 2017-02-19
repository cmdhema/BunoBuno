package kjw.kr.bunobuno.data.source.addedit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kjw.kr.bunobuno.bunos.addedit.AddEditSiteContract;
import kjw.kr.bunobuno.bunos.addedit.AddEditSitePresenter;
import kjw.kr.bunobuno.data.Site;
import kjw.kr.bunobuno.data.source.SitesRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kjwook on 2017. 1. 23..
 */

public class AddEditSitePresenterTask {

    @Mock
    private SitesRepository sitesRepository;

    @Mock
    private AddEditSiteContract.View addEditSiteView;

    private AddEditSitePresenter addEditSitePresenter;

    @Before
    public void setupMocksAndView() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void saveNewSite_showSuccessMessageUi() {

        // Get a reference to the class under test
        addEditSitePresenter = new AddEditSitePresenter(null, sitesRepository, addEditSiteView, true);

        // When the presenter is asked to save a task
        addEditSitePresenter.saveSite("test title", "test password");

        verify(sitesRepository).saveSite(any(Site.class));
        verify(addEditSiteView).showSitesList();
    }
}
