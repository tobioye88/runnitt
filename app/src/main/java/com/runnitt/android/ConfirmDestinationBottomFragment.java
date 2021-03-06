package com.runnitt.android;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmDestinationBottomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmDestinationBottomFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btConfirmDestination;


    private OnConfirmButtonClickListener listener;

    public ConfirmDestinationBottomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmDestinationBottomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmDestinationBottomFragment newInstance(String param1, String param2) {
        ConfirmDestinationBottomFragment fragment = new ConfirmDestinationBottomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectDestinationBottomFragment.OnFragmentInteractionListener) {
            listener = (OnConfirmButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_destination_bottom, container, false);
        btConfirmDestination = view.findViewById(R.id.btConfirmDestination);
        btConfirmDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirmButtonClick();
            }
        });
        return view;
    }

    public interface OnConfirmButtonClickListener {
        void onConfirmButtonClick();
    }
}
