package com.example.nvclothes.service;

import com.example.nvclothes.entity.ClientEntity;
import com.example.nvclothes.exception.ClientNotFoundException;
import com.example.nvclothes.repository.interfaces.ClientEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientEntityService {

    private final ClientEntityRepositoryInterface clientRepository;

    private  final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean validateClientEntity(String name, String lastName, String email, String password,
                                        String telephoneNumber, String password2){

        boolean lol = name.matches("^[a-zA-Z]*$");
        lol = lastName.matches("^[a-zA-Z]*$");
        lol = password.equals(password2);
        lol = telephoneNumber.matches("^\\+?(?:38)?0?(\\d{9})$");
        lol = email.matches("^[\\w_.+-]*[\\w_.]@(?:[\\w]+\\.)+[a-zA-Z]{2,7}$");
        if (!(name.isEmpty()) && name.matches("^[a-zA-Z]*$") &&
        !(lastName.isEmpty()) && lastName.matches("^[a-zA-Z]*$") &&
        !(password.isEmpty()) && password.equals(password2) &&
        !(telephoneNumber.isEmpty()) && telephoneNumber.matches("^\\+?(?:38)?0?(\\d{9})$") &&
        !(email.isEmpty()) && email.matches("^[\\w_.+-]*[\\w_.]@(?:[\\w]+\\.)+[a-zA-Z]{2,7}$")){
            return true;
        }
        return false;
    }

    public void save(ClientEntity clientEntity){
        clientEntity.setPassword(bCryptPasswordEncoder.encode(clientEntity.getPassword()));
        clientRepository.save(clientEntity);
    }

    public ClientEntity getClientById(Long id) throws ClientNotFoundException{
        Optional<ClientEntity> clientOptional = clientRepository.getClientEntityById(id);
        if (!clientOptional.isPresent()) {
            throw new ClientNotFoundException("Client not found for id: " + id);
        }
        return clientOptional.get();
    }

    public ClientEntity getClientByEmail(String email) throws ClientNotFoundException{
        Optional<ClientEntity> clientOptional = clientRepository.getClientEntityByEmail(email);
        if (!clientOptional.isPresent()) {
            throw new ClientNotFoundException("Client not found for email: " + email);
        }
        return clientOptional.get();
    }

    String getClientEntityPasswordByEmail(String email) throws ClientNotFoundException{
        Optional<ClientEntity> clientOptional = clientRepository.getClientEntityByEmail(email);
        if (!clientOptional.isPresent()) {
            throw new ClientNotFoundException("Client not found for email: " + email);
        }
        ClientEntity client = clientOptional.get();
        return  client.getPassword();
    }




    public boolean validateCredentials(String email, String password){
        String encodedPassword = "";
        try {
            encodedPassword = this.getClientEntityPasswordByEmail(email);
        } catch (ClientNotFoundException e){
            //some decision
        }

        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }


}
