# Common Utilities JAR

## Introduction
This is a Java Maven Project for utility functions that can be used for different Spring Boot microservices.

```
common-utilities/src/main/java/com/custom/common/utilities
├── constants
│   ├── Constants.java
│   ├── FailureConstants.java
│   └── SuccessConstants.java
├── convertors
│   └── TypeConversionUtils.java
├── encryption
│   ├── AESUtils.java
│   └── RSAUtils.java
├── exception
│   ├── CommonException.java
│   └── GlobalExceptionHandler.java
├── filters
│   └── RequestResponseLoggingFilter.java
├── httpclient
│   ├── CommonHttpFunctions.java
│   └── HttpUtils.java
├── response
│   ├── CommonResponse.java
│   ├── RequestResponse.java
│   ├── ResponseHelper.java
│   ├── Status.java
│   └── WebserviceResponse.java
└── validators
    ├── FileValidator.java
    ├── SafeInput.java
    └── SafeInputValidator.java
```
