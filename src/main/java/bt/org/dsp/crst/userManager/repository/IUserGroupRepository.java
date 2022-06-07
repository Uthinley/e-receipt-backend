package bt.org.dsp.crst.userManager.repository;

import bt.org.dsp.crst.userManager.model.UserGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserGroupRepository extends CrudRepository<UserGroup, Integer> {
    UserGroup findAllById(Integer id);
}
