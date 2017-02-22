package kjw.kr.bunobuno.bunos.bank;

import java.util.List;

import kjw.kr.bunobuno.BasePresenter;
import kjw.kr.bunobuno.BaseView;
import kjw.kr.bunobuno.data.Bank;

/**
 * Created by kjwook on 2017. 2. 21..
 */

public interface BankContract {

    interface View extends BaseView {
        void setBankPresenter(BankPresenter presenter);
        void showBanks(List<Bank> banks);
        void showAddEditBankUI();
    }

    interface Presenter extends BasePresenter {
        void loadBanks(boolean forceUpdate);
        void addNewBank();
    }
}
