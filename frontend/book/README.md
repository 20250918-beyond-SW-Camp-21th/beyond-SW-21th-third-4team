# MyPage Frontend

Simple HTML/CSS/JavaScript frontend for testing the MyPage API endpoints.

> **Note:** This project includes both a React/Vite frontend and a standalone HTML page (`mypage.html`). The standalone page can be used without any build setup - just open it in your browser!

## Features

1. **User Authentication**
   - Login form with JWT token storage
   - Automatic token persistence using localStorage
   - Logout functionality

2. **User Information Display**
   - Shows all user data from UserDTO:
     - Login ID
     - Name
     - Phone
     - Email
     - Birth Date
     - Address

3. **Order History**
   - Display all orders with detailed information
   - Filter buttons to toggle between:
     - All Orders (no filter)
     - Active Orders (PAYMENT_COMPLETED, PREPARING, SHIPPING)
     - Completed Orders (DELIVERED)
     - Cancelled Orders (CANCELLED)
   - Shows order details including:
     - Order ID
     - Status
     - Order date/time
     - Total price
     - Receiver information
     - Delivery address
     - Order items list

## How to Use

### 1. Start Your Backend Server

Make sure your Spring Boot backend is running:

```bash
cd backend
./gradlew bootRun
```

The backend should be running on `http://localhost:8080`

### 2. Open the Frontend

Simply open the HTML file in your browser:

```bash
# Option 1: Double-click the file
open frontend/mypage.html

# Option 2: Open from command line (macOS)
open frontend/mypage.html

# Option 3: Open from command line (Linux)
xdg-open frontend/mypage.html

# Option 4: Open from command line (Windows)
start frontend/mypage.html
```

Or drag and drop `frontend/mypage.html` into your browser.

### 3. Login

1. Enter your login credentials (loginId and password)
2. Click "로그인" button
3. The JWT token will be saved automatically

### 4. View Your Information

Once logged in, you'll see:
- Your user information in the "내 정보" card
- Your order history in the "주문 내역" card

### 5. Filter Orders

Click the filter buttons to see different order statuses:
- **전체 주문**: Shows all orders
- **진행중 주문**: Shows active orders (payment completed, preparing, shipping)
- **완료된 주문**: Shows delivered orders
- **취소된 주문**: Shows cancelled orders

## API Endpoints Used

- `POST /api/v1/auth/login` - User login
- `GET /api/v1/mypage/user-info` - Get user information
- `GET /api/v1/mypage/orders` - Get all orders
- `GET /api/v1/mypage/orders?status=active` - Get active orders
- `GET /api/v1/mypage/orders?status=completed` - Get completed orders
- `GET /api/v1/mypage/orders?status=cancelled` - Get cancelled orders

## Configuration

If your backend is running on a different port or host, update the `API_BASE_URL` in the HTML file:

```javascript
const API_BASE_URL = 'http://localhost:8080/api/v1';
```

## CORS Configuration

✅ **CORS is already configured!** The backend `SecurityConfig.java` has been updated to allow cross-origin requests.

If you encounter CORS errors, check:
1. Backend is running on `http://localhost:8080`
2. Browser console for specific error messages
3. The CORS configuration in `backend/src/main/java/com/insilenceclone/backend/common/config/SecurityConfig.java`

For a detailed explanation of the CORS changes, see: [`SECURITY_CONFIG_CHANGES.md`](../SECURITY_CONFIG_CHANGES.md)

## Features Implemented

✅ User authentication with JWT
✅ User information display (all fields from UserDTO)
✅ Order history display with all details
✅ Order filtering (all/active/completed/cancelled)
✅ Responsive design
✅ Loading states
✅ Error handling
✅ Token persistence (stays logged in after page refresh)
✅ Professional UI with gradient colors
✅ Mobile-friendly layout

## Browser Compatibility

Works on all modern browsers:
- Chrome/Edge (recommended)
- Firefox
- Safari

## Troubleshooting

### "서버 연결에 실패했습니다"
- Make sure your backend is running on `http://localhost:8080`
- Check if CORS is configured correctly
- Check browser console for detailed error messages

### "로그인에 실패했습니다"
- Verify your credentials are correct
- Check if the user exists in the database
- Verify the backend `/api/v1/auth/login` endpoint is working

### Orders not showing
- Make sure you have orders in the database
- Check if the JWT token is valid
- Verify the `/api/v1/mypage/orders` endpoint is accessible
