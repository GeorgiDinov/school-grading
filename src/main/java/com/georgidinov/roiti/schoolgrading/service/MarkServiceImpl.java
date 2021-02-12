package com.georgidinov.roiti.schoolgrading.service;

import com.georgidinov.roiti.schoolgrading.api.v1.mapper.MarkMapper;
import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkDTO;
import com.georgidinov.roiti.schoolgrading.api.v1.model.MarkListDTO;
import com.georgidinov.roiti.schoolgrading.exception.EntityNotFoundCustomException;
import com.georgidinov.roiti.schoolgrading.repository.MarkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.georgidinov.roiti.schoolgrading.util.ApplicationConstants.ERROR_MARK_NOT_FOUND;

@Slf4j
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
        log.info("MarkServiceImp::findAllMarks");
        return new MarkListDTO(
                this.markRepository.findAll()
                        .stream()
                        .map(markMapper::markToMarkDTO)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public MarkDTO findMarkById(Long id) {
        log.info("MarkServiceImpl::findMarkById -> id passed = {}", id);
        return this.markRepository.findById(id)
                .map(markMapper::markToMarkDTO)
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        String.format(ERROR_MARK_NOT_FOUND, id))
                );
    }
}