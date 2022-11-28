package oit.is.z1102.kaizi.janken.model;

public class Janken {
  String result;
  int user1num = 0;
  int user2num = 0;

  public Janken() {
  }

  public void result(String user1Hand, String user2Hand) {

    if (user1Hand.equals("Gu")) {
      user1num = 0;
    } else if (user1Hand.equals("Choki")) {
      user1num = 1;
    } else if (user1Hand.equals("Pa")) {
      user1num = 2;
    }

    if (user2Hand.equals("Gu")) {
      user2num = 0;
    } else if (user2Hand.equals("Choki")) {
      user2num = 1;
    } else if (user2Hand.equals("Pa")) {
      user2num = 2;
    }

    if (user1num == user2num) {
      result = "引き分け";
    } else if ((user1num == 0 && user2num == 1) || (user1num == 1 && user2num == 2)
        || (user1num == 2 && user2num == 0)) {
      result = "user1の勝利";
    } else {
      result = "user2の勝利";
    }
  }
}

/* G=0,T=1,P=2 */
