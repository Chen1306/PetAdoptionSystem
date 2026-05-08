package com.petadopt.mapper;

import com.petadopt.entity.Pet;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PetMapper {

    @Select({
            "<script>",
            "SELECT * FROM pets",
            "WHERE review_status = 'APPROVED' AND adopt_status = 'AVAILABLE'",
            "<if test='type != null and type != \"\"'>AND type = #{type}</if>",
            "<if test='keyword != null and keyword != \"\"'>",
            "AND (pet_name LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%'))",
            "</if>",
            "ORDER BY id DESC",
            "</script>"
    })
    List<Pet> findPublicList(@Param("type") String type, @Param("keyword") String keyword);

    @Select("SELECT * FROM pets WHERE id = #{id} LIMIT 1")
    Pet findById(@Param("id") Long id);

    @Select("SELECT * FROM pets ORDER BY id DESC")
    List<Pet> findAll();

    @Insert("INSERT INTO pets(pet_name, type, age, gender, image_url, description, review_status, adopt_status) VALUES(#{petName}, #{type}, #{age}, #{gender}, #{imageUrl}, #{description}, #{reviewStatus}, #{adoptStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Pet pet);

    @Update("UPDATE pets SET pet_name = #{petName}, type = #{type}, age = #{age}, gender = #{gender}, image_url = #{imageUrl}, description = #{description}, updated_at = NOW() WHERE id = #{id}")
    int update(Pet pet);

    @Delete("DELETE FROM pets WHERE id = #{id}")
    int deleteById(@Param("id") Long id);

    @Update("UPDATE pets SET review_status = #{reviewStatus}, review_remark = #{reviewRemark}, reviewer_id = #{reviewerId}, reviewed_at = #{reviewedAt}, updated_at = NOW() WHERE id = #{id}")
    int review(@Param("id") Long id,
               @Param("reviewStatus") String reviewStatus,
               @Param("reviewRemark") String reviewRemark,
               @Param("reviewerId") Long reviewerId,
               @Param("reviewedAt") LocalDateTime reviewedAt);

    @Update("UPDATE pets SET adopt_status = #{adoptStatus}, updated_at = NOW() WHERE id = #{id}")
    int updateAdoptStatus(@Param("id") Long id, @Param("adoptStatus") String adoptStatus);

    @Select("SELECT COUNT(1) FROM pets")
    Long countAll();
}
