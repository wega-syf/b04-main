package com.burat.simpel.service;

import com.burat.simpel.model.TitleModel;

import java.util.List;

public interface TitleService {

    public List<TitleModel> getAllTitle();
    TitleModel getTitleById(Long id);
    public List<TitleModel> getSomeTitle();
}
