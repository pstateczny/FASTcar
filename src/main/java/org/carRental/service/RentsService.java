package org.carRental.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.carRental.model.Pojazdy;
import org.carRental.model.Rents;
import org.carRental.repository.RentsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentsService {


    private final RentsRepository rentsRepository;

    public void deleteRentBasedOnCar(Pojazdy pojazd) {
        rentsRepository.deleteRentBasedOnCar(pojazd);
    }

    public List<Rents> getAllRents() {
        return rentsRepository.findAll();
    }

    public void saveRent(Rents rents) {
        rentsRepository.save(rents);
    }
}

