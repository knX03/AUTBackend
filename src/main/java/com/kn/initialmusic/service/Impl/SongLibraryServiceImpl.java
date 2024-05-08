package com.kn.initialmusic.service.Impl;

import com.kn.initialmusic.mapper.SongLibraryMapper;
import com.kn.initialmusic.service.SongLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongLibraryServiceImpl implements SongLibraryService {

    @Autowired
    private SongLibraryMapper songLibraryMapper;


}
