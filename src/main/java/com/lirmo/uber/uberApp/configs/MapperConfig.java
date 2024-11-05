package com.lirmo.uber.uberApp.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.locationtech.jts.geom.Point;
import com.lirmo.uber.uberApp.dto.PointDto;
import com.lirmo.uber.uberApp.utils.GeometryUtil;

@Configuration
public class MapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();

    mapper.typeMap(PointDto.class, Point.class).setConverter(context -> {
      PointDto pointDto = context.getSource();
      return GeometryUtil.createPoint(pointDto);
    });

    mapper.typeMap(Point.class, PointDto.class).setConverter(context -> {
      Point point = context.getSource();
      double coordinates[] = {
          point.getX(),
          point.getY()
      };
      return new PointDto(coordinates);
    });

    return mapper;
  }
}
