package net.lunalabs.central.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CMRespDto<T> { //Front 서버에 보내줄 공통 DTO
    private int statusCode;
    private String msg;
    private T data;
}