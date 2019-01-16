package com.bokun.bkjcb.voteapp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bokun.bkjcb.voteapp.Interface.TextChanged;
import com.bokun.bkjcb.voteapp.Model.PersonModel;
import com.bokun.bkjcb.voteapp.R;
import com.bokun.bkjcb.voteapp.Utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.widget.HeaderScrollHelper;

/**
 * Created by DengShuai on 2018/6/4.
 * Description :投票Fragment
 */
public class VoteFragment extends Fragment implements HeaderScrollHelper.ScrollableContainer {
    private PersonModel info;
    private View view;
    private TextChanged textChanged;
    private EditText score;
    private boolean isCompleted;

    public static VoteFragment newInstance(PersonModel person) {
        VoteFragment fragment = new VoteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Key", person);

        fragment.setArguments(bundle);
        return fragment;
    }

    public void setTextChanged(TextChanged textChanged) {
        this.textChanged = textChanged;
    }

    public String getScore() {
        return score.getText().toString();
    }

    public void hasCompleted(){
        score.setEnabled(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RequestOptions options = new RequestOptions().placeholder(R.drawable.man1).error(R.drawable.man1);

        info = (PersonModel) getArguments().getSerializable("Key");
        isCompleted = info.getIscompleted().equals("true");
        view = inflater.inflate(R.layout.detail_view, container, false);
        //初始化详情视图
        ImageView img = view.findViewById(R.id.detail_img);
        TextView name = view.findViewById(R.id.detail_name);
        TextView infos = view.findViewById(R.id.detail_info);
        TextView title = view.findViewById(R.id.detail_title);
        TextView age = view.findViewById(R.id.detail_age);
        TextView position = view.findViewById(R.id.detail_position);
        score = view.findViewById(R.id.detail_score);

        name.setText(info.getPerson());
        infos.setText(info.getRemark());
        title.setText(info.getPtitle());
        age.setText(info.getAge());
        position.setText(info.getPosition());
        if (!info.getScore().equals("")) {
            score.setText(info.getScore());
        }
        if (isCompleted) {
            score.setEnabled(false);
        }
        if (info.getFileurl().equals("")) {
            img.setImageDrawable(Utils.getRandomImage(getContext(), info.getSex()));
        } else {
            Glide.with(getContext()).load(Utils.getImageUrl(info.getFileurl())).apply(options).into(img);
        }
        return view;
    }

    @Override
    public View getScrollableView() {

        return getView();
    }

    @Override
    public void onStart() {
        super.onStart();
        score.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isSuccess(editable.toString().trim())) {
                    textChanged.onTextChange(info, editable.toString().trim());
                } else {
                    /*score.setText("");*/
                    textChanged.onTextChange(info, "");
                }
            }
        });
    }

    public static boolean isSuccess(String s) {
        try {
            int num = Integer.valueOf(s);
            if (num > 100 || num < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
