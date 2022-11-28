package oit.is.z1102.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  @Select("SELECT matchinfo.id, matchinfo.user1, matchinfo.user2, matchinfo.isActive from matchinfo where isActive = true;")
  ArrayList<MatchInfo> selectMatchActive();

  @Select("SELECT matchinfo.id, matchinfo.user1, matchinfo.user2, matchinfo.user1Hand from matchinfo where (isActive = true) and (user2=#{user1}) limit 1;")
  MatchInfo selectMatchActive2(int user1);

  @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchInfo);

  @Update("UPDATE matchinfo SET isActive= FALSE where id = #{id}")
  void updateByisActive(int id);

}
