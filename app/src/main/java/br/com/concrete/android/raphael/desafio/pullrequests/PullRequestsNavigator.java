package br.com.concrete.android.raphael.desafio.pullrequests;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

class PullRequestsNavigator {

    static void openWebBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
        context.startActivity(intent);
    }
}
