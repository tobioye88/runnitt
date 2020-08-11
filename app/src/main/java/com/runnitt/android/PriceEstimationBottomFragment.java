package com.runnitt.android;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PriceEstimationBottomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PriceEstimationBottomFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView lvDetailsList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btSelectRecipient;
    private Button btConfirm;
    private LinearLayout linearLayout;

    private OnConfirmPriceClickListener listener;

    public PriceEstimationBottomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PriceEstimationBottomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PriceEstimationBottomFragment newInstance(String param1, String param2) {
        PriceEstimationBottomFragment fragment = new PriceEstimationBottomFragment();
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
            listener = (PriceEstimationBottomFragment.OnConfirmPriceClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_price_estimation_bottom, container, false);
        btSelectRecipient = view.findViewById(R.id.btSelectRecipient);
        linearLayout = view.findViewById(R.id.recipient_layout);
        btConfirm = view.findViewById(R.id.btConfirm);

        btSelectRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                btSelectRecipient.setVisibility(View.GONE);
                btConfirm.setVisibility(View.VISIBLE);
            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirmPriceClick();
            }
        });
        return view;
    }

    public interface OnConfirmPriceClickListener {
        void onConfirmPriceClick();
    }

}
