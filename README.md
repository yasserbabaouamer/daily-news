## Daily News

Your daily dose of news at your fingertips.

## Project Description

Daily News is a mobile app that delivers the latest global headlines and stories in real-time. With
a personalized news feed and articles from trusted sources, it offers an easy-to-use interface for
staying informed on the go.

## Table of content

- [Features](#1-features)
- [Technologies used](#2-technologies-used)
- [Screenshots](#3-screenshots)
- [Reflection](#4-reflection)
- [Project Status](#5-project-status)
- [Acknowledgements](#6-acknowledgement)
- [Contact](#7-contact-information)

## 1. Features

- Fetch news and top headlines from trusted sources
- Ability to save your favorite news
- Login and Signup with multiple methods: Email, Password, Google, and Facebook
- Manage your saved favorites
- Manage your account information

## 2. Technologies Used

- **Mobile App**: Kotlin, Retrofit, XML
- **Backend**: Firebase
- **Authentication**: OAuth2, Email & Password

## 3. Screenshots

<img id="loading" src="https://drive.usercontent.google.com/download?id=1gwCn0fmWrCC1FGxl9pbdlXhduxxQ84Ff" width="32%"></img>
<img id="home" src="https://drive.usercontent.google.com/download?id=1h9-V1ilPy7t5mL4ZW4jrMU42_GJbeiTZ" width="32%"></img> 
<img id="favorites" src="https://drive.usercontent.google.com/download?id=1g_tqUHuK96TCVavwOAkk5ej4Cy3fM3Ws" width="32%"></img> 
<img id="login" src="https://drive.usercontent.google.com/download?id=1hL_GEy4DucZhQALSFkCV0-LCSMqQVUzC" width="32%"></img> 
<img id="signup" src="https://drive.usercontent.google.com/download?id=1hKH05FAhLaVDfBu7ag0Cg71Pw9P_AsJd" width="32%"></img> 
<img id="profile" src="https://drive.usercontent.google.com/download?id=1hD7frYHlWPAL4bzFc40-kP0UWfSW6oCp" width="32%"></img> 

## 4. Reflection

Daily News is a mobile application developed as part of a personal project. The main goals of this project were to:

- **Integrate a reliable news API** to provide users with up-to-date headlines and stories from trusted sources.
- **Enable users to save and manage their favorite news** on Firebase, offering a personalized reading experience.
- **Implement user authentication** with flexible options, including Email, Password, Google, and Facebook login.
- **Allow users to manage their accounts** and customize their settings easily.

## Challenges Faced

### Implementing user authentication with several SSO providers

Integrating multiple SSO providers like Google and Facebook required configuring different SDKs and handling unique authentication flows. Ensuring smooth transitions between login methods while maintaining consistent user sessions was a key challenge.

### Ensuring consistency between the API and Firebase storage

Synchronizing news from the API with user-saved favorites in Firestore required careful handling of real-time updates. Managing data integrity, especially during network issues, was essential for a consistent user experience.

## 5. Project Status

**alpha version:** This project is currently in the alpha stage of development. we are planning to add new features in the future.

## 6. Acknowledgement

Thanks to all the open-source libraries used in this project.

## 7. Contact Information

For any inquiries, please contact me at [[yacerbaba10@gmail.com](mailto:yacerbaba10@gmail.com)].