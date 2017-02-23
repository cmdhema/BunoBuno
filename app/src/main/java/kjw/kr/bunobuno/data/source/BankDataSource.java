package kjw.kr.bunobuno.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import kjw.kr.bunobuno.data.Bank;

/**
 * Created by kjwook on 2017. 2. 21..
 */

public interface BankDataSource {

    interface LoadBanksCallback {
        void onBanksLoaded(List<Bank> banks);
        void onDataNotAvailable();
    }

    interface GetBankCallback {
        void onBankLoaded(Bank bank);
        void onDataNotAvailable();
    }

    void getBanks(@NonNull LoadBanksCallback callback);
    void getBank(@NonNull String bankId, @NonNull GetBankCallback callback);
    void saveBank(@NonNull Bank bank);
    void deleteBank(@NonNull String bankId);
}
