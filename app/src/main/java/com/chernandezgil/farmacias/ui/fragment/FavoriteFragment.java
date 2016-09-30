package com.chernandezgil.farmacias.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chernandezgil.farmacias.R;
import com.chernandezgil.farmacias.Utilities.Util;
import com.chernandezgil.farmacias.data.LoaderProvider;
import com.chernandezgil.farmacias.model.Pharmacy;
import com.chernandezgil.farmacias.presenter.FavoritePresenter;
import com.chernandezgil.farmacias.ui.adapter.CustomItemAnimator;
import com.chernandezgil.farmacias.ui.adapter.FavoriteAdapter;
import com.chernandezgil.farmacias.ui.adapter.PreferencesManager;
import com.chernandezgil.farmacias.ui.adapter.PreferencesManagerImp;
import com.chernandezgil.farmacias.ui.adapter.item_decoration.CustomItemDecoration;
import com.chernandezgil.farmacias.ui.adapter.touch_helper.OnStartDragListener;
import com.chernandezgil.farmacias.ui.adapter.touch_helper.SimpleItemTouchHelperCallback;
import com.chernandezgil.farmacias.view.FavoriteContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 28/09/2016.
 */

public class FavoriteFragment extends Fragment implements FavoriteContract.View,
        FavoriteAdapter.FavoriteAdapterOnClickHandler, OnStartDragListener {

    private FavoritePresenter mPresenter;
    private PreferencesManager mSharedPreferences;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.emptyView)
    TextView mEmptyView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private FavoriteAdapter mAdapter;
    private Unbinder mUnbinder;
    private ItemTouchHelper mItemTouchHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = new PreferencesManagerImp(getActivity());
        Location location= mSharedPreferences.getLocation();
        LoaderManager loaderManager= getLoaderManager();
        LoaderProvider loaderProvider = new LoaderProvider(getActivity().getApplicationContext());
        mPresenter = new FavoritePresenter(location,loaderManager,loaderProvider);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_favorite,container,false);
        mUnbinder= ButterKnife.bind(this,view);
        setUpRecyclerView();
        mPresenter.setView(this);
        return view;
      //
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onInitLoader();
    }

    private void setUpRecyclerView(){
        CustomItemAnimator customItemAnimator = new CustomItemAnimator();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new FavoriteAdapter(getActivity().getApplicationContext(),this,mRecyclerView,customItemAnimator,this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(customItemAnimator);
     //   mRecyclerView.addItemDecoration(new CustomItemDecoration());
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
    @Override
    public void showResults(List<Pharmacy> pharmacyList) {
        mAdapter.swapData(pharmacyList);
    }

    @Override
    public void showNoResults() {

        mEmptyView.setVisibility(View.VISIBLE);

    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void showSnackBar(String message) {

    }

    @Override
    public void onClickGo(Pharmacy pharmacy) {

    }

    @Override
    public void onClickFavorite(Pharmacy pharmacy) {

    }

    @Override
    public void onClickPhone(String phone) {

    }

    @Override
    public void onClickShare(Pharmacy pharmacy) {

    }
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
   //     viewHolder.itemView.setBackgroundColor(Util.modifyAlpha(ContextCompat.getColor(getActivity(),R.color.red_200),0.40f));
        mItemTouchHelper.startDrag(viewHolder);

    }




    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }


}
