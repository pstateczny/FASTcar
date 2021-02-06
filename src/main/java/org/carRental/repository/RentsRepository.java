package org.carRental.repository;


import org.carRental.model.Osoby;
import org.carRental.model.Pojazdy;
import org.carRental.model.Rents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RentsRepository extends JpaRepository<Rents, Long> {

    @Query("select r from Rents r where r.klient=?1")
    List<Rents> findByClient(Osoby osoby);

    @Transactional
    @Modifying
    @Query("delete from Rents r where r.pojazd=?1")
    void deleteRentBasedOnCar(Pojazdy pojazd);
}
