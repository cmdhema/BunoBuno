package kjw.kr.bunobuno.bunos.sites.addedit;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import kjw.kr.bunobuno.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditSiteFragment extends Fragment implements AddEditSiteContract.View{

    public static final String ARGUMENT_EDIT_SITE_ID = "EDIT_SITE_ID";

    private EditText mTitleTv;
    private EditText mPasswordTv;

    private AddEditSiteContract.Presenter mAddEditSitePresenter;

    public AddEditSiteFragment() {
    }

    public static AddEditSiteFragment newInstance() {
        return new AddEditSiteFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        mAddEditSitePresenter.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_site_done);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddEditSitePresenter.saveSite(mTitleTv.getText().toString(), mPasswordTv.getText().toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addedit_site, container, false);
        mTitleTv = (EditText) root.findViewById(R.id.add_site_title);
        mPasswordTv = (EditText) root.findViewById(R.id.add_site_password);
        setHasOptionsMenu(true);
        return root;
    }


    @Override
    public void showSitesList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showEmptySiteError() {
        Snackbar.make(mTitleTv, getString(R.string.empty_task_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public void setPresenter(@NonNull AddEditSiteContract.Presenter presenter) {
        mAddEditSitePresenter = checkNotNull(presenter);
    }

    @Override
    public void showSuccessfullySavedMessage() {

    }
}
