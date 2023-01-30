package com.johanekstroem.model.responseDTO;

import java.util.List;

public class OrganizationDTO {
    private List<KeyValuePairSources> companyIdentifier;
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

    public OrganizationDTO(List<KeyValuePairSources> companyIdentifier, List<Source> source) {
        this.companyIdentifier = companyIdentifier;
        this.source = source;
    }

    public List<KeyValuePairSources> getCompanyIdentifier() {
        return companyIdentifier;
    }

    public void setCompanyIdentifier(List<KeyValuePairSources> companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }
}
