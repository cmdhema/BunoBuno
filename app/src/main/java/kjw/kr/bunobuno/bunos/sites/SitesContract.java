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

    interface View extends BaseView<Presenter> {
        void showSites(List<Site> sites);
        void showAddSite();
        void showSiteDetailUi(String siteId);
        void setLoadingIndicator(boolean active);

        void showSuccessfullySavedMessage();
    }

    interface Presenter extends BasePresenter {
        void loadSites(boolean forceUpdate);
        void addNewSite();
        void openSiteDetails(@NonNull Site requestedSite);

        void result(int requestCode, int resultCode);
    }
}
