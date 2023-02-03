package com.johanekstroem.service.serviceSources;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.johanekstroem.model.PeppolDirectoryPOJO.DocType;
import com.johanekstroem.model.PeppolDirectoryPOJO.Entity;
import com.johanekstroem.model.PeppolDirectoryPOJO.Identifier;
import com.johanekstroem.model.PeppolDirectoryPOJO.Match;
import com.johanekstroem.model.PeppolDirectoryPOJO.Name;
import com.johanekstroem.model.PeppolDirectoryPOJO.PeppolDirectoryPOJO;
import com.johanekstroem.model.ResponseDTO.DocTypeDTO;
import com.johanekstroem.model.ResponseDTO.Format;
import com.johanekstroem.model.ResponseDTO.KeyValuePair;
import com.johanekstroem.model.ResponseDTO.KeyValuePairSources;
import com.johanekstroem.model.ResponseDTO.OrganizationDTO;
import com.johanekstroem.model.ResponseDTO.Source;
import com.johanekstroem.service.AvailableDocTypes;
import com.johanekstroem.service.DTOService;
import com.johanekstroem.service.ExternalSources;
import com.johanekstroem.service.Helpers;
import com.johanekstroem.service.IdentifiersENUM;

public class PeppolDirectoryService {
    public static CompletableFuture<HttpResponse<String>> peppolDirectoryLookup(String queryString) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://directory.peppol.eu/search/1.0/json?q=" + queryString + "&beautify=true"))
                .GET()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    // wide fetch of organizations from PeppolDirectory
    public static void fetchFromPeppolDirectory(List<OrganizationDTO> listOfOrganizationDTOs,
            PeppolDirectoryPOJO dataFromPeppolDirectory, ExternalSources mockSource) {

        for (Match match : dataFromPeppolDirectory.getMatches()) {
            OrganizationDTO organizationProspect = new OrganizationDTO(new ArrayList<KeyValuePairSources>(),
                    new ArrayList<Source>());
            fillIdentifiersFromMatch(organizationProspect, match, mockSource);
            DTOService.findOrganizationAndUpdateListOfOrganizations(listOfOrganizationDTOs,
                    organizationProspect);
        }
    }

    
    private static void fillIdentifiersFromMatch(OrganizationDTO organizationProspect, Match match,
            ExternalSources mockSource) {
        if (match.getParticipantID() != null) {
            // Lägg till scheme och value i identifierslistan.
            DTOService.addIdentifier(organizationProspect, new KeyValuePairSources(match.getParticipantID().getScheme(),
                    match.getParticipantID().getValue(), mockSource.toString()));
        }
        for (Entity entity : match.getEntities()) {
            // From name collection
            for (Name name : entity.getName()) {

                DTOService.addIdentifier(organizationProspect,
                        new KeyValuePairSources(IdentifiersENUM.NAME.toString(), name.getName(), mockSource.toString()));
            }

            // From Identifiers collection
            if (entity.getIdentifiers() != null) {
                for (Identifier identifier : entity.getIdentifiers()) {
                    if (DTOService.getIdentifier(identifier.getScheme()) != null) {
                        DTOService.addIdentifier(organizationProspect,
                                new KeyValuePairSources(DTOService.getIdentifier(identifier.getScheme()),
                                        identifier.getValue(),
                                        // ExternalSources.PeppolDirectory.toString()));
                                        mockSource.toString()));
                    }
                }
            }
        }
    }

    public static void fullFetchFromPeppolDirectory(List<OrganizationDTO> listOfOrganizationDTOs,
            PeppolDirectoryPOJO resultFromPeppolDirectory, ExternalSources mockSource) {
        for (Match match : resultFromPeppolDirectory.getMatches()) {
            OrganizationDTO organizationProspect = new OrganizationDTO(new ArrayList<KeyValuePairSources>(),
                    new ArrayList<Source>());

            fillIdentifiersFromMatch(organizationProspect, match, mockSource);
            fillSourcesFromMatch(organizationProspect, match, mockSource);
            DTOService.findOrganizationAndUpdateListOfOrganizations(listOfOrganizationDTOs,
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
            AvailableDocTypes docTypeScheme = Helpers.getDocTypeFromPeppolScheme(docType.getValue());
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
            ids.add(new KeyValuePair(IdentifiersENUM.PEPPOLID.toString(), match.getParticipantID().getValue()));
        }

        format.setIds(ids);

        return format;
    }

}
