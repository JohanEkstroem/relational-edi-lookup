package com.johanekstroem.Model.PeppolDirectoryPOJO;

public class Identifier {
    private String scheme;
    private String value;

    public Identifier() {
    }

    public Identifier(String scheme, String value) {
        super();
        this.scheme = scheme;
        this.value = value;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
