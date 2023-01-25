
package com.johanekstroem;

import io.javalin.Javalin;
import com.johanekstroem.Controller.*;

public class Main {
  public static void main(String[] args) {
    Javalin app = Javalin.create().start(7000);
    app.get("/general", ctx -> HandleRequest.wideSearchQuery(ctx));
    app.get("/specific", ctx -> HandleRequest.narrowSearchQuery(ctx));
  }
}
