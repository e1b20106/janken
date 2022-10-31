package oit.is.z1102.kaizi.janken.model;

import java.util.ArrayList;

//import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT users.id, users.name from users;")
  ArrayList<User> selectAllByuser();

  @Select("SELECT * from users where id=#{id};")
  User selectAllByUser(int id);

  @Select("SELECT users.id from users where name=#{name};")
  User selectById(String name);

}
