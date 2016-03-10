package myView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cqhh.ibeacon.R;
import myModel.ListData;

/**
 * Created by cooper on 2016.3.7.
 */
public class TextAdapter extends BaseAdapter {
    private List<ListData> lists;
    private Context context;
    private RelativeLayout layout;

    public TextAdapter(List<ListData> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (lists.get(position).getFlag() == ListData.RECEIVE) {
            layout = (RelativeLayout) inflater.inflate(R.layout.leftitem, null);
        }
        if (lists.get(position).getFlag() == ListData.SEND) {
            layout = (RelativeLayout) inflater.inflate(R.layout.rightitem, null);
        }
        TextView tv = (TextView) layout.findViewById(R.id.tv);
        tv.setText(lists.get(position).getContent());
        TextView timeText = (TextView) layout.findViewById(R.id.time);
        timeText.setText(lists.get(position).getTime());
        return layout;
    }
}
