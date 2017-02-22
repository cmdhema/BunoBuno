package kjw.kr.bunobuno;

import android.content.Context;
import android.support.annotation.NonNull;

import kjw.kr.bunobuno.data.source.BankRepository;
import kjw.kr.bunobuno.data.source.SitesRepository;
import kjw.kr.bunobuno.data.source.local.BankLocalDataSource;
import kjw.kr.bunobuno.data.source.local.SitesLocalDataSource;
import kjw.kr.bunobuno.data.source.remote.SitesRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kjwook on 2017. 1. 23..
 */

public class Injection {
    public static SitesRepository provideSitesRepository(@NonNull Context context ) {
        checkNotNull(context);

        return SitesRepository.getInstance(SitesRemoteDataSource.getInstance(), SitesLocalDataSource.getInstance(context));
    }

    public static BankRepository provideBankRepository(@NonNull Context context) {
        checkNotNull(context);

        return BankRepository.getInstance(BankLocalDataSource.getInstance(context));
    }
}
