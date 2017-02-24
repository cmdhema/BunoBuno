package kjw.kr.bunobuno.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kjw.kr.bunobuno.data.Site;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public class SitesRepository implements SitesDataSource {

    private static SitesRepository INSTANCE = null;
    private final SitesDataSource mSitesRemoteDataSource;
    private final SitesDataSource mSitesLocalDataSource;

    Map<String, Site> mCachedSites;
    boolean mCacheIsDirty = false;

    private SitesRepository(@NonNull SitesDataSource sitesRemoteDataSource, @NonNull SitesDataSource sitesLocalDataSource) {
        mSitesLocalDataSource = checkNotNull(sitesLocalDataSource);
        mSitesRemoteDataSource = checkNotNull(sitesRemoteDataSource);
    }

    public static SitesRepository getInstance(SitesDataSource sitesRemoteDataSource, SitesDataSource sitesLocalDataSource) {

        if ( INSTANCE == null ) {
            INSTANCE = new SitesRepository(sitesRemoteDataSource, sitesLocalDataSource);
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getSites(@NonNull final LoadSitesCallback callback) {
        checkNotNull(callback);
        Log.i("SitesRepository", "1");
        if ( mCachedSites != null && !mCacheIsDirty ) {
            Log.i("SitesRepository", "2");
            callback.onSitesLoaded(new ArrayList<>(mCachedSites.values()));
            return;
        }

//        if ( mCacheIsDirty ) {
//            Log.i("SitesRepository", "3");
//            getSitesFromRemoteDataSource(callback);
//        } else {
//            mSitesLocalDataSource.getSites(new LoadSitesCallback() {
//                @Override
//                public void onSitesLoaded(List<Site> sites) {
//                    Log.i("SitesRepository", sites.get(0).getTitle());
//                    refreshCache(sites);
//                    callback.onSitesLoaded(new ArrayList<>(mCachedSites.values()));
//                }
//
//                @Override
//                public void onDataNotAvailable() {
//                    Log.i("SitesRepository", "4");
//                    getSitesFromRemoteDataSource(callback);
//                }
//            });
//        }

            mSitesLocalDataSource.getSites(new LoadSitesCallback() {
                @Override
                public void onSitesLoaded(List<Site> sites) {
                    Log.i("SitesRepository", sites.get(0).getTitle());
                    refreshCache(sites);
                    callback.onSitesLoaded(new ArrayList<>(mCachedSites.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    Log.i("SitesRepository", "4");
                    getSitesFromRemoteDataSource(callback);
                }
            });
    }

    private void refreshCache(List<Site> sites ) {
        if ( mCachedSites == null )
            mCachedSites = new LinkedHashMap<>();

        mCachedSites.clear();
        for ( Site site : sites ) {
            mCachedSites.put(site.getId(), site);
        }

        mCacheIsDirty = false;
    }

    @Override
    public void getSite(@NonNull final String siteId, @NonNull final GetSiteCallback callback) {
        checkNotNull(siteId);
        checkNotNull(callback);

        Site cachedSite = getSiteWithId(siteId);

        if ( cachedSite != null ) {
            callback.onSiteLoaded(cachedSite);
            return;
        }

        mSitesLocalDataSource.getSite(siteId, new GetSiteCallback() {
            @Override
            public void onSiteLoaded(Site site) {
                if ( mCachedSites == null )
                    mCachedSites = new LinkedHashMap<>();
                mCachedSites.put(site.getId(), site);
                callback.onSiteLoaded(site);
            }

            @Override
            public void onDataNotAvailable() {
                mSitesRemoteDataSource.getSite(siteId, new GetSiteCallback() {
                    @Override
                    public void onSiteLoaded(Site site) {
                        if ( mCachedSites == null )
                            mCachedSites = new LinkedHashMap<>();
                        mCachedSites.put(site.getId(), site);
                        callback.onSiteLoaded(site);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void saveSite(@NonNull Site site) {
        checkNotNull(site);
        mSitesRemoteDataSource.saveSite(site);
        mSitesLocalDataSource.saveSite(site);

        if ( mCachedSites == null )
            mCachedSites = new LinkedHashMap<>();

        mCachedSites.put(site.getId(), site);
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
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllSites() {
        mSitesRemoteDataSource.deleteAllSites();
        mSitesLocalDataSource.deleteAllSites();

        if ( mCachedSites == null )
            mCachedSites = new LinkedHashMap<>();

        mCachedSites.clear();
    }

    @Override
    public void deleteSite(@NonNull String siteId) {
        mSitesRemoteDataSource.deleteSite(checkNotNull(siteId));
        mSitesLocalDataSource.deleteSite(checkNotNull(siteId));

        mCachedSites.remove(siteId);
    }

    @Nullable
    private Site getSiteWithId(@NonNull String id ) {
        checkNotNull(id);
        if ( mCachedSites == null || mCachedSites.isEmpty())
            return null;
        else
            return mCachedSites.get(id);
    }

    private void getSitesFromRemoteDataSource(@NonNull final LoadSitesCallback callback) {
        mSitesRemoteDataSource.getSites(new LoadSitesCallback() {
            @Override
            public void onSitesLoaded(List<Site> sites) {
                refreshCache(sites);
                refreshLocalDataSource(sites);
                callback.onSitesLoaded(new ArrayList<>(mCachedSites.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<Site> sites) {
        mSitesLocalDataSource.deleteAllSites();
        for ( Site site : sites )
            mSitesLocalDataSource.saveSite(site);
    }
}
