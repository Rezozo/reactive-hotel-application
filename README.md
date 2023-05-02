# Reactive-hotel-application🚀

<br/>

## File Tree Diagram<br/>

```
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───hotel
│   │   │           └───app
│   │   │               │   ReactiveHotelApplication.java
│   │   │               │
│   │   │               ├───config
│   │   │               │   │   AuthenticationManager.java
│   │   │               │   │   SecurityConfig.java
│   │   │               │   │   SecurityContext.java
│   │   │               │   │
│   │   │               │   ├───request
│   │   │               │   │       AuthenticationRequest.java
│   │   │               │   │       RegisterRequest.java
│   │   │               │   │
│   │   │               │   └───response
│   │   │               │           AuthenticationResponse.java
│   │   │               │
│   │   │               ├───controller
│   │   │               │       AuthController.java
│   │   │               │       HomeController.java
│   │   │               │
│   │   │               ├───dto
│   │   │               │       BookingInfoDto.java
│   │   │               │       ReviewInfoDto.java
│   │   │               │       RoomInfoDto.java
│   │   │               │
│   │   │               ├───enums
│   │   │               │       Role.java
│   │   │               │
│   │   │               ├───exceptions
│   │   │               │       ArrivalException.java
│   │   │               │       DatePastException.java
│   │   │               │       MaxPeriodException.java
│   │   │               │       ReviewExistException.java
│   │   │               │       RoomOccupiedException.java
│   │   │               │
│   │   │               ├───handler
│   │   │               │       Handler.java
│   │   │               │
│   │   │               ├───kafka
│   │   │               │   │   MessageRequest.java
│   │   │               │   │
│   │   │               │   ├───config
│   │   │               │   │       KafkaConsumerConfig.java
│   │   │               │   │       KafkaProducerConfig.java
│   │   │               │   │       KafkaTopicConfig.java
│   │   │               │   │
│   │   │               │   └───service
│   │   │               │           KafkaConsumerService.java
│   │   │               │           KafkaProducerService.java
│   │   │               │
│   │   │               ├───models
│   │   │               │       Booking.java
│   │   │               │       Customer.java
│   │   │               │       Review.java
│   │   │               │       Room.java
│   │   │               │       RoomType.java
│   │   │               │       Users.java
│   │   │               │
│   │   │               ├───repository
│   │   │               │       BookingRepository.java
│   │   │               │       CustomerRepository.java
│   │   │               │       ReviewRepository.java
│   │   │               │       RoomRepository.java
│   │   │               │       RoomTypeRepository.java
│   │   │               │       UsersRepository.java
│   │   │               │
│   │   │               ├───service
│   │   │               │   │   RoomService.java
│   │   │               │   │   RoomTypeService.java
│   │   │               │   │   UsersService.java
│   │   │               │   │
│   │   │               │   └───impl
│   │   │               │           RoomServiceImpl.java
│   │   │               │           RoomTypeServiceImpl.java
│   │   │               │           UsersServiceImpl.java
│   │   │               │
│   │   │               └───validate
│   │   │                   │   EmailValidator.java
│   │   │                   │   PhoneNumberValidator.java
│   │   │                   │
│   │   │                   └───impl
│   │   │                           EmailValidatorImpl.java
│   │   │                           PhoneNumberValidatorImpl.java
│   │   │
│   │   ├───kotlin
│   │   │   └───com
│   │   │       └───hotel
│   │   │           └───app
│   │   │               ├───config
│   │   │               │       BookingConfig.kt
│   │   │               │       CustomerConfig.kt
│   │   │               │       ReviewConfig.kt
│   │   │               │       UsersConfig.kt
│   │   │               │
│   │   │               ├───controller
│   │   │               │       AdminController.kt
│   │   │               │       BookingController.kt
│   │   │               │       ManagerController.kt
│   │   │               │       ProfileController.kt
│   │   │               │       ReviewController.kt
│   │   │               │
│   │   │               ├───fcm
│   │   │               │   ├───config
│   │   │               │   │       FCMInitializer.kt
│   │   │               │   │
│   │   │               │   ├───models
│   │   │               │   │       PushRequest.kt
│   │   │               │   │       PushResponse.kt
│   │   │               │   │
│   │   │               │   └───service
│   │   │               │           FCMService.kt
│   │   │               │           PushService.kt
│   │   │               │
│   │   │               ├───service
│   │   │               │   │   BookingService.kt
│   │   │               │   │   CustomerService.kt
│   │   │               │   │   JwtService.kt
│   │   │               │   │   ReviewService.kt
│   │   │               │   │
│   │   │               │   └───impl
│   │   │               │           AuthenticationServiceImpl.kt
│   │   │               │           BookingServiceImpl.kt
│   │   │               │           CustomerServiceImpl.kt
│   │   │               │           JwtServiceImpl.kt
│   │   │               │           ReviewServiceImpl.kt
│   │   │               │
│   │   │               └───validate
│   │   │                       BookingValidate.kt
│   │   │
│   │   └───resources
│   │           application.properties
│   │
│   └───test
│       └───java
│           └───com
│               └───hotel
│                   └───app
│                       └───reactivehotelapplication
│                           │   ReactiveHotelApplicationTests.java
│                           │
│                           ├───controller
│                           │       AuthControllerTests.java
│                           │       BookingControllerTests.java
│                           │       HomeControllerTests.java
│                           │       ProfileControllerTests.java
│                           │       ReviewControllerTests.java
│                           │
│                           └───validate
│                                   BookingValidateTest.java
│                                   RegisterValidateTest.java

```
