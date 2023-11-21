[![Contributors][contributors-shield]][contributors-url]

[![Forks][forks-shield]][forks-url]

[![Stargazers][stars-shield]][stars-url]

[![Issues][issues-shield]][issues-url]

[![MIT License][license-shield]][license-url]

[![LinkedIn][linkedin-shield]][linkedin-url]
# My Cinema
![App logo](https://i.ibb.co/k6GWXVM/370125421-2555136981316951-8906401763159225686-n.png =300x200)

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


## Technologies

-   [Show Image](https://developer.android.com/images/brand/Android_Robot.png)
-   [Show Image](https://www.oracle.com/a/tech/img/cb88-java-logo-001.png)
-   [Show Image](https://www.gstatic.com/mobilesdk/160415_mobilesdk/logo/2x/firebase_28dp.png)
-   [Show Image](https://www.sqlite.org/images/sqlite370_banner.gif)

### Firebase Services

-   [Show Image](https://www.gstatic.com/mobilesdk/160505_mobilesdk/auth_featured_graphic.png)- Authentication
-   [Show Image](https://cloud.google.com/images/products/logos/firestore.svg)- Database
-   [Show Image](https://cloud.google.com/images/products/logos/storage.svg)- File Storage
-   [Show Image](https://cloud.google.com/images/products/logos/cloud-hosting.svg)- Web hosting

## Architecture

[Show Image](https://claude.ai/architecture-diagram.png)


## Getting Started

To run My Cinema on your local machine:

1.  Clone this repository
2.  Open project in Android Studio
3.  Run on mobile emulator or connected physical device

## Contributing

Contributions are welcome! Feel free to open an issue or PR for any bugs or desired new features.
