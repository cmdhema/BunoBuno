package kjw.kr.bunobuno.data;

import android.support.annotation.NonNull;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.Map;

import kjw.kr.bunobuno.data.source.SitesDataSource;

/**
 * Created by kjwook on 2017. 1. 23..
 */

public class FakeSitesRemoteDataSource implements SitesDataSource {

    private static FakeSitesRemoteDataSource INSTANCE;

    private static final Map<String, Site> SITES_SERVICE_DATA = new LinkedHashMap<>();

    private FakeSitesRemoteDataSource() {

    }

    public static FakeSitesRemoteDataSource getInstance() {
        if ( INSTANCE == null )
            INSTANCE = new FakeSitesRemoteDataSource();

        return INSTANCE;
    }

    @Override
    public void getSites(@NonNull LoadSitesCallback callback) {
        callback.onSitesLoaded(Lists.newArrayList(SITES_SERVICE_DATA.values()));
    }

    @Override
    public void getSite(@NonNull String siteId, @NonNull GetSiteCallback callback) {
        Site site = SITES_SERVICE_DATA.get(siteId);
        callback.onSiteLoaded(site);
    }

    @Override
    public void saveSite(@NonNull Site site) {
        SITES_SERVICE_DATA.put(site.getId(), site);
    }

    @Override
    public void completeSite(@NonNull Site site) {
        Site completedSite = new Site(site.getTitle(), site.getPassword(), site.getId(), true);
        SITES_SERVICE_DATA.put(site.getId(), completedSite);
    }

    @Override
    public void completeSite(@NonNull String siteId) {

    }

    @Override
    public void activateSite(@NonNull Site site) {

    }

    @Override
    public void activateSite(@NonNull String siteId) {

    }

    @Override
    public void refreshSites() {

    }

    @Override
    public void deleteAllSites() {
        SITES_SERVICE_DATA.clear();
    }

    @Override
    public void deleteSite(@NonNull String siteId) {
        SITES_SERVICE_DATA.remove(siteId);
    }

    @VisibleForTesting
    public void addSites(Site... sites) {
        for ( Site site : sites ) {
            SITES_SERVICE_DATA.put(site.getId(), site);
        }
    }
}
