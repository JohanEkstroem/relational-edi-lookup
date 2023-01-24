package com.johanekstroem.Model.PeppolDirectoryPOJO;

public class Match {
    private ParticipantID participantID;
    private List<DocType> docTypes = null;
    private List<Entity> entities = null;

    public ParticipantID getParticipantID() {
        return participantID;
    }

    public void setParticipantID(ParticipantID participantID) {
        this.participantID = participantID;
    }

    public List<DocType> getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(List<DocType> docTypes) {
        this.docTypes = docTypes;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}
