package oit.is.z1102.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {

  @Select("SELECT matches.id, matches.user1, matches.user2, matches.user1Hand, matches.user2Hand from matches;")
  ArrayList<Match> selectAllMatches();

  @Select("SELECT * from matches where id = #{id};")
  Match selectById(int id);

  @Select("SELECT * from matches where isActive = true;")
  Match selectAllResult();

  @Insert("INSERT INTO matches (user1, user2, user1Hand, user2Hand, isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(Match match);

  @Update("UPDATE matches SET isActive = FALSE where id = #{id}")
  void updateMatch(int id);

}
