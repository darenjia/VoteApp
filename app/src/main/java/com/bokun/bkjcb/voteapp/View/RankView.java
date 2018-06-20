package com.bokun.bkjcb.voteapp.View;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bokun.bkjcb.voteapp.Model.PersonResult;
import com.bokun.bkjcb.voteapp.R;

import java.util.List;

/**
 * Created by DengShuai on 2018/6/8.
 * Description :
 */
public class RankView {
    static int[] drawables = {
            R.drawable.first,
            R.drawable.second,
            R.drawable.third,
//            R.drawable.fourth,
//            R.drawable.fifth,
//            R.drawable.sixth
            R.drawable.four,
            R.drawable.fifit,
            R.drawable.six,
            R.drawable.senver,
            R.drawable.eight
//           R.drawable.nine,
//           R.drawable.ten
    };

    public static View builder(Context context, List<PersonResult> list) {
        View view = View.inflate(context, R.layout.rank_view, null);
        LinearLayout layout = view.findViewById(R.id.result_view);

        for (int i = 0; i < list.size(); i++) {

            View child_view = View.inflate(context, R.layout.rank_child_view, null);
            if (i < 8) {
                ((ImageView) child_view.findViewById(R.id.rank_img)).setImageDrawable(context.getResources().getDrawable(drawables[i]));
            }else{
                //((ImageView) child_view.findViewById(R.id.rank_img)).setImageDrawable(""));
            }
            ((TextView) child_view.findViewById(R.id.rank_name)).setText(list.get(i).getPerson());
            ((TextView) child_view.findViewById(R.id.rank_score)).setText(list.get(i).getScore());
            layout.addView(child_view);

        }
        return view;
    }
}
