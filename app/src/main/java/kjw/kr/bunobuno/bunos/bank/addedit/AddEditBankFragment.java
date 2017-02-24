package kjw.kr.bunobuno.bunos.bank.addedit;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import kjw.kr.bunobuno.R;
import kjw.kr.bunobuno.bunos.sites.addedit.AddEditSiteFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditBankFragment extends Fragment implements  AddEditBankContract.View {

    public static final String ARGUMENT_EDIT_BANK_ID = "BANK_ID";

    private AddEditBankContract.Presenter addEditBankPresenter;

    private EditText mTitleTv;
    private EditText mNumberTv;

    private Spinner bankSpinner;

    public AddEditBankFragment() {
    }

    public static AddEditBankFragment newInstance() {
        return new AddEditBankFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        addEditBankPresenter.start();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_bank_done);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AddBankFragment", "1");
                addEditBankPresenter.saveBank(mTitleTv.getText().toString(), mNumberTv.getText().toString(), bankSpinner.getSelectedItemPosition());
                Log.i("AddBankFragment", "2");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_addedit_bank, container, false);
        mTitleTv = (EditText) root.findViewById(R.id.add_bank_title);
        mNumberTv = (EditText) root.findViewById(R.id.add_bank_number);
        bankSpinner = (Spinner) root.findViewById(R.id.add_bank_bank);

        String[] banks = getResources().getStringArray(R.array.banks);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, banks);
        bankSpinner.setAdapter(stringArrayAdapter);
        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void showBankList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

    @Override
    public void setBank(int bank) {
        bankSpinner.setSelection(bank);
    }

    @Override
    public void setNumber(String number) {
        mNumberTv.setText(number);
    }

    @Override
    public void setPresenter(AddEditBankContract.Presenter presenter) {
        addEditBankPresenter = presenter;
    }

    @Override
    public void showSuccessfullySavedMessage() {

    }
}
