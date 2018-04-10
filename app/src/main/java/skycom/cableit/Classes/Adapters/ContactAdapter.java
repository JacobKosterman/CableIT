package skycom.cableit.Classes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

import skycom.cableit.Classes.Contact;
import skycom.cableit.R;

public class ContactAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ContactFilter contactFilter;
    private ArrayList<Contact> contactList;
    private ArrayList<Contact> filteredList;

    public ContactAdapter(Context activity, ArrayList<Contact> contactList) {
        this.context = activity;
        this.contactList = contactList;
        this.filteredList = contactList;

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
        final Contact user = (Contact) getItem(position);


        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.row_contact, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.email = (TextView) view.findViewById(R.id.email);

            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        // bind text with view holder content view for efficient use
        holder.name.setText(user.contactName);
        holder.email.setText(user.emailAddress);

        return view;
    }

    @Override
    public Filter getFilter() {
        if (contactFilter == null) {
            contactFilter = new ContactFilter();
        }

        return contactFilter;
    }

    static class ViewHolder {
        TextView email;
        TextView name;
    }

    private class ContactFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Contact> tempList = new ArrayList<Contact>();

                // search content in friend list
                for (Contact user : contactList) {
                    if (user.contactName.toLowerCase().contains(constraint.toString().toLowerCase())
                            || user.emailAddress.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = contactList.size();
                filterResults.values = contactList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Contact>) results.values;
            notifyDataSetChanged();
        }
    }
}
