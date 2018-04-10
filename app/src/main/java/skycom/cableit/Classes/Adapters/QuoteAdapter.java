package skycom.cableit.Classes.Adapters;

import android.arch.persistence.room.Database;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import skycom.cableit.Classes.Quote;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class QuoteAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private QuoteFilter quoteFilter;
    private ArrayList<Quote> quotetList;
    private ArrayList<Quote> filteredList;
    private AppDatabase database;

    public QuoteAdapter(Context activity, ArrayList<Quote> quotetList) {
        this.context = activity;
        this.quotetList = quotetList;
        this.filteredList = quotetList;
        database = AppDatabase.getDatabase(context.getApplicationContext());
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
        final Quote quote = (Quote) getItem(position);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.row_quote, parent, false);
            holder = new ViewHolder();
            holder.quoteNumber = (TextView) view.findViewById(R.id.quoteNumber);
            holder.dateCreated = (TextView) view.findViewById(R.id.dateCreated);
            holder.companyName = (TextView) view.findViewById(R.id.companyName);

            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        // bind text with view holder content view for efficient use
        holder.quoteNumber.setText(quote.quoteNumber);
        holder.dateCreated.setText(quote.dateCreated.toString());
        holder.companyName.setText(database.companyDAO().getCompany(quote.companyID).get(0).name);

        return view;
    }

    @Override
    public Filter getFilter() {
        if (quoteFilter == null) {
            quoteFilter = new QuoteFilter();
        }

        return quoteFilter;
    }

    static class ViewHolder {
        TextView quoteNumber;
        TextView dateCreated;
        TextView companyName;
    }

    private class QuoteFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Quote> tempList = new ArrayList<Quote>();

                // search content in friend list
                for (Quote quote : quotetList) {
                    tempList.add(quote);
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = quotetList.size();
                filterResults.values = quotetList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Quote>) results.values;
            notifyDataSetChanged();
        }
    }
}