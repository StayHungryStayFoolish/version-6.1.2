package com.monolithic.service.mapper;

import com.monolithic.domain.*;
import com.monolithic.service.dto.IPRecordsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IPRecords} and its DTO {@link IPRecordsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IPRecordsMapper extends EntityMapper<IPRecordsDTO, IPRecords> {



    default IPRecords fromId(Long id) {
        if (id == null) {
            return null;
        }
        IPRecords iPRecords = new IPRecords();
        iPRecords.setId(id);
        return iPRecords;
    }
}
