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

    public List<UnitResponse> getAllUnitsByHskLevel(String levelId) {
        try {
            if (levelId == null || levelId.isBlank()) {
                return getAllUnits();
            }

            return unitsRepository.findAllByLevel_Id(java.util.UUID.fromString(levelId))
                    .stream().map(unitMapper::toUnitResponse)
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching units by HSK level {}: {}", levelId, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public UnitResponse getUnitById(UUID id) {
        try {
            return unitsRepository.findById(id)
                    .map(unitMapper::toUnitResponse)
                    .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));
        } catch (Exception e) {
            log.error("Error fetching unit by id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public UnitResponse createUnit(UnitRequest unitRequest) {
        try {
            var unit = unitMapper.toUnit(unitRequest);
            unit.setLevel(hskLevelRepository.findById(UUID.fromString(unitRequest.getLevelId()))
                    .orElseThrow(() -> new AppException(ErrorCode.HSK_LEVEL_NOT_FOUND)));
            return unitMapper.toUnitResponse(unitsRepository.save(unit));
        } catch (Exception e) {
            log.error("Error creating unit: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public void updateUnit(UUID id, UnitUpdateRequest unitRequest) {
        try {
            Unit existingUnit = unitsRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));
            existingUnit.setLevel(hskLevelRepository.findById(UUID.fromString(unitRequest.getLevel()))
                    .orElseThrow(() -> new AppException(ErrorCode.HSK_LEVEL_NOT_FOUND)));
            unitMapper.updateUnit(existingUnit, unitRequest);
            unitsRepository.save(existingUnit);
        } catch (Exception e) {
            log.error("Error updating unit with id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public void deleteUnit(UUID id) {
        try {
            Unit existingUnit = unitsRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.UNIT_NOT_FOUND));
            unitsRepository.delete(existingUnit);
        } catch (Exception e) {
            log.error("Error deleting unit with id {}: {}", id, e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public List<UnitResponse> getAllUnits() {
        try {
            return unitsRepository.findAll()
                    .stream()
                    .map(unitMapper::toUnitResponse)
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching all units: {}", e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

}
