package com.chrajeshkumar.nearby.Pojo;

import java.util.List;

/**
 * Created by ChRajeshKumar on 26-Jan-17.
 */

public class Root {

    private List<Object> htmlAttributions = null;

    private List<Result> results = null;

    private String status;

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
