package edu.escuelaing.arem.ASE.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class HttpServer {
     public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
 
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
 
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            
            boolean firstLine = true;
            String uriStr = "";
 
            while ((inputLine = in.readLine()) != null) {
                if(firstLine){
                    uriStr = inputLine.split(" ")[1];
                    firstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            
            if(uriStr.startsWith("/cliente")){
                outputLine = htttpClientHtml();
            }else if(uriStr.startsWith("/imagen")){
                OutputStream outImage = clientSocket.getOutputStream();
                jpgImages(outImage);
                outputLine = null;
            }else if(uriStr.startsWith("/texto")){
                outputLine = httpRequestText();
            }else if(uriStr.startsWith("/script")){
                outputLine = httpRequestJavaScript();
            }else if(uriStr.startsWith("/css")){
                outputLine = httpRequestCSS();
            }else{
                 outputLine = httpError();
            }
            out.println(outputLine);
 
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
    
     private static String httpError() {
        String outputLine = "HTTP/1.1 400 Not Found\r\n"
                    + "Content-Type:text/html\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>Error Not found</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <h1>Error</h1>\n"
                    + "    </body>\n";
        return outputLine;
                
     }
    
    public static String htttpClientHtml(){
        String outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type:text/html\r\n"
                    + "\r\n";

        Path file = Paths.get("target/classes/public/cliente.html");
        Charset charset = Charset.forName("UTF-8");
        try(BufferedReader reader = Files.newBufferedReader(file, charset)){
            String line = null;
            while((line = reader.readLine()) != null){
                System.out.println(line);
                outputLine += line;
            } 
        } catch (IOException x){
               System.err.format("IOeXCEPTION; %s%n", x); 
        }
        return outputLine;
    
    }

    public static void jpgImages(OutputStream outImage) throws IOException {
        Path imagePath = Paths.get("target/classes/public/logo2.jpg");

        try (InputStream inputStream = Files.newInputStream(imagePath)) {
            byte[] imageBytes = inputStream.readAllBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            String header = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: image/jpeg\r\n" +
                    "Content-Length: " + base64Image.length() + "\r\n" +
                    "\r\n";
            
            outImage.write(header.getBytes()); 
            outImage.write(Base64.getDecoder().decode(base64Image)); 
        }
    }

    public static String httpRequestText() {
        Path file = Paths.get("target/classes/public/testTXT.txt");
        Charset charset = Charset.forName("UTF-8");
    
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            StringBuilder contentBuilder = new StringBuilder();
            String line;
    
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
    
            String textContent = contentBuilder.toString();
    
            String header = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/plain\r\n" +
                    "Content-Length: " + textContent.length() + "\r\n" +
                    "\r\n" + textContent;
    
            return header;
        } catch (IOException e) {
            e.printStackTrace();
            return "HTTP/1.1 404 Not Found\r\n" +
                    "Content-Type: text/plain\r\n" +
                    "\r\n" +
                    "Error: Text file not found.";
        }
    }

    public static String httpRequestJavaScript() {
        Path file = Paths.get("target/classes/public/testJS.js");
        Charset charset = Charset.forName("UTF-8");
    
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            StringBuilder contentBuilder = new StringBuilder();
            String line;
    
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
    
            String jsContent = contentBuilder.toString();
    
            String header = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/javascript\r\n" +
                    "Content-Length: " + jsContent.length() + "\r\n" +
                    "\r\n" + jsContent;
    
            return header;
        } catch (IOException e) {
            e.printStackTrace();
            return "HTTP/1.1 404 Not Found\r\n" +
                    "Content-Type: text/plain\r\n" +
                    "\r\n" +
                    "Error: JavaScript file not found.";
        }
    }

    public static String httpRequestCSS() {
        Path file = Paths.get("target/classes/public/testCSS.css");
        Charset charset = Charset.forName("UTF-8");
    
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            StringBuilder contentBuilder = new StringBuilder();
            String line;
    
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
    
            String cssContent = contentBuilder.toString();
    
            String header = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/css\r\n" +
                    "Content-Length: " + cssContent.length() + "\r\n" +
                    "\r\n" + cssContent;
    
            return header;
        } catch (IOException e) {
            e.printStackTrace();
            return "HTTP/1.1 404 Not Found\r\n" +
                    "Content-Type: text/plain\r\n" +
                    "\r\n" +
                    "Error: CSS file not found.";
        }
    }

}
