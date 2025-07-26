package com.example.myfurniture.service;


import com.example.myfurniture.dto.request.VoucherRequest;
import com.example.myfurniture.entity.Voucher;
import com.example.myfurniture.repository.VoucherRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    @Autowired
    VoucherRepository voucherRepository;

    public Voucher creatVoucher(@Valid VoucherRequest req) {
        Voucher voucher = new Voucher();
        voucher.setCode(req.getCode());
        voucher.setDescription(req.getDescription());
        voucher.setQuantity(req.getQuantity());
        voucher.setDiscount(req.getDiscount());
        return voucherRepository.save(voucher);
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    public Voucher updateVoucher(long id, VoucherRequest req) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow();
        voucher.setCode(req.getCode());
        voucher.setDescription(req.getDescription());
        voucher.setQuantity(req.getQuantity());
        return voucherRepository.save(voucher);
    }

    public void deleteVoucher(long id) {
        voucherRepository.deleteById(id);
    }

    public Voucher getVoucherByCode(String code) {
        return voucherRepository.findDistinctByCode(code);
    }
}
