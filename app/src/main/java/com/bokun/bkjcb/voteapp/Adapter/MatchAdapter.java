package com.bokun.bkjcb.voteapp.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bokun.bkjcb.voteapp.Model.MatchList;
import com.bokun.bkjcb.voteapp.R;
import com.bokun.bkjcb.voteapp.Utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.haozhang.lib.SlantedTextView;

/**
 * Created by DengShuai on 2018/6/27.
 * Description :
 */
public class MatchAdapter extends BaseAdapter {
    Context context;
    MatchList list;
    private ViewHolder holder;

    public MatchAdapter(Context context, MatchList list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.getData().size();
    }

    @Override
    public MatchList.Data getItem(int i) {
        return list.getData().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            holder = new ViewHolder(context, R.layout.match_child_view);
            view = holder.view;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.setImage(R.id.match_img, Utils.getImageUrl(getItem(i).getFileurl()))
                .setText(R.id.match_title, getItem(i).getTitle())
                .setText(R.id.match_content, getItem(i).getContent())
                .setText(R.id.match_date, getItem(i).getDate())
                .setSlantedView(R.id.slanted_view,
                        getItem(i).getIscomplete().equals("0") ? "已完成" : "进行中",
                        getItem(i).getIscomplete().equals("0") ? context.getResources().getColor(R.color.lightGreen) : context.getResources().getColor(R.color.orange));
        return view;
    }

    public void setData(MatchList list) {
        this.list.getData().clear();
        this.list.getData().addAll(list.getData());
        notifyDataSetChanged();
    }

    class ViewHolder {
        View view;
        RequestOptions options = new RequestOptions().placeholder(null).error(null);

        ViewHolder(Context context, int layoutId) {
            view = View.inflate(context, layoutId, null);
        }

        ViewHolder setText(int resId, String txt) {
            ((TextView) view.findViewById(resId)).setText(txt);
            return this;
        }

        ViewHolder setImage(int resId, String url) {
            Glide.with(context).load(url).apply(options).into((ImageView) view.findViewById(resId));
            return this;
        }

        ViewHolder setSlantedView(int resId, String txt, int color) {
            ((SlantedTextView) view.findViewById(resId)).setText(txt).setSlantedBackgroundColor(color);
            return this;
        }
    }
}
