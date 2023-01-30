package com.johanekstroem.model.responseDTO;

import java.util.ArrayList;
import java.util.List;

public class Source {
    private String sourceName;
    private List<DocTypeDTO> directionIn = new ArrayList<>();
    private List<DocTypeDTO> directionOut = new ArrayList<>();

    public Source(String sourceName, List<DocTypeDTO> directionIn, List<DocTypeDTO> directionOut) {
        this.sourceName = sourceName;
        this.directionIn = directionIn;
        this.directionOut = directionOut;
    }

    public Source() {
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public List<DocTypeDTO> getDirectionIn() {
        return directionIn;
    }

    public void setDirectionIn(List<DocTypeDTO> directionIn) {
        this.directionIn = directionIn;
    }

    public List<DocTypeDTO> getDirectionOut() {
        return directionOut;
    }

    public void setDirectionOut(List<DocTypeDTO> directionOut) {
        this.directionOut = directionOut;
    }
}
