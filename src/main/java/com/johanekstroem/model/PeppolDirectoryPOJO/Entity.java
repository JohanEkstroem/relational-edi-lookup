package com.johanekstroem.model.PeppolDirectoryPOJO;

import java.util.List;

public class Entity {
    private List<Name> name = null;
    private String countryCode;
    private List<Identifier> identifiers = null;
    private String regDate;

    public List<Name> getName() {
        return name;
    }

    public void setName(List<Name> name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

}
