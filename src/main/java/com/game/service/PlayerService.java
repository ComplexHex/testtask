package com.game.service;


import com.game.entity.Player;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    @PersistenceContext
    private EntityManager em;

    public List<Player> findPlayers(Player searchedPlayer) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Player> cq = cb.createQuery(Player.class);
        Root<Player> playerRoot = cq.from(Player.class);
        List<Predicate> predicates = new ArrayList<>();

        if (searchedPlayer.getName() != null) {
            predicates.add(cb.equal(playerRoot.get("name"), searchedPlayer.getName()));
        }
        if (searchedPlayer.getTitle() != null) {
            predicates.add(cb.like(playerRoot.get("title"), "%" + searchedPlayer.getTitle() + "%"));
        }
        // other predicates

        cq.select(playerRoot).where(predicates.toArray(new Predicate[]{}));
        List<Player> players = em.createQuery(cq).getResultList();
        return players;
    }
}