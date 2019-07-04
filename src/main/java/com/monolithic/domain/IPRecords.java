package com.monolithic.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

@Entity
@Table(name = "ip_records")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IPRecords extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "device", nullable = false)
    private String device;

    @NotNull
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public IPRecords userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDevice() {
        return device;
    }

    public IPRecords device(String device) {
        this.device = device;
        return this;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public IPRecords ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCountryName() {
        return countryName;
    }

    public IPRecords countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public IPRecords cityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStatus() {
        return status;
    }

    public IPRecords status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IPRecords)) {
            return false;
        }
        return id != null && id.equals(((IPRecords) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IPRecords{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", device='" + getDevice() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", countryName='" + getCountryName() + "'" +
            ", cityName='" + getCityName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
