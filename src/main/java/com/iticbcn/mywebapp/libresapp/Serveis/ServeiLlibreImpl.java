package com.iticbcn.mywebapp.libresapp.Serveis;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iticbcn.mywebapp.libresapp.Model.Llibre;
import com.iticbcn.mywebapp.libresapp.Repositori.RepoLlibre;

@Service
public class ServeiLlibreImpl implements ServeiLlibre {

    @Autowired
    private RepoLlibre repoLlibre;

    @Override
    public Set<Llibre> findAll() {
        return repoLlibre.findAll();
    }

    @Override
    public Optional<Llibre> findByTitol(String titol) {
        return repoLlibre.findByTitol(titol);
    }

    @Override
    public Set<Llibre> findByTitolAndEditorial(String titol, String editorial) {
        return repoLlibre.findByTitolAndEditorial(titol, editorial);
    }

    @Override
    public Optional<Llibre> findByIdLlibre(int idLlibre) {
        return repoLlibre.findById(idLlibre);
    }

    @Override
    public boolean validarISBN(String isbn) {
        return isbn.matches("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");
    }

    @Override
    public void save(Llibre llibre) {
        repoLlibre.save(llibre);
    }
}
