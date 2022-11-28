package oit.is.z1102.kaizi.janken.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z1102.kaizi.janken.model.Match;
import oit.is.z1102.kaizi.janken.model.MatchMapper;
import oit.is.z1102.kaizi.janken.model.MatchInfo;
import oit.is.z1102.kaizi.janken.model.MatchInfoMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;
  int id;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper mMapper;

  @Autowired
  MatchInfoMapper minfoMapper;

  @Transactional
  public void syncUpdataMatchInfo(int id) {
    minfoMapper.updateByisActive(id);
  }

  public void syncSetId(int id) {
    this.id = id;
    this.dbUpdated = true;
  }

  @Async
  public void asyncShowResult(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {// 無限ループ
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        Match match = mMapper.selectById(this.id);
        emitter.send(match);
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;
        mMapper.updateMatch(this.id);

      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
  }

}
