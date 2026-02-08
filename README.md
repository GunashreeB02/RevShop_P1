# RevShop_P1
RevShop is a Java console-based e-commerce application for buyers and sellers, built using layered architecture and Oracle Database, supporting registration, login, product management, and order workflows.

## Database ER Diagram

```mermaid
erDiagram

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
```
