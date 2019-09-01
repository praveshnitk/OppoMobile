package com.pravesh.oppomobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.pravesh.oppomobile.R;
import com.pravesh.oppomobile.model.Datum;
import com.pravesh.oppomobile.model.FundListData;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by dell on 16-Nov-17.
 */

public class FundDataAdapter extends RecyclerView.Adapter<FundDataAdapter.RecyclerViewHolder>
{
    SimpleDateFormat format,formatOut;
    Context context;
    LayoutInflater inflater;
    FundListData fundListData;
    FundListData newfundListData;

    ArrayList<Datum> filterData = new ArrayList<Datum>();
    TextView mFilter;
    public FundDataAdapter(Context context, FundListData fundListData, TextView mFilter) {
        try {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.fundListData=fundListData;
            this.newfundListData=fundListData;
            filterData.addAll(fundListData.getData());
            this.mFilter=mFilter;
            mFilter.setText("0");
        }catch (Exception ex){
        }
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            View v = inflater.inflate(R.layout.fund_data_adapter, parent, false);
            RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
            return viewHolder;
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        try {
            holder.mBankName.setText(newfundListData.getData().get(position).getName());
            holder.mFundCategory.setText(newfundListData.getData().get(position).getFundCategory());
            holder.mNavReturn.setText(newfundListData.getData().get(position).getNav()+" %");
            holder.mRating.setRating(newfundListData.getData().get(position).getRating());
            Picasso.with(context).load(newfundListData.getData().get(position).getIcon()).error(R.mipmap.ic_launcher).into(holder.mIvIcon);
        }catch (Exception ex){
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mBankName,mFundCategory,mNavReturn;
        MaterialRippleLayout full;
        ImageView mIvIcon;
        RatingBar mRating;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            try {
                mBankName = (TextView) itemView.findViewById(R.id.tvBankName);
                mIvIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
                mFundCategory = (TextView) itemView.findViewById(R.id.tvFundCategory);
                mRating = (RatingBar) itemView.findViewById(R.id.simpleRatingBar);
                mNavReturn = (TextView) itemView.findViewById(R.id.tvNavReturn);
                full = (MaterialRippleLayout) itemView.findViewById(R.id.materialRippleLayout);
            }catch (Exception ex){
            }
        }
    }

    public void filter(String charText) {
        try {
            charText = charText.toLowerCase(Locale.getDefault());
            if (charText.length() == 0) {
                filterData = new ArrayList<Datum>();
                newfundListData = new FundListData();
                filterData.addAll(fundListData.getData());
                newfundListData.setData(filterData);
                mFilter.setText("0");
            } else {
                ArrayList<Datum> duplicatList = new ArrayList<Datum>();
                for (Datum postDetail : fundListData.getData()) {
                    if (charText.length() != 0 && postDetail.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        duplicatList.add(postDetail);
                    }
                }
                mFilter.setText(String.valueOf(duplicatList.size()));
                filterData = new ArrayList<Datum>();
                this.filterData = duplicatList;
                newfundListData = new FundListData();
                //filterData.addAll(filterData);
                newfundListData.setData(filterData);

            }
            notifyDataSetChanged();
        }catch (Exception ex){
            Log.i("tag",ex.toString());
        }
    }

    @Override
    public int getItemCount() {
        try {
            return newfundListData.getData().size();
        }catch (Exception ex){
            return 0;
        }
    }
}
