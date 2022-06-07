package bt.org.dsp.crst.userManager.repository;

import bt.org.dsp.crst.userManager.model.PermissionSetup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPermissionRepository extends JpaRepository<PermissionSetup, Integer> {
    List<PermissionSetup> findAllById(Integer id);

    List<PermissionSetup> findAllByUserGroupId(Integer id);
}
