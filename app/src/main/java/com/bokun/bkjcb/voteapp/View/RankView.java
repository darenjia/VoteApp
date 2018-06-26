package com.bokun.bkjcb.voteapp.View;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bokun.bkjcb.voteapp.Model.PersonModel;
import com.bokun.bkjcb.voteapp.R;
import com.bokun.bkjcb.voteapp.Utils.Constants;
import com.bokun.bkjcb.voteapp.Utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by DengShuai on 2018/6/8.
 * Description :
 */
public class RankView {
    int[] drawables = {
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
    int type;
    private View view;

    public View builder(Context context, List<PersonModel> list, int type) {
        this.type = type;
        if (type == 1) {
            view = View.inflate(context, R.layout.score_view, null);
            GridView gridView = view.findViewById(R.id.grid);
            setHorizontalGridView(list.size(), gridView, context);
            gridView.setAdapter(new MyListAdapter(list, context));
        } else {
            view = View.inflate(context, R.layout.rank_view, null);
            ListView layout = view.findViewById(R.id.result_view);
            layout.setAdapter(new MyListAdapter(list, context));
        }
        return view;
    }

    private void setHorizontalGridView(int siz, GridView gridView, Context context) {
        int size = siz;
//      int length = (int) getActivity().getResources().getDimension(
//              R.dimen.coreCourseWidth);
        int length = 90;
        int gridviewWidth = Utils.dip2px(context, siz * length);
        int itemWidth = (Utils.dip2px(context, length));

        @SuppressWarnings("deprecation")
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(0); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数

    }

    class MyListAdapter extends BaseAdapter {
        List<PersonModel> list;
        Context context;
        private ViewHolder builder;

        public MyListAdapter(List<PersonModel> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public PersonModel getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                if (type==1){
                    builder = new ViewHolder(context, R.layout.score_child_view);
                }else {
                    builder = new ViewHolder2(context,R.layout.rank_child_view);
                }
                view = builder.view;
                view.setTag(builder);
            } else {
                builder = (ViewHolder) view.getTag();
            }
            builder.setContent(list.get(i),i);
            return builder.view;
        }

        class ViewHolder2 extends ViewHolder {

            public ViewHolder2(Context context, int layoutId) {
                super(context,layoutId);

            }

            public void setContent(PersonModel person, int position) {
                name.setText(person.getPerson());
                score.setText(person.getScore());
                if (position < drawables.length) {
                    imageView.setImageDrawable(context.getResources().getDrawable(drawables[position]));
                }
            }
        }

        class ViewHolder {
            ImageView imageView;
            TextView name;
            TextView score;
            Context context;
            View view;
            RequestOptions options = new RequestOptions().placeholder(R.drawable.man1).error(R.drawable.man1);

            public ViewHolder(Context context, int layoutId) {
                this.context = context;
                view = View.inflate(context, layoutId, null);
                name = view.findViewById(R.id.rank_name);
                score = view.findViewById(R.id.rank_score);
                imageView = view.findViewById(R.id.rank_img);
            }

            public void setContent(PersonModel person, int position) {
                name.setText(person.getPerson());
                score.setText(person.getScore());
                Glide.with(context).load(Constants.imgurl + person.getFileurl()).apply(options).into(imageView);
            }
        }
    }
}
