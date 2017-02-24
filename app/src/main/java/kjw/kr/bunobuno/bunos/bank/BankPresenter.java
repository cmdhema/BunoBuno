package kjw.kr.bunobuno.bunos.bank;

import android.support.annotation.NonNull;

import java.util.List;

import kjw.kr.bunobuno.data.Bank;
import kjw.kr.bunobuno.data.source.BankDataSource;
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
        loadBanks();
    }

    @Override
    public void addNewBank() {
        bankView.showAddEditBankUI();
    }

    @Override
    public void openBankDetail(@NonNull String bankId) {
        bankView.showDetailBankUI(bankId);
    }

    @Override
    public void start() {
        loadBanks();
    }

    private void loadBanks() {
        bankRepository.getBanks(new BankDataSource.LoadBanksCallback() {
            @Override
            public void onBanksLoaded(List<Bank> banks) {

                if ( !banks.isEmpty())
                    bankView.showBanks(banks);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
    @Override
    public void result(int requestCode, int resultCode) {

    }
}
