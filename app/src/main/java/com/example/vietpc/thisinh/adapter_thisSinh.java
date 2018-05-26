package com.example.vietpc.thisinh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class adapter_thisSinh extends BaseAdapter implements Filterable{
    Context context;
    List<thiSinh> thiSinhList;
    List<thiSinh> dataBackup;

    public adapter_thisSinh(Context context, List<thiSinh> thiSinhList) {
        this.context = context;
        this.thiSinhList = thiSinhList;
        this.dataBackup = thiSinhList;
    }

    @Override
    public int getCount() {
        return thiSinhList.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    class viewHolder{
        TextView tv_sbd,tv_ten,tv_td;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){

            convertView = inflater.inflate(R.layout.item_thisinh,null);
            holder = new viewHolder();
            holder.tv_sbd = (TextView) convertView.findViewById(R.id.textView_sbd);
            holder.tv_ten =(TextView) convertView.findViewById(R.id.textView_ten);
            holder.tv_td = (TextView) convertView.findViewById(R.id.textView_tongDiem);

            convertView.setTag(holder);
        }
        else holder = (viewHolder) convertView.getTag();

        holder.tv_sbd.setText(thiSinhList.get(position).getSbd() + "");
        holder.tv_ten.setText(thiSinhList.get(position).getTen());
        holder.tv_td.setText(thiSinhList.get(position).getTongDiem() + "");

        return convertView;
        }

    @Override
    public Filter getFilter() {
        final Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(dataBackup == null) dataBackup = new ArrayList<>();
                if (constraint == null || constraint.length() == 0)  {
                    filterResults.values = dataBackup;
                    filterResults.count = dataBackup.size();
                }else{
                    List<thiSinh> listfilter = new ArrayList<>();
                    for(thiSinh ts : thiSinhList){
                        if(ts.getTen().toLowerCase().contains(constraint.toString().toLowerCase()))
                            listfilter.add(ts);
                    }
                    filterResults.values = listfilter;
                    filterResults.count = listfilter.size();

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                thiSinhList = (List<thiSinh>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

}
