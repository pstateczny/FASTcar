package org.carRental.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.carRental.model.Konta;
import org.carRental.model.Osoby;
import org.carRental.repository.KontaRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final KontaRepository kontaRepository;


    public Konta getAccountByCredentials(String poswiadczeniaUzytkownika) {
        return kontaRepository.findByCredentials(poswiadczeniaUzytkownika);
    }

    public Konta getAccountByUser(Osoby osoba) {
        return kontaRepository.findByUser(osoba);
    }

}