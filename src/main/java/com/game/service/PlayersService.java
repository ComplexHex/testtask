package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlayersService {
    private PlayerRepository playerRepository;

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getAllPlayers(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Player> pagedResult = playerRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Player>();
        }
    }

    public List<Player> getAllPlayers(Integer pageNo, Integer pageSize, Sort.Direction direction, String... properties)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(direction, properties));
        Page<Player> pagedResult = playerRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Player>();
        }
    }


    public Page<Player> getAllPlayers(Pageable pageable){
        return playerRepository.findAll(pageable);
    }


    public Player getByID(Long id) {
        return playerRepository.getOne(id);
    }

    public void savePlayer(Player player) {
        playerRepository.save(player);
    }

    public long count(){
        return  playerRepository.count();
    }
}
