package com.example.cong.articlesearch.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.cong.articlesearch.R;
import com.example.cong.articlesearch.adapter.ArticleRecycler;
import com.example.cong.articlesearch.api.ArticleApi;
import com.example.cong.articlesearch.model.Article;
import com.example.cong.articlesearch.model.SearchRequest;
import com.example.cong.articlesearch.model.SearchResult;
import com.example.cong.articlesearch.utils.RetrofitUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ArticleApi mArticleApi;
    private ArticleRecycler articleRecycler;
    private SearchRequest mSearchRequest;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private SearchView msearchView;
    @BindView(R.id.rvArticle)
    RecyclerView rvArticle;
    @BindView(R.id.pbLoading)
    View pbLoading;
    @BindView(R.id.pbLoadMore)
    ProgressBar pbLoadMore;
    private interface Listener{
        void onResult(SearchResult searchResult);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpApi();
        setUpViews();
        search();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        setUpSearchView(menuItem);
        return super.onCreateOptionsMenu(menu);

    }

    private void setUpSearchView(MenuItem menuItem) {
        msearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        msearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchRequest.setQ(query);
                search();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case(R.id.sort_bar):
                Intent intent = new Intent(MainActivity.this,DialogActivity.class);
                intent.putExtra("search",mSearchRequest);
                startActivityForResult(intent,1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            mSearchRequest = (SearchRequest) data.getSerializableExtra("searchBack");
        }
    }

    private void setUpViews() {
        articleRecycler = new ArticleRecycler();
        articleRecycler.setListener(new ArticleRecycler.Listener() {
            @Override
            public void loadMore() {
                searchMore();
            }
        });
        staggeredGridLayoutManager  = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvArticle.setLayoutManager(staggeredGridLayoutManager);
        rvArticle.setAdapter(articleRecycler);

    }

    private void setUpApi() {
        mArticleApi = RetrofitUtils.get().create(ArticleApi.class);
        mSearchRequest = new SearchRequest();
    }
    private void search(){
        pbLoading.setVisibility(View.VISIBLE);
        fetchArticles(new Listener() {
            @Override
            public void onResult(SearchResult searchResult) {
                mSearchRequest.resetPage();
                articleRecycler.setArticles(searchResult.getArticles());
            }
        });
    }
    private void searchMore(){
        pbLoadMore.setVisibility(View.VISIBLE);
        fetchArticles(new Listener() {
            @Override
            public void onResult(SearchResult searchResult) {
                mSearchRequest.addPage();
                articleRecycler.addArticles(searchResult.getArticles());

            }
        });

    }
    private void fetchArticles(final Listener listener){


        mArticleApi.search(mSearchRequest.castToMap()).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                listener.onResult(response.body());
                handleComplete();
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {

            }
        });
    }

    private void handleComplete() {
        pbLoading.setVisibility(View.GONE);
        pbLoadMore.setVisibility(View.GONE);
    }
}
