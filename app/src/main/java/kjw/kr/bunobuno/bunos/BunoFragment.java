package kjw.kr.bunobuno.bunos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import kjw.kr.bunobuno.R;
import kjw.kr.bunobuno.bunos.addedit.AddEditSiteActivity;
import kjw.kr.bunobuno.bunos.sites.SitesContract;
import kjw.kr.bunobuno.data.Site;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kjwook on 2017. 1. 22..
 */

public class BunoFragment extends Fragment implements SitesContract.View {

    private BunoAdapter mListAdapter;

    private SitesContract.Presenter mSitesPresenter;

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

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_buno);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSitesPresenter.addNewSite();
            }
        });

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
    public void showSites(List<Site> sites) {

    }

    @Override
    public void showAddSite() {
        Intent intent = new Intent(getContext(), AddEditSiteActivity.class);
        startActivityForResult(intent, AddEditSiteActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void showSiteDetailUi(String siteId) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_task_message));
    }

    @Override
    public void setPresenter(@NonNull SitesContract.Presenter presenter) {
        mSitesPresenter = checkNotNull(presenter);
    }

    private static class BunoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    public interface SiteItemListener {
        void onSiteClick(Site clickedSite);
    }
}
