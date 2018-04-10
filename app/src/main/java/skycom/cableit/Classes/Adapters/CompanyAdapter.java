package skycom.cableit.Classes.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

import skycom.cableit.Classes.Company;
import skycom.cableit.R;

public class CompanyAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private CompanyFilter companyFilter;
    private ArrayList<Company> companyList;
    private ArrayList<Company> filteredList;

    public CompanyAdapter(Context activity, ArrayList<Company> companyList) {
        this.context = activity;
        this.companyList = companyList;
        this.filteredList = companyList;

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
        final Company company = (Company) getItem(position);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            //TODO: Update with new layout
            view = inflater.inflate(R.layout.row_company, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.description = (TextView) view.findViewById(R.id.email);

            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        // bind text with view holder content view for efficient use
        holder.name.setText(company.name);
        holder.description.setText(company.description);

        return view;
    }

    @Override
    public Filter getFilter() {
        if (companyFilter == null) {
            companyFilter = new CompanyFilter();
        }

        return companyFilter;
    }

    static class ViewHolder {
        TextView name;
        TextView description;
    }

    private class CompanyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Company> tempList = new ArrayList<Company>();

                // search content in friend list
                for (Company company : companyList) {
                    if (company.name.toLowerCase().contains(constraint.toString().toLowerCase())
                            || company.description.toLowerCase().contains(constraint.toString().toLowerCase())){
                        tempList.add(company);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = companyList.size();
                filterResults.values = companyList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Company>) results.values;
            notifyDataSetChanged();
        }
    }
}
