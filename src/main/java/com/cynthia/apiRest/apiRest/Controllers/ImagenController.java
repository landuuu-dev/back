package com.cynthia.apiRest.apiRest.Controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImagenController {

    // Ruta para servir las imágenes desde el directorio 'static/images'
    @GetMapping("/images/{imagenNombre}")
    public Resource getImagen(@PathVariable String imagenNombre) {
        // Ruta absoluta del directorio donde se almacenan las imágenes
        String rutaImagen = "src/main/resources/static/images/" + imagenNombre;
        return new FileSystemResource(rutaImagen);
    }
}
