package com.game.controller;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import com.game.service.PlayersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(path = "/rest/players", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class PlayersController {
    private PlayersService playersService;
    private PlayerRepository playerRepository;
    private final Logger logger = LoggerFactory.getLogger(Player.class);

    @Autowired
    public void setPlayersService(PlayersService playersService) {
        this.playersService = playersService;
    }

//    @Autowired
//    public PlayersController(PlayerRepository playerRepository) {
//        this.playerRepository = playerRepository;
//    }

//    @GetMapping("")
//    public Iterable<Player> allPlayers(){
//        PageRequest pageRequest = PageRequest.of(0,3, Sort.by("name").ascending());
//        return playerRepository.findAll(pageRequest).getContent();
//    }




//    @GetMapping("")
//    public List<Player> list() {
//        return playersService.getAllPlayers();
//    }

    // Bad request to GET /rest/players?&pageNumber=0&pageSize=3&order=NAME
    @GetMapping("")
    public ResponseEntity<List<Player>> getAllPlayers(
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy) {
        List<Player> list = playersService.getAllPlayers(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<Player>>(list, HttpStatus.OK);
    }

     @GetMapping("")
    @JsonView()
    public ResponseEntity<Page<Player>> getAllPlayers(
            @PageableDefault(page = 0, size = 5, sort = "id",direction = Sort.Direction.ASC)Pageable pageable){
        Page<Player> players = this.playerRepository.findAll(pageable);
        return ResponseEntity.ok(players);


//    @GetMapping("")
//    public ResponseEntity<List<Player>> getAllPlayers(
//            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
//            @RequestParam(defaultValue = "3") Integer pageSize,
//            @RequestParam (defaultValue = "ASC")Sort.Direction direction,
//            @RequestParam (value = "id") String[] playerOrder) {
//        playerOrder = playersService.playerOrder();
//        List<Player> list = playersService.getAllPlayers(pageNo, pageSize, direction, playerOrder);
//        return new ResponseEntity<List<Player>>(list,  HttpStatus.OK);
//    }


//    @GetMapping("")
//    public ResponseEntity<Page<Player>> getAllPlayers(
//            @PageableDefault(sort = {"id"}, value = 10)
//            @SortDefault.SortDefaults({
//                    @SortDefault(sort = "id", direction = Sort.Direction.DESC)
//            }) Pageable pageable) {
//        Page<Player> players = playersService.getAllPlayers(pageable);
//        return new ResponseEntity<Page<Player>>(players, HttpStatus.OK);
//    }


//    @GetMapping("")
//    public ResponseEntity<List<Player>> getAllPlayers(@RequestParam(required = false) String name) {
//        try {
//            List<Player> players = new ArrayList<Player>();
//            if (name == null)
//                players.addAll(playerRepository.findAll());
//            else
//                players.addAll(playerRepository.findByNameContaining(name));
//            if (players.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(players, new HttpHeaders(), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @GetMapping("/count")
    public long getCount() {
        return playersService.count();
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
