package com.adish.grahaksensemono.activity.entity.Enums;

public enum Events {

    // Product interaction
    VIEW_PRODUCT,
    CLICK_PRODUCT,
    SEARCH_PRODUCT,
    ADD_TO_CART,
    REMOVE_FROM_CART,

    // Purchase journey
    BEGIN_CHECKOUT,
    PURCHASE,
    CANCEL_PURCHASE,
    REFUND,

    // User engagement
    LOGIN,
    LOGOUT,
    SIGN_UP,

    // Category / seller interaction
    VIEW_CATEGORY,
    VIEW_SELLER,

    // Recommendation interaction
    VIEW_RECOMMENDATION,
    CLICK_RECOMMENDATION
}
