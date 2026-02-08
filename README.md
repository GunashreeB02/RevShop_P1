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

```mermaid
erDiagram
USERS {
    NUMBER user_id PK
    VARCHAR email UNIQUE
    VARCHAR role
}
BUYER_DETAILS {
    NUMBER buyer_id PK FK->USERS.user_id
    VARCHAR full_name
    VARCHAR phone UNIQUE
}
SELLER_DETAILS {
    NUMBER seller_id PK FK->USERS.user_id
    VARCHAR business_name
    VARCHAR gst_number UNIQUE
}
CATEGORIES {
    NUMBER category_id PK
    VARCHAR category_name
}
PRODUCT {
    NUMBER product_id PK
    NUMBER seller_id FK->USERS.user_id
    VARCHAR product_name
    NUMBER mrp
    NUMBER selling_price
    NUMBER category_id FK->CATEGORIES.category_id
}
ORDERS {
    NUMBER order_id PK
    NUMBER buyer_id FK->USERS.user_id
    NUMBER total_amount
}
ORDER_ITEMS {
    NUMBER order_item_id PK
    NUMBER order_id FK->ORDERS.order_id
    NUMBER product_id FK->PRODUCT.product_id
    NUMBER quantity
}
REVIEWS {
    NUMBER review_id PK
    NUMBER product_id FK->PRODUCT.product_id
    NUMBER buyer_id FK->USERS.user_id
    NUMBER rating
}
CART {
    NUMBER cart_id PK
    NUMBER buyer_id FK->USERS.user_id UNIQUE
}
CART_ITEMS {
    NUMBER cart_item_id PK
    NUMBER cart_id FK->CART.cart_id
    NUMBER product_id FK->PRODUCT.product_id
}
FAVOURITES {
    NUMBER favourite_id PK
    NUMBER buyer_id FK->USERS.user_id
    NUMBER product_id FK->PRODUCT.product_id
}
NOTIFICATIONS_SELLER {
    NUMBER notification_id PK
    NUMBER seller_id FK->USERS.user_id
    VARCHAR notification_message
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
