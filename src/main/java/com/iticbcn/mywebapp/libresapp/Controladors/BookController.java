package com.iticbcn.mywebapp.libresapp.Controladors;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.iticbcn.mywebapp.libresapp.Model.Llibre;
import com.iticbcn.mywebapp.libresapp.Serveis.ServeiLlibre;

@Controller
public class BookController {

    @Autowired
    private ServeiLlibre serveiLlibre;

    @GetMapping("/")
    public String iniciar() {
        return "login";
    }

    @PostMapping("/index")
    public String login(@RequestParam("usuari") String usuari,
            @RequestParam("password") String password,
            Model model) {

        if (usuari.equals("toni") && password.equals("h3ll0!!")) {
            return "index";
        } else {
            return "login";
        }
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/consulta")
    public String consulta(Model model) {
        Set<Llibre> llibres = serveiLlibre.findAll(); // Obtenemos todos los libros del servicio
        ArrayList<Llibre> listaLlibres = new ArrayList<>(llibres);
        model.addAttribute("llibres", listaLlibres); // Añadimos la lista de libros al modelo para que esté disponible en la vista
        return "consulta";
    }

    @GetMapping("/cercaid")
    public String inputCerca(Model model) {
        model.addAttribute("llibreErr", true); // Añadimos un atributo para indicar que no hay errores inicialmente
        model.addAttribute("message", ""); 
        model.addAttribute("llibre", new Llibre()); // Creamos un objeto Llibre vacío y lo añadimos al modelo
        return "cercaid";
    }

    @GetMapping("/inserir")
    public String inputInserir() {
        return "inserir";
    }

    @PostMapping("/inserir")
    public String inserir(@RequestParam("titol") String titol,
            @RequestParam("autor") String autor,
            @RequestParam("editorial") String editorial,
            @RequestParam("isbn") String isbn,
            @RequestParam("data_publicacio") String datapublicacio,
            @RequestParam("tematica") String tematica,
            Model model) {

        String message = "";
        boolean llibreErr = false;

        if (!serveiLlibre.validarISBN(isbn)) {
            message = "El ISBN no es válido";
            llibreErr = true;
        } else {
            LocalDate dataPublicacio = LocalDate.parse(datapublicacio, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Llibre llibre = new Llibre(0, titol, autor, editorial, isbn, dataPublicacio, tematica);
            serveiLlibre.save(llibre); // Guardamos el libro en la base de datos
        }

        model.addAttribute("message", message);
        model.addAttribute("llibreErr", llibreErr);
        Set<Llibre> llibres = serveiLlibre.findAll();
        model.addAttribute("llibres", llibres);

        return "consulta";
    }

    @PostMapping("/cercaid")
    public String cercaId(@RequestParam("id_llibre") String idLlibre, Model model) {
        int idLlib = Integer.parseInt(idLlibre);
        Optional<Llibre> llibre = serveiLlibre.findByIdLlibre(idLlib); // Buscamos el libro por ID 
        String message = "";
        boolean llibreErr = false;

        if (llibre.isPresent()) {
            model.addAttribute("llibre", llibre.get()); // Si existe, lo añadimos al modelo para mostrarlo en la vista
        } else {
            message = "No hi ha cap llibre amb aquesta id";
            llibreErr = true;
        }

        model.addAttribute("message", message);
        model.addAttribute("llibreErr", llibreErr);
        return "cercaid";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
