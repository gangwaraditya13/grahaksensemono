package com.adish.grahaksensemono.Admin.controller;

import com.adish.grahaksensemono.seller.service.SellerService;
import com.adish.grahaksensemono.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    private SellerService sellerService;

    public AdminController(UserService userService, SellerService sellerService) {
        this.userService = userService;
        this.sellerService = sellerService;
    }


}
