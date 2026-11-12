package com.petadopt.mapper;

import com.petadopt.entity.AdoptRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AdoptRecordMapper {

    @Insert("INSERT INTO adopt_records(user_id, pet_id, apply_description, status) VALUES(#{userId}, #{petId}, #{applyDescription}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AdoptRecord record);

    @Select("SELECT * FROM adopt_records WHERE user_id = #{userId} ORDER BY id DESC")
    List<AdoptRecord> findByUserId(@Param("userId") Long userId);

    @Select("SELECT * FROM adopt_records ORDER BY id DESC")
    List<AdoptRecord> findAll();

    @Select("SELECT * FROM adopt_records WHERE id = #{id} LIMIT 1")
    AdoptRecord findById(@Param("id") Long id);

    @Select("SELECT * FROM adopt_records WHERE user_id = #{userId} AND pet_id = #{petId} LIMIT 1")
    AdoptRecord findByUserIdAndPetId(@Param("userId") Long userId, @Param("petId") Long petId);

    @Update("UPDATE adopt_records SET status = #{status}, audit_remark = #{auditRemark}, audited_by = #{auditedBy}, audited_at = #{auditedAt}, updated_at = NOW() WHERE id = #{id}")
    int audit(@Param("id") Long id,
              @Param("status") String status,
              @Param("auditRemark") String auditRemark,
              @Param("auditedBy") Long auditedBy,
              @Param("auditedAt") LocalDateTime auditedAt);

    @Update("UPDATE adopt_records SET status = 'CANCELED', updated_at = NOW() WHERE id = #{id}")
    int cancel(@Param("id") Long id);

    @Select("SELECT COUNT(1) FROM adopt_records")
    Long countAll();

    @Select("SELECT COUNT(1) FROM adopt_records WHERE status = 'WAITING'")
    Long countWaiting();

    @Select("SELECT COUNT(1) FROM adopt_records WHERE pet_id = #{petId}")
    Long countByPetId(@Param("petId") Long petId);
}
