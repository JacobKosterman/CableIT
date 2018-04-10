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

import skycom.cableit.Classes.Product;
import skycom.cableit.Classes.QuoteLine;
import skycom.cableit.Database.AppDatabase;
import skycom.cableit.R;

public class QuoteLineAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private QuoteLineFilter quoteLineFilter;
    private ArrayList<QuoteLine> quoteLinetList;
    private ArrayList<QuoteLine> filteredList;
    private AppDatabase database;

    public QuoteLineAdapter(Context activity, ArrayList<QuoteLine> quoteLinetList) {
        this.context = activity;
        this.quoteLinetList = quoteLinetList;
        this.filteredList = quoteLinetList;
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
        final QuoteLine quoteLine = (QuoteLine) getItem(position);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.row_quote_line, parent, false);
            holder = new ViewHolder();
            holder.partNumber = (TextView) view.findViewById(R.id.productNumber);
            holder.description = (TextView) view.findViewById(R.id.productDescription);
            holder.costPrice = (TextView) view.findViewById(R.id.costPrice);

            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        Product product = database.productDAO().getProduct(quoteLine.productID).get(0);
        holder.costPrice.setText(new StringBuilder().append("Cost: ")
                .append(String.valueOf(quoteLine.productCost)).append(" Price: ")
                .append(String.valueOf((quoteLine.productCost*(quoteLine.markupRate/100))+quoteLine.markupAmount)));
        holder.partNumber.setText(product.partNo);
        holder.description.setText(product.description);

        return view;
    }

    @Override
    public Filter getFilter() {
        if (quoteLineFilter == null) {
            quoteLineFilter = new QuoteLineFilter();
        }

        return quoteLineFilter;
    }

    static class ViewHolder {
        TextView partNumber;
        TextView description;
        TextView costPrice;
    }

    private class QuoteLineFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<QuoteLine> tempList = new ArrayList<QuoteLine>();

                // search content in friend list
                for (QuoteLine quoteLine : quoteLinetList) {
                    if (true){
                        tempList.add(quoteLine);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = quoteLinetList.size();
                filterResults.values = quoteLinetList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<QuoteLine>) results.values;
            notifyDataSetChanged();
        }
    }
}