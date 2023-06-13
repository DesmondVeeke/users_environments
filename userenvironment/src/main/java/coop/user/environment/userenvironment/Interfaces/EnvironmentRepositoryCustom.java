package coop.user.environment.userenvironment.Interfaces;

import coop.user.environment.userenvironment.Entities.Environment;
import coop.user.environment.userenvironment.Entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvironmentRepositoryCustom extends EnvironmentRepository{
    List<Environment> findByParticipantsContaining(User user);

    List<Environment> findByOwner_Id(long ownerId);
}
