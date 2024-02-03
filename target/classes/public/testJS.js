document.addEventListener("DOMContentLoaded", function () {
    // Crear un nuevo elemento canvas
    var canvas = document.createElement("canvas");
    document.body.appendChild(canvas);

    // Obtener el contexto 2D del lienzo
    var context = canvas.getContext("2d");

    // Configurar el tamaño del lienzo
    canvas.width = 300;
    canvas.height = 50;

    // Configurar el estilo de fuente y color
    context.font = "20px Arial";
    context.fillStyle = "black";

    // Dibujar el mensaje "HOLA SANTIAGO" en el lienzo con píxeles
    context.fillText("HOLA SANTIAGO", 10, 30);
});