package oit.is.z1102.kaizi.janken.model;

import java.util.ArrayList;

//import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchMapper {

  @Select("SELECT matches.id, matches.user1, matches.user2, matches.user1Hand, matches.user2Hand from matches;")
  ArrayList<Match> selectAllMatches();

}
