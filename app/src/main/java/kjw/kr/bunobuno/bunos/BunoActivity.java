package kjw.kr.bunobuno.bunos;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import kjw.kr.bunobuno.Injection;
import kjw.kr.bunobuno.R;
import kjw.kr.bunobuno.bunos.bank.BankPresenter;
import kjw.kr.bunobuno.bunos.sites.SitesPresenter;
import kjw.kr.bunobuno.util.ActivityUtils;

public class BunoActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private SitesPresenter mSitesPresenter;
    private BankPresenter mBankPresenver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if ( navigationView != null )
            setupDrawerContent(navigationView);

        BunoFragment bunoFragment = (BunoFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if ( bunoFragment == null ) {
            bunoFragment = BunoFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), bunoFragment, R.id.contentFrame);
        }

        mSitesPresenter = new SitesPresenter(Injection.provideSitesRepository(getApplicationContext()), bunoFragment);
        mBankPresenver = new BankPresenter(Injection.provideBankRepository(getApplicationContext()), bunoFragment);
    }

    private void setupDrawerContent(NavigationView navigationView) {

    }
}
