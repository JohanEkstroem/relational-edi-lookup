package com.johanekstroem.model.ResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class KeyValuePairSources {
    private String key;
    private String value;
    private List<String> listOfSources = new ArrayList<>();

    public List<String> getListOfSources() {
        return listOfSources;
    }

    public void setListOfSources(List<String> listOfSources) {
        this.listOfSources = listOfSources;
    }

    public KeyValuePairSources() {
    }

    public KeyValuePairSources(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KeyValuePairSources(String key, String value, String source) {
        this.key = key;
        this.value = value;

        this.listOfSources.add(source);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addListOfSources(String source) {
        this.listOfSources.add(source);
    }
}
