package com.tollywood24.tollywoodcircle.ui.landingpage.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tollywood24.tollywoodcircle.R;

/**
 *
 */
public class DemoFragment extends Fragment {

    private FrameLayout fragmentContainer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    /**
     * Create a new instance of the fragment
     */
    public static DemoFragment newInstance(int index) {
        DemoFragment fragment = new DemoFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments().getInt("index", 0) == 0) {
            View view = inflater.inflate(R.layout.fragment_demo_settings, container, false);
            return view;
        } else {
            View view = inflater.inflate(R.layout.fragment_demo_list, container, false);
            return view;
        }
    }
}
