package oit.is.z1102.kaizi.janken.contoroller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import oit.is.z1102.kaizi.janken.model.Entry;

import oit.is.z1102.kaizi.janken.model.User;
import oit.is.z1102.kaizi.janken.model.UserMapper;
import oit.is.z1102.kaizi.janken.model.Match;
import oit.is.z1102.kaizi.janken.model.MatchMapper;

import oit.is.z1102.kaizi.janken.model.Janken;
import java.util.Random;

@Controller
public class JankenController {

  @Autowired
  // private Entry room;
  UserMapper userMapper;
  @Autowired
  MatchMapper matchMapper;

  @GetMapping("/janken")
  @Transactional
  public String janken(Principal prin, ModelMap model1, ModelMap model2, ModelMap model3) {
    String loginUser = prin.getName();
    // this.room.addUser(loginUser);
    model1.addAttribute("user", loginUser);
    ArrayList<User> users = userMapper.selectAllByname();
    model2.addAttribute("users", users);
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model3.addAttribute("matches", matches);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, Principal prin, ModelMap model1, ModelMap model2) {
    String loginUser = prin.getName();
    model1.addAttribute("user", loginUser);
    ArrayList<User> buser = userMapper.selectById(id);
    model2.addAttribute("buser", buser);
    return "match.html";
  }

  @GetMapping("/janken/{te}")
  public String janken(@PathVariable("te") String te, ModelMap model) {

    int user = 0;
    Random rand = new Random();
    int cpu = rand.nextInt(3);
    Janken janken = new Janken();

    if (te.equals("Gu")) {
      user = 0;
    } else if (te.equals("Ty")) {
      user = 1;
    } else if (te.equals("Pa")) {
      user = 2;
    }

    janken.setUser(user);
    janken.setCpu(cpu);
    janken.result();

    String result = janken.getResult();

    if (user == 0) {
      model.addAttribute("tuser", "Gu");
    } else if (user == 1) {
      model.addAttribute("tuser", "Ty");
    } else {
      model.addAttribute("tuser", "Pa");
    }

    if (cpu == 0) {
      model.addAttribute("tcpu", "Gu");
    } else if (cpu == 1) {
      model.addAttribute("tcpu", "Ty");
    } else {
      model.addAttribute("tcpu", "Pa");
    }

    model.addAttribute("result", result);

    return "janken.html";

  }

}
