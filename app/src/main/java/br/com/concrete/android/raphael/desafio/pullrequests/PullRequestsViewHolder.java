package br.com.concrete.android.raphael.desafio.pullrequests;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.concrete.android.raphael.desafio.R;
import butterknife.BindView;
import butterknife.ButterKnife;


class PullRequestsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.pull_contact_icon)
    ImageView pullRequestIcon;

    @BindView(R.id.pull_title)
    TextView pulltitle;

    @BindView(R.id.pull_user_name)
    TextView pullUserName;

    @BindView(R.id.pull_date)
    TextView pullDate;

    @BindView(R.id.pull_description)
    TextView pullDescription;

    @BindView(R.id.card_view)
    CardView pullItem;

    PullRequestsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
