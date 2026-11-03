package com.adish.grahaksensemono.cart.service.Impl;

import com.adish.grahaksensemono.cart.Dto.CartRequestDto;
import com.adish.grahaksensemono.cart.Dto.CartResponseDto;
import com.adish.grahaksensemono.cart.Dto.UpdateCartRequestDto;
import com.adish.grahaksensemono.cart.Mapper.CartMapper;
import com.adish.grahaksensemono.cart.entity.Cart;
import com.adish.grahaksensemono.cart.repository.CartRepository;
import com.adish.grahaksensemono.cart.service.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    private CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper){
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }


    @Override
    public CartResponseDto createCart(CartRequestDto cartRequestDto) {

        try {
            Cart cart = cartMapper.cartRequestDtoToCart(cartRequestDto);
            Cart save = cartRepository.save(cart);
            CartResponseDto cartRequestDto1 = cartMapper.cartToCartResponcetDto(save);
            return cartRequestDto1;
        }catch (Exception e){
            throw new RuntimeException("Cart not created: "+e.getMessage());
        }
    }

    @Override
    public CartResponseDto updateCart(UpdateCartRequestDto cartRequestDto) {
        boolean update=false;

        boolean cartCheck = cartRepository.existsById(cartRequestDto.getId());

        if(cartCheck){
            Cart cart = cartRepository.findById(cartRequestDto.getId()).orElseThrow();

            if(cartRequestDto.getQuantity() != 0
                    && !(cartRequestDto.getQuantity() < 0)
                    && cartRequestDto.getQuantity() != cart.getQuantity()){
                cart.setQuantity(cartRequestDto.getQuantity());
                update=true;
            }

            if(cartRequestDto.getPriceSnapshot().compareTo(BigDecimal.ZERO) != 0
                    && !(cartRequestDto.getPriceSnapshot().compareTo(BigDecimal.ZERO) < 0)
                    && cartRequestDto.getPriceSnapshot().compareTo(cart.getPriceSnapshot()) != 0){
                cart.setPriceSnapshot(cartRequestDto.getPriceSnapshot());
                update=true;
            }

            if(update){
                Cart save = cartRepository.save(cart);
                CartResponseDto cartResponseDto = cartMapper.cartToCartResponcetDto(save);
                return cartResponseDto;
            }
        }

        return null;
    }

    @Override
    public List<CartResponseDto> getCartByUserId(Long userId) {

        List<Cart> byUserId = cartRepository.findByUserId(userId);

        List<CartResponseDto> cartResponseDtoList = byUserId.stream()
                .map(cart -> cartMapper.cartToCartResponcetDto(cart)).collect(Collectors.toList());

        return cartResponseDtoList;
    }

    @Override
    public List<CartResponseDto> getAllCarts() {
        List<CartResponseDto> cartResponseDtoList = cartRepository.findAll().stream().map(cart -> cartMapper.cartToCartResponcetDto(cart)).collect(Collectors.toList());
        return cartResponseDtoList;
    }

    @Override
    public boolean deleteCartById(Long cartId) {
        if(cartRepository.existsById(cartId)){
            cartRepository.deleteById(cartId);
            return true;
        }
        return false;
    }
}
