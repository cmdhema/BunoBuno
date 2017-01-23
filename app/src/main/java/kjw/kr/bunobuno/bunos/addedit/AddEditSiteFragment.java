package kjw.kr.bunobuno.bunos.addedit;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kjw.kr.bunobuno.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditSiteFragment extends Fragment {

    public static final String ARGUMENT_EDIT_SITE_ID = "EDIT_SITE_ID";

    private TextView mTitleTv;
    private TextView mPasswordTv;

    public AddEditSiteFragment() {
    }

    public static AddEditSiteFragment newInstance() {
        return new AddEditSiteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addedit, container, false);
        mTitleTv = (TextView) root.findViewById(R.id.add_site_title);
        mPasswordTv = (TextView) root.findViewById(R.id.add_site_password);
        setHasOptionsMenu(true);
        return root;
    }


}
