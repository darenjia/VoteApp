package com.bokun.bkjcb.voteapp.View;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bokun.bkjcb.voteapp.Model.PersonModel;
import com.bokun.bkjcb.voteapp.R;
import com.bokun.bkjcb.voteapp.Utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by DengShuai on 2018/6/8.
 * Description :
 */
public class ConfirmScoreView {

    private View view;


    public View builder(Context context, PersonModel person) {
        view = View.inflate(context, R.layout.score_view, null);
        TextView title = view.findViewById(R.id.person_title);
        TextView name = view.findViewById(R.id.person_name);
        TextView score = view.findViewById(R.id.person_score);
        ImageView img = view.findViewById(R.id.person_img);
        title.setText(person.getPtitle());
        name.setText(person.getPerson());
        score.setText(person.getScore());
        RequestOptions options = new RequestOptions().placeholder(R.drawable.man1).error(R.drawable.man1);
        Glide.with(context).load(Utils.getImageUrl(person.getFileurl())).apply(options).into(img);

        return view;
    }

}
