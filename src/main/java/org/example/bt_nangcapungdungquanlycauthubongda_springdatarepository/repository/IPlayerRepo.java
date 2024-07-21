package org.example.bt_nangcapungdungquanlycauthubongda_springdatarepository.repository;


import lombok.Data;
import org.example.bt_nangcapungdungquanlycauthubongda_springdatarepository.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface IPlayerRepo extends CrudRepository<Player,Long>, PagingAndSortingRepository<Player,Long> {

    Page<Player> findAllByNameContaining(String search, Pageable pageable);
    @Query("SELECT p FROM Player p WHERE " +
            "(:name IS NULL OR p.name like %:name%) AND " +
            "(:dobMin IS NULL OR p.dob >= :dobMin) AND " +
            "(:dobMax IS NULL OR p.dob <= :dobMax) ")
    Page<Player> findAll(
            Pageable pageable,
            @Param("name") String name,
            @Param("dobMin") Date dobMin,
            @Param("dobMax") Date dobMax

    );

    Page<Player> findByNameContainingAndDobBetween(Pageable pageable, String name, Date dobMin, Date dobMax);
}
