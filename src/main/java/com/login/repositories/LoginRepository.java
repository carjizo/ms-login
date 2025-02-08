package com.login.repositories;

import com.login.entities.Login;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {

    Optional<Login> findByIdDocument(String idDocument);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE login SET password=?2 WHERE id_document=?1")
    void updatePassword(String idDocument, String password);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE login SET role=?2, status=?3 WHERE id_document=?1")
    void update(String idDocument, String role, boolean status);
}
