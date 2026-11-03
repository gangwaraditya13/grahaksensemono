package com.adish.grahaksensemono.cart.service;

import com.adish.grahaksensemono.cart.Dto.CartRequestDto;
import com.adish.grahaksensemono.cart.Dto.CartResponseDto;
import com.adish.grahaksensemono.cart.Dto.UpdateCartRequestDto;

import java.util.List;

public interface CartService {

    CartResponseDto createCart(CartRequestDto cartRequestDto);

    CartResponseDto updateCart(UpdateCartRequestDto cartRequestDto);

    List<CartResponseDto> getCartByUserId(Long userId);

    List<CartResponseDto> getAllCarts();

    boolean deleteCartById(Long CartId);

}
