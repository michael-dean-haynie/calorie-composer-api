package com.codetudes.caloriecomposerapi.util.mergers;

import com.codetudes.caloriecomposerapi.config.MergeMapper;
import com.codetudes.caloriecomposerapi.contracts.ConversionRatioDTO;
import com.codetudes.caloriecomposerapi.db.domain.ConversionRatio;
import com.codetudes.caloriecomposerapi.db.domain.Food;
import com.codetudes.caloriecomposerapi.db.repositories.ConversionRatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversionRatioMerger {
    @Autowired
    private MergeMapper mergeMapper;
    @Autowired
    private UnitMerger unitMerger;
    @Autowired
    private ConversionRatioRepository conversionRatioRepository;

    ConversionRatio merge(ConversionRatioDTO cvRatDTO, ConversionRatio cvRat, Food food) {
        boolean needsPersisted = false;
        if (cvRat == null) {
            if (food.getId() != null) {
                needsPersisted = true;
            }
            cvRat = new ConversionRatio();
        }

        cvRat.setFood(food);
        cvRat.setAmountA(cvRatDTO.getAmountA());
        cvRat.setUnitA(unitMerger.merge(cvRatDTO.getUnitA(), cvRat.getUnitA()));
        cvRat.setFreeFormValueA(cvRatDTO.getFreeFormValueA());
        cvRat.setAmountB(cvRatDTO.getAmountB());
        cvRat.setUnitB(unitMerger.merge(cvRatDTO.getUnitB(), cvRat.getUnitB()));
        cvRat.setFreeFormValueB(cvRatDTO.getFreeFormValueB());

        if (needsPersisted) {
            conversionRatioRepository.save(cvRat);
        }
        return cvRat;
    }
}
