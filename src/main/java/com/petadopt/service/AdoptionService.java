package com.petadopt.service;

import com.petadopt.dto.AdoptionAuditRequest;
import com.petadopt.dto.AdoptionCreateRequest;
import com.petadopt.entity.AdoptRecord;
import com.petadopt.vo.LoginUser;

import java.util.List;

public interface AdoptionService {

    Long createApplication(LoginUser loginUser, AdoptionCreateRequest request);

    List<AdoptRecord> listMyApplications(Long userId);

    void cancelApplication(LoginUser loginUser, Long id);

    List<AdoptRecord> listAllApplications();

    void auditApplication(LoginUser loginUser, Long id, AdoptionAuditRequest request);
}
