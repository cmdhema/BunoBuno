package kjw.kr.bunobuno.data.source;

import com.google.common.collect.Lists;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import kjw.kr.bunobuno.data.Bank;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by kjwook on 2017. 2. 21..
 */

public class BankRepositoryTest {

    private static List<Bank> BANKS = Lists.newArrayList(new Bank("ShinHan", "1234", 0), new Bank("Woori", "5678", 1));

    private BankRepository bankRepository;

    @Mock
    private BankDataSource bankDataSource;

    @Mock
    private BankDataSource.GetBankCallback getBankCallback;

    @Mock
    private BankDataSource.LoadBanksCallback loadBanksCallback;

    @Captor
    private ArgumentCaptor<BankDataSource.LoadBanksCallback> banksCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<BankDataSource.GetBankCallback> bankCallbackArgumentCaptor;

    @Before
    public void setupBankRepository() {
        MockitoAnnotations.initMocks(this);

        bankRepository = BankRepository.getInstance(bankDataSource);

    }

    @After
    public void destroyBankRepositoryInstance() {
        BankRepository.destroyInstance();
    }

    @Test
    public void getBanks_requestsAllBanksFromLocalDataSource() {
        bankRepository.getBanks(loadBanksCallback);
        verify(bankDataSource).getBanks(any(BankDataSource.LoadBanksCallback.class));
    }
}
