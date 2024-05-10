package com.example.nvclothes.repository.interfaces;


import com.example.nvclothes.entity.City;
import com.example.nvclothes.entity.PostOffice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostOfficeRepositoryInterface extends JpaRepository<PostOffice, Long> {

    Optional<PostOffice> getPostOfficeById(Long id);

    List<PostOffice> getPostOfficesByCityId(Long cityId);

    @Transactional
    void deletePostOfficeById(Long id);
}
