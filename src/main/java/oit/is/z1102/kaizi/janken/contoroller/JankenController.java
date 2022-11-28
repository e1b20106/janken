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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z1102.kaizi.janken.model.User;
import oit.is.z1102.kaizi.janken.model.UserMapper;
import oit.is.z1102.kaizi.janken.model.Match;
import oit.is.z1102.kaizi.janken.model.MatchMapper;
import oit.is.z1102.kaizi.janken.model.MatchInfo;
import oit.is.z1102.kaizi.janken.model.MatchInfoMapper;
import oit.is.z1102.kaizi.janken.service.AsyncKekka;

//import oit.is.z1102.kaizi.janken.model.Janken;

@Controller
public class JankenController {

  @Autowired
  // private Entry room;
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @Autowired
  AsyncKekka kekka;

  @GetMapping("/janken")
  @Transactional
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    // this.room.addUser(loginUser);
    model.addAttribute("user", loginUser);
    ArrayList<User> users = userMapper.selectAllByuser();
    model.addAttribute("users", users);
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model.addAttribute("matches", matches);
    ArrayList<MatchInfo> matchactive = matchInfoMapper.selectMatchActive();
    model.addAttribute("matchactive", matchactive);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, Principal prin, ModelMap model1, ModelMap model2) {
    String loginUser = prin.getName();
    model1.addAttribute("user", loginUser);
    User buser = userMapper.selectAllById(id);
    model2.addAttribute("buser", buser);
    return "match.html";
  }

  @GetMapping("/fight")
  @Transactional
  public String fight(@RequestParam Integer id, @RequestParam String te, Principal prin, ModelMap model) {

    String loginUser = prin.getName();
    User user = userMapper.selectByName(loginUser);
    User buser = userMapper.selectAllById(id);
    model.addAttribute("user", loginUser);
    model.addAttribute("buser", buser);

    MatchInfo matchactive = matchInfoMapper.selectMatchActive2(user.getId());
    if (matchactive == null) {
      MatchInfo active = new MatchInfo();
      active.setUser1(user.getId());
      active.setUser2(buser.getId());
      active.setUser1Hand(te);
      active.setisActive(true);
      matchInfoMapper.insertMatchInfo(active);
    } else {
      Match matchResult = new Match();
      matchResult.setUser1(user.getId());
      matchResult.setUser2(buser.getId());
      matchResult.setUser1Hand(te);
      matchResult.setUser2Hand(matchactive.getUser1Hand());
      matchResult.setisActive(true);
      matchMapper.insertMatch(matchResult);

      this.kekka.syncUpdataMatchInfo(matchactive.getId());
      kekka.syncSetId(matchResult.getId());
    }

    return "wait.html";

  }

  @GetMapping("/result")
  public SseEmitter result() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.kekka.asyncShowResult(sseEmitter);
    return sseEmitter;
  }
}
