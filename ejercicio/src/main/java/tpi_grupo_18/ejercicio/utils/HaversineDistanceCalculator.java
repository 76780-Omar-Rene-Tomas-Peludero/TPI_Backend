package tpi_grupo_18.ejercicio.utils;

public class HaversineDistanceCalculator {
    // Radio de la Tierra en kilómetros (aproximadamente 6371 km).
    private static final double EARTH_RADIUS = 6371.0;

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Convierte las coordenadas de grados a radianes.
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Diferencias de latitud y longitud.
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Fórmula haversine.
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distancia en kilómetros.
        return EARTH_RADIUS * c;
    }
}
