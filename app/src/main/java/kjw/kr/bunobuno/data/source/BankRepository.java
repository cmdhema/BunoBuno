package kjw.kr.bunobuno.data.source;

import android.support.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.List;

import kjw.kr.bunobuno.data.Bank;
import kjw.kr.bunobuno.data.Site;
import kjw.kr.bunobuno.data.source.local.BankLocalDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kjwook on 2017. 2. 21..
 */

public class BankRepository implements BankDataSource {

    private static BankRepository INSTANCE = null;
    private BankDataSource bankLocalDataSource;

    private BankRepository(@NonNull BankDataSource bankLocalDataSource) {
        this.bankLocalDataSource = checkNotNull(bankLocalDataSource);
    }

    public static BankRepository getInstance(BankDataSource bankLocalDataSource) {
        if ( INSTANCE == null )
            INSTANCE = new BankRepository(bankLocalDataSource);
        return INSTANCE;
    }

    @Override
    public void getBanks(@NonNull final LoadBanksCallback callback) {

        bankLocalDataSource.getBanks(new LoadBanksCallback() {
            @Override
            public void onBanksLoaded(List<Bank> banks) {
                callback.onBanksLoaded(banks);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getBank(@NonNull String bankId, @NonNull final GetBankCallback callback) {
        checkNotNull(bankId);
        checkNotNull(callback);

        bankLocalDataSource.getBank(bankId, new BankDataSource.GetBankCallback() {
            @Override
            public void onBankLoaded(Bank bank) {
                callback.onBankLoaded(bank);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }

        });

    }

    @Override
    public void saveBank(@NonNull Bank bank) {
        checkNotNull(bank);
        bankLocalDataSource.saveBank(bank);
    }

    @Override
    public void deleteBank(@NonNull String bankId) {

    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


}
