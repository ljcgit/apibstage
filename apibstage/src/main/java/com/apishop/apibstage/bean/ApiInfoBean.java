package com.apishop.apibstage.bean;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "api_info")
public class ApiInfoBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String url;

    @NotBlank
    private String protocol = "HTTP";

    @Column(columnDefinition = "INT default 0")
    private int method = 0;    // 0:GET   1:POST

    @Column(columnDefinition = "INT default 0")
    private int request_format = 0;  // 0:JSON

    @Column(columnDefinition = "INT default 0")
    private int response_format = 0;   // 0:JSON

    private String example_url;

    @Column(columnDefinition = "INT default 0")
    @Range(min = 0,max = 2)
    private int status = 0;   // 0:普通   1：通过审核的  2： 通过测试的


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="info_id",referencedColumnName = "id")
    private Set<ApiParameterBean> parameters;    //参数信息


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="info_id",referencedColumnName = "id")
    private Set<ApiCodeBean> codes;    //状态码

    public ApiInfoBean(){}

    public ApiInfoBean(@NotBlank String name, @NotBlank String url, @NotBlank String protocol, int method, int request_format, int response_format, String example_url, @Range(min = 0, max = 2) int status) {
        this.name = name;
        this.url = url;
        this.protocol = protocol;
        this.method = method;
        this.request_format = request_format;
        this.response_format = response_format;
        this.example_url = example_url;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ApiParameterBean> getParameters() {
        return parameters;
    }

    public void setParameters(Set<ApiParameterBean> parameters) {
        this.parameters = parameters;
    }

    public Set<ApiCodeBean> getCodes() {
        return codes;
    }

    public void setCodes(Set<ApiCodeBean> codes) {
        this.codes = codes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getRequest_format() {
        return request_format;
    }

    public void setRequest_format(int request_format) {
        this.request_format = request_format;
    }

    public int getResponse_format() {
        return response_format;
    }

    public void setResponse_format(int response_format) {
        this.response_format = response_format;
    }

    public String getExample_url() {
        return example_url;
    }

    public void setExample_url(String example_url) {
        this.example_url = example_url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ApiInfoBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", protocol='" + protocol + '\'' +
                ", method=" + method +
                ", request_format=" + request_format +
                ", response_format=" + response_format +
                ", example_url='" + example_url + '\'' +
                ", status=" + status +
                '}';
    }
}
