package com.example.cong.articlesearch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cong.articlesearch.R;
import com.example.cong.articlesearch.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cong on 23/10/2016.
 */

public class ArticleRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Article> mArticles;
    private final int IMAGE = 0;
    private final int NOIMAGE = 1;
    private Listener mlistener;

    public interface Listener{
        void loadMore();
    }
    public void setListener(Listener listener){
        mlistener = listener;
    }
    public ArticleRecycler() {
        this.mArticles = new ArrayList<>();
    }
    public void setArticles(List<Article> articles){
        mArticles.clear();
        mArticles.addAll(articles);
        notifyDataSetChanged();
    }
    public void addArticles(List<Article> articles){
        mArticles.addAll(articles);
        notifyItemRangeChanged(getItemCount(),articles.size());
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article,parent,false);
                return new ImageViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_no_image,parent,false);
                return new NoImageViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Article article = mArticles.get(position);
        if(holder instanceof NoImageViewHolder){
            fillNoImage(article,(NoImageViewHolder) holder);
        }else fillImage(article,(ImageViewHolder) holder);

        if(position == mArticles.size()-1&&mlistener!=null){
            mlistener.loadMore();

        }
    }

    private void fillNoImage(Article article,NoImageViewHolder holder) {
        holder.txtSnippet.setText(article.getSnippet());
    }

    private void fillImage(Article article,ImageViewHolder holder) {
        holder.txtSnippet.setText(article.getSnippet());
        Picasso.with(holder.itemView.getContext())
                .load(article.getMutimedia().get(0).getUrl())
                .into(holder.ivImage);


    }

    @Override
    public int getItemViewType(int position) {
        Article article = mArticles.get(position);
        if(article.getMutimedia()!=null&&!article.getMutimedia().isEmpty()){
            return IMAGE;
        }
        return NOIMAGE;
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.txtSnippet)
        TextView txtSnippet;
        public ImageViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
    public class NoImageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtSnippet)
        TextView txtSnippet;

        public NoImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
