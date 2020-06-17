package ro.ubb.monitor.web.converter;

import ro.ubb.monitor.web.dto.BaseDto;
import ro.ubb.monitor.core.model.BaseEntity;

/**
 * Created by radu.
 */

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}

