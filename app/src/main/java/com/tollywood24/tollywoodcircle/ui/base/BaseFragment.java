package com.tollywood24.tollywoodcircle.ui.base;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;

import com.tollywood24.tollywoodcircle.Application;
import com.tollywood24.tollywoodcircle.injection.component.ConfigPersistentComponent;
import com.tollywood24.tollywoodcircle.injection.component.DaggerConfigPersistentComponent;
import com.tollywood24.tollywoodcircle.injection.component.FragmentComponent;
import com.tollywood24.tollywoodcircle.injection.module.FragmentModule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import timber.log.Timber;

public class BaseFragment extends BottomSheetDialogFragment {


    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();
    private Integer screenId;
    private HashMap<String, Object> meatDataHashMap;
    private FragmentComponent mFragmentComponent;
    private long mActivityId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;


        if (!sComponentsMap.containsKey(mActivityId)) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(Application.get(getActivity()).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = sComponentsMap.get(mActivityId);
        }
        mFragmentComponent = configPersistentComponent.fragmentComponent(new FragmentModule(this));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    public void onDestroy() {
        if (!getActivity().isFinishing()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", mActivityId);
            sComponentsMap.remove(mActivityId);
        }
        super.onDestroy();
    }

    public FragmentComponent fragmentComponent() {
        return mFragmentComponent;
    }


}
