package com.johanekstroem.Service;

import java.util.ArrayList;
import java.util.List;

import com.johanekstroem.Model.PeppolDirectoryPOJO.DocType;
import com.johanekstroem.Model.PeppolDirectoryPOJO.Entity;
import com.johanekstroem.Model.PeppolDirectoryPOJO.Identifier;
import com.johanekstroem.Model.PeppolDirectoryPOJO.Match;
import com.johanekstroem.Model.PeppolDirectoryPOJO.Name;
import com.johanekstroem.Model.PeppolDirectoryPOJO.PeppolDirectoryPOJO;
import com.johanekstroem.Model.ResponseDTO.DocTypeDTO;
import com.johanekstroem.Model.ResponseDTO.Format;
import com.johanekstroem.Model.ResponseDTO.KeyValuePair;
import com.johanekstroem.Model.ResponseDTO.OrganizationDTO;
import com.johanekstroem.Model.ResponseDTO.Source;

public class DTOService {

    // wide fetch of organizations from PeppolDirectory
    public static void fetchFromPeppolDirectory(List<OrganizationDTO> listOfOrganizationDTOs,
            PeppolDirectoryPOJO dataFromPeppolDirectory, ExternalSources mockSource) {

        for (Match match : dataFromPeppolDirectory.getMatches()) {
            OrganizationDTO organizationProspect = new OrganizationDTO(new ArrayList<KeyValuePair>(),
                    new ArrayList<Source>());
            fillIdentifiersFromMatch(organizationProspect, match, mockSource);
            findOrganizationAndUpdateListOfOrganizations(listOfOrganizationDTOs, match, mockSource,
                    organizationProspect);
        }
    }

    private static void fillIdentifiersFromMatch(OrganizationDTO organizationProspect, Match match,
            ExternalSources mockSource) {
        if (match.getParticipantID() != null) {
            // Lägg till scheme och value i identifierslistan.
            addIdentifier(organizationProspect, new KeyValuePair(match.getParticipantID().getScheme(),
                    match.getParticipantID().getValue(), mockSource.toString()));
        }
        for (Entity entity : match.getEntities()) {
            // From name collection
            for (Name name : entity.getName()) {

                addIdentifier(organizationProspect,
                        new KeyValuePair(Identifiers.NAME.toString(), name.getName(), mockSource.toString()));
            }

            // From Identifiers collection
            if (entity.getIdentifiers() != null) {
                for (Identifier identifier : entity.getIdentifiers()) {
                    if (getIdentifier(identifier.getScheme()) != null) {
                        addIdentifier(organizationProspect,
                                new KeyValuePair(getIdentifier(identifier.getScheme()),
                                        identifier.getValue(),
                                        // ExternalSources.PeppolDirectory.toString()));
                                        mockSource.toString()));
                    }
                }
            }
        }
    }

    public static void addIdentifier(OrganizationDTO organization, KeyValuePair newIdentifier) {
        String newIdentifierKey = newIdentifier.getKey().trim().toLowerCase();
        String newIdentifierValue = newIdentifier.getValue().trim().toLowerCase();
        if (newIdentifierKey.equals("iso6523-actorid-upis")) {
            newIdentifier.setKey(Identifiers.PEPPOLID.toString());
        }

        // If there's no match -> add newIdentifier
        if (organization.getCompanyIdentifier().stream().noneMatch(
                x -> x.getKey().trim().toLowerCase().equals(newIdentifierKey)
                        && x.getValue().trim().toLowerCase().equals(newIdentifierValue))) {
            organization.getCompanyIdentifier().add(newIdentifier);
        } else {
            // Ifrån organization.getCompanyIdentifier(),addressera raden som matchar
            // newIdentifier.getKey(), newIdentifier.getValue() och lägg till en ny source i
            // listan.
            for (KeyValuePair element : organization.getCompanyIdentifier()) {
                String elementKey = element.getKey().trim().toLowerCase();
                String elementValue = element.getValue().trim().toLowerCase();

                if (elementKey.equals(newIdentifierKey) && elementValue.equals(newIdentifierValue)) {
                    // Check if list of sources already have externalSources -> Add source to
                    // companyIdentifierList
                    if (!element.getListOfSources().contains(newIdentifier.getListOfSources().get(0))) {
                        element.getListOfSources().add(newIdentifier.getListOfSources().get(0));
                    }
                }
            }
        }

    }

