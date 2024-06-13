package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.HealthData;
import io.bootify.health_hive.domain.User;
import io.bootify.health_hive.model.HealthDataDTO;
import io.bootify.health_hive.repos.HealthDataRepository;
import io.bootify.health_hive.repos.UserRepository;
import io.bootify.health_hive.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class HealthDataService {

    private final HealthDataRepository healthDataRepository;
    private final UserRepository userRepository;

    public HealthDataService(final HealthDataRepository healthDataRepository,
                             final UserRepository userRepository) {
        this.healthDataRepository = healthDataRepository;
        this.userRepository = userRepository;
    }

    public List<HealthDataDTO> findAll() {
        final List<HealthData> healthDatas = healthDataRepository.findAll(Sort.by("healthDataId"));
        return healthDatas.stream()
                .map(healthData -> mapToDTO(healthData, new HealthDataDTO()))
                .toList();
    }

    public HealthDataDTO get(final Long healthDataId) {
        return healthDataRepository.findById(healthDataId)
                .map(healthData -> mapToDTO(healthData, new HealthDataDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final HealthDataDTO healthDataDTO) {
        final HealthData healthData = new HealthData();
        mapToEntity(healthDataDTO, healthData);
        return healthDataRepository.save(healthData).getHealthDataId();
    }

    public void update(final Long healthDataId, final HealthDataDTO healthDataDTO) {
        final HealthData healthData = healthDataRepository.findById(healthDataId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(healthDataDTO, healthData);
        healthDataRepository.save(healthData);
    }

    public void delete(final Long healthDataId) {
        healthDataRepository.deleteById(healthDataId);
    }

    private HealthDataDTO mapToDTO(final HealthData healthData, final HealthDataDTO healthDataDTO) {
        healthDataDTO.setHealthDataId(healthData.getHealthDataId());
        healthDataDTO.setDate(healthData.getDate());
        healthDataDTO.setWeight(healthData.getWeight());
        healthDataDTO.setHeight(healthData.getHeight());
        healthDataDTO.setNotes(healthData.getNotes());
        healthDataDTO.setUser(healthData.getUser() == null ? null : healthData.getUser().getId());
        return healthDataDTO;
    }

    private HealthData mapToEntity(final HealthDataDTO healthDataDTO, final HealthData healthData) {
        healthData.setDate(healthDataDTO.getDate());
        healthData.setWeight(healthDataDTO.getWeight());
        healthData.setHeight(healthDataDTO.getHeight());
        healthData.setNotes(healthDataDTO.getNotes());
        final User user = healthDataDTO.getUser() == null ? null : userRepository.findById(healthDataDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        healthData.setUser(user);
        return healthData;
    }

}