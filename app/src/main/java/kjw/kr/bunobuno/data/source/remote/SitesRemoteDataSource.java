package kjw.kr.bunobuno.data.source.remote;

import android.support.annotation.NonNull;

import kjw.kr.bunobuno.data.Site;
import kjw.kr.bunobuno.data.source.SitesDataSource;

/**
 * Created by kjwook on 2017. 1. 23..
 */

public class SitesRemoteDataSource implements SitesDataSource {

    private static SitesRemoteDataSource INSTANCE;

    private SitesRemoteDataSource() {

    }

    public static SitesRemoteDataSource getInstance() {
        if ( INSTANCE == null )
            INSTANCE = new SitesRemoteDataSource();

        return INSTANCE;
    }

    @Override
    public void getSites(@NonNull LoadSitesCallback callback) {

    }

    @Override
    public void getSite(@NonNull String siteId, @NonNull GetSiteCallback callback) {

    }

    @Override
    public void saveSite(@NonNull Site site) {

    }

    @Override
    public void completeSite(@NonNull Site site) {

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

    }

    @Override
    public void deleteSite(@NonNull String siteId) {

    }
}
