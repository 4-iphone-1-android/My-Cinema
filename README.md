<!DOCTYPE html>
<html>


<body class="stackedit">
  <div class="stackedit__html"><!-- BADGES -->
<p><a href="https://github.com/4-iphone-1-android/mid_term_my_cinema/graphs/contributors"><img src="https://img.shields.io/github/contributors/4-iphone-1-android/mid_term_my_cinema.svg?style=for-the-badge" alt="Contributors"></a> <a href="https://github.com/4-iphone-1-android/mid_term_my_cinema/network/members"><img src="https://img.shields.io/github/forks/4-iphone-1-android/mid_term_my_cinema.svg?style=for-the-badge" alt="Forks"></a>  <a href="https://github.com/4-iphone-1-android/mid_term_my_cinema/stargazers"><img src="https://img.shields.io/github/stars/4-iphone-1-android/mid_term_my_cinema.svg?style=for-the-badge" alt="Stargazers"></a> <a href="https://github.com/4-iphone-1-android/mid_term_my_cinema/issues"><img src="https://img.shields.io/github/issues/4-iphone-1-android/mid_term_my_cinema.svg?style=for-the-badge" alt="Issues"></a> <img src="https://img.shields.io/github/license/4-iphone-1-android/mid_term_my_cinema.svg?style=for-the-badge" alt="MIT License"></p>
<!-- Badge Definitions -->
<h1 id="my-cinema">My Cinema</h1>
<p><img src="https://i.ibb.co/k6GWXVM/370125421-2555136981316951-8906401763159225686-n.png" alt="App logo" width="300" height="200"></p>
<h2 id="table-of-contents-">Table of Contents *</h2>
<p><a href="#about-the-project">About the Project</a><br>
<a href="#overview">Overview</a><br>
<a href="#features">Features</a><br>
<a href="#getting-started">Getting-Started</a><br>
<a href="#contributing">Contributing</a><br>
<a href="#license">License</a><br>
<a href="#contact">Contact</a></p>
<h2 id="about-the-project">About The Project</h2>
<p>My Cinema is an Android application for booking movie tickets online. Users can browse films, read descriptions, watch trailers, reserve seats, and buy tickets conveniently from their phone.</p>
<p>Some key features include:</p>
<ul>
<li>Browse new movies by genre or see what’s popular</li>
<li>Read detailed overviews including plots, casts, ratings before deciding</li>
<li>Watch movie trailers and clips right in the app</li>
<li>Receive electronic ticket confirmation to show at theater</li>
<li>Find movies quickly and easily with searching and favorites</li>
</ul>
<p>This app was built to modernize and optimize the movie ticketing experience. No more waiting in long lines at the cinema or dealing with sold out shows when you can plan ahead on your own schedule.</p>
<p>The project aims to demonstrate capabilities in Android development with Java and Google Firebase services. It incorporates authentication, cloud databases, file storage, analytics, and more in a real world application.</p>
<h2 id="overview">Overview</h2>
<h2 id="features">Features</h2>
<p><strong>Authentication</strong></p>
<p>The app offers full user authentication with:</p>
<ul>
<li><strong>Login</strong>  - User login with email and password validation</li>
<li><strong>Registration</strong>  - New user sign up with name, email, password</li>
<li><strong>Forgot Password</strong>  - Password reset functionality via email</li>
<li><strong>Profile Management</strong>
<ul>
<li>Users can update their name, profile photo, and other account details</li>
<li>Change password option</li>
<li>Delete account permanentlly</li>
</ul>
</li>
<li><strong>Logout</strong>  - Users can securely logout from their profile</li>
</ul>
<p><strong>Home Screen</strong></p>
<p>The home screen displays:</p>
<ul>
<li>Now playing movies loaded from the Firestore database</li>
<li>A search bar to find movies by name</li>
<li>Personalized recommendations based on favorited genres</li>
</ul>
<p><strong>Favorites</strong></p>
<p>Users can favorite movies to:</p>
<ul>
<li>Save movies to easily find them later</li>
<li>Sort favorites by ascending/descending order</li>
<li>Used to improve and personalize recommendations</li>
</ul>
<p><strong>Movie Details</strong></p>
<p>In a movie’s detail view, users can see additional metadata like:</p>
<ul>
<li>Plot synopsis</li>
<li>Runtime</li>
<li>User ratings</li>
<li>Release date</li>
<li>Movie poster image</li>
<li>Play movie trailer videos</li>
</ul>
<p><strong>Booking</strong></p>
<p>To book movie tickets:</p>
<ul>
<li>First user selects a date and showtime</li>
<li>They can choose seat(s) on an interactive theater map</li>
<li>Quantity selector to pick # of tickets</li>
<li>Payment handled via integrated Stripe API</li>
<li>App generates unique QR code after purchase that serves as the user’s ticket</li>
</ul>
<p><strong>Booking History</strong></p>
<ul>
<li>Users can view a complete history of their booked tickets</li>
<li>Sort tickets by date or movie title</li>
<li>Scan ticket barcode/QR code to redeem at theater</li>
</ul>
<p><strong>Genres</strong></p>
<p>Movies can be browsed by category including:</p>
<ul>
<li>Action</li>
<li>Comedy</li>
<li>Drama</li>
<li>Horror</li>
<li>Animation</li>
<li>And more genres to easily find films</li>
</ul>
<p><strong>Technical</strong></p>
<p>My Cinema leverages the following tech stack:</p>
<ul>
<li><strong>Java</strong>  - Primary language</li>
<li><strong>Android Studio</strong>  - Development environment</li>
<li><strong>Firebase</strong>  - Back-end and databases
<ul>
<li><strong>Authentication</strong>  for user accounts and security</li>
<li><strong>Cloud Firestore</strong>  for storing movies, theaters data</li>
<li><strong>Cloud Storage</strong>  for images, videos</li>
<li><strong>Realtime Database</strong>  for booking and performance</li>
</ul>
</li>
<li><strong>Zxing API</strong>  - QR code generation</li>
</ul>
<p>Supports phones and tablets on Android 5.0 and higher. Uses Google Cloud Messaging for push notifications to users.</p>
<h2 id="getting-started">Getting Started</h2>
<p>To run My Cinema on your local machine:</p>
<ol>
<li>Clone this repository</li>
<li>Open project in Android Studio</li>
<li>Run on mobile emulator or connected physical device</li>
</ol>
<h2 id="contributing">Contributing</h2>
<p>Contributions are welcome! Feel free to open an issue or PR for any bugs or desired new features.</p>
<h2 id="license">License</h2>
<p>Distributed under the MIT License. See <code>LICENSE.txt</code> for more information.</p>
<h2 id="contact">Contact</h2>
<p>Bảo Toàn - <a href="https://facebook.com/baotoan.trinh3">@Trinh Bao Toan</a> - <a href="mailto:baotoantrinh2002@gmail.com">baotoantrinh2002@gmail.com</a></p>
<p>Duy Tuấn - <a href="https://www.facebook.com/profile.php?id=100004737558512">@Nguyen Duy Tuan</a> - <a href="mailto:tuannguyenduy2@gmail.com">tuannguyenduy2@gmail.com</a></p>
<p>Phúc Viên - <a href="https://www.facebook.com/phamvu.phucvien">@Pham Vu Phuc Vien</a> - <a href="mailto:phamvuphucvien@gmail.com">phamvuphucvien@gmail.com</a></p>
<p>Huyền Trang - <a href="https://www.facebook.com/clara.nguyen.336">@Le Huynh Huyen Trang</a> - <a href="mailto:huynh.anh.nguyen.gj@gmail.com">huynh.anh.nguyen.gj@gmail.com</a></p>
<p>Minh Thành - <a href="https://www.facebook.com/astero1dz">@Nguyen Cong Minh Thanh</a> - <a href="mailto:nhockrockspx@gmail.com">nhockrockspx@gmail.com</a></p>
<p>Project Link: <a href="https://github.com/4-iphone-1-android/mid_term_my_cinema">https://github.com/4-iphone-1-android/mid_term_my_cinema</a></p>
</div>
</body>

</html>
