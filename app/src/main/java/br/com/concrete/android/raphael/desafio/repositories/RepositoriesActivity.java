package br.com.concrete.android.raphael.desafio.repositories;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import javax.inject.Inject;

import br.com.concrete.android.raphael.desafio.App;
import br.com.concrete.android.raphael.desafio.R;
import br.com.concrete.android.raphael.desafio.base.BaseActivity;
import br.com.concrete.android.raphael.desafio.common.api.model.Items;
import br.com.concrete.android.raphael.desafio.common.api.response.Repositories;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class RepositoriesActivity extends BaseActivity implements RepositoriesContract.View {

    final static String ARG_RESPONSE = "last_response";

    @BindView(R.id.view_container)
    CoordinatorLayout viewContainer;

    @BindView(R.id.repo_recycler)
    RecyclerView repositoriesRecycler;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    RepositoriesAdapter adapterItems;

    @Inject
    RepositoriesPresenter repositoriesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        setupDependencyInjection();
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setupAdapter();
        setupRecycleViewer();
        restoreSavedInstanceState(savedInstanceState);
        setupToolbar();
    }

    private void setupDependencyInjection() {
        App.getAppComponent().inject(this);
    }

    private void setupToolbar() {
        toolbar.setTitle(R.string.java_pop);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
    }

    private void restoreSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_RESPONSE)) {
            showPullRequests(savedInstanceState.getParcelable(ARG_RESPONSE));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        repositoriesPresenter.bindView(this);
        repositoriesPresenter.requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repositoriesPresenter.unbindView();
    }

    private void setupAdapter() {
        adapterItems.setOnRepositoryClickedListener((position, items) -> {
            String login = items.get(position).getOwner().getLogin();
            String repo = items.get(position).getName();
            RepositoriesNavigator.startPullRequestsActivity(this, login, repo);
        });
    }

    private void setupRecycleViewer() {
        repositoriesRecycler.setAdapter(adapterItems);
        repositoriesRecycler.setHasFixedSize(false);
        repositoriesRecycler.setLayoutManager(new LinearLayoutManager(this));
        repositoriesRecycler.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        repositoriesRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean reachEnd = !recyclerView.canScrollVertically(RecyclerView.VERTICAL);
                if (reachEnd && repositoriesPresenter.hasNextPage()) {
                    repositoriesPresenter.bindView(RepositoriesActivity.this);
                    repositoriesPresenter.loadMore();
                }
            }
        });
    }

    @Override
    public void showProgress() {
        super.showProgressDialog();
    }

    @Override
    public void onRequestFailure() {
        new Handler().post(() -> Snackbar.make(viewContainer, getString(R.string.error_generic), Snackbar.LENGTH_INDEFINITE)
                .setAction(getText(R.string.retry), listener -> {
                    if (repositoriesPresenter.getCachedResponse() == null) {
                        repositoriesPresenter.requestData();
                    } else {
                        repositoriesPresenter.loadMore();
                    }
                }).show());
    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
    }

    @Override
    public void showPullRequests(Repositories response) {
        loadRecyclerItems(response);
        new Handler().post(() -> Snackbar.make(viewContainer,
                String.format(getString(R.string.number_items),
                        String.valueOf(response.getItems().size())), Snackbar.LENGTH_SHORT).show());
    }

    private void loadRecyclerItems(Repositories response) {
        List<Items> items = response.getItems();
        adapterItems.setItems(items);
    }
}
