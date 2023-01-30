package com.johanekstroem.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.johanekstroem.model.peppolDirectoryPOJO.PeppolDirectoryPOJO;
import com.johanekstroem.model.responseDTO.OrganizationDTO;
import com.johanekstroem.service.DTOService;
import com.johanekstroem.service.ExternalSources;

import io.javalin.http.Context;

public class HandleRequest {
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

    public static void searchQuery(Context ctx) {
        boolean isFullSearch = ctx.endpointHandlerPath().equals("/specific");

        List<OrganizationDTO> listOfOrganizationDTOs = new ArrayList<>();
        var futurePeppolDirectory = peppolDirectoryLookup(ctx.queryString());
        var futureMockViaduct = mockViaductRequest(ctx.queryString());
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futurePeppolDirectory, futureMockViaduct)
                .thenAcceptAsync((Void) -> {
                    ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                            false);
                    try {
                        PeppolDirectoryPOJO resultFromPeppolDirectory = om.readValue(futurePeppolDirectory.get().body(), PeppolDirectoryPOJO.class);
                        PeppolDirectoryPOJO resultFromMockViaduct = om.readValue(futureMockViaduct.get().body(), PeppolDirectoryPOJO.class);

                        if (isFullSearch) {
                            DTOService.fullFetchFromPeppolDirectory(listOfOrganizationDTOs, resultFromPeppolDirectory, ExternalSources.PeppolDirectory);
                        } else {
                            DTOService.fetchFromPeppolDirectory(listOfOrganizationDTOs, resultFromPeppolDirectory, ExternalSources.PeppolDirectory);
                            DTOService.fetchFromPeppolDirectory(listOfOrganizationDTOs, resultFromMockViaduct, ExternalSources.Viaduct);
                        }

                        ctx.json(listOfOrganizationDTOs);
                    } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
                        ctx.result("Something went wrong");
                    }
                });
        ctx.future(() -> combinedFuture);
    }

}
