package kjw.kr.bunobuno.bunos.addedit;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kjw.kr.bunobuno.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditSiteFragment extends Fragment {

    public AddEditSiteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_site_detail, container, false);
    }
}