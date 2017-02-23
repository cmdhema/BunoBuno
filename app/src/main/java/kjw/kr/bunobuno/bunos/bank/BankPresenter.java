package kjw.kr.bunobuno.bunos.bank;

import android.support.annotation.NonNull;

import kjw.kr.bunobuno.data.source.BankRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kjwook on 2017. 2. 21..
 */

public class BankPresenter implements BankContract.Presenter {

    private BankRepository bankRepository;
    private BankContract.View bankView;

    public BankPresenter(@NonNull BankRepository bankRepository, @NonNull BankContract.View bankView) {
        this.bankRepository = checkNotNull(bankRepository);
        this.bankView = checkNotNull(bankView);

        bankView.setBankPresenter(this);
    }

    @Override
    public void loadBanks(boolean forceUpdate) {

    }

    @Override
    public void addNewBank() {
        bankView.showAddEditBankUI();
    }

    @Override
    public void start() {

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }
}
