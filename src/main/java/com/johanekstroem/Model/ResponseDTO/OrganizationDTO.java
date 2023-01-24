package com.johanekstroem.Model.ResponseDTO;

import java.util.List;

public class OrganizationDTO {
    private List<KeyValuePair> companyIdentifier;
    private List<Source> source;

    public OrganizationDTO() {
    }

    public List<Source> getSource() {
        return source;
    }

    public void setSource(List<Source> source) {
        this.source = source;
    }

    public void addSource(Source source) {
        this.source.add(source);
    }

    public OrganizationDTO(List<KeyValuePair> companyIdentifier, List<Source> source) {
        this.companyIdentifier = companyIdentifier;
        this.source = source;
    }

    public List<KeyValuePair> getCompanyIdentifier() {
        return companyIdentifier;
    }

    public void setCompanyIdentifier(List<KeyValuePair> companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }
}
