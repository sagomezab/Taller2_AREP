package org.arep.webapps;

import org.arep.server.HttpServer;

import java.io.IOException;

public class WebApp {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.getInstance();
        server.addService("/form", new FormHTMLService());
        server.addService("/style", new FormCSSService());
        server.addService("/script", new FormJSService());
        server.addService("/image", new FormImgService());
        server.addService("/404", new Error404Service());
        server.run(args);
    }
}
