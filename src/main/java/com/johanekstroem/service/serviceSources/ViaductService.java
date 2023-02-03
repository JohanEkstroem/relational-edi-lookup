package com.johanekstroem.service.serviceSources;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.johanekstroem.model.PeppolDirectoryPOJO.PeppolDirectoryPOJO;
import com.johanekstroem.model.ResponseDTO.OrganizationDTO;
import com.johanekstroem.service.ExternalSources;

public class ViaductService {

    public static CompletableFuture<HttpResponse<String>> mockViaductRequest(String queryString) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://directory.peppol.eu/search/1.0/json?q=" + queryString + "&beautify=true"))
                .GET()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public static void fullFetchFromViaduct(List<OrganizationDTO> listOfOrganizationDTOs,
            PeppolDirectoryPOJO resultFromMockViaduct, ExternalSources viaduct) {
    }

    public static void fetchFromViaduct(List<OrganizationDTO> listOfOrganizationDTOs,
            PeppolDirectoryPOJO resultFromMockViaduct, ExternalSources viaduct) {
    }
}
