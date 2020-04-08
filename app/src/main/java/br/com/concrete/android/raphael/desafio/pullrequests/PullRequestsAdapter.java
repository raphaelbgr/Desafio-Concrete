package br.com.concrete.android.raphael.desafio.pullrequests;

import android.app.Application;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.concrete.android.raphael.desafio.R;
import br.com.concrete.android.raphael.desafio.common.api.response.PullRequest;


class PullRequestsAdapter extends RecyclerView.Adapter<PullRequestsViewHolder> {

    private Context context;
    private List<PullRequest> pullRequests;
    private OnPullRequestClicked onPullRequestClickedListener;

    PullRequestsAdapter(Application context) {
        this.context = context;
    }

    void setPullRequests(List<PullRequest> pullRequests) {
        this.pullRequests = pullRequests;
        notifyDataSetChanged();
    }

    @Override
    public PullRequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.item_pull_request, parent, false);
        return new PullRequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PullRequestsViewHolder holder, int position) {
        holder.pulltitle.setText(pullRequests.get(position).getTitle());
        holder.pullUserName.setText(pullRequests.get(position).getUser().getLogin());
        holder.pullDate.setText(pullRequests.get(position).getCreatedAtString());
        holder.pullDescription.setText(pullRequests.get(position).getBody());

        Picasso.with(context)
                .load(pullRequests.get(position).getUser().getAvatar_url())
                .placeholder(R.drawable.ic_profile_icon)
                .error(R.drawable.ic_profile_icon)
                .into(holder.pullRequestIcon);

        holder.pullItem.setOnClickListener(v
                -> onPullRequestClickedListener.onPullRequestClicked(position, pullRequests.get(position)));
    }

    @Override
    public int getItemCount() {
        return pullRequests != null ? pullRequests.size() : 0;
    }

    void setPullRequestClickedListener(OnPullRequestClicked onRepositoryClickedListener) {
        this.onPullRequestClickedListener = onRepositoryClickedListener;
    }

    interface OnPullRequestClicked {
        void onPullRequestClicked(int position, PullRequest pullRequest);
    }

}
