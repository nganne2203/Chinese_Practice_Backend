package fptedu.nganmtt.ChinesePractice.service;

import fptedu.nganmtt.ChinesePractice.dto.request.UnitRequest;
import fptedu.nganmtt.ChinesePractice.dto.request.UnitUpdateRequest;
import fptedu.nganmtt.ChinesePractice.dto.response.UnitResponse;
import fptedu.nganmtt.ChinesePractice.exception.AppException;
import fptedu.nganmtt.ChinesePractice.exception.ErrorCode;
import fptedu.nganmtt.ChinesePractice.mapper.UnitMapper;
import fptedu.nganmtt.ChinesePractice.model.Unit;
import fptedu.nganmtt.ChinesePractice.repository.HskLevelRepository;
import fptedu.nganmtt.ChinesePractice.repository.UnitsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UnitService {
    UnitsRepository unitsRepository;
    UnitMapper unitMapper;
    HskLevelRepository hskLevelRepository;
    public List<UnitResponse> getAllUnitsByHskLevel(UUID levelId) {
        return unitsRepository.findAllByLevel_Id(levelId)
                .stream().map(unitMapper::toUnitResponse)
                .toList();
    }

    public UnitResponse getUnitById(UUID id) {
        return unitsRepository.findById(id)
                .map(unitMapper::toUnitResponse)
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));
    }

    public UnitResponse createUnit(UnitRequest unitRequest) {
        var unit = unitMapper.toUnit(unitRequest);
        unit.setLevel(hskLevelRepository.findById(UUID.fromString(unitRequest.getLevelId()))
                .orElseThrow(() -> new AppException(ErrorCode.HSK_LEVEL_NOT_FOUND)));
        return unitMapper.toUnitResponse(unitsRepository.save(unit));
    }

    public void updateUnit(UUID id, UnitUpdateRequest unitRequest) {
        Unit existingUnit = unitsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));

        unitMapper.updateUnit(existingUnit, unitRequest);
        unitsRepository.save(existingUnit);
    }

    public void deleteUnit(UUID id) {
        Unit existingUnit = unitsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));
        unitsRepository.delete(existingUnit);
    }

}
