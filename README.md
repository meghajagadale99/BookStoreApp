Book-Store Application (back-end)
Book Store App is having 4 models
User Model
Book Model
Cart Model
Order Model

Model 1 : UserRegistration Service
User Model→
Id - Long ✔️
First name - String ✔️
Last name - String ✔️
Kyc - String ✔️
Dob - LocalDate ✔️
Registered date - LocalDate ✔️
updated date - LocalDate ✔️
Password - String ✔️ (passwordencoder)
Email Id - String ✔️
Boolean Verify ✔️
Otp-6 digit
Purchase Date - LocalDate ✔️
Expiry Date - LocalDate ✔️

API List -
Section-1
Register API
CRUD operations = /add, /read or retrieve, /update/:id,  /delete/:id
Login API = /login

Section-2
Verify-token = /user/verify/@pathVeribale token
resetPassword(token,new password) /
forgetPassword(@RequestParam String emailId)
Send otp(optional)
Verify otp(optional)

Section-3
Purchase→(token)
Expiry -->email your subscription is ending


Model 2: Book Service (token as Requestheader)
Book Model
Id
Book Name
Book Author
Bookdescription
Book Logo-MultiPart
Book Price
Book Quantity

API List -
CRUD operations
ChangeBookQuanity(token,bookid,quantity)
ChangeBookPrice(token,bookid,price)


Model 3: CartService
Cart Model
Id - Long
User_id  - long
Book_id - long
Quantity - long
TotalPrice - long

API List -
Addtocart
Remove From Cart (cartId)
update quantity(token,cartid,quantity)
getallcartitemsforuser(token)
getallcartitems()


Model 4: OrderService
Order Model
Id
Orderdate
Price
Quantity
Address
Userid
Bookid
cancel(boolean)

API List -
Placeorder(token)
cancel order(token,orderid)-boolean cancel as true
Getallorders-(boolean cancel as false)
getallordersforuser-(token)

