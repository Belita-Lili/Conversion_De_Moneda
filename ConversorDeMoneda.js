function asignarTextoElemento(elemento, texto){
    let elementoHTML = document.querySelector(elemento);
    elementoHTML.innerHTML = texto;
    return;
}


// Función para obtener las tasas de cambio desde exchangeratesapi.io
async function obtenerTasasDeCambio() {
    const url = 'https://v6.exchangerate-api.com/v6/59972b7bbdbe303a42e8b721/latest/USD';
    
    try {
        const response = await fetch(url);
        const data = await response.json();
        return data.rates;
    } catch (error) {
        console.error('Error al obtener las tasas de cambio:', error);
        return null;
    }
}

// Función para convertir la moneda
async function convertirMoneda(cantidad, monedaOrigen, monedaDestino) {
    let numeroDeUsuario1 = parseInt(document.getElementById('valorUsuario1').value);
    let numeroDeUsuario2 = parseInt(document.getElementById('valorUsuario2').value);
    
    const tasasDeCambio = await obtenerTasasDeCambio();
    if (!tasasDeCambio) {
        console.error('No se pueden obtener las tasas de cambio.');
        return null;
    }
    
    const tasaOrigen = tasasDeCambio[monedaOrigen];
    const tasaDestino = tasasDeCambio[monedaDestino];
    
    if (!tasaOrigen || !tasaDestino) {
        console.error('Moneda no compatible.');
        return null;
    }
    
    const cantidadConvertida = cantidad * (tasaDestino / tasaOrigen);
    return cantidadConvertida.toFixed(2); // Redondear a 2 decimales
}


