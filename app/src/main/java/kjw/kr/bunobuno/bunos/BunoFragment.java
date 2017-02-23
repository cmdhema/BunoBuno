package kjw.kr.bunobuno.bunos;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

import java.util.ArrayList;
import java.util.List;

import kjw.kr.bunobuno.Fab;
import kjw.kr.bunobuno.R;
import kjw.kr.bunobuno.bunos.bank.BankContract;
import kjw.kr.bunobuno.bunos.bank.BankPresenter;
import kjw.kr.bunobuno.bunos.sites.SitesPresenter;
import kjw.kr.bunobuno.bunos.sites.addedit.AddEditSiteActivity;
import kjw.kr.bunobuno.bunos.sites.SitesContract;
import kjw.kr.bunobuno.data.Bank;
import kjw.kr.bunobuno.data.BunoConstants;
import kjw.kr.bunobuno.data.Site;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public class BunoFragment extends Fragment implements SitesContract.View, BankContract.View, View.OnClickListener {

    private BunoExpandableListAdapter mListAdapter;

    private SitesContract.Presenter mSitesPresenter;
    private BankContract.Presenter mBankPresenter;

    private RecyclerView recyclerView;
    private MaterialSheetFab materialSheetFab;

    private int statusBarColor;

    public BunoFragment() {

    }

    public static BunoFragment newInstance() {
        return new BunoFragment();
    }

    SiteItemListener mSiteItemListener = new SiteItemListener() {
        @Override
        public void onSiteClick(Site clickedSite) {
            mSitesPresenter.openSiteDetails(clickedSite);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mSitesPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Fab fab = (Fab) getActivity().findViewById(R.id.fab_add_buno);
        View sheetView = getActivity().findViewById(R.id.fab_sheet);
        View overlay = getActivity().findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.background_card);
        int fabColor = getResources().getColor(R.color.theme_accent);

        getActivity().findViewById(R.id.fab_sheet_item_site).setOnClickListener(this);
        getActivity().findViewById(R.id.fab_sheet_item_bank).setOnClickListener(this);

        // Create material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        // Set material sheet event listener
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor();
                // Set darker status bar color to match the dim overlay
                setStatusBarColor(getResources().getColor(R.color.theme_primary_dark2));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor);
            }
        });

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        List<BunoExpandableListAdapter.BunoItem> data = new ArrayList<>();
        data.add(new BunoExpandableListAdapter.BunoItem(BunoExpandableListAdapter.HEADER, BunoConstants.TITLE_SITE, "Apple", ""));
        data.add(new BunoExpandableListAdapter.BunoItem(BunoExpandableListAdapter.CHILD, BunoConstants.TITLE_SITE, "Pine", ""));
        data.add(new BunoExpandableListAdapter.BunoItem(BunoExpandableListAdapter.CHILD, BunoConstants.TITLE_SITE, "Straw", ""));
        data.add(new BunoExpandableListAdapter.BunoItem(BunoExpandableListAdapter.HEADER, BunoConstants.TITLE_BANK, "Sonata", ""));
        data.add(new BunoExpandableListAdapter.BunoItem(BunoExpandableListAdapter.CHILD, BunoConstants.TITLE_BANK, "Audi", ""));

        recyclerView.setAdapter(new BunoExpandableListAdapter(data, mSiteItemListener));
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSitesPresenter.result(requestCode, resultCode);
    }

    @Override
    public void setSitesPresenter(SitesPresenter presenter) {
        mSitesPresenter = checkNotNull(presenter);
    }

    @Override
    public void showSites(List<Site> sites) {
        Log.i("BunoFragment", sites.get(0).getTitle());
        //refreshData(sites);
    }

    @Override
    public void showAddSite() {
        Intent intent = new Intent(getContext(), AddEditSiteActivity.class);
        startActivityForResult(intent, AddEditSiteActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void showSiteDetailUi(String siteId) {
        Intent intent = new Intent(getContext(), AddEditSiteActivity.class);
        intent.putExtra(AddEditSiteActivity.EXTRA_SITE_ID, siteId);
        startActivity(intent);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_task_message));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setBankPresenter(BankPresenter presenter) {
        mBankPresenter = checkNotNull(presenter);
    }

    @Override
    public void showBanks(List<Bank> banks) {

    }

    @Override
    public void showAddEditBankUI() {

    }

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getActivity().getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void onClick(View view) {
        if ( view.getId() == R.id.fab_sheet_item_bank ) {
            mBankPresenter.addNewBank();
        } else if ( view.getId() == R.id.fab_sheet_item_site ) {
            mSitesPresenter.addNewSite();
        }
    }

    public interface SiteItemListener {
        void onSiteClick(Site clickedSite);
    }
}
