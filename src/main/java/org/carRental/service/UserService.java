package org.carRental.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.carRental.model.Konta;
import org.carRental.model.Osoby;
import org.carRental.repository.KontaRepository;
import org.carRental.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KontaRepository kontaRepository;
    private final UserRepository userRepository;



    public Konta getAccountByCredentials(String encodedUserDetails) {
        return kontaRepository.findByCredentials(encodedUserDetails);
    }

    public void saveUser(Osoby osoby) {
        userRepository.save(osoby);
    }

    public Osoby getUserByNumerDowodu(String numerDowodu) {
        return userRepository.findByNumerDowodu(numerDowodu);
    }

    public void saveAccount(Konta client) {
        kontaRepository.save(client);
    }

    public Osoby getOneUser(Long userId) {
        return userRepository.getOne(userId);
    }
}

