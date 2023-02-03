
package com.johanekstroem;

import com.johanekstroem.controller.*;

import io.javalin.Javalin;

public class Main {
  public static void main(String[] args) {
    Javalin app = Javalin.create().start(7000);
    app.get("/general",  HandleRequestController::searchQuery);
    app.get("/specific", HandleRequestController::searchQuery);
  }
}
