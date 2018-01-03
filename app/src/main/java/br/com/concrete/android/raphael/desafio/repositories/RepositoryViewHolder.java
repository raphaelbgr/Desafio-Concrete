package br.com.concrete.android.raphael.desafio.repositories;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.concrete.android.raphael.desafio.R;
import butterknife.BindView;
import butterknife.ButterKnife;


class RepositoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.repo_name)
    TextView repoName;

    @BindView(R.id.repo_desc)
    TextView repoDesc;

    @BindView(R.id.repo_fork_num)
    TextView repoFormNum;

    @BindView(R.id.repo_star_num)
    TextView repoStarNum;

    @BindView(R.id.repo_user_name)
    TextView repoUserName;

    @BindView(R.id.repo_full_name)
    TextView repoFullName;

    @BindView(R.id.repo_profile_image)
    ImageView repoProfileImage;

    @BindView(R.id.repository_item)
    ConstraintLayout repoItem;

    RepositoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
