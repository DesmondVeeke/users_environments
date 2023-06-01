package coop.user.environment.userenvironment.Interfaces;

import coop.user.environment.userenvironment.Entities.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
}
