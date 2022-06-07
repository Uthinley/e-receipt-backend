package bt.org.dsp.crst.userManager.dto;

import bt.org.dsp.crst.userManager.model.PermissionSetup;
import lombok.Data;

import java.util.List;

@Data
public class PermissionListDTO {
    private Integer id;
    private Integer groupId;
    private List<Integer> screenId;
    private Boolean isView;
    private Boolean isEdit;
    private Boolean isSave;
    private Boolean isDelete;

    public PermissionSetup mapEntity(PermissionListDTO permissionListDTO) {
        PermissionSetup permissionSetup = new PermissionSetup();
        if (permissionListDTO.getIsView() != null)
            permissionSetup.setView(permissionListDTO.getIsView());
        if (permissionListDTO.getIsEdit() != null)
            permissionSetup.setEdit(permissionListDTO.getIsEdit());
        if (permissionListDTO.getIsSave() != null)
            permissionSetup.setSave(permissionListDTO.getIsSave());
        if (permissionListDTO.getIsDelete() != null)
            permissionSetup.setDelete(permissionListDTO.getIsDelete());
        return permissionSetup;
    }
}
