package kjw.kr.bunobuno.bunos.bank.addedit;

import android.support.annotation.NonNull;
import android.util.Log;

import kjw.kr.bunobuno.data.Bank;
import kjw.kr.bunobuno.data.source.BankDataSource;
import kjw.kr.bunobuno.data.source.BankRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kjwook on 2017. 2. 23..
 */

public class AddEditBankPresenter implements AddEditBankContract.Presenter, BankDataSource.GetBankCallback {

    @NonNull
    private final BankRepository bankRepository;
    @NonNull
    private final AddEditBankContract.View addEditBankView;

    private final String bankId;

    public AddEditBankPresenter(String bankId, BankRepository bankRepository, AddEditBankContract.View view) {
        this.bankRepository = checkNotNull(bankRepository);
        this.addEditBankView = checkNotNull(view);
        this.bankId = bankId;
    }

    @Override
    public void populateBank() {
        if ( isNewBank() )
            throw new RuntimeException("populateBank() was called but bank is new.");
        Log.i("AddEditBankPresenter", "populateBank");
        bankRepository.getBank(bankId, this);
    }

    @Override
    public void saveBank(String title, String number, int bank) {
        Log.i("AddBankPresenter", "3");
        if ( isNewBank() )
            createBank(title, number, bank);
        else
            updateBank(title, number, bank);
    }

    private void updateBank(String title, String number, int bank) {
        Bank b = new Bank(title, bankId, number, bank);

        if ( isNewBank() )
            throw new RuntimeException("updateBank() was called but task is new.");

        bankRepository.saveBank(b);
        addEditBankView.showBankList();
    }

    private void createBank(String title, String number, int bank) {
        Bank b = new Bank(title, number, bank);

        if ( !b.isEmpty() ){
            Log.i("AddBankPresenter", "4");
            bankRepository.saveBank(b);
            addEditBankView.showBankList();
        }

    }


    @Override
    public void start() {
        if ( !isNewBank() )
            populateBank();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    private boolean isNewBank() {
        return bankId == null;
    }

    @Override
    public void onBankLoaded(Bank bank) {
        Log.i("AddEditBankPresenter", bank.getTitle() +", " + bank.getNumber());
        addEditBankView.setTitle(bank.getTitle());
        addEditBankView.setBank(bank.getBank());
        addEditBankView.setNumber(bank.getNumber());
    }

    @Override
    public void onDataNotAvailable() {

    }
}
