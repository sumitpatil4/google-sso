package com.example.googlesso.Service;

import com.example.googlesso.Entity.Admin;
import com.example.googlesso.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    public AdminRepository adminRepository;

    public void saveAdmin(Admin admin){
        System.out.println("Saving....");
        adminRepository.save(admin);
    }
}
