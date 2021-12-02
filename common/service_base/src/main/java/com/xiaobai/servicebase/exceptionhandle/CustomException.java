package com.xiaobai.servicebase.exceptionhandle;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaobai
 * @create 2021-05-27 3:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "异常信息")
    private String msg;

    @Override
    public String toString() {
        return "CustomException{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }
}
