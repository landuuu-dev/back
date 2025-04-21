package com.cynthia.apiRest.apiRest.Controllers;

import com.cynthia.apiRest.apiRest.Entities.Categoria;
import com.cynthia.apiRest.apiRest.Entities.Producto;
import com.cynthia.apiRest.apiRest.Repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;


    @GetMapping
    public List<Producto> getAllProductos(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Categoria categoria) {

        if (id != null) {
            return productoRepository.findById(id)
                    .map(List::of)
                    .orElseThrow(() -> new RuntimeException("No se encontró el producto con el ID: " + id));
        }

        if (categoria != null) {
            return productoRepository.findByCategoria(categoria);
        }

        return productoRepository.findAll();
    }


    @GetMapping("/id")
    public Producto obtenerProductoPorId(@RequestParam Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createProducto(
            @RequestParam String nombre,
            @RequestParam Double precio,
            @RequestParam String descripcion,
            @RequestParam String categoria,
            @RequestParam(required = false) MultipartFile imagenPrincipal,
            @RequestParam(required = false) MultipartFile imagenExtra1,
            @RequestParam(required = false) MultipartFile imagenExtra2) {

        if (productoRepository.existsByNombre(nombre)) {
            return ResponseEntity.badRequest().body("El nombre del producto ya está en uso");
        }

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        producto.setCategoria(Categoria.valueOf(categoria));

        // Ruta donde se guardarán las imágenes
        String rutaBase = new File("src/main/resources/static/images").getAbsolutePath();

        try {
            if (imagenPrincipal != null && !imagenPrincipal.isEmpty()) {
                String nombreArchivo = imagenPrincipal.getOriginalFilename();
                File archivoDestino = new File(rutaBase, nombreArchivo);
                imagenPrincipal.transferTo(archivoDestino);
                producto.setImagenPrincipal(nombreArchivo);
            }

            if (imagenExtra1 != null && !imagenExtra1.isEmpty()) {
                String nombreArchivo = imagenExtra1.getOriginalFilename();
                File archivoDestino = new File(rutaBase, nombreArchivo);
                imagenExtra1.transferTo(archivoDestino);
                producto.setImagenExtra1(nombreArchivo);
            }

            if (imagenExtra2 != null && !imagenExtra2.isEmpty()) {
                String nombreArchivo = imagenExtra2.getOriginalFilename();
                File archivoDestino = new File(rutaBase, nombreArchivo);
                imagenExtra2.transferTo(archivoDestino);
                producto.setImagenExtra2(nombreArchivo);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al guardar imágenes");
        }

        productoRepository.save(producto);
        return ResponseEntity.ok(producto);
    }






    @PutMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> actualizarProducto(
            @RequestParam Long id,
            @RequestParam String nombre,
            @RequestParam double precio,
            @RequestParam String descripcion,
            @RequestParam Categoria categoria,
            @RequestParam(value = "imagenPrincipal", required = false) MultipartFile imagenPrincipal,
            @RequestParam(value = "imagenExtra1", required = false) MultipartFile imagenExtra1,
            @RequestParam(value = "imagenExtra2", required = false) MultipartFile imagenExtra2
    ) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No se encontró el producto con el ID: " + id
                ));

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        producto.setCategoria(categoria);

        if (imagenPrincipal != null) {
            producto.setImagenPrincipal(imagenPrincipal.getOriginalFilename());
        }
        if (imagenExtra1 != null) {
            producto.setImagenExtra1(imagenExtra1.getOriginalFilename());
        }
        if (imagenExtra2 != null) {
            producto.setImagenExtra2(imagenExtra2.getOriginalFilename());
        }

        return ResponseEntity.ok(productoRepository.save(producto));
    }



    @DeleteMapping
    public String eliminarProductoPorId(@RequestParam Long id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID: " + id ));
     productoRepository.delete(producto);
     return "El producto con el ID: " + id + " se elimino correctamente";
    }

}
