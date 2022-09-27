package com.bridgelabz.BookStoreApp.Repository;

import com.bridgelabz.BookStoreApp.Model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRegistrationRepository extends JpaRepository<UserData, Long> {
    @Query(value = "select * from user_registration where email=:email", nativeQuery = true)
    UserData findUserDataByEmail(String email);
    @Query(value = "select * from user_registration where email=:email", nativeQuery = true)
    Optional<UserData> findUserDataByEmailId(String email);

}