package kjw.kr.bunobuno.bunos.addedit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import kjw.kr.bunobuno.Injection;
import kjw.kr.bunobuno.R;
import kjw.kr.bunobuno.util.ActivityUtils;

public class AddEditSiteActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_TASK = 1;

    private AddEditSitePresenter mAddEditSitePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        AddEditSiteFragment addEditSiteFragment = (AddEditSiteFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        String siteId = getIntent().getStringExtra(AddEditSiteFragment.ARGUMENT_EDIT_SITE_ID);

        if ( addEditSiteFragment == null ) {
            addEditSiteFragment = AddEditSiteFragment.newInstance();

            if ( getIntent().hasExtra(AddEditSiteFragment.ARGUMENT_EDIT_SITE_ID)) {
                actionBar.setTitle("Edit Site");
                Bundle bundle = new Bundle();
                bundle.putString(AddEditSiteFragment.ARGUMENT_EDIT_SITE_ID, siteId);
                addEditSiteFragment.setArguments(bundle);
            } else
                actionBar.setTitle("New Site");

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), addEditSiteFragment, R.id.contentFrame);
        }

        boolean shouldLoadDataFromRepo = true;

        mAddEditSitePresenter = new AddEditSitePresenter(siteId, Injection.provideSitesRepository(getApplicationContext()), addEditSiteFragment, shouldLoadDataFromRepo);

        addEditSiteFragment.setPresenter(mAddEditSitePresenter);
    }

}
