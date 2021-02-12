package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.mapper.MarkMapper;
import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkListDTO;
import com.georgidinov.roiti.schoolgrading.repository.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MarkServiceImpl implements MarkService {

    //== fields ==
    private final MarkRepository markRepository;
    private final MarkMapper markMapper;

    @Autowired
    public MarkServiceImpl(MarkRepository markRepository,
                           MarkMapper markMapper) {
        this.markRepository = markRepository;
        this.markMapper = markMapper;
    }


    @Override
    public MarkListDTO findAllMarks() {
        return new MarkListDTO(
                this.markRepository.findAll()
                        .stream()
                        .map(markMapper::markToMarkDTO)
                        .collect(Collectors.toList())
        );
    }

}