    public static void findOrganizationAndUpdateListOfOrganizations(List<OrganizationDTO> listOfOrganizations,
            Match match, ExternalSources mockSource, OrganizationDTO organizationProspect) {

        var identifiersToCheck = organizationProspect.getCompanyIdentifier();

        // Hämta ut alla organisationer i listan listOfOrganizations som har någon match
        // i "companyIdentifiers" någon av identifierarna i identifiersToCheck (som i
        // sin
        // tur kommer från det nya, hämtade, datat från API:et)
        OrganizationDTO foundOrganization = null;

        try {
            foundOrganization = listOfOrganizations.stream()
                    .filter(x -> x.getCompanyIdentifier().stream().anyMatch(z -> identifiersToCheck.stream()
                            .anyMatch(y -> z.getKey().equals(y.getKey()) && z.getValue().equals(y.getValue()))))
                    .findFirst().get();
        } catch (Exception e) {
            // TODO: handle exception
        }

        if (foundOrganization == null) {
            // det finns ingen organisation i listan som passar med den nyhämtade datat i
            // "matchen"
            listOfOrganizations.add(organizationProspect);
        } else {
            // det finns organisation i listan som passar med den nyhämtade datat i
            // "matchen", då fyller vi på med identifierare som inte redan finns i
            // organisationen från listan
            fillOrganizationWithNewIds(organizationProspect, foundOrganization);
        }

    }

    public static void fillOrganizationWithNewIds(OrganizationDTO organizationDTOFromMatch,
            OrganizationDTO existingOrganizationDTO) {

        for (KeyValuePair key : organizationDTOFromMatch.getCompanyIdentifier()) {

            addIdentifier(existingOrganizationDTO, key);
        }

    }

    public static String getIdentifier(String identifierString) {
        identifierString = identifierString.trim().replace(" ", "");
        for (Identifiers identifier : Identifiers.values()) {
            String identifierValue = identifier.toString();

            if (identifierString.equals(identifierValue)) {
                return identifierValue;
            }
        }
        return null;
    }

    public static void fullFetchFromPeppolDirectory(List<OrganizationDTO> listOfOrganizationDTOs,
            PeppolDirectoryPOJO resultFromPeppolDirectory, ExternalSources mockSource) {
        for (Match match : resultFromPeppolDirectory.getMatches()) {
            OrganizationDTO organizationProspect = new OrganizationDTO(new ArrayList<KeyValuePair>(),
                    new ArrayList<Source>());

            fillIdentifiersFromMatch(organizationProspect, match, mockSource);
            fillSourcesFromMatch(organizationProspect, match, mockSource);
            findOrganizationAndUpdateListOfOrganizations(listOfOrganizationDTOs, match, ExternalSources.PeppolDirectory,
                    organizationProspect);
        }
    }

    private static void fillSourcesFromMatch(OrganizationDTO organizationProspect, Match match,
            ExternalSources mockSource) {
        Source source = new Source();
        source.setSourceName(mockSource.toString());

        findDocType(match.getDocTypes(), source, match);

        organizationProspect.addSource(source);
    }

    public static void findDocType(List<DocType> listOfDocTypeDTO, Source source, Match match) {
        for (DocType docType : listOfDocTypeDTO) {
            DocTypeDTO docTypeDTO = new DocTypeDTO();
            AvailableDocTypes docTypeScheme = Helpers.getDocTypeFromPeppolScheme(docType.getValue().toString());
            String description = Helpers.getDescriptionFormTechnicalDoctype(docTypeScheme);

            docTypeDTO.setTechnical(docTypeScheme.toString());
            docTypeDTO.setDescription(description);

            docTypeDTO.getFormat().add(findFormat(docType, match));
            source.getDirectionIn().add(docTypeDTO);

        }

    }

    public static Format findFormat(DocType docType, Match match) {
        Format format = new Format();
        format.setScheme(docType.getScheme());
        format.setValue(docType.getValue());

        List<KeyValuePair> ids = new ArrayList<>();
        String participantIDKey = match.getParticipantID().getScheme();
        String PEPPOLID = "iso6523-actorid-upis";
        if (participantIDKey.equals(PEPPOLID)) {
            ids.add(new KeyValuePair(Identifiers.PEPPOLID.toString(), match.getParticipantID().getValue()));
        }

        format.setIds(ids);

        return format;
    }
}
