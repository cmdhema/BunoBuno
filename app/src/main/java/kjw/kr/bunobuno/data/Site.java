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
    @Nullable
    private final String mSiteId;

    private final boolean mCompleted;

    public Site(@Nullable String title, @Nullable String id, @Nullable String password) {
        this(title, id, password, UUID.randomUUID().toString(), false);
    }

    public Site(@Nullable String title, @Nullable String id, @Nullable String password, @NonNull String entryId) {
        this(title, id, password, entryId, false);
    }

    public Site(@Nullable String title, @Nullable String siteId, @Nullable String password, @NonNull String id, boolean completed) {
        super(title, id);
        mSiteId = siteId;
        mPassword = password;
        mCompleted = completed;
    }

    @Nullable
    public String getPassword() {
        return mPassword;
    }

    @Nullable
    public String getSiteId() {
        return mSiteId;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(getTitle()) && Strings.isNullOrEmpty(mPassword);
    }

}
