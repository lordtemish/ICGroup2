package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewJalobaFragment extends Fragment {

    TextView workerTextView, qualityTextView;
    public AddNewJalobaFragment() {
        // Required empty public constructor
    }

    private void createViews(View view){
        workerTextView=(TextView)view.findViewById(R.id.workerTextView);
        qualityTextView=(TextView)view.findViewById(R.id.qualityTextView);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_jaloba, container, false);
        createViews(view);
        return view;
    }

}
