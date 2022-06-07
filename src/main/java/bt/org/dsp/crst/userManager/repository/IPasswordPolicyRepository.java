package bt.org.dsp.crst.userManager.repository;

import bt.org.dsp.crst.userManager.model.PasswordPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPasswordPolicyRepository extends JpaRepository<PasswordPolicy, Integer> {
}
