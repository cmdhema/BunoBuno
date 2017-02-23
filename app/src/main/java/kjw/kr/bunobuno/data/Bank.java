package kjw.kr.bunobuno.data;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

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

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(getTitle()) && Strings.isNullOrEmpty(getNumber());
    }


}
