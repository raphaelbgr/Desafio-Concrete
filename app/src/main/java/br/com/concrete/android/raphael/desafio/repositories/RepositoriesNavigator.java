package br.com.concrete.android.raphael.desafio.repositories;


import android.content.Context;
import android.content.Intent;

import br.com.concrete.android.raphael.desafio.pullrequests.PullRequestsActivity;

class RepositoriesNavigator {

    final private static String ARG_LOGIN = "login";
    final private static String ARG_REPO = "repo";

    static void startPullRequestsActivity(Context context, String login, String repo) {
        Intent intent = new Intent(context, PullRequestsActivity.class);
        intent.putExtra(ARG_LOGIN, login);
        intent.putExtra(ARG_REPO, repo);
        context.startActivity(intent);
    }
}
