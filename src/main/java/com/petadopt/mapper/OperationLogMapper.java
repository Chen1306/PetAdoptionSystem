package com.petadopt.mapper;

import com.petadopt.entity.OperationLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface OperationLogMapper {

    @Insert("INSERT INTO operation_logs(operator_id, operator_role, module, action, target_id, content, ip) VALUES(#{operatorId}, #{operatorRole}, #{module}, #{action}, #{targetId}, #{content}, #{ip})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OperationLog log);
}
