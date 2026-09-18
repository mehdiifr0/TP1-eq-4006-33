package uspace.application.cruise.dtos;

import uspace.application.cruise.hyperdrive.dtos.HyperdriveModuleDto;

import java.util.List;

public class CruiseDto {
    public String id;
    public String departureDateTime;
    public String endDateTime;
    public List<HyperdriveModuleDto> hyperdriveModules;

    public CruiseDto(String id, String departureDateTime, String endDateTime, List<HyperdriveModuleDto> hyperdriveModules) {
        this.id = id;
        this.departureDateTime = departureDateTime;
        this.endDateTime = endDateTime;
        this.hyperdriveModules = hyperdriveModules;
    }
}
