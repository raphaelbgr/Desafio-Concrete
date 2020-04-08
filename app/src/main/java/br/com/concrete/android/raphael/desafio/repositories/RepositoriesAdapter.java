package br.com.concrete.android.raphael.desafio.repositories;

import android.app.Application;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import br.com.concrete.android.raphael.desafio.R;
import br.com.concrete.android.raphael.desafio.common.api.model.Items;


class RepositoriesAdapter extends RecyclerView.Adapter<RepositoryViewHolder> {

    private Context context;
    private List<Items> items;
    private OnRepoItemClicked onRepositoryClickedListener;

    @Inject
    RepositoriesAdapter(Application context) {
        this.context = context;
    }

    public void setItems(List<Items> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.item_repository, parent, false);
        return new RepositoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        holder.repoName.setText(items.get(position).getName());
        holder.repoDesc.setText(items.get(position).getDescription());
        holder.repoFormNum.setText(items.get(position).getForks_count());
        holder.repoStarNum.setText(items.get(position).getStargazers_count());
        holder.repoUserName.setText(items.get(position).getOwner().getLogin());
        holder.repoFullName.setText(items.get(position).getFull_name());

        Picasso.with(context)
                .load(items.get(position).getOwner().getAvatar_url())
                .placeholder(R.drawable.ic_profile_icon)
                .error(R.drawable.ic_profile_icon)
                .into(holder.repoProfileImage);

        holder.repoItem.setOnClickListener(v
                -> onRepositoryClickedListener.onRepoItemClicked(position, items));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    void setOnRepositoryClickedListener(OnRepoItemClicked onRepositoryClickedListener) {
        this.onRepositoryClickedListener = onRepositoryClickedListener;
    }

    interface OnRepoItemClicked {
        void onRepoItemClicked(int position, List<Items> items);
    }

}
