package kjw.kr.bunobuno.bunos.bank.addedit;

import kjw.kr.bunobuno.BasePresenter;
import kjw.kr.bunobuno.BaseView;

/**
 * Created by kjwook on 2017. 2. 23..
 */

public class AddEditBankContract {

    interface View extends BaseView<Presenter> {
        void showBankList();
        void setTitle(String title);
        void setBank(int bank);
        void setNumber(String number);
    }

    interface Presenter extends BasePresenter {
        void populateBank();
        void saveBank(String title, String number, int bank);
    }
}
