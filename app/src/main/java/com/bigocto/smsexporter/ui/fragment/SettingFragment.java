package com.bigocto.smsexporter.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bigocto.smsexporter.app.R;

public class SettingFragment extends Fragment{

    View view;
    Context context;
    public SettingFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context=getActivity();
        view=inflater.inflate(R.layout.fragment_setting,container,false);

        return view;
    }
}
