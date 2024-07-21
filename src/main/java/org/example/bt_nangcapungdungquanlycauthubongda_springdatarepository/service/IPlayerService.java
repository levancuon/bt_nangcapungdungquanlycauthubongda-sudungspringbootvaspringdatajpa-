package org.example.bt_nangcapungdungquanlycauthubongda_springdatarepository.service;


import org.example.bt_nangcapungdungquanlycauthubongda_springdatarepository.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface IPlayerService {
    Page<Player> findAll(int page, int size, String sort, String name, Date dobMin,Date dobMax);

    Optional<Player> findById(Long id);

    void save(Player player);
    void remove(Long id);

    Page<Player> findAllByNameContaining(String search,Pageable pageable);

    Page<Player> findAllAndSort(String sort);
    Page<Player> getPlayer( int page, int size,String name, Date dobMin, Date dobMax);
}
