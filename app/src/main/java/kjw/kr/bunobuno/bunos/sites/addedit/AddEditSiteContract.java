package kjw.kr.bunobuno.bunos.sites.addedit;

import kjw.kr.bunobuno.BasePresenter;
import kjw.kr.bunobuno.BaseView;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public interface AddEditSiteContract {

    interface View extends BaseView<Presenter> {
        void showSitesList();
        void showEmptySiteError();
        void setTitle(String title);
        void setPassword(String password);
    }

    interface Presenter extends BasePresenter {
        void populateSite();
        void saveSite(String title, String password);
    }
}
