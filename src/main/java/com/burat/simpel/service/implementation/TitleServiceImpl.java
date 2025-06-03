package com.burat.simpel.service.implementation;

import com.burat.simpel.model.TitleModel;
import com.burat.simpel.repository.CompetencyDb;
import com.burat.simpel.repository.CompetencyModelDb;
import com.burat.simpel.repository.TitleDb;
import com.burat.simpel.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TitleServiceImpl implements TitleService {
    @Autowired
    private TitleDb titleDb;

    @Autowired
    private CompetencyModelDb competencyModelDb;

    @Override
    public List<TitleModel> getAllTitle() {
        return titleDb.findAll();
    }

    @Override
    public TitleModel getTitleById(Long id) {
        Optional<TitleModel> title = titleDb.findById(id);
        if (title.isPresent()) {
            return title.get();
        } else
            return null;
    }

    @Override
    public List<TitleModel> getSomeTitle() {
        List<TitleModel> listUniqueTitle = new ArrayList<TitleModel>();
        for (TitleModel title : titleDb.findAll()) {
            if (competencyModelDb.findTitleExisting().contains(title)) {
                continue;
            } else {
                listUniqueTitle.add(title);
            }
        }
        return listUniqueTitle;
    }
}
