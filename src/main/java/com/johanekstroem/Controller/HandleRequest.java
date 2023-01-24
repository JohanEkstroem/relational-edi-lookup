package com.johanekstroem.Controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import io.javalin.http.Context;

public class HandleRequest {
    public static CompletableFuture<HttpResponse<String>> peppolDirectoryLookup(String queryString) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://directory.peppol.eu/search/1.0/json?q=" + queryString + "+beautif=true"))
                .GET()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public static void wideSearchQuery(Context ctx) {
        ctx.result("Hello World!");
    }

}
