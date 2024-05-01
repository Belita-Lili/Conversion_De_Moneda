function convertir() {
    // Obtener el valor del importe y las monedas seleccionadas
    var importe = document.getElementById("valorUsuario1").value;
    var moneda1 = document.getElementById("Moneda1").value;
    var moneda2 = document.getElementById("Moneda2").value;

    // URL de la API para obtener las tasas de cambio
    var url = "https://api.exchangerate-api.com/v4/latest/" + moneda1;

    // Realizar la solicitud GET a la API
    fetch(url)
    .then(response => response.json())
    .then(data => {
        // Obtener la tasa de cambio de la segunda moneda respecto a la primera
        var tasaCambio = data.rates[moneda2];

        // Calcular el importe convertido
        var importeConvertido = importe * tasaCambio;

        // Mostrar el resultado en la página
        document.getElementById("resultado").innerText = importeConvertido.toFixed(2);
    })
    .catch(error => {
        console.error('Error al obtener las tasas de cambio: ', error);
        document.getElementById("resultado").innerText = "Error";
    });
}

