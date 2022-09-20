package myproject.project.service;

import myproject.project.model.request.UsedElectricWaterRequest;
import myproject.project.model.response.UsedElectricWaterResponse;

public interface UsedEletricWaterService {
    UsedElectricWaterResponse save(UsedElectricWaterRequest request);
}
