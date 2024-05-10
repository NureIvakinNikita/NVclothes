package com.example.nvclothes.service;

import com.example.nvclothes.entity.PostOffice;
import com.example.nvclothes.exception.PostOfficeNotFoundException;
import com.example.nvclothes.repository.interfaces.PostOfficeRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostOfficeService {

    @Autowired
    private PostOfficeRepositoryInterface postOfficeRepository;

    public PostOffice getCityById(Long id) throws PostOfficeNotFoundException {
        Optional<PostOffice> postOfficeOptional = postOfficeRepository.getPostOfficeById(id);
        if (!postOfficeOptional.isPresent()){
            throw new PostOfficeNotFoundException("Post office wasn't found by id: "+id);
        }
        return postOfficeOptional.get();
    }

    public  List<PostOffice> getPostOfficesByCityId(Long id){
        return postOfficeRepository.getPostOfficesByCityId(id);
    }


    public void deleteCityById(Long id){
        postOfficeRepository.deletePostOfficeById(id);
    }

    public List<PostOffice> getAll(){
        return postOfficeRepository.findAll();
    }
}
