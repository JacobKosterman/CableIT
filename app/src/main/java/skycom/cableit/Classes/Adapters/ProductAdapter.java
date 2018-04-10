package skycom.cableit.Classes.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

import skycom.cableit.Classes.Product;
import skycom.cableit.R;

public class ProductAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ProductFilter productFilter;
    private ArrayList<Product> productList;
    private ArrayList<Product> filteredList;

    public ProductAdapter(Context activity, ArrayList<Product> productList) {
        this.context = activity;
        this.productList = productList;
        this.filteredList = productList;

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
        final Product product = (Product) getItem(position);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            //TODO: Update with new layout
            view = inflater.inflate(R.layout.row_contact, parent, false);
            holder = new ViewHolder();
            holder.productName = (TextView) view.findViewById(R.id.name);
            holder.partNumber = (TextView) view.findViewById(R.id.email);

            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        // bind text with view holder content view for efficient use
        holder.productName.setText(product.productName);
        holder.partNumber.setText(product.partNo);
        holder.description.setText(product.description);

        return view;
    }

    @Override
    public Filter getFilter() {
        if (productFilter == null) {
            productFilter = new ProductFilter();
        }

        return productFilter;
    }

    static class ViewHolder {
        TextView partNumber;
        TextView description;
        TextView productName;
    }

    private class ProductFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Product> tempList = new ArrayList<Product>();

                // search content in friend list
                for (Product product : productList) {
                    if (product.partNo.toLowerCase().contains(constraint.toString().toLowerCase())
                            || product.productName.toLowerCase().contains(constraint.toString().toLowerCase())
                            || product.description.toLowerCase().contains(constraint.toString().toLowerCase())){
                        tempList.add(product);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = productList.size();
                filterResults.values = productList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Product>) results.values;
            notifyDataSetChanged();
        }
    }
}
