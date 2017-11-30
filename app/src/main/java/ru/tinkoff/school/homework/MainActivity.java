package ru.tinkoff.school.homework;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.tinkoff.school.homework.databinding.ActivityMainBinding;
import ru.tinkoff.school.homework.databinding.ListItemTitleBinding;

public class MainActivity extends AppCompatActivity {

    private TitleAdapter mAdapter;
    private ActivityMainBinding mBinding;
    private TinkoffApi mTinkoffApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.newsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        mAdapter = new TitleAdapter();
        mBinding.newsRecyclerView.setAdapter(mAdapter);

        initData();
        loadData();
    }

    public void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.tinkoff.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mTinkoffApi = retrofit.create(TinkoffApi.class);
    }

    public void loadData() {
        mTinkoffApi.getNews().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    mAdapter.setPayloads(response.body().getPayload());
                    mBinding.swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }

    private class TitleHolder extends RecyclerView.ViewHolder {

        private ListItemTitleBinding mBinding;

        public TitleHolder(ListItemTitleBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Payload payload) {
            mBinding.setViewModel(new TitleViewModel(payload));
            mBinding.executePendingBindings();
        }
    }

    private class TitleAdapter extends RecyclerView.Adapter<TitleHolder> {

        private SortedList<Payload> mPayloads;

        public TitleAdapter() {
            mPayloads = new SortedList<>(Payload.class, new SortedListAdapterCallback<Payload>(this) {
                @Override
                public int compare(Payload o1, Payload o2) {
                    int result = (int) (o1.getPublicationDate().getMilliseconds() - o2.getPublicationDate().getMilliseconds());
                    if (result != 0) {
                        return (-1) * result / Math.abs(result);
                    }
                    return 0;
                }

                @Override
                public boolean areContentsTheSame(Payload oldItem, Payload newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }

                @Override
                public boolean areItemsTheSame(Payload item1, Payload item2) {
                    return item1.getId().equals(item2.getId());
                }
            });;
        }

        @Override
        public TitleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ListItemTitleBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_title,
                    parent, false);
            return new TitleHolder(binding);
        }

        @Override
        public void onBindViewHolder(TitleHolder holder, int position) {
            Payload payload = mPayloads.get(position);
            holder.bind(payload);
        }

        @Override
        public int getItemCount() {
            return mPayloads.size();
        }

        public void setPayloads(List<Payload> payloads) {
            mPayloads.clear();
            mPayloads.addAll(payloads);
        }
    }
}
