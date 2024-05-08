package com.kn.initialmusic.service.Impl;

import com.kn.initialmusic.mapper.AdminMapper;
import com.kn.initialmusic.pojo.Admin;
import com.kn.initialmusic.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Boolean save(Admin admin) {
        return null;
    }
}
