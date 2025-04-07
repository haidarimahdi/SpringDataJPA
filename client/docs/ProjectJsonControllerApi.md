# ProjectJsonControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createProjectJSON**](ProjectJsonControllerApi.md#createProjectJSON) | **POST** /project | 
[**deleteProjectJSON**](ProjectJsonControllerApi.md#deleteProjectJSON) | **DELETE** /project/{id}.json | 
[**getAllProjectsJSON**](ProjectJsonControllerApi.md#getAllProjectsJSON) | **GET** /project/list.json | 
[**getProjectDetailJSON**](ProjectJsonControllerApi.md#getProjectDetailJSON) | **GET** /project/{id}.json | 
[**updateProjectJSON**](ProjectJsonControllerApi.md#updateProjectJSON) | **POST** /project/{id}.json | 

<a name="createProjectJSON"></a>
# **createProjectJSON**
> ProjectDetailDTO createProjectJSON(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProjectJsonControllerApi;


ProjectJsonControllerApi apiInstance = new ProjectJsonControllerApi();
ProjectDTO body = new ProjectDTO(); // ProjectDTO | 
try {
    ProjectDetailDTO result = apiInstance.createProjectJSON(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectJsonControllerApi#createProjectJSON");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProjectDTO**](ProjectDTO.md)|  |

### Return type

[**ProjectDetailDTO**](ProjectDetailDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteProjectJSON"></a>
# **deleteProjectJSON**
> deleteProjectJSON(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProjectJsonControllerApi;


ProjectJsonControllerApi apiInstance = new ProjectJsonControllerApi();
Integer id = 56; // Integer | 
try {
    apiInstance.deleteProjectJSON(id);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectJsonControllerApi#deleteProjectJSON");
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

<a name="getAllProjectsJSON"></a>
# **getAllProjectsJSON**
> List&lt;ProjectListDTO&gt; getAllProjectsJSON()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProjectJsonControllerApi;


ProjectJsonControllerApi apiInstance = new ProjectJsonControllerApi();
try {
    List<ProjectListDTO> result = apiInstance.getAllProjectsJSON();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectJsonControllerApi#getAllProjectsJSON");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ProjectListDTO&gt;**](ProjectListDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProjectDetailJSON"></a>
# **getProjectDetailJSON**
> ProjectDetailDTO getProjectDetailJSON(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProjectJsonControllerApi;


ProjectJsonControllerApi apiInstance = new ProjectJsonControllerApi();
Integer id = 56; // Integer | 
try {
    ProjectDetailDTO result = apiInstance.getProjectDetailJSON(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectJsonControllerApi#getProjectDetailJSON");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  |

### Return type

[**ProjectDetailDTO**](ProjectDetailDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateProjectJSON"></a>
# **updateProjectJSON**
> ProjectDetailDTO updateProjectJSON(body, id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.ProjectJsonControllerApi;


ProjectJsonControllerApi apiInstance = new ProjectJsonControllerApi();
ProjectDTO body = new ProjectDTO(); // ProjectDTO | 
Integer id = 56; // Integer | 
try {
    ProjectDetailDTO result = apiInstance.updateProjectJSON(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectJsonControllerApi#updateProjectJSON");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProjectDTO**](ProjectDTO.md)|  |
 **id** | **Integer**|  |

### Return type

[**ProjectDetailDTO**](ProjectDetailDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

