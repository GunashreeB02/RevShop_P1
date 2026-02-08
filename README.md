# RevShop_P1

RevShop is a **Java console-based e-commerce application** for buyers and sellers, built using **layered architecture** and **Oracle Database**.  
It supports **user registration, login, product management, order workflows, cart, favourites, reviews, and notifications**.

---

## Features

- Buyer & Seller registration and login
- Product CRUD for sellers
- Browsing products by categories
- Adding products to cart and favourites
- Placing orders and managing order addresses
- Writing product reviews (once per order)
- Seller notifications
- Payment tracking (COD, UPI, CARD)

---

## Database ER Diagram (Simplified)

erDiagram
USERS {
    user_id
    email
    role
}
BUYER_DETAILS {
    buyer_id
    full_name
    phone
}
SELLER_DETAILS {
    seller_id
    business_name
    gst_number
}
CATEGORIES {
    category_id
    category_name
}
PRODUCT {
    product_id
    seller_id
    product_name
    mrp
    selling_price
    category_id
}
ORDERS {
    order_id
    buyer_id
    total_amount
}
ORDER_ITEMS {
    order_item_id
    order_id
    product_id
    quantity
}
REVIEWS {
    review_id
    product_id
    buyer_id
    rating
}
CART {
    cart_id
    buyer_id
}
CART_ITEMS {
    cart_item_id
    cart_id
    product_id
}
FAVOURITES {
    favourite_id
    buyer_id
    product_id
}
NOTIFICATIONS_SELLER {
    notification_id
    seller_id
    notification_message
}

USERS ||--|| BUYER_DETAILS : has
USERS ||--|| SELLER_DETAILS : has
USERS ||--o{ ORDERS : places
USERS ||--o{ REVIEWS : writes
USERS ||--|| CART : owns
USERS ||--o{ FAVOURITES : adds
USERS ||--o{ NOTIFICATIONS_SELLER : receives

CATEGORIES ||--o{ PRODUCT : contains

PRODUCT ||--o{ REVIEWS : gets
PRODUCT ||--o{ ORDER_ITEMS : appears_in
PRODUCT ||--o{ CART_ITEMS : added_to
PRODUCT ||--o{ FAVOURITES : liked_in

ORDERS ||--o{ ORDER_ITEMS : includes
