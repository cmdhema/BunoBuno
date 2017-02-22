package kjw.kr.bunobuno.bunos.sites;

import android.support.annotation.NonNull;

import java.util.List;

import kjw.kr.bunobuno.BasePresenter;
import kjw.kr.bunobuno.BaseView;
import kjw.kr.bunobuno.data.Site;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public interface SitesContract {

    interface View extends BaseView {
        void setSitesPresenter(SitesPresenter presenter);
        void showSites(List<Site> sites);
        void showAddSite();
        void showSiteDetailUi(String siteId);
        void setLoadingIndicator(boolean active);

    }

    interface Presenter extends BasePresenter {
        void loadSites(boolean forceUpdate);
        void addNewSite();
        void openSiteDetails(@NonNull Site requestedSite);
    }
}
