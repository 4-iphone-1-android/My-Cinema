# My Cinema
![App logo](https://i.ibb.co/k6GWXVM/370125421-2555136981316951-8906401763159225686-n.png)
My Cinema is an Android app for movie lovers built using Java and Firebase. Users can browse movies, book tickets, track favorites, and more.

## Overview


## Features

**Authentication**

The app offers full user authentication with:

-   **Login**  - User login with email and password validation
-   **Registration**  - New user sign up with name, email, password
-   **Forgot Password**  - Password reset functionality via email
-   **Profile Management**
    -   Users can update their name, profile photo, and other account details
    -   Change password option
    -   Delete account permanentlly
-   **Logout**  - Users can securely logout from their profile

**Home Screen**

The home screen displays:

-   Now playing movies loaded from the Firestore database
-   A search bar to find movies by name
-   Personalized recommendations based on favorited genres

**Favorites**

Users can favorite movies to:

-   Save movies to easily find them later
-   Sort favorites by ascending/descending order
-   Used to improve and personalize recommendations

**Movie Details**

In a movie's detail view, users can see additional metadata like:

-   Plot synopsis
-   Runtime
-   User ratings
-   Release date
-   Movie poster image
-   Play movie trailer videos

**Booking**

To book movie tickets:

-   First user selects a date and showtime
-   They can choose seat(s) on an interactive theater map
-   Quantity selector to pick # of tickets
-   Payment handled via integrated Stripe API
-   App generates unique QR code after purchase that serves as the user's ticket

**Booking History**

-   Users can view a complete history of their booked tickets
-   Sort tickets by date or movie title
-   Scan ticket barcode/QR code to redeem at theater

**Genres**

Movies can be browsed by category including:

-   Action
-   Comedy
-   Drama
-   Horror
-   Animation
-   And more genres to easily find films

**Technical**

My Cinema leverages the following tech stack:

-   **Java**  - Primary language
-   **Android Studio**  - Development environment
-   **Firebase**  - Back-end and databases
    -   **Authentication**  for user accounts and security
    -   **Cloud Firestore**  for storing movies, theaters data
    -   **Cloud Storage**  for images, videos
    -   **Realtime Database**  for booking and performance
-   **Zxing API**  - QR code generation

Supports phones and tablets on Android 5.0 and higher. Uses Google Cloud Messaging for push notifications to users.



## Getting Started

To run My Cinema on your local machine:

1.  Clone this repository
2.  Open project in Android Studio
3.  Run on mobile emulator or connected physical device

## Contributing

Contributions are welcome! Feel free to open an issue or PR for any bugs or desired new features.
