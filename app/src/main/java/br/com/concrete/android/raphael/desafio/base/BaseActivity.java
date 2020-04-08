package br.com.concrete.android.raphael.desafio.base;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;

import br.com.concrete.android.raphael.desafio.R;
import butterknife.BindView;


public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.indeterminateBar)
    ProgressBar progressBar;

    public void showProgressDialog() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    public void dismissProgressDialog() {
        progressBar.setVisibility(ProgressBar.GONE);
    }

    public void showWarningDialog(String repo) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.information)
                .setMessage(String.format("%s %s %s", getString(R.string.reposotory), repo, getString(R.string.does_not_contain_pr)))
                .setNegativeButton(R.string.back, (dialogInterface, i) -> onBackPressed())
                .setOnDismissListener(dialogInterface -> onBackPressed())
                .show();
    }

}
