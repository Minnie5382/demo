package com.example.demo.src.shop;

import com.example.demo.config.BaseException;
import com.example.demo.src.shop.ShopDao;
import com.example.demo.src.shop.ShopProvider;
import com.example.demo.src.shop.model.*;
import com.example.demo.src.shop.ShopDao;
import com.example.demo.src.shop.ShopProvider;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class ShopService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ShopDao shopDao;
    private final ShopProvider shopProvider;
    private final JwtService jwtService;

    @Autowired
    public ShopService(ShopDao shopDao, ShopProvider shopProvider, JwtService jwtService) {
        this.shopDao = shopDao;
        this.shopProvider = shopProvider;
        this.jwtService = jwtService;

    }



}

