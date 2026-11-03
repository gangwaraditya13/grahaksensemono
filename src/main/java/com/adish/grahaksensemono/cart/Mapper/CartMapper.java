package com.adish.grahaksensemono.cart.Mapper;

import com.adish.grahaksensemono.cart.Dto.CartRequestDto;
import com.adish.grahaksensemono.cart.Dto.CartResponseDto;
import com.adish.grahaksensemono.cart.entity.Cart;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CartMapper {
    public static Cart cartRequestDtoToCart(CartRequestDto cartRequestDto){
        Cart cart = new Cart();
//        cart.setUserId(cartRequestDto.getUserId());
        cart.setQuantity(cartRequestDto.getQuantity());
        cart.setProductId(cartRequestDto.getProductId());
        cart.setPriceSnapshot(cartRequestDto.getPriceSnapshot());
        cart.setAddedAt(LocalDateTime.now());
        return cart;
    }

    public static CartResponseDto cartToCartResponcetDto(Cart cart){
        CartResponseDto cartResponseDto = new CartResponseDto();

        cartResponseDto.setUserId(cart.getUserId());
        cartResponseDto.setProductId(cart.getProductId());
        cartResponseDto.setQuantity(cart.getQuantity());
        cartResponseDto.setPriceSnapshot(cart.getPriceSnapshot());

        return cartResponseDto;
    }

}
