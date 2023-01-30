package com.johanekstroem.model.PeppolDirectoryPOJO;

import java.util.List;

public class PeppolDirectoryPOJO {
    private String version;
    private Integer totalResultCount;
    private Integer usedResultCount;
    private Integer resultPageIndex;
    private Integer resultPageCount;
    private Integer firstResultIndex;
    private Integer lastResultIndex;
    private String queryTerms;
    private String creationDt;
    private List<Match> matches = null;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(Integer totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    public Integer getUsedResultCount() {
        return usedResultCount;
    }

    public void setUsedResultCount(Integer usedResultCount) {
        this.usedResultCount = usedResultCount;
    }

    public Integer getResultPageIndex() {
        return resultPageIndex;
    }

    public void setResultPageIndex(Integer resultPageIndex) {
        this.resultPageIndex = resultPageIndex;
    }

    public Integer getResultPageCount() {
        return resultPageCount;
    }

    public void setResultPageCount(Integer resultPageCount) {
        this.resultPageCount = resultPageCount;
    }

    public Integer getFirstResultIndex() {
        return firstResultIndex;
    }

    public void setFirstResultIndex(Integer firstResultIndex) {
        this.firstResultIndex = firstResultIndex;
    }

    public Integer getLastResultIndex() {
        return lastResultIndex;
    }

    public void setLastResultIndex(Integer lastResultIndex) {
        this.lastResultIndex = lastResultIndex;
    }

    public String getQueryTerms() {
        return queryTerms;
    }

    public void setQueryTerms(String queryTerms) {
        this.queryTerms = queryTerms;
    }

    public String getCreationDt() {
        return creationDt;
    }

    public void setCreationDt(String creationDt) {
        this.creationDt = creationDt;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
