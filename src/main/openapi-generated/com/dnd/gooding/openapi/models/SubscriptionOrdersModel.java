package com.dnd.gooding.openapi.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * SubscriptionOrdersModel
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class SubscriptionOrdersModel implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private String subscriptionId;

  private String result;

  private String userId;

  private String orderId;

  private String paidAt;

  private String refundedAt;

  @Valid
  private Map<String, String> additionalProperties = new HashMap<>();

  public SubscriptionOrdersModel id(String id) {
    this.id = id;
    return this;
  }

  /**
   * orderId
   * @return id
  */
  
  @Schema(name = "id", example = "246523764", description = "orderId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public SubscriptionOrdersModel subscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
    return this;
  }

  /**
   * 정기 결제가 진행된 구독 ID 입니다
   * @return subscriptionId
  */
  
  @Schema(name = "subscriptionId", example = "ad4d8bed-1967-4e68-af97-290ca710c2f6_3647777233", description = "정기 결제가 진행된 구독 ID 입니다", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("subscriptionId")
  public String getSubscriptionId() {
    return subscriptionId;
  }

  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  public SubscriptionOrdersModel result(String result) {
    this.result = result;
    return this;
  }

  /**
   * 여부 실패시, additionalProperties 에 failedReason 포함됨
   * @return result
  */
  
  @Schema(name = "result", example = "SUCCESS | FAILED", description = "여부 실패시, additionalProperties 에 failedReason 포함됨", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("result")
  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public SubscriptionOrdersModel userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  */
  
  @Schema(name = "userId", example = "ad4d8bed-1967-4e68-af97-290ca710c2f6", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("userId")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public SubscriptionOrdersModel orderId(String orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * PG 에서 확인할 수 있는 고유 결제 ID
   * @return orderId
  */
  
  @Schema(name = "orderId", example = "order-2312355", description = "PG 에서 확인할 수 있는 고유 결제 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("orderId")
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public SubscriptionOrdersModel paidAt(String paidAt) {
    this.paidAt = paidAt;
    return this;
  }

  /**
   * 결제일을 확인할 수 있다.
   * @return paidAt
  */
  
  @Schema(name = "paidAt", example = "2022-08-11", description = "결제일을 확인할 수 있다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("paidAt")
  public String getPaidAt() {
    return paidAt;
  }

  public void setPaidAt(String paidAt) {
    this.paidAt = paidAt;
  }

  public SubscriptionOrdersModel refundedAt(String refundedAt) {
    this.refundedAt = refundedAt;
    return this;
  }

  /**
   * 결제일을 확인할 수 있다.
   * @return refundedAt
  */
  
  @Schema(name = "refundedAt", example = "2022-08-09", description = "결제일을 확인할 수 있다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("refundedAt")
  public String getRefundedAt() {
    return refundedAt;
  }

  public void setRefundedAt(String refundedAt) {
    this.refundedAt = refundedAt;
  }

  public SubscriptionOrdersModel additionalProperties(Map<String, String> additionalProperties) {
    this.additionalProperties = additionalProperties;
    return this;
  }

  public SubscriptionOrdersModel putAdditionalPropertiesItem(String key, String additionalPropertiesItem) {
    if (this.additionalProperties == null) {
      this.additionalProperties = new HashMap<>();
    }
    this.additionalProperties.put(key, additionalPropertiesItem);
    return this;
  }

  /**
   * 추가 되어질 수 있는 내역 담기
   * @return additionalProperties
  */
  
  @Schema(name = "additionalProperties", description = "추가 되어질 수 있는 내역 담기", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("additionalProperties")
  public Map<String, String> getAdditionalProperties() {
    return additionalProperties;
  }

  public void setAdditionalProperties(Map<String, String> additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubscriptionOrdersModel subscriptionOrdersModel = (SubscriptionOrdersModel) o;
    return Objects.equals(this.id, subscriptionOrdersModel.id) &&
        Objects.equals(this.subscriptionId, subscriptionOrdersModel.subscriptionId) &&
        Objects.equals(this.result, subscriptionOrdersModel.result) &&
        Objects.equals(this.userId, subscriptionOrdersModel.userId) &&
        Objects.equals(this.orderId, subscriptionOrdersModel.orderId) &&
        Objects.equals(this.paidAt, subscriptionOrdersModel.paidAt) &&
        Objects.equals(this.refundedAt, subscriptionOrdersModel.refundedAt) &&
        Objects.equals(this.additionalProperties, subscriptionOrdersModel.additionalProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, subscriptionId, result, userId, orderId, paidAt, refundedAt, additionalProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionOrdersModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    subscriptionId: ").append(toIndentedString(subscriptionId)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    paidAt: ").append(toIndentedString(paidAt)).append("\n");
    sb.append("    refundedAt: ").append(toIndentedString(refundedAt)).append("\n");
    sb.append("    additionalProperties: ").append(toIndentedString(additionalProperties)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

