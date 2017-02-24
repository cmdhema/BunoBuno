package kjw.kr.bunobuno.bunos;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import java.util.ArrayList;
import java.util.List;

import kjw.kr.bunobuno.Fab;
import kjw.kr.bunobuno.R;
import kjw.kr.bunobuno.bunos.bank.BankContract;
import kjw.kr.bunobuno.bunos.bank.BankPresenter;
import kjw.kr.bunobuno.bunos.bank.addedit.AddEditBankActivity;
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

    private List<BunoExpandableListAdapter.BunoItem> bunoItemList;
    private List<Site> siteList;
    private List<Bank> bankList;

    private KakaoLink kakaoLink;

    private String[] banks;

    public BunoFragment() {

    }

    public static BunoFragment newInstance() {
        return new BunoFragment();
    }

    BunoItemListener mBunoItemListener = new BunoItemListener() {
        @Override
        public void onSiteClick(BunoExpandableListAdapter.BunoItem bunoItem) {
            if (bunoItem.getHeaderTitle().equals(BunoConstants.TITLE_SITE))
                mSitesPresenter.openSiteDetails(bunoItem.getId());
            else
                mBankPresenter.openBankDetail(bunoItem.getId());
        }

        @Override
        public void onKakaoClick(BunoExpandableListAdapter.BunoItem bunoItem) {
            final KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();

            if (bunoItem.getHeaderTitle().equals(BunoConstants.TITLE_SITE)) {
                for ( Site site : siteList ) {
                    if ( site.getId().equals(bunoItem.getId() )) {
                        String msg = "[버노버노 앱에서 보낸 메세지 입니다.] \n" +
                                site.getTitle()+"의 아이디는 " + site.getSiteId() + ",\n " +
                                "비밀번호는 " + site.getPassword() + "입니다";
//                        Toast.makeText(getActivity(), msg, 0).show();
                        try {
                            kakaoTalkLinkMessageBuilder.addText(msg);
                        } catch (KakaoParameterException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
            else {
                for ( Bank bank : bankList ) {
                    if ( bank.getId().equals(bunoItem.getId() )) {
                        String msg = "[버노버노 앱에서 보낸 메세지 입니다.] \n" +
                                "은행은 " + banks[bank.getBank()] + ",\n " +
                                "계좌번호는 " + bank.getNumber() + "입니다";
//                        Toast.makeText(getActivity(), msg, 0).show();
                        try {
                            kakaoTalkLinkMessageBuilder.addText(msg);
                        } catch (KakaoParameterException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mSitesPresenter.start();
        mBankPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banks = getResources().getStringArray(R.array.banks);
        try {
            kakaoLink = KakaoLink.getKakaoLink(getActivity());
        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }
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

        bunoItemList = new ArrayList<>();
        recyclerView.setAdapter(new BunoExpandableListAdapter(bunoItemList, mBunoItemListener));
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
        bunoItemList.clear();
        siteList = sites;
        Log.i("BunoFragment", sites.get(0).getTitle());
        bunoItemList.add(new BunoExpandableListAdapter.BunoItem(BunoExpandableListAdapter.HEADER, BunoConstants.TITLE_SITE, "", ""));
        for ( Site site : sites) {
            bunoItemList.add(new BunoExpandableListAdapter.BunoItem(BunoExpandableListAdapter.CHILD, BunoConstants.TITLE_SITE, site.getTitle(), site.getId()));
        }
    }

    @Override
    public void showAddSite() {
        Intent intent = new Intent(getContext(), AddEditSiteActivity.class);
        startActivityForResult(intent, AddEditSiteActivity.REQUEST_ADD_SITE);
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
        bankList = banks;
        Log.i("BunoFragment", banks.get(0).getTitle());
        bunoItemList.add(new BunoExpandableListAdapter.BunoItem(BunoExpandableListAdapter.HEADER, BunoConstants.TITLE_BANK, "", ""));
        for ( Bank bank : banks) {
            bunoItemList.add(new BunoExpandableListAdapter.BunoItem(BunoExpandableListAdapter.CHILD, BunoConstants.TITLE_BANK, bank.getTitle(), bank.getId()));
        }


    }

    @Override
    public void showAddEditBankUI() {
        Intent intent = new Intent(getContext(), AddEditBankActivity.class);
        startActivityForResult(intent, AddEditBankActivity.REQUEST_ADD_BANK);
    }

    @Override
    public void showDetailBankUI(String bankId) {
        Intent intent = new Intent(getContext(), AddEditBankActivity.class);
        intent.putExtra(AddEditBankActivity.EXTRA_BANK_ID, bankId);
        startActivity(intent);
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

    public interface BunoItemListener {
        void onSiteClick(BunoExpandableListAdapter.BunoItem clickedItem);
        void onKakaoClick(BunoExpandableListAdapter.BunoItem clickedItem);
    }
}
