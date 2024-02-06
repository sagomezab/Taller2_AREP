package edu.escuelaing.arem.ASE.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class FormImgService implements RESTService {
    @Override
    public String getHeader() {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-type: image/png\r\n" +
                "\r\n";
    }

    @Override
    public String getResponse() {
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(Paths.get("src/main/resources/eciImg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Base64.getEncoder().encodeToString(fileContent));
        return Base64.getEncoder().encodeToString(fileContent);
    }
}