package com.apishop.apibstage.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = "api_parameter")
@JsonIgnoreProperties(value = {"apiInfoBean"})
public class ApiParameterBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String parameter_name;

    @Column(columnDefinition = "INT default 0")
    private int type = 0;     //0 : JSON   1 : long   2: TEXT

    @Column(columnDefinition = "INT default 0")
    private int required = 0;     //是否必填   0：否   1：是

    private String remark;

    @Column(columnDefinition = "INT default 0")
    private int kind = 0;    //0：请求参数   1：返回参数

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "info_id")
    private ApiInfoBean apiInfoBean;

    public ApiParameterBean(){}

    public ApiParameterBean(@NotBlank String parameter_name, int type, int required, String remark, int kind) {
        this.parameter_name = parameter_name;
        this.type = type;
        this.required = required;
        this.remark = remark;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public ApiInfoBean getApiInfoBean() {
        return apiInfoBean;
    }

    public void setApiInfoBean(ApiInfoBean apiInfoBean) {
        this.apiInfoBean = apiInfoBean;
    }

    @Override
    public String toString() {
        return "ApiParameterBean{" +
                "id=" + id +
                ", parameter_name='" + parameter_name + '\'' +
                ", type=" + type +
                ", required=" + required +
                ", remark='" + remark + '\'' +
                ", kind=" + kind +
                '}';
    }
}



//import java.util.*;
//public class Main{
//    public static void main(String[] args){
//        Scanner sc = new Scanner(System.in);
//        while(sc.hasNextInt()){
//            int n = sc.nextInt();
//            int[] array = new int[n];
//            for(int i=0;i<n;i++){
//                array[i]=sc.nextInt();
//            }
//            Arrays.sort(array);
//            int min = array[n-1]-array[0];
//            int minS = 0;
//            for(int i=0;i<n-1;i++){
//                int sub = array[i+1]-array[i];
//                if(sub<min){
//                    min = sub;
//                    minS=1;
//                }else if(min == sub){
//                    minS++;
//                }
//            }
//            int x=1;
//            for(int i =1;i<n-1;i++){
//                if(array[i]!=array[0]){
//                    break;
//                }
//                x++;
//            }
//            int y = 1;
//            for(int i=n-2;i>0;i--){
//                if(array[i]!=array[n-1]){
//                    break;
//                }
//                y++;
//            }
//
//            System.out.println(minS+" "+(x*y));
//        }
//    }
//}