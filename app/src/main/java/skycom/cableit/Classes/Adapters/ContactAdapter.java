package skycom.cableit.Classes.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.*;

import skycom.cableit.Classes.Contact;
import skycom.cableit.R;

public class ContactAdapter extends BaseAdapter implements Filterable {

    private Context activity;
    private FriendFilter friendFilter;
    private ArrayList<Contact> friendList;
    private ArrayList<Contact> filteredList;

    /**
     * Initialize context variables
     * @param activity friend list activity
     * @param friendList friend list
     */
    public ContactAdapter(Context activity, ArrayList<Contact> friendList) {
        this.activity = activity;
        this.friendList = friendList;
        this.filteredList = friendList;

        getFilter();
    }

    /**
     * Get size of user list
     * @return userList size
     */
    @Override
    public int getCount() {
        return filteredList.size();
    }

    /**
     * Get specific item from user list
     * @param i item index
     * @return list item
     */
    @Override
    public Object getItem(int i) {
        return filteredList.get(i);
    }

    /**
     * Get user list item id
     * @param i item index
     * @return current item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Create list row view
     * @param position index
     * @param view current list item view
     * @param parent parent
     * @return view
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        final ViewHolder holder;
        final Contact user = (Contact) getItem(position);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
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

    /**
     * Get custom filter
     * @return filter
     */
    @Override
    public Filter getFilter() {
        if (friendFilter == null) {
            friendFilter = new FriendFilter();
        }

        return friendFilter;
    }

    /**
     * Keep reference to children view to avoid unnecessary calls
     */
    static class ViewHolder {
        TextView email;
        TextView name;
    }

    /**
     * Custom filter for friend list
     * Filter content in friend list according to the search text
     */
    private class FriendFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Contact> tempList = new ArrayList<Contact>();

                // search content in friend list
                for (Contact user : friendList) {
                    if (user.contactName.toLowerCase().contains(constraint.toString().toLowerCase())
                            || user.emailAddress.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = friendList.size();
                filterResults.values = friendList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Contact>) results.values;
            notifyDataSetChanged();
        }
    }

}
