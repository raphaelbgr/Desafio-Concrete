package br.com.concrete.android.raphael.desafio.pullrequests;

import android.content.Intent;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import br.com.concrete.android.raphael.desafio.App;
import br.com.concrete.android.raphael.desafio.R;
import br.com.concrete.android.raphael.desafio.base.BaseActivity;
import br.com.concrete.android.raphael.desafio.common.api.response.PullRequest;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class PullRequestsActivity extends BaseActivity implements PullRequestsContract.View {

    final private static String ARG_LOGIN = "login";
    final private static String ARG_REPO = "repo";

    @BindView(R.id.alert_open_closed_issues)
    TextView alertText;

    @BindView(R.id.pull_request_recycler)
    RecyclerView pullRequestsRecycler;

    @BindView(R.id.view_container)
    CoordinatorLayout viewContainer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    PullRequestsPresenter pullRequestsPresenter;

    @Inject
    PullRequestsAdapter adapterItems;

    private String login;
    private String repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_requests);
        setupDependencyInjection();
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        setupAdapter();
        setupRecycleViewer();
        retrieveIntentData();
        setupToolbar();
    }

    private void setupDependencyInjection() {
        App.getAppComponent().inject(this);
    }

    private void retrieveIntentData() {
        Intent intent = getIntent();
        login = intent.getExtras().getString(ARG_LOGIN);
        repo = intent.getExtras().getString(ARG_REPO);
    }

    private void setupToolbar() {
        toolbar.setTitle(repo);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
            bar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        pullRequestsPresenter.bindView(this);
        pullRequestsPresenter.requestData(login, repo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pullRequestsPresenter.unbindView();
    }

    private void setupAdapter() {
        adapterItems.setPullRequestClickedListener((position, item) ->
                PullRequestsNavigator.openWebBrowser(this, item.getHtml_url()));
    }

    private void setupRecycleViewer() {
        pullRequestsRecycler.setAdapter(adapterItems);
        pullRequestsRecycler.setHasFixedSize(false);
        pullRequestsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showProgressDialog() {
        super.showProgressDialog();
    }

    @Override
    public void onRequestFailure() {
        Snackbar.make(viewContainer, getString(R.string.error_generic), Snackbar.LENGTH_INDEFINITE)
                .setAction(getText(R.string.retry), listener -> {
                    String key = login + repo;
                    if (pullRequestsPresenter.getCachedResponse(key) == null) {
                        pullRequestsPresenter.requestData(login, repo);
                    }
                }).show();
    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
    }

    @Override
    public void showPullRequests(List<PullRequest> response) {
        loadRecyclerItems(response);
        alertText.setText(String.format(getString(R.string.open_prs), String.valueOf(response.size())));
        Snackbar.make(viewContainer, String.format(getString(R.string.number_items), String.valueOf(response.size())),
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyView() {
        showWarningDialog(repo);
    }

    private void loadRecyclerItems(List<PullRequest> pullRequests) {
        adapterItems.setPullRequests(pullRequests);
    }
}
