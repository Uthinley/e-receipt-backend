package bt.org.dsp.crst.userManager.repository;

import bt.org.dsp.crst.userManager.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IScreenRepository extends JpaRepository<Screen, Integer> {
    List<Screen> findAll();
    Screen findAllById(Integer id);
}
