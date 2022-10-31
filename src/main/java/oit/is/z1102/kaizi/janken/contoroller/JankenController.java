package oit.is.z1102.kaizi.janken.contoroller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
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
    ArrayList<User> users = userMapper.selectAllByuser();
    model2.addAttribute("users", users);
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model3.addAttribute("matches", matches);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, Principal prin, ModelMap model1, ModelMap model2) {
    String loginUser = prin.getName();
    model1.addAttribute("user", loginUser);
    User buser = userMapper.selectAllByUser(id);
    model2.addAttribute("buser", buser);
    return "match.html";
  }

  @GetMapping("/fight")
  public String fight(@RequestParam Integer id, @RequestParam String te, Principal prin, ModelMap model,
      ModelMap model2) {

    int usernum = 0;
    String userHand;
    String buserHand;
    Random rand = new Random();
    int cpunum = rand.nextInt(3);
    Janken janken = new Janken();

    if (te.equals("Gu")) {
      usernum = 0;
    } else if (te.equals("Choki")) {
      usernum = 1;
    } else if (te.equals("Pa")) {
      usernum = 2;
    }

    janken.setUser(usernum);
    janken.setCpu(cpunum);
    janken.result();

    String result = janken.getResult();

    if (usernum == 0) {
      userHand = "Gu";
    } else if (usernum == 1) {
      userHand = "Choki";
    } else {
      userHand = "Pa";
    }

    if (cpunum == 0) {
      buserHand = "Gu";
    } else if (cpunum == 1) {
      buserHand = "Choki";
    } else {
      buserHand = "Pa";
    }

    model.addAttribute("tuser", userHand);
    model.addAttribute("tcpu", buserHand);

    model.addAttribute("result", result);

    String loginUser = prin.getName();
    User user = userMapper.selectById(loginUser);
    User buser = userMapper.selectAllByUser(id);
    model.addAttribute("user", loginUser);
    model2.addAttribute("buser", buser);

    Match matchResult = new Match();
    matchResult.setUser1(user.getId());
    matchResult.setUser2(buser.getId());
    matchResult.setUser1Hand(userHand);
    matchResult.setUser2Hand(buserHand);
    matchMapper.insertMatch(matchResult);

    return "match.html";

  }

}
