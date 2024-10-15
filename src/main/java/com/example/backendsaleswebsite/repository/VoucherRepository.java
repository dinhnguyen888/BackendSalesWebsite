package com.example.backendsaleswebsite.repository;

import com.example.backendsaleswebsite.model.Voucher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long>{
	
	Optional<Voucher> findByVoucherId(Long voucherId);

}
