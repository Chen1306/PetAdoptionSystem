package com.petadopt.mapper;

import com.petadopt.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users(username, password, phone, role, status) VALUES(#{username}, #{password}, #{phone}, #{role}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT * FROM users WHERE username = #{username} LIMIT 1")
    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM users WHERE id = #{id} LIMIT 1")
    User findById(@Param("id") Long id);

    @Select("SELECT * FROM users ORDER BY id DESC")
    List<User> findAll();

    @Update({
            "<script>",
            "UPDATE users",
            "<set>",
            "<if test='username != null and username != \"\"'>username = #{username},</if>",
            "<if test='phone != null and phone != \"\"'>phone = #{phone},</if>",
            "<if test='password != null and password != \"\"'>password = #{password},</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"
    })
    int updateProfile(User user);

    @Update("UPDATE users SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    @Select("SELECT COUNT(1) FROM users")
    Long countAll();
}
