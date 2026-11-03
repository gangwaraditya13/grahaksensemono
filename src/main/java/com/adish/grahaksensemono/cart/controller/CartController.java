package com.adish.grahaksensemono.cart.controller;

import com.adish.grahaksensemono.cart.Dto.CartRequestDto;
import com.adish.grahaksensemono.cart.Dto.CartResponseDto;
import com.adish.grahaksensemono.cart.Dto.UpdateCartRequestDto;
import com.adish.grahaksensemono.cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create")
    public ResponseEntity<CartResponseDto> createCart(@RequestBody CartRequestDto cartRequestDto){
        CartResponseDto cart = cartService.createCart(cartRequestDto);
        if(cart != null){
            return new ResponseEntity<>(cart, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(cart, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartResponseDto>> getCartByUserId(@PathVariable("userId") Long userId){
        List<CartResponseDto> cartByUserId = cartService.getCartByUserId(userId);
        return new ResponseEntity<>(cartByUserId,HttpStatus.OK);
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<CartResponseDto>> getAllCart(){
        List<CartResponseDto> allCarts = cartService.getAllCarts();

        return new ResponseEntity<>(allCarts, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody UpdateCartRequestDto cartRequestDto){
        CartResponseDto cartResponseDto = cartService.updateCart(cartRequestDto);
        if(cartResponseDto != null){
            return new ResponseEntity<>(cartRequestDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable("cartId") Long cartId){
        boolean deleted = cartService.deleteCartById(cartId);

        if(deleted){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
