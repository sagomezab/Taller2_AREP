document.addEventListener("DOMContentLoaded", function () {
    fetch("/api/data")
        .then(response => response.json())
        .then(data => {
            document.getElementById("apiResponse").innerText = data.message;
        })
        .catch(error => {
            console.error("Error fetching data:", error);
        });
});