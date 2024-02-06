package org.arep.webapps;

import org.arep.server.RESTService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FormCSSService implements RESTService {
    @Override
    public String getHeader() {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-type: text/css\r\n" +
                "\r\n";
    }

    @Override
    public String getResponse() {
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(Paths.get("src/main/resources/style.css"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(fileContent);
    }
}
