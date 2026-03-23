package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.User;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface AdminRepository
        extends JpaRepository<User,Long>{

    @Query("""

SELECT new map(

u.id as id,
u.email as email,
u.role as role,
u.enabled as enabled,
u.phoneNumber as phoneNumber

)

FROM User u

""")
    List<Map<String,Object>> getAllUsers();


    @Query("""

SELECT new map(

u.id as id,
u.email as email,
u.role as role,
u.enabled as enabled,
u.phoneNumber as phoneNumber

)

FROM User u

WHERE u.id = :id

""")
    Map<String,Object> getUserById(Long id);

}