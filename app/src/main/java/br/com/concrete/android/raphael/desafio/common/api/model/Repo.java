package br.com.concrete.android.raphael.desafio.common.api.model;


@SuppressWarnings("CanBeFinal")
public class Repo {
    private int open_issues;
    private int open_issues_count;
    private Owner owner;

    public Owner getOwner() {
        return owner;
    }

    public int getOpen_issues() {
        return open_issues;
    }

    public int getOpen_issues_count() {
        return open_issues_count;
    }
}