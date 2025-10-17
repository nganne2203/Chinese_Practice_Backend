package fptedu.nganmtt.ChinesePractice.mapper;

import fptedu.nganmtt.ChinesePractice.dto.request.UnitRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.UnitUpdateRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.UnitResponse;
import fptedu.nganmtt.ChinesePractice.model.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {HskLevelMapper.class})
public interface UnitMapper {
    Unit toUnit(UnitRequest unitRequest);

    UnitResponse toUnitResponse(Unit unit);

    @Mapping(ignore = true, target = "id")
    void updateUnit(@MappingTarget Unit existingUnit, UnitUpdateRequest unitRequest);
}
