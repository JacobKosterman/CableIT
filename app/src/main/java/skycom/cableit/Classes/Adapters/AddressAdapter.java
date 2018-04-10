package skycom.cableit.Classes.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

import skycom.cableit.Classes.Address;
import skycom.cableit.R;

public class AddressAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private AddressFilter addressFilter;
    private ArrayList<Address> addressList;
    private ArrayList<Address> filteredList;

    public AddressAdapter(Context activity, ArrayList<Address> addressList) {
        this.context = activity;
        this.addressList = addressList;
        this.filteredList = addressList;

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
        final Address address = (Address) getItem(position);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            //TODO: Update with new layout
            view = inflater.inflate(R.layout.row_contact, parent, false);
            holder = new ViewHolder();
            holder.address1 = (TextView) view.findViewById(R.id.name);
            holder.address2 = (TextView) view.findViewById(R.id.email);

            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        // bind text with view holder content view for efficient use
        holder.address1.setText(address.address1);
        holder.address2.setText(address.address2);
        holder.cityProvPostal.setText(new StringBuilder().append(address.city).append(", ")
                .append(address.province).append(", ").append(address.postalCode).toString());

        return view;
    }

    @Override
    public Filter getFilter() {
        if (addressFilter == null) {
            addressFilter = new AddressFilter();
        }

        return addressFilter;
    }

    static class ViewHolder {
        TextView address1;
        TextView address2;
        TextView cityProvPostal;
    }

    private class AddressFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Address> tempList = new ArrayList<Address>();

                // search content in friend list
                for (Address address : addressList) {
                    if (address.city.toLowerCase().contains(constraint.toString().toLowerCase())
                            || address.country.toLowerCase().contains(constraint.toString().toLowerCase())
                            || address.postalCode.toLowerCase().contains(constraint.toString().toLowerCase())
                            || address.province.toLowerCase().contains(constraint.toString().toLowerCase())
                            || address.country.toLowerCase().contains(constraint.toString().toLowerCase())){
                        tempList.add(address);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = addressList.size();
                filterResults.values = addressList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Address>) results.values;
            notifyDataSetChanged();
        }
    }
}
