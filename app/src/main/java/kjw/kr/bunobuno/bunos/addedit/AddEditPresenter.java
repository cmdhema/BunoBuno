package kjw.kr.bunobuno.bunos.addedit;

import kjw.kr.bunobuno.BasePresenter;
import kjw.kr.bunobuno.BaseView;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public class AddEditPresenter {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

    }

    interface Presenter extends BasePresenter {

    }
}
