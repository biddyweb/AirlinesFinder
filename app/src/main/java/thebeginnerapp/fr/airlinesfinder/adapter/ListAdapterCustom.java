package thebeginnerapp.fr.airlinesfinder.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thebeginnerapp.fr.airlinesfinder.R;

/**
 * File created by Kevin on 08/02/2015 for AirlinesFinder
 */
public class ListAdapterCustom extends ArrayAdapter<String> {

	private ArrayList<String> countryList;
	private Context context;

	public ListAdapterCustom(Context context, int textViewResourceId,
	                         ArrayList<String> countryList) {
		super(context, textViewResourceId, countryList);
		this.countryList = new ArrayList<>();
		this.countryList.addAll(countryList);
		this.context = context;
	}

	private class ViewHolder {
		TextView code;
		CheckBox name;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		Log.v("ConvertView", String.valueOf(position));

		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.item_list, null);

			holder = new ViewHolder();
			holder.code = (TextView) convertView.findViewById(R.id.code);
			holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
			convertView.setTag(holder);


			holder.name.setOnClickListener( new View.OnClickListener() {
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v ;

					Toast.makeText(context,
							"Clicked on Checkbox: " + cb.getText() +
									" is " + cb.isChecked(),
							Toast.LENGTH_LONG).show();
				}
			});
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.code.setText(this.countryList.get(position));
		holder.name.setChecked(true);

		return convertView;

	}

}
