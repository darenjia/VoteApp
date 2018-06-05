package com.bokun.bkjcb.voteapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bokun.bkjcb.voteapp.Model.PersonInfo;
import com.bokun.bkjcb.voteapp.R;
import com.lzy.widget.HeaderScrollHelper;

/**
 * Created by DengShuai on 2018/6/4.
 * Description :投票Fragment
 */
public class VoteFragment extends Fragment implements HeaderScrollHelper.ScrollableContainer {
    private PersonInfo info;
    private View view;

    public static VoteFragment newInstance(PersonInfo person) {
        VoteFragment fragment = new VoteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Key", person);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        info = (PersonInfo) getArguments().getSerializable("Key");
        view = inflater.inflate(R.layout.detail_view, container,false);
        //初始化详情视图
        return view;
    }

    @Override
    public View getScrollableView() {

        return getView();
    }
}
