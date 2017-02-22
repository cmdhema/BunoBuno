package kjw.kr.bunobuno.data;

import android.support.annotation.NonNull;

/**
 * Created by kjwook on 2017. 2. 21..
 */

public class Buno {

    @NonNull
    private String title;
    @NonNull
    private String id;

    public Buno(@NonNull String mTitle, @NonNull String mId) {
        this.title = mTitle;
        this.id = mId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
