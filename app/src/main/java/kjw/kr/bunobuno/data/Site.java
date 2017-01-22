package kjw.kr.bunobuno.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.UUID;

/**
 * Created by kjwook on 2017. 1. 20..
 */

public final class Site {

    @NonNull
    private final String mId;

    @Nullable
    private final String mTitle;

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
        mId = id;
        mTitle = title;
        mPassword = password;
        mCompleted = completed;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(mTitle)) {
            return mTitle;
        } else {
            return mPassword;
        }
    }

    @Nullable
    public String getPassword() {
        return mPassword;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mTitle) && Strings.isNullOrEmpty(mPassword);
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj )
            return true;
        if ( obj == null || getClass() != obj.getClass())
            return false;
        Site site = (Site) obj;
        return Objects.equal(mId, site.mId) && Objects.equal(mTitle, site.mTitle) && Objects.equal(mPassword, site.mPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle, mPassword);
    }

    @Override
    public String toString() {
        return "Task with title " + mTitle;
    }
}
