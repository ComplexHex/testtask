package com.game.controller;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import com.game.service.PlayersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RequestMapping(path = "/rest/players")
@RestController
public class PlayersController {
    private PlayersService playersService;
    private PlayerRepository playerRepository;
    private final Logger logger = LoggerFactory.getLogger(Player.class);

    @Autowired
    public void setPlayersService(PlayersService playersService) {
        this.playersService = playersService;
    }

    @GetMapping("")
    public ResponseEntity<List<Player>> getAllPlayers(
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "id",required = false) String sortBy,
            HttpServletRequest request) {

        String order;

//      "&pageNumber=0&pageSize=3&order=EXPERIENCE";
        String uri = request.getQueryString();
        System.out.println(uri);

        Pattern pat = Pattern.compile("([^&=]+)=([^&]*)");
        Matcher matcher = pat.matcher(uri);
        Map<String, String> map = new HashMap<>();
        while (matcher.find()) {
            map.put(matcher.group(1), matcher.group(2));
        }
        order = map.get("order");
        System.out.println(map.get("order"));

        if (sortBy == null || order.equals("ID")) {
            sortBy = "id";
        }
        if (order.equals("NAME")) {
            sortBy = "name";
        }
        if (order.equals("EXPERIENCE")) {
            sortBy = "experience";
        }
        if (order.equals("BIRTHDAY")) {
            sortBy = "birthday";
        }

        List<Player> list = playersService.getAllPlayers(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<Player>>(list,  HttpStatus.OK);
    }
//add some code

   
    @GetMapping("/count")
    public long getCount() {
        return playersService.count();
    }

    
     @GetMapping("")
    public ResponseEntity<Page<Player>> getPlayers(PlayerPage playerPage,
                                                   PlayerSearchCriteria playerSearchCriteria) {
        return new ResponseEntity<>(playersService.getPlayers(playerPage, playerSearchCriteria), HttpStatus.OK);
    }

//    @PostMapping("/")
//    public void add(@RequestBody Player player) {
//        playersService.savePlayer(player);
//    }
//
//
//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") Long id, Model model){
//        Player player = playersService.getByID(id);
//        model.addAttribute("player", player);
//        return "/{id}";
//    }

}
