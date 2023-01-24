package com.johanekstroem.Model.ResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class DocTypeDTO {

    private String technical;
    private String description;
    private List<Format> format = new ArrayList<>();

    public DocTypeDTO() {
    }

    public DocTypeDTO(String technical, String description, List<Format> format) {
        this.technical = technical;
        this.description = description;
        this.format = format;
    }

    public String getTechnical() {
        return technical;
    }

    public void setTechnical(String technical) {
        this.technical = technical;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Format> getFormat() {
        return format;
    }

    public void setFormat(List<Format> format) {
        this.format = format;
    }
}
