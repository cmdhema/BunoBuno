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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Buno buno = (Buno) o;

        if (!title.equals(buno.title)) return false;
        return id.equals(buno.id);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Buno{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
