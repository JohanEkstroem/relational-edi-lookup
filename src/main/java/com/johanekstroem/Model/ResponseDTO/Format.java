package com.johanekstroem.model.responseDTO;

import java.util.ArrayList;
import java.util.List;

public class Format {
    private String scheme;
    private String value;
    private List<KeyValuePair> ids = new ArrayList<>();

    public Format() {
    }

    public Format(String scheme, String value, List<KeyValuePair> ids) {
        this.scheme = scheme;
        this.value = value;
        this.ids = ids;
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

    public List<KeyValuePair> getIds() {
        return ids;
    }

    public void setIds(List<KeyValuePair> ids) {
        this.ids = ids;
    }
}
