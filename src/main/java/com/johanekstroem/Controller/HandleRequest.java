package com.johanekstroem.Controller;

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
import com.johanekstroem.Model.PeppolDirectoryPOJO.PeppolDirectoryPOJO;
import com.johanekstroem.Model.ResponseDTO.OrganizationDTO;
import com.johanekstroem.Service.DTOService;
import com.johanekstroem.Service.ExternalSources;

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

    public static void wideSearchQuery(Context ctx) {
        List<OrganizationDTO> listOfOrganizationDTOs = new ArrayList<>();
        var futurePeppolDirectory = peppolDirectoryLookup(ctx.queryString());

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futurePeppolDirectory)
                .thenAcceptAsync((Void) -> {
                    ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                            false);
                    try {
                        PeppolDirectoryPOJO resultFromPeppolDirectory = om.readValue(futurePeppolDirectory.get().body(),
                                PeppolDirectoryPOJO.class);
                        DTOService.fetchFromPeppolDirectory(listOfOrganizationDTOs, resultFromPeppolDirectory,
                                ExternalSources.PeppolDirectory);

                        ctx.json(listOfOrganizationDTOs);
                    } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
                        ctx.result("Something went wrong!");
                    }
                });
        ctx.future(() -> combinedFuture);
    }

    public static void narrowSearchQuery(Context ctx) {
        List<OrganizationDTO> listOfOrganizationDTOs = new ArrayList<>();
        var futurePeppolDirectory = peppolDirectoryLookup(ctx.queryString());
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futurePeppolDirectory)
                .thenAcceptAsync((Void) -> {
                    ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                            false);
                    try {
                        PeppolDirectoryPOJO resultFromPeppolDirectory = om.readValue(futurePeppolDirectory.get().body(),
                                PeppolDirectoryPOJO.class);
                        DTOService.fullFetchFromPeppolDirectory(listOfOrganizationDTOs, resultFromPeppolDirectory,
                                ExternalSources.PeppolDirectory);

                        ctx.json(listOfOrganizationDTOs);
                    } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
                        ctx.result("Something went wrong");
                    }
                });
        ctx.future(() -> combinedFuture);
    }

}
