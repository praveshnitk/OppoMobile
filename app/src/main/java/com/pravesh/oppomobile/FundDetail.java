package com.pravesh.oppomobile;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;
import com.pravesh.oppomobile.adapter.FundDataAdapter;
import com.pravesh.oppomobile.model.Datum;
import com.pravesh.oppomobile.model.FundListData;
import com.pravesh.oppomobile.retrofit.ApiClient;
import com.pravesh.oppomobile.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FundDetail extends AppCompatActivity implements MaterialSpinner.OnItemSelectedListener {

    private RecyclerView mRecyclerView;
    private FundDataAdapter adapter;
    private SearchView searchView;
    private ActionBar actionBar;
    private TextView mFilter;
    private MaterialSpinner materialSpinner;
    private ArrayList<String> filterName;
    private FundListData fundListData;
    ArrayList<Datum> sortedData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_detail);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle("Top Rated Funds");
        //getActionBar().setTitle("Top Rated Funds");
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mFilter = (TextView) findViewById(R.id.tvFilter);
        materialSpinner = (MaterialSpinner) findViewById(R.id.spinner);
        callFundData();
        spinnerCall();
    }

    private void callFundData() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog progressDialog = new ProgressDialog(FundDetail.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Call<JsonObject> call = apiService.getResult();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    Gson gson = new Gson();
                    fundListData = gson.fromJson(response.body().toString(), FundListData.class);
                    if (fundListData != null && !fundListData.getData().isEmpty()) {
                        sortedData = new ArrayList<>();
                        sortedData.addAll(fundListData.getData());
                        callAdapter(fundListData);
                    } else {
                        Toast.makeText(FundDetail.this, "Record Not Found", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex){
                    Toast.makeText(FundDetail.this, ex.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Toast.makeText(FundDetail.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callAdapter(FundListData fundListData) {
        try {
            adapter = new FundDataAdapter(FundDetail.this, fundListData,mFilter);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(FundDetail.this));
        }catch (Exception ex){
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.search, menu);
            final MenuItem searchItem = menu.findItem(R.id.action_search);
            searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

            EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchEditText.setHintTextColor(getResources().getColor(R.color.white));
            searchEditText.setTextColor(getResources().getColor(R.color.white));

            searchEditText.setCursorVisible(true);
            searchView.setIconified(true);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.filter(newText.toString().trim());
                    mRecyclerView.invalidate();
                    return true;
                }
            });
        }catch (Exception ex){
        }
//        searchView.onActionViewCollapsed();
        return true;
    }

    private void spinnerCall() {
        try {
            filterName = new ArrayList<>();
            filterName.add("No Filter");
            filterName.add("Bank Name");
            filterName.add("Fund Category");
            //leave name spinner
            MaterialSpinnerAdapter materialSpinnerAdapter = new MaterialSpinnerAdapter(FundDetail.this, filterName);
            materialSpinnerAdapter.setTextColor(R.color.grey_txt);
            materialSpinner.setAdapter(materialSpinnerAdapter);
            materialSpinner.setOnItemSelectedListener(this);
        }catch (Exception ex){
        }
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        if (position==1){
            Collections.sort(sortedData, new Comparator<Datum>() {
                public int compare(Datum v1, Datum v2) {
                    try {
                        String name1 = v1.getName();
                        String name2 = v2.getName();
                        try {
                            return name1.compareTo(name2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception ex) {
                    }
                    return 0;
                }
            });
            FundListData sortFund = new FundListData();
            sortFund.setData(sortedData);
            callAdapter(sortFund);
        }else if (position==2){
            Collections.sort(sortedData, new Comparator<Datum>() {
                public int compare(Datum v1, Datum v2) {
                    try {
                        String name1 = v1.getFundCategory();
                        String name2 = v2.getFundCategory();
                        try {
                            return name1.compareTo(name2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception ex) {
                    }
                    return 0;
                }
            });
            FundListData sortFund = new FundListData();
            sortFund.setData(sortedData);
            callAdapter(sortFund);
        }else {
            callAdapter(fundListData);
        }
        searchView.setQuery("", false);
        searchView.clearFocus();
    }
}
