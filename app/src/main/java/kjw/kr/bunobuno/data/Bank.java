package kjw.kr.bunobuno.data;

import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by kjwook on 2017. 1. 29..
 */

public class Bank extends Buno {

    private String number;
    private int bank;

    public Bank(@NonNull String mTitle, @NonNull String number, @NonNull int bank) {
        this(mTitle, UUID.randomUUID().toString(), number, bank);
    }

    public Bank(@NonNull String mTitle, @NonNull String mId, @NonNull String number, @NonNull int bank) {
        super(mTitle, mId);
        this.number = number;
        this.bank = bank;
    }

    public String getNumber() {
        return number;
    }

    public int getBank() {
        return bank;
    }

}
