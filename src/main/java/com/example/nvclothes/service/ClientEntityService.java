package com.example.nvclothes.nvclothes.service;

import com.example.nvclothes.nvclothes.repository.interfaces.ClientEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientEntityService {

    private final ClientEntityRepositoryInterface clientRepository;




}
