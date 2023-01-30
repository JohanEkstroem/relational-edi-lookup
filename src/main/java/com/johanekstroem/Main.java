
package com.johanekstroem;

import com.johanekstroem.controller.*;

import io.javalin.Javalin;

public class Main {
  public static void main(String[] args) {
    Javalin app = Javalin.create().start(7000);
    app.get("/general", ctx -> HandleRequestController.searchQuery(ctx));
    app.get("/specific", ctx -> HandleRequestController.searchQuery(ctx));
  }
}
