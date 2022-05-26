package com.game.controller;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import com.game.service.PlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RequestMapping(path = "/rest/players")
@RestController
public class PlayersController {

    private PlayersService playersService;

    @Autowired
    private PlayerRepository playerRepository;


    public PlayersController(PlayersService playersService) {
        this.playersService = playersService;
    }

    @GetMapping("")
    public ResponseEntity<Page<Player>> getAllPlayers(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        System.out.println(request.getQueryString());
        System.out.println(response.getStatus());

//        "&pageNumber=0&pageSize=3&order=EXPERIENCE";
        String uri = request.getQueryString();
        System.out.println(uri);

        String order = request.getParameter("order");
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        boolean banned = Boolean.getBoolean(request.getParameter("banned"));
        String sortBy = "id";

        if ( order.equals("ID")) {
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

        System.out.println(order);
        System.out.println(pageNumber);
        System.out.println(pageSize);

        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Player> pagedResult = playerRepository.findAll(paging);
        return new ResponseEntity(pagedResult.getContent(), HttpStatus.OK);
    }
    
   // will write some code with criteria and jpa
//
//    @GetMapping("")
////    public ResponseEntity<Map<String, Object>> getAllPlayers(
//    public ResponseEntity<List<Player>> getAllPlayers(
//
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "3") int size,
//            @RequestParam(defaultValue = "id", required = false) String sortBy,
//            HttpServletRequest request) {
//        try {
//            List<Player> players = new ArrayList<>();
//            Pageable paging = PageRequest.of(page, size,  Sort.by(sortBy));
//
//            String uri = request.getQueryString();
//            System.out.println(uri);
//
//            Page<Player> pagePlayers;
////            if (name == null) {
//                pagePlayers = playerRepository.findAll(paging);
////            } else pagePlayers = playerRepository.findByNameContaining(name, paging);
//            players = pagePlayers.getContent();
//            Map<String, Object> response = new HashMap<>();
//            response.put("", players);
//
//            return new ResponseEntity<List<Player>>(players, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }


//    @GetMapping("")
//    public ResponseEntity<List<Player>> getAllPlayers(
//            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
//            @RequestParam(required = false, defaultValue = "3") Integer pageSize,
//            @RequestParam(defaultValue = "id", required = false) String sortBy,
//            HttpServletRequest request) {
//
//        String order;
//
////      "&pageNumber=0&pageSize=3&order=EXPERIENCE";
//        String uri = request.getQueryString();
//        System.out.println(uri);
//
//        Pattern pat = Pattern.compile("([^&=]+)=([^&]*)");
//        Matcher matcher = pat.matcher(uri);
//        Map<String, String> map = new HashMap<>();
//        while (matcher.find()) {
//            map.put(matcher.group(1), matcher.group(2));
//        }
//        order = map.get("order");
//        System.out.println(map.get("order"));
//
//        if (sortBy == null || order.equals("ID")) {
//            sortBy = "id";
//        }
//        if (order.equals("NAME")) {
//            sortBy = "name";
//        }
//        if (order.equals("EXPERIENCE")) {
//            sortBy = "experience";
//        }
//        if (order.equals("BIRTHDAY")) {
//            sortBy = "birthday";
//        }
//
//        List<Player> list = playersService.getAllPlayers(pageNo, pageSize, sortBy);
//        return new ResponseEntity<List<Player>>(list, new HttpHeaders(), HttpStatus.OK);
//    }


//    public ResponseEntity<List<Player>> findPlayers(
//            @And({
//                    @Spec(path = "name", spec = Equal.class),
//                    @Spec(path = "title", spec = Equal.class),
//                    @Spec(path = "banned", spec = Equal.class)
//            }) Specification<Player> playerSpecification, Pageable pageable,
//            HttpRequest request,
//            HttpServletResponse response
//    ) {
//        System.out.println(request);
//        System.out.println(response);
//
//        List<Player> list = playerRepository.findAll(playerSpecification, pageable).getContent();
//        return new ResponseEntity<List<Player>>(list, new  HttpHeaders(), HttpStatus.OK);
//    }


//    public PlayersController() {
//    }


//    @PostMapping("/")
//    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
//        return new ResponseEntity<>(playersService.addPlayer(player), HttpStatus.OK);
//    }


//    @GetMapping("")
//    public ResponseEntity<List<Player>> getAllPlayers(
//            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
//            @RequestParam(required = false, defaultValue = "3") Integer pageSize,
//            @RequestParam(defaultValue = "id", required = false) String sortBy,
//            HttpServletRequest request) {
//
//        String order;
//
////      "&pageNumber=0&pageSize=3&order=EXPERIENCE";
//        String uri = request.getQueryString();
//        System.out.println(uri);
//
//        Pattern pat = Pattern.compile("([^&=]+)=([^&]*)");
//        Matcher matcher = pat.matcher(uri);
//        Map<String, String> map = new HashMap<>();
//        while (matcher.find()) {
//            map.put(matcher.group(1), matcher.group(2));
//        }
//        order = map.get("order");
//        System.out.println(map.get("order"));
//
//        if (sortBy == null || order.equals("ID")) {
//            sortBy = "id";
//        }
//        if (order.equals("NAME")) {
//            sortBy = "name";
//        }
//        if (order.equals("EXPERIENCE")) {
//            sortBy = "experience";
//        }
//        if (order.equals("BIRTHDAY")) {
//            sortBy = "birthday";
//        }
//
//        List<Player> list = playersService.getAllPlayers(pageNo, pageSize, sortBy);
//        return new ResponseEntity<List<Player>>(list, new HttpHeaders(), HttpStatus.OK);
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
