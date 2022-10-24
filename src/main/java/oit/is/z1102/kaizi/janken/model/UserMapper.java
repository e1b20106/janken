package oit.is.z1102.kaizi.janken.model;

import java.util.ArrayList;

//import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT users.name from users;")
  ArrayList<User> selectAllByname();

  @Select("SELECT users.id, users.name from users where id=#{id};")
  ArrayList<User> selectById(int id);

}
