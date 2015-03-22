package au.amir.personal.reality.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import au.amir.personal.reality.R;
import au.amir.personal.reality.activity.AbstractFragment;
import au.amir.personal.reality.service.APIService;
import au.amir.personal.reality.service.MyService;



public class HomeFragment extends AbstractFragment    {

    EditText SearchQuery =null;
    View view;
    RecyclerView recyclerView;
    FactsAdapter factsAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private static final String TAG = HomeFragment.class.getName();

    @Override
    public String getHeaderName() {
        return null;
    }

    @Override
    protected int getViewFragmentId() {
        return R.layout.home_fragment;
    }

    @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.facts_rc_view);
        factsAdapter = new FactsAdapter(getActivity() );
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.MySwipeRefreshLayout);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        recyclerView.setAdapter(factsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HomeFragment.this.performRefresh();  // perform swipe view refresh on recyclerview
            }
        });
        return view;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public boolean onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case APIService.STATUS_RUNNING:
                //showProgressDialog(WebCommandEnums.GET_CATALOUGE.name()); // progress dialog can be displayed here
                break;
            case APIService.STATUS_FINISHED:
                // means data fetched success
               // dismissProgressDialog();  // close the dialog
                ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
                actionBar.setTitle(MyService.getInstance().getFactsSheet().getTitle());

                mSwipeRefreshLayout.setRefreshing(false);

                factsAdapter = new FactsAdapter(getActivity() );
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
                recyclerView.setAdapter(factsAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                
                break;
        }
        return true;
    }

}
