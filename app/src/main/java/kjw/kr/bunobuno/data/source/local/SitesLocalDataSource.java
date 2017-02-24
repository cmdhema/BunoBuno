package kjw.kr.bunobuno.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kjw.kr.bunobuno.data.Site;
import kjw.kr.bunobuno.data.source.SitesDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public class SitesLocalDataSource implements SitesDataSource{

    private static SitesLocalDataSource INSTANCE;
    private DBHelper dbHelper;

    private SitesLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        dbHelper = new DBHelper(context);
    }

    public static SitesLocalDataSource getInstance(@NonNull Context context) {
        if ( INSTANCE == null )
            INSTANCE = new SitesLocalDataSource(context);
        return INSTANCE;
    }

    @Override
    public void getSites(@NonNull LoadSitesCallback callback) {
        List<Site> siteList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBScheme.SiteEntry.ENTRY_ID,
                DBScheme.SiteEntry.ENTRY_TITLE,
                DBScheme.SiteEntry.ENTRY_SITE_ID,
                DBScheme.SiteEntry.ENTRY_PASSWORD
        };

        Cursor c = db.query("site", projection, null, null, null ,null, null);
        Log.i("SitesLocalDataSource", c.getCount() +" 개");
        if ( c != null && c.getCount() > 0 ) {
            while ( c.moveToNext() ) {
                String siteTitle = c.getString(c.getColumnIndexOrThrow(DBScheme.SiteEntry.ENTRY_TITLE));
                String siteId = c.getString(c.getColumnIndexOrThrow(DBScheme.SiteEntry.ENTRY_SITE_ID));
                String sitePassword = c.getString(c.getColumnIndexOrThrow(DBScheme.SiteEntry.ENTRY_PASSWORD));
                String siteEntryId = c.getString(c.getColumnIndexOrThrow(DBScheme.SiteEntry.ENTRY_ID));
                Site site = new Site(siteTitle, siteId, sitePassword, siteEntryId);
                siteList.add(site);
            }
        }

        if( c != null )
            c.close();

        db.close();

        if ( siteList.isEmpty() ) {
            callback.onDataNotAvailable();
        } else
            callback.onSitesLoaded(siteList);
    }

    @Override
    public void getSite(@NonNull String siteId, @NonNull GetSiteCallback callback) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DBScheme.SiteEntry.ENTRY_ID,
                DBScheme.SiteEntry.ENTRY_TITLE,
                DBScheme.SiteEntry.ENTRY_SITE_ID,
                DBScheme.SiteEntry.ENTRY_PASSWORD
        };

        String selection = DBScheme.SiteEntry.ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {siteId};

        Cursor c = db.query(DBScheme.SiteEntry.TABLE_NAME, projection, selection, selectionArgs, null ,null, null);

        Site site = null;

        if ( c != null && c.getCount() > 0 ) {
            c.moveToFirst();

            String itemId = c.getString(c.getColumnIndexOrThrow(DBScheme.SiteEntry.ENTRY_ID));
            String itemTitle = c.getString(c.getColumnIndexOrThrow(DBScheme.SiteEntry.ENTRY_TITLE));
            String itemSiteId = c.getString(c.getColumnIndexOrThrow(DBScheme.SiteEntry.ENTRY_SITE_ID));
            String itemPassword = c.getString(c.getColumnIndexOrThrow(DBScheme.SiteEntry.ENTRY_PASSWORD));

            site = new Site(itemTitle, itemSiteId, itemPassword, itemId);

            db.close();

            if ( site != null )
                callback.onSiteLoaded(site);
            else
                callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveSite(@NonNull Site site) {
        checkNotNull(site);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBScheme.SiteEntry.ENTRY_ID, site.getId());
        cv.put(DBScheme.SiteEntry.ENTRY_TITLE, site.getTitle());
        cv.put(DBScheme.SiteEntry.ENTRY_PASSWORD, site.getPassword());
        cv.put(DBScheme.SiteEntry.ENTRY_SITE_ID, site.getSiteId());
        db.insert(DBScheme.SiteEntry.TABLE_NAME, null, cv);

        db.close();

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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = DBScheme.SiteEntry.ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {siteId};

        db.delete(DBScheme.SiteEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }
}
