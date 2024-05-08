package com.kn.initialmusic.service;


import com.kn.initialmusic.pojo.Admin;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminService {

    /**
     * 保存管理员
     *
     * @param admin
     * @return
     */
    Boolean save(Admin admin);
}
