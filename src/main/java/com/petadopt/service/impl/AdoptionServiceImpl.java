package com.petadopt.service.impl;

import com.petadopt.common.BusinessException;
import com.petadopt.common.ResultCode;
import com.petadopt.dto.AdoptionAuditRequest;
import com.petadopt.dto.AdoptionCreateRequest;
import com.petadopt.entity.AdoptRecord;
import com.petadopt.entity.Pet;
import com.petadopt.entity.User;
import com.petadopt.mapper.AdoptRecordMapper;
import com.petadopt.mapper.PetMapper;
import com.petadopt.mapper.UserMapper;
import com.petadopt.service.AdoptionService;
import com.petadopt.vo.LoginUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdoptionServiceImpl implements AdoptionService {

    private final AdoptRecordMapper adoptRecordMapper;
    private final PetMapper petMapper;
    private final UserMapper userMapper;

    public AdoptionServiceImpl(AdoptRecordMapper adoptRecordMapper, PetMapper petMapper, UserMapper userMapper) {
        this.adoptRecordMapper = adoptRecordMapper;
        this.petMapper = petMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Long createApplication(LoginUser loginUser, AdoptionCreateRequest request) {
        ensureUserRole(loginUser);
        User user = userMapper.findById(loginUser.getUserId());
        if (user == null || "DISABLED".equalsIgnoreCase(user.getStatus())) {
            throw new BusinessException(ResultCode.FORBIDDEN, "user can not apply");
        }
        Pet pet = petMapper.findById(request.getPetId());
        if (pet == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "pet not found");
        }
        if (!"APPROVED".equalsIgnoreCase(pet.getReviewStatus()) || !"AVAILABLE".equalsIgnoreCase(pet.getAdoptStatus())) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "pet is not available for adoption");
        }
        if (adoptRecordMapper.findByUserIdAndPetId(loginUser.getUserId(), request.getPetId()) != null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "you have already applied for this pet");
        }
        AdoptRecord record = new AdoptRecord();
        record.setUserId(loginUser.getUserId());
        record.setPetId(request.getPetId());
        record.setApplyDescription(request.getApplyDescription());
        record.setStatus("WAITING");
        adoptRecordMapper.insert(record);
        return record.getId();
    }

    @Override
    public List<AdoptRecord> listMyApplications(Long userId) {
        return adoptRecordMapper.findByUserId(userId);
    }

    @Override
    public void cancelApplication(LoginUser loginUser, Long id) {
        ensureUserRole(loginUser);
        AdoptRecord record = getRecord(id);
        if (!loginUser.getUserId().equals(record.getUserId())) {
            throw new BusinessException(ResultCode.FORBIDDEN, "can not cancel another user's application");
        }
        if (!"WAITING".equalsIgnoreCase(record.getStatus())) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "only waiting application can be canceled");
        }
        adoptRecordMapper.cancel(id);
    }

    @Override
    public List<AdoptRecord> listAllApplications() {
        return adoptRecordMapper.findAll();
    }

    @Override
    public void auditApplication(LoginUser loginUser, Long id, AdoptionAuditRequest request) {
        ensureAdminRole(loginUser);
        AdoptRecord record = getRecord(id);
        if (!"WAITING".equalsIgnoreCase(record.getStatus())) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "only waiting application can be audited");
        }
        adoptRecordMapper.audit(id, request.getStatus(), request.getAuditRemark(), loginUser.getUserId(), LocalDateTime.now());
        if ("PASSED".equalsIgnoreCase(request.getStatus())) {
            petMapper.updateAdoptStatus(record.getPetId(), "ADOPTED");
        }
    }

    private AdoptRecord getRecord(Long id) {
        AdoptRecord record = adoptRecordMapper.findById(id);
        if (record == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "adoption record not found");
        }
        return record;
    }

    private void ensureAdminRole(LoginUser loginUser) {
        if (!"admin".equalsIgnoreCase(loginUser.getRole())) {
            throw new BusinessException(ResultCode.FORBIDDEN, "admin permission required");
        }
    }

    private void ensureUserRole(LoginUser loginUser) {
        if (!"user".equalsIgnoreCase(loginUser.getRole())) {
            throw new BusinessException(ResultCode.FORBIDDEN, "user permission required");
        }
    }
}
