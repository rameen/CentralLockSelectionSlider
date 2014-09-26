package com.example.centralLockSelectionSlider;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.utils.Utils;



public class CustomExperienceSlider extends RelativeLayout {

	public static final String LAST_ELEMENT_SUFFIX = "+";

	public static final String ZEROTH_ELEMENT = "-";

	private ItemSelectedListener itemSelectionListener;

	private int selectorColor;
	private int rangeEnd;
	private int rangeBegin;

	private Gallery gallery;
	private CentralSelectionSliderAdapter adapter;

	public static interface ItemSelectedListener {
		public void onItemSelected(String item, int position);
	}

	public CustomExperienceSlider(Context context) {
		super(context);
		init(null);
	}

	public CustomExperienceSlider(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);

	}

	public CustomExperienceSlider(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	private void init(AttributeSet attrs) {

		extractAttributes(attrs);

		inflateAndInitalize();

	}

	private void inflateAndInitalize() {

		inflate(getContext(), R.layout.main_slider, this);

		final TextView selectorView = (TextView) findViewById(R.id.tv_selector);
		selectorView.setBackgroundColor(selectorColor);

		this.gallery = (Gallery) findViewById(R.id.gallery);
		adapter = new CentralSelectionSliderAdapter(getItems(), getContext(),
				R.layout.slider_exp_item, R.id.tv_slider, 50, selectorView);
		gallery.setAdapter(adapter);
		gallery.setCallbackDuringFling(false);

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			boolean firstTime = true;

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long id) {

				if (itemSelectionListener != null) {
					itemSelectionListener.onItemSelected(
							(String) adapter.getItem(position), position);
				}
				if (firstTime) {

					positionSelectorAtCenter(view, selectorView);
					firstTime = false;

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// no implementation yet
			}
		});
	}

	private void extractAttributes(AttributeSet attrs) {
		if (attrs != null) {

			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.CustomExperienceSlider);
			this.selectorColor = a.getColor(
					R.styleable.CustomExperienceSlider_centre_viewColor,
					R.color.tab_selected_blue);
			this.rangeBegin = a.getInteger(
					R.styleable.CustomExperienceSlider_rangeBegin, 1);
			this.rangeEnd = a.getInteger(
					R.styleable.CustomExperienceSlider_rangeEnd, 10);

			// Don't forget this
			a.recycle();
		} else {

			this.selectorColor = R.color.tab_selected_blue;
			this.rangeBegin = 1;
			this.rangeEnd = 10;

		}
	}

	private void positionSelectorAtCenter(View centerSelectedView,
			TextView selectorView) {

		RelativeLayout container = (RelativeLayout) centerSelectedView;
		TextView listItem = (TextView) container.getChildAt(0);

		int containerHeight = container.getHeight();
		// int itemLocation[] = new int[2];

		int listItemWidth = listItem.getWidth();
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				listItemWidth, containerHeight);

		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
				RelativeLayout.TRUE);
		// listItem.getLocationOnScreen(itemLocation);
		// layoutParams.setMargins(itemLocation[0], 0, 0, 0);
		// container.setPadding(0, 0, 0, 0);
		selectorView.setLayoutParams(layoutParams);

	}

	class CentralSelectionSliderAdapter extends BaseAdapter {

		private static final int INVALID_POSITION = -1;
		private String[] items;
		private int resourceId;
		private LayoutInflater infalter;
		private Context context;
		private int textViewId;
		int width;
		
		public CentralSelectionSliderAdapter(String[] items, Context context,
				int resourceId, int textViewId, int textViewSize,
				TextView selectorTextView) {
			this.items = items;
			this.context = context;
			this.resourceId = resourceId;
			this.textViewId = textViewId;
			infalter = LayoutInflater.from(this.context);
			width = (int) Utils.convertDpToPixel(textViewSize, context);

		}

		@Override
		public int getCount() {
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			return items[position];
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;

			if (convertView == null) {

				convertView = infalter.inflate(resourceId, null);
				viewHolder = new ViewHolder();
				viewHolder.expTextView = (TextView) convertView
						.findViewById(this.textViewId);
				convertView.setTag(viewHolder);
				convertView.setLayoutParams(new Gallery.LayoutParams(width,
						width));

			} else {

				viewHolder = (ViewHolder) convertView.getTag();

			}

			viewHolder.expTextView.setText(items[position]);

			return convertView;

		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		
		public int getPosition(String item)
		{
			int len = items.length;
			
			for (int i = 0 ; i < len;i++)
			{
				if (items[i].equals(item)) return i;
			}
			return INVALID_POSITION;
		}
		class ViewHolder {
			TextView expTextView;
		}

	}

	public void setItemSelectionListener(ItemSelectedListener listener) {
		this.itemSelectionListener = listener;

	}

	private String[] getItems() {

		int total = rangeEnd - rangeBegin + 1 + 2;

		String[] items = new String[total];
		items[0] = ZEROTH_ELEMENT;

		for (int i = rangeBegin; i <= rangeEnd; i++) {
			items[i] = "" + i;

		}
		items[rangeEnd + 1] = "" + rangeEnd + LAST_ELEMENT_SUFFIX;

		return items;

	}

	public void setSelection(String item) {
		
		int position = this.adapter.getPosition(item);
		
		if (position != CentralSelectionSliderAdapter.INVALID_POSITION)this.gallery.setSelection(position);

	}

}
