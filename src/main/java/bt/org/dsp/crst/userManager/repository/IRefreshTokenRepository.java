package bt.org.dsp.crst.userManager.repository;

import bt.org.dsp.crst.userManager.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Object findByToken(String token);

    void deleteByToken(String token);
}
