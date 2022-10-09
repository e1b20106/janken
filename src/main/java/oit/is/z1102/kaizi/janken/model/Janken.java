package oit.is.z1102.kaizi.janken.model;

import java.util.Random;

public class Janken {
  int user;
  int cpu;
  String result;

  public Janken() {
    this.user = 0;
    Random rand = new Random();
    this.cpu = rand.nextInt(3);
  }

  public void result() {
    if (user == cpu) {
      result = "Draw";
    } else if ((user == 0 && cpu == 1) || (user == 1 && cpu == 2) || (user == 2 && cpu == 0)) {
      result = "You Win";
    } else {
      result = "You Lose";
    }
  }

  public void setUser(int user) {
    this.user = user;
  }

  public int getCpu() {
    return cpu;
  }

  public String getResult() {
    return result;
  }
}

/* G=0,T=1,P=2 */