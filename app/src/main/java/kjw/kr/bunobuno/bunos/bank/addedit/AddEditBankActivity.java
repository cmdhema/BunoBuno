package kjw.kr.bunobuno.bunos.bank.addedit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import kjw.kr.bunobuno.Injection;
import kjw.kr.bunobuno.R;
import kjw.kr.bunobuno.bunos.sites.addedit.AddEditSiteFragment;
import kjw.kr.bunobuno.bunos.sites.addedit.AddEditSitePresenter;
import kjw.kr.bunobuno.util.ActivityUtils;

public class AddEditBankActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_BANK = 2;
    public static final String EXTRA_BANK_ID = "BANK_ID";
    private AddEditBankPresenter addEditBankPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_bank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_edit_bank_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AddEditBankFragment addEditBankFragment = (AddEditBankFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        String siteId = getIntent().getStringExtra(AddEditBankFragment.ARGUMENT_EDIT_BANK_ID);

        if ( addEditBankFragment == null ) {
            addEditBankFragment = AddEditBankFragment.newInstance();

            if ( getIntent().hasExtra(AddEditBankFragment.ARGUMENT_EDIT_BANK_ID)) {
                actionBar.setTitle("Edit Site");
                Bundle bundle = new Bundle();
                bundle.putString(AddEditBankFragment.ARGUMENT_EDIT_BANK_ID, siteId);
                addEditBankFragment.setArguments(bundle);
            } else
                actionBar.setTitle("New Site");

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), addEditBankFragment, R.id.contentFrame);
        }

        addEditBankPresenter = new AddEditBankPresenter(siteId, Injection.provideBankRepository(getApplicationContext()), addEditBankFragment);

        addEditBankFragment.setPresenter(addEditBankPresenter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
