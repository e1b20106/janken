package oit.is.z1102.kaizi.janken.contoroller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z1102.kaizi.janken.model.Janken;

import java.util.Random;

@Controller
public class JankenController {

  @GetMapping("/janken")
  public String name(ModelMap model, Principal prin) {
    String loginUser = prin.getName(); // ログインユーザ情報
    model.addAttribute("name", loginUser);
    return "janken.html";
  }

  /*
   * @PostMapping("/janken")
   * public String noname() {
   * return "janken.html";
   * }
   */

  /**
   * @return
   */

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