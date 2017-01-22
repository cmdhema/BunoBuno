package kjw.kr.bunobuno.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import kjw.kr.bunobuno.data.Site;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public interface SitesDataSource {

    interface LoadSitesCallback {
        void onSitesLoaded(List<Site> sites);
        void onDataNotAvailable();
    }

    interface GetSiteCallback {
        void onSiteLoaded(Site site);
        void onDataNotAvailable();
    }

    void getSites(@NonNull LoadSitesCallback callback);
    void getSite(@NonNull String siteId, @NonNull GetSiteCallback callback);
    void saveSite(@NonNull Site site);
    void completeSite(@NonNull Site site);
    void completeSite(@NonNull String siteId);
    void activateSite(@NonNull Site site);
    void activateSite(@NonNull String siteId);
    void refreshSites();
    void deleteAllSites();
    void deleteSite(@NonNull String siteId);
}
