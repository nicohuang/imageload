package hwz.com.imageload;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jan on 15/7/3.
 */
public class LoadImageAdapter extends BaseAdapter
{
    private Context context;
    private List<String> items;
    public LoadImageAdapter(Context context,List<String> items)
    {
        this.context = context;
        this.items = items;
    }
    public final class ListItems
    {
        TextView tv_name;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ListItems listItems = new ListItems();;
        if(convertView == null)
        {

            convertView = View.inflate(context,R.layout.item_image,null);
            listItems.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            convertView.setTag(listItems);
        }
        else
        {
            listItems = (ListItems)convertView.getTag();
        }
        listItems.tv_name.setText(items.get(position));
        return convertView;
    }

    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public Object getItem(int position)
    {
        return items.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
}
