package com.travel.traveladmin.Repository;

import com.travel.traveladmin.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
}
