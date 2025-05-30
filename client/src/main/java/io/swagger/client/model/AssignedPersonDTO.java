/*
 * OpenAPI definition
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.client.model.TimeSlotDetailDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * AssignedPersonDTO
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-04-07T21:28:19.344429018Z[GMT]")

public class AssignedPersonDTO {
  @SerializedName("personId")
  private Integer personId = null;

  @SerializedName("firstName")
  private String firstName = null;

  @SerializedName("lastName")
  private String lastName = null;

  @SerializedName("projectTimeSlots")
  private List<TimeSlotDetailDTO> projectTimeSlots = null;

  @SerializedName("totalHoursBooked")
  private Double totalHoursBooked = null;

  @SerializedName("percentageOfTotalHours")
  private Double percentageOfTotalHours = null;

  public AssignedPersonDTO personId(Integer personId) {
    this.personId = personId;
    return this;
  }

   /**
   * Get personId
   * @return personId
  **/
  @Schema(description = "")
  public Integer getPersonId() {
    return personId;
  }

  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

  public AssignedPersonDTO firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

   /**
   * Get firstName
   * @return firstName
  **/
  @Schema(description = "")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public AssignedPersonDTO lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

   /**
   * Get lastName
   * @return lastName
  **/
  @Schema(description = "")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public AssignedPersonDTO projectTimeSlots(List<TimeSlotDetailDTO> projectTimeSlots) {
    this.projectTimeSlots = projectTimeSlots;
    return this;
  }

  public AssignedPersonDTO addProjectTimeSlotsItem(TimeSlotDetailDTO projectTimeSlotsItem) {
    if (this.projectTimeSlots == null) {
      this.projectTimeSlots = new ArrayList<TimeSlotDetailDTO>();
    }
    this.projectTimeSlots.add(projectTimeSlotsItem);
    return this;
  }

   /**
   * Get projectTimeSlots
   * @return projectTimeSlots
  **/
  @Schema(description = "")
  public List<TimeSlotDetailDTO> getProjectTimeSlots() {
    return projectTimeSlots;
  }

  public void setProjectTimeSlots(List<TimeSlotDetailDTO> projectTimeSlots) {
    this.projectTimeSlots = projectTimeSlots;
  }

  public AssignedPersonDTO totalHoursBooked(Double totalHoursBooked) {
    this.totalHoursBooked = totalHoursBooked;
    return this;
  }

   /**
   * Get totalHoursBooked
   * @return totalHoursBooked
  **/
  @Schema(description = "")
  public Double getTotalHoursBooked() {
    return totalHoursBooked;
  }

  public void setTotalHoursBooked(Double totalHoursBooked) {
    this.totalHoursBooked = totalHoursBooked;
  }

  public AssignedPersonDTO percentageOfTotalHours(Double percentageOfTotalHours) {
    this.percentageOfTotalHours = percentageOfTotalHours;
    return this;
  }

   /**
   * Get percentageOfTotalHours
   * @return percentageOfTotalHours
  **/
  @Schema(description = "")
  public Double getPercentageOfTotalHours() {
    return percentageOfTotalHours;
  }

  public void setPercentageOfTotalHours(Double percentageOfTotalHours) {
    this.percentageOfTotalHours = percentageOfTotalHours;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssignedPersonDTO assignedPersonDTO = (AssignedPersonDTO) o;
    return Objects.equals(this.personId, assignedPersonDTO.personId) &&
        Objects.equals(this.firstName, assignedPersonDTO.firstName) &&
        Objects.equals(this.lastName, assignedPersonDTO.lastName) &&
        Objects.equals(this.projectTimeSlots, assignedPersonDTO.projectTimeSlots) &&
        Objects.equals(this.totalHoursBooked, assignedPersonDTO.totalHoursBooked) &&
        Objects.equals(this.percentageOfTotalHours, assignedPersonDTO.percentageOfTotalHours);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personId, firstName, lastName, projectTimeSlots, totalHoursBooked, percentageOfTotalHours);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssignedPersonDTO {\n");
    
    sb.append("    personId: ").append(toIndentedString(personId)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    projectTimeSlots: ").append(toIndentedString(projectTimeSlots)).append("\n");
    sb.append("    totalHoursBooked: ").append(toIndentedString(totalHoursBooked)).append("\n");
    sb.append("    percentageOfTotalHours: ").append(toIndentedString(percentageOfTotalHours)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
