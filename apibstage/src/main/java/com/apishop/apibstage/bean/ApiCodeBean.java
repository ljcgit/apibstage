package com.apishop.apibstage.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "api_code")
@JsonIgnoreProperties(value = {"apiInfoBean"})
public class ApiCodeBean implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String status_code;   //状态码

    @NotBlank
    private String description;   //描述信息

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "info_id")
    private ApiInfoBean apiInfoBean;

    public ApiCodeBean() {
    }

    public ApiCodeBean(@NotBlank String statusCode, @NotBlank String description) {
        this.status_code = statusCode;
        this.description = description;
    }

    public ApiInfoBean getApiInfoBean() {
        return apiInfoBean;
    }

    public void setApiInfoBean(ApiInfoBean apiInfoBean) {
        this.apiInfoBean = apiInfoBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ApiCodeBean{" +
                "id=" + id +
                ", statusCode='" + status_code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
