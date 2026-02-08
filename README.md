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

## Database ER Diagram

```mermaid
erDiagram
USERS {
    NUMBER user_id PK
    VARCHAR email UNIQUE
    VARCHAR password
    VARCHAR role CHECK(BUYER, SELLER)
    TIMESTAMP created_at
    NUMBER security_question_id FK
    VARCHAR security_answer
}
BUYER_DETAILS {
    NUMBER buyer_id PK FK->USERS.user_id
    VARCHAR full_name
    VARCHAR gender
    DATE date_of_birth
    VARCHAR phone UNIQUE
}
SELLER_DETAILS {
    NUMBER seller_id PK FK->USERS.user_id
    VARCHAR business_name
    VARCHAR gst_number UNIQUE
    VARCHAR address
    VARCHAR phone UNIQUE
}
CATEGORIES {
    NUMBER category_id PK
    VARCHAR category_name
    VARCHAR description
}
PRODUCT {
    NUMBER product_id PK
    NUMBER seller_id FK->USERS.user_id
    VARCHAR product_name
    VARCHAR description
    VARCHAR manufacturer
    NUMBER mrp
    NUMBER selling_price
    NUMBER stock
    NUMBER stock_threshold
    NUMBER category_id FK->CATEGORIES.category_id
    TIMESTAMP created_at
    NUMBER is_active
}
ORDERS {
    NUMBER order_id PK
    NUMBER buyer_id FK->USERS.user_id
    TIMESTAMP order_date
    NUMBER total_amount
    VARCHAR status
}
ORDER_ITEMS {
    NUMBER order_item_id PK
    NUMBER order_id FK->ORDERS.order_id
    NUMBER product_id FK->PRODUCT.product_id
    NUMBER seller_id FK->USERS.user_id
    NUMBER quantity
    NUMBER price
}
ORDER_ADDRESS {
    NUMBER address_id PK
    NUMBER order_id FK->ORDERS.order_id
    VARCHAR address_type CHECK(SHIPPING,BILLING)
    VARCHAR full_name
    VARCHAR phone
    VARCHAR address_line1
    VARCHAR address_line2
    VARCHAR city
    VARCHAR state
    VARCHAR pincode
}
PAYMENT {
    NUMBER payment_id PK
    NUMBER order_id FK->ORDERS.order_id
    VARCHAR payment_method CHECK(COD,UPI,CARD)
    VARCHAR payment_status CHECK(PENDING,SUCCESS,FAILED)
    NUMBER amount
    TIMESTAMP paid_at
}
REVIEWS {
    NUMBER review_id PK
    NUMBER product_id FK->PRODUCT.product_id
    NUMBER buyer_id FK->USERS.user_id
    NUMBER rating CHECK(1..5)
    VARCHAR review_comment
    TIMESTAMP review_date
    NUMBER order_id FK->ORDERS.order_id
    UNIQUE(buyer_id, product_id, order_id)
}
CART {
    NUMBER cart_id PK
    NUMBER buyer_id FK->USERS.user_id UNIQUE
    TIMESTAMP created_at
}
CART_ITEMS {
    NUMBER cart_item_id PK
    NUMBER cart_id FK->CART.cart_id
    NUMBER product_id FK->PRODUCT.product_id
    NUMBER seller_id FK->SELLER_DETAILS.seller_id
    NUMBER quantity
    UNIQUE(cart_id, product_id)
}
FAVOURITES {
    NUMBER favourite_id PK
    NUMBER buyer_id FK->USERS.user_id
    NUMBER product_id FK->PRODUCT.product_id
    TIMESTAMP added_at
    UNIQUE(buyer_id, product_id)
}
NOTIFICATIONS_SELLER {
    NUMBER notification_id PK
    NUMBER seller_id FK->USERS.user_id
    VARCHAR notification_message
    CHAR is_read CHECK(Y,N)
    TIMESTAMP created_at
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
ORDERS ||--o{ ORDER_ADDRESS : has
ORDERS ||--|| PAYMENT : paid_by

CART ||--o{ CART_ITEMS : contains
