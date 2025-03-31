# PersonJsonControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPersonJSON**](PersonJsonControllerApi.md#createPersonJSON) | **POST** /person | 
[**deletePersonJSON**](PersonJsonControllerApi.md#deletePersonJSON) | **DELETE** /person/{id}.json | 
[**getPersonDetailJSON**](PersonJsonControllerApi.md#getPersonDetailJSON) | **GET** /person/{id}.json | 
[**updatePersonJSON**](PersonJsonControllerApi.md#updatePersonJSON) | **POST** /person/{id}.json | 

<a name="createPersonJSON"></a>
# **createPersonJSON**
> PersonDetailDTO createPersonJSON(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PersonJsonControllerApi;


PersonJsonControllerApi apiInstance = new PersonJsonControllerApi();
PersonDTO body = new PersonDTO(); // PersonDTO | 
try {
    PersonDetailDTO result = apiInstance.createPersonJSON(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonJsonControllerApi#createPersonJSON");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PersonDTO**](PersonDTO.md)|  |

### Return type

[**PersonDetailDTO**](PersonDetailDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deletePersonJSON"></a>
# **deletePersonJSON**
> deletePersonJSON(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PersonJsonControllerApi;


PersonJsonControllerApi apiInstance = new PersonJsonControllerApi();
Integer id = 56; // Integer | 
try {
    apiInstance.deletePersonJSON(id);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonJsonControllerApi#deletePersonJSON");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getPersonDetailJSON"></a>
# **getPersonDetailJSON**
> PersonDetailDTO getPersonDetailJSON(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PersonJsonControllerApi;


PersonJsonControllerApi apiInstance = new PersonJsonControllerApi();
Integer id = 56; // Integer | 
try {
    PersonDetailDTO result = apiInstance.getPersonDetailJSON(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonJsonControllerApi#getPersonDetailJSON");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  |

### Return type

[**PersonDetailDTO**](PersonDetailDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updatePersonJSON"></a>
# **updatePersonJSON**
> PersonDetailDTO updatePersonJSON(body, id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PersonJsonControllerApi;


PersonJsonControllerApi apiInstance = new PersonJsonControllerApi();
PersonDTO body = new PersonDTO(); // PersonDTO | 
Integer id = 56; // Integer | 
try {
    PersonDetailDTO result = apiInstance.updatePersonJSON(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PersonJsonControllerApi#updatePersonJSON");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PersonDTO**](PersonDTO.md)|  |
 **id** | **Integer**|  |

### Return type

[**PersonDetailDTO**](PersonDetailDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

