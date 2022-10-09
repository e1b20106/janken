package oit.is.z1102.kaizi.janken.contoroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z1102.kaizi.janken.model.Janken;

@Controller
public class JankenController {

  @PostMapping("/janken")
  public String name(@RequestParam String name, ModelMap model) {
    model.addAttribute("name", name);
    return "janken.html";
  }

  /**
   * @return
   */

  @GetMapping("/janken/{te}")
  public String janken(@PathVariable("te") String te, ModelMap model) {

    int user = 0;
    int cpu = 0;
    Janken janken = new Janken();

    if (te.equals("Gu")) {
      user = 0;
    } else if (te.equals("Ty")) {
      user = 1;
    } else if (te.equals("Pa")) {
      user = 2;
    }

    janken.setUser(user);
    janken.result();

    String result = janken.getResult();
    cpu = janken.getCpu();

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
