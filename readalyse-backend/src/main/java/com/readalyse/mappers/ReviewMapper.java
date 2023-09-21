package com.readalyse.mappers;

import com.readalyse.entities.ReviewEntity;
import com.readalyse.model.Review;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

  Review entityToModel(ReviewEntity review);

  List<Review> entitiesToModels(List<ReviewEntity> reviews);
}
