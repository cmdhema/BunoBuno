package kjw.kr.bunobuno.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.UUID;

/**
 * Created by kjwook on 2017. 1. 20..
 */

public final class Site extends Buno {

    @Nullable
    private final String mPassword;

    private final boolean mCompleted;

    public Site(@Nullable String title, @Nullable String password) {
        this(title, password, UUID.randomUUID().toString(), false);
    }

    public Site(@Nullable String title, @Nullable String password, @NonNull String id) {
        this(title, password, id, false);
    }

    public Site(@Nullable String title, @Nullable String description, boolean completed) {
        this(title, description, UUID.randomUUID().toString(), completed);
    }

    public Site(@Nullable String title, @Nullable String password, @NonNull String id, boolean completed) {
        super(title, id);
        mPassword = password;
        mCompleted = completed;
    }

    @Nullable
    public String getPassword() {
        return mPassword;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(getTitle()) && Strings.isNullOrEmpty(mPassword);
    }

}
