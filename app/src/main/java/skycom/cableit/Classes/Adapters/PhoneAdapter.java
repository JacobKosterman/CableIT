package skycom.cableit.Classes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import skycom.cableit.Classes.Phone;
import skycom.cableit.R;

public class PhoneAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private PhoneFilter phoneFilter;
    private ArrayList<Phone> phoneList;
    private ArrayList<Phone> filteredList;

    public PhoneAdapter(Context activity, ArrayList<Phone> phoneList) {
        this.context = activity;
        this.phoneList = phoneList;
        this.filteredList = phoneList;

        getFilter();
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        final Phone phoneNumber = (Phone) getItem(position);


        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.row_phone, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.email = (TextView) view.findViewById(R.id.email);


            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        // bind text with view holder content view for efficient use

        String tempPhone = "Test String";//phoneNumber.phoneNumber + " " + phoneNumber.ext;
        holder.name.setText(tempPhone);
        holder.email.setText(phoneNumber.description);

        return view;
    }

    @Override
    public Filter getFilter() {
        if (phoneFilter == null) {
            phoneFilter = new PhoneFilter();
        }

        return phoneFilter;
    }

    static class ViewHolder {
        TextView email;
        TextView name;
    }

    private class PhoneFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Phone> tempList = new ArrayList<Phone>();

                // search content in friend list
                for (Phone phone : phoneList) {
                    if (phone.phoneNumber.toLowerCase().contains(constraint.toString().toLowerCase())
                            || phone.description.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(phone);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = phoneList.size();
                filterResults.values = phoneList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Phone>) results.values;
            notifyDataSetChanged();
        }
    }
}
