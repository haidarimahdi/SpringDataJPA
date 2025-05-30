# TimeSlotJsonControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createTimeSlotJSON**](TimeSlotJsonControllerApi.md#createTimeSlotJSON) | **POST** /timeSlot | 
[**deleteTimeSlotJSON**](TimeSlotJsonControllerApi.md#deleteTimeSlotJSON) | **DELETE** /timeSlot/{id}.json | 
[**getAllTimeSlotsJSONPaginated**](TimeSlotJsonControllerApi.md#getAllTimeSlotsJSONPaginated) | **GET** /timeSlot/list.json | 
[**getProjectTimeSlotsJSONPaginated**](TimeSlotJsonControllerApi.md#getProjectTimeSlotsJSONPaginated) | **GET** /timeSlot/project/{projectId}.json | 
[**getTimeSlotDetailJSON**](TimeSlotJsonControllerApi.md#getTimeSlotDetailJSON) | **GET** /timeSlot/{id}.json | 
[**updateTimeSlotJSON**](TimeSlotJsonControllerApi.md#updateTimeSlotJSON) | **POST** /timeSlot/{id}.json | 

<a name="createTimeSlotJSON"></a>
# **createTimeSlotJSON**
> TimeSlotDetailDTO createTimeSlotJSON(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TimeSlotJsonControllerApi;


TimeSlotJsonControllerApi apiInstance = new TimeSlotJsonControllerApi();
TimeSlotDTO body = new TimeSlotDTO(); // TimeSlotDTO | 
try {
    TimeSlotDetailDTO result = apiInstance.createTimeSlotJSON(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TimeSlotJsonControllerApi#createTimeSlotJSON");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**TimeSlotDTO**](TimeSlotDTO.md)|  |

### Return type

[**TimeSlotDetailDTO**](TimeSlotDetailDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteTimeSlotJSON"></a>
# **deleteTimeSlotJSON**
> deleteTimeSlotJSON(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TimeSlotJsonControllerApi;


TimeSlotJsonControllerApi apiInstance = new TimeSlotJsonControllerApi();
Integer id = 56; // Integer | 
try {
    apiInstance.deleteTimeSlotJSON(id);
} catch (ApiException e) {
    System.err.println("Exception when calling TimeSlotJsonControllerApi#deleteTimeSlotJSON");
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

<a name="getAllTimeSlotsJSONPaginated"></a>
# **getAllTimeSlotsJSONPaginated**
> PageTimeSlot getAllTimeSlotsJSONPaginated(pageable)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TimeSlotJsonControllerApi;


TimeSlotJsonControllerApi apiInstance = new TimeSlotJsonControllerApi();
Pageable pageable = new Pageable(); // Pageable | 
try {
    PageTimeSlot result = apiInstance.getAllTimeSlotsJSONPaginated(pageable);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TimeSlotJsonControllerApi#getAllTimeSlotsJSONPaginated");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pageable** | [**Pageable**](.md)|  |

### Return type

[**PageTimeSlot**](PageTimeSlot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProjectTimeSlotsJSONPaginated"></a>
# **getProjectTimeSlotsJSONPaginated**
> PageTimeSlot getProjectTimeSlotsJSONPaginated(projectId, pageable)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TimeSlotJsonControllerApi;


TimeSlotJsonControllerApi apiInstance = new TimeSlotJsonControllerApi();
Integer projectId = 56; // Integer | 
Pageable pageable = new Pageable(); // Pageable | 
try {
    PageTimeSlot result = apiInstance.getProjectTimeSlotsJSONPaginated(projectId, pageable);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TimeSlotJsonControllerApi#getProjectTimeSlotsJSONPaginated");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectId** | **Integer**|  |
 **pageable** | [**Pageable**](.md)|  |

### Return type

[**PageTimeSlot**](PageTimeSlot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTimeSlotDetailJSON"></a>
# **getTimeSlotDetailJSON**
> TimeSlotDetailDTO getTimeSlotDetailJSON(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TimeSlotJsonControllerApi;


TimeSlotJsonControllerApi apiInstance = new TimeSlotJsonControllerApi();
Integer id = 56; // Integer | 
try {
    TimeSlotDetailDTO result = apiInstance.getTimeSlotDetailJSON(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TimeSlotJsonControllerApi#getTimeSlotDetailJSON");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  |

### Return type

[**TimeSlotDetailDTO**](TimeSlotDetailDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateTimeSlotJSON"></a>
# **updateTimeSlotJSON**
> TimeSlotDetailDTO updateTimeSlotJSON(body, id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.TimeSlotJsonControllerApi;


TimeSlotJsonControllerApi apiInstance = new TimeSlotJsonControllerApi();
TimeSlotDTO body = new TimeSlotDTO(); // TimeSlotDTO | 
Integer id = 56; // Integer | 
try {
    TimeSlotDetailDTO result = apiInstance.updateTimeSlotJSON(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TimeSlotJsonControllerApi#updateTimeSlotJSON");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**TimeSlotDTO**](TimeSlotDTO.md)|  |
 **id** | **Integer**|  |

### Return type

[**TimeSlotDetailDTO**](TimeSlotDetailDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

