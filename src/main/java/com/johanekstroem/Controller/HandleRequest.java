package com.johanekstroem.Controller;

import io.javalin.http.Context;

public class HandleRequest {
    public static void wideSearchQuery(Context ctx) {
        ctx.result("Hello World!");
    }
}
