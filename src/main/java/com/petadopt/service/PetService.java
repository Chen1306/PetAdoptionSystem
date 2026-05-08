package com.petadopt.service;

import com.petadopt.dto.PetReviewRequest;
import com.petadopt.entity.Pet;
import com.petadopt.vo.LoginUser;

import java.util.List;

public interface PetService {

    List<Pet> listPublicPets(String type, String keyword);

    Pet getPetDetail(Long id);

    List<Pet> listAllPets();

    Long createPet(Pet pet);

    void updatePet(Pet pet);

    void deletePet(Long id);

    void reviewPet(LoginUser loginUser, Long id, PetReviewRequest request);
}
