package kjw.kr.bunobuno.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kjw.kr.bunobuno.data.Bank;
import kjw.kr.bunobuno.data.Site;
import kjw.kr.bunobuno.data.source.BankDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kjwook on 2017. 2. 21..
 */

public class BankLocalDataSource implements BankDataSource{

    private DBHelper dbHelper;

    private static BankLocalDataSource INSTANCE = null;

    private BankLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        dbHelper = new DBHelper(context);
    }

    public static BankLocalDataSource getInstance(@NonNull Context context) {
        if ( INSTANCE == null )
            INSTANCE = new BankLocalDataSource(context);
        return INSTANCE;
    }

    @Override
    public void getBanks(@NonNull LoadBanksCallback callback) {
        List<Bank> banks = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBScheme.BankEntry.ENTRY_ID,
                DBScheme.BankEntry.ENTRY_TITLE,
                DBScheme.BankEntry.ENTRY_BANK,
                DBScheme.BankEntry.ENTRY_NUMBER,
        };

        Cursor c = db.query("bank", projection, null, null, null, null, null);

        if ( c != null && c.getCount() > 0 ) {
            while ( c.moveToNext() ) {
                String bankTitle = c.getString(c.getColumnIndexOrThrow(DBScheme.BankEntry.ENTRY_TITLE));
                String bankNumber = c.getString(c.getColumnIndexOrThrow(DBScheme.BankEntry.ENTRY_NUMBER));
                int bankBank = c.getInt(c.getColumnIndexOrThrow(DBScheme.BankEntry.ENTRY_BANK));
                String bankId = c.getString(c.getColumnIndexOrThrow(DBScheme.BankEntry.ENTRY_ID));
                Bank bank = new Bank(bankTitle, bankId, bankNumber,bankBank);
                banks.add(bank);
            }
        }

        if ( c != null )
            c.close();

        if ( banks.isEmpty() )
            callback.onDataNotAvailable();
        else
            callback.onBanksLoaded(banks);
    }

    @Override
    public void getBank(@NonNull String bankId, @NonNull GetBankCallback callback) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBScheme.BankEntry.ENTRY_ID,
                DBScheme.BankEntry.ENTRY_TITLE,
                DBScheme.BankEntry.ENTRY_BANK,
                DBScheme.BankEntry.ENTRY_NUMBER,
        };

        String selection = DBScheme.BankEntry.ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {bankId};

        Cursor c = db.query(DBScheme.BankEntry.TABLE_NAME, projection, selection, selectionArgs, null ,null, null);
        Bank bank = null;

        if ( c != null && c.getCount() > 0 ) {
            c.moveToFirst();

            String itemId = c.getString(c.getColumnIndexOrThrow(DBScheme.BankEntry.ENTRY_ID));
            String itemTitle = c.getString(c.getColumnIndexOrThrow(DBScheme.BankEntry.ENTRY_TITLE));
            String itemNumber = c.getString(c.getColumnIndexOrThrow(DBScheme.BankEntry.ENTRY_NUMBER));
            int itemBank = c.getInt(c.getColumnIndexOrThrow(DBScheme.BankEntry.ENTRY_BANK));
            bank = new Bank(itemTitle, itemId, itemNumber, itemBank);
            Log.i("BankLocalDataSOurce", itemId + ", " + itemTitle);
            if ( c != null )
                c.close();

            db.close();

            if ( bank != null )
                callback.onBankLoaded(bank);
            else
                callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveBank(@NonNull Bank bank) {
        Log.i("BankLocalDataSource", "SaveBank!!");
        checkNotNull(bank);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBScheme.BankEntry.ENTRY_ID, bank.getId());
        cv.put(DBScheme.BankEntry.ENTRY_TITLE, bank.getTitle());
        cv.put(DBScheme.BankEntry.ENTRY_NUMBER, bank.getNumber());
        cv.put(DBScheme.BankEntry.ENTRY_BANK, bank.getBank());

        db.insert(DBScheme.BankEntry.TABLE_NAME, null, cv);
        Log.i("BankLocalDataSource", "SaveBank");
        db.close();
    }

    @Override
    public void deleteBank(@NonNull String bankId) {

    }
}
