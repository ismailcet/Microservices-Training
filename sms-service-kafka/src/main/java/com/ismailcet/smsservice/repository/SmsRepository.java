package com.ismailcet.smsservice.repository;

import com.ismailcet.smsservice.entity.Sms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsRepository extends JpaRepository<Sms, Integer> {
}
