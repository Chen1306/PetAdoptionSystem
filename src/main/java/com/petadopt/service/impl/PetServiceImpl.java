package com.petadopt.service.impl;

import com.petadopt.common.BusinessException;
import com.petadopt.common.ResultCode;
import com.petadopt.dto.PetReviewRequest;
import com.petadopt.entity.Pet;
import com.petadopt.mapper.PetMapper;
import com.petadopt.service.PetService;
import com.petadopt.vo.LoginUser;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetMapper petMapper;

    public PetServiceImpl(PetMapper petMapper) {
        this.petMapper = petMapper;
    }

    @Override
    public List<Pet> listPublicPets(String type, String keyword) {
        return petMapper.findPublicList(type, keyword);
    }

    @Override
    public Pet getPetDetail(Long id) {
        Pet pet = petMapper.findById(id);
        if (pet == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "pet not found");
        }
        return pet;
    }

    @Override
    public List<Pet> listAllPets() {
        return petMapper.findAll();
    }

    @Override
    public Long createPet(Pet pet) {
        if (!StringUtils.hasText(pet.getReviewStatus())) {
            pet.setReviewStatus("PENDING");
        }
        if (!StringUtils.hasText(pet.getAdoptStatus())) {
            pet.setAdoptStatus("AVAILABLE");
        }
        petMapper.insert(pet);
        return pet.getId();
    }

    @Override
    public void updatePet(Pet pet) {
        ensureExists(pet.getId());
        petMapper.update(pet);
    }

    @Override
    public void deletePet(Long id) {
        ensureExists(id);
        petMapper.deleteById(id);
    }

    @Override
    public void reviewPet(LoginUser loginUser, Long id, PetReviewRequest request) {
        ensureExists(id);
        petMapper.review(id, request.getReviewStatus(), request.getReviewRemark(), loginUser.getUserId(), LocalDateTime.now());
    }

    private void ensureExists(Long id) {
        if (petMapper.findById(id) == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "pet not found");
        }
    }
}
