package com.its.sanve.api.communication.SanVe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.its.sanve.api.entities.Province;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Data {

    List<Province> province;

    public List<Province> getProvince() {
        return province;
    }

    public void setProvince( List<Province>  province) {
        this.province = province;
    }
}
