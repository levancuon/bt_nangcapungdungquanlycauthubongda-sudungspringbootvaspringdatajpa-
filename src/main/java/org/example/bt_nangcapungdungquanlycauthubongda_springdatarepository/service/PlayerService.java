package org.example.bt_nangcapungdungquanlycauthubongda_springdatarepository.service;


import org.example.bt_nangcapungdungquanlycauthubongda_springdatarepository.model.Player;
import org.example.bt_nangcapungdungquanlycauthubongda_springdatarepository.repository.IPlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService implements IPlayerService {
    @Autowired
    private IPlayerRepo playerRepo;

//    @Override
//    public Page<Player> findAll(int page, int size, String sort, String name, Date dobMin, Date dobMax) {
//        Sort sorted = createSort(sort);
//        Pageable pageable = PageRequest.of(page, size, sorted);
//        return playerRepo.findAll(pageable,name,dobMin,dobMax);
//    }
//    private Sort createSort(String sortString) {
//        if (sortString != null && !sortString.isEmpty()) {
//            String[] sorts = sortString.split(" ");
//            return Sort.by(Sort.Direction.fromString(sorts[1]), sorts[0]);
//        } else {
//            return Sort.by(Sort.Direction.DESC, "id");
//        }
//    }
    public Page<Player> getPlayer( int page, int size,String name, Date dobMin, Date dobMax) {
        if (size==0){
            size=5;
        }
        Pageable pageable = PageRequest.of(
                page, size, Sort.by("name").ascending().and(Sort.by("dob").descending())
        );
        return playerRepo.findAll(pageable,name, dobMin, dobMax );


    }

    @Override
    public Page<Player> findAll(int page, int size, String sort, String name, Date dobMin, Date dobMax) {
        return null;
    }

    @Override
    public Optional<Player> findById(Long id) {
        return playerRepo.findById(id);
    }

    @Override
    public void save(Player player) {
        playerRepo.save(player);
    }

    @Override
    public void remove(Long id) {
        playerRepo.deleteById(id);
    }

    @Override
    public Page<Player> findAllByNameContaining(String search, Pageable pageable) {
        return playerRepo.findAllByNameContaining(search, pageable);
    }

    @Override
    public Page<Player> findAllAndSort(String sort) {
        Sort sorted = Sort.by(sort.equals("increase") ? Sort.Direction.ASC : Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0,6,sorted);
        return playerRepo.findAll(pageable);
    }


}
