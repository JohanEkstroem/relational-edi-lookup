package com.johanekstroem.model.responseDTO;

import java.util.ArrayList;
import java.util.List;

public class KeyValuePair {
    private String key;
    private String value;
    private List<String> listOfSources = new ArrayList<>();

    public List<String> getListOfSources() {
        return listOfSources;
    }

    public void setListOfSources(List<String> listOfSources) {
        this.listOfSources = listOfSources;
    }

    public KeyValuePair() {
    }

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KeyValuePair(String key, String value, String source) {
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
}
