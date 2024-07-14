package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LabOldUploadsDTO {

    private Long id;

    @NotNull
    private Long labid;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String fileName;

    private Long labRequestId;

}
