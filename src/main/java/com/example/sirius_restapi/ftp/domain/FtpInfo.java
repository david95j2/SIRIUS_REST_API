package com.example.sirius_restapi.ftp.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FtpInfo {
    private String ftpIp;
    private String ftpPort;
    private String ftpId;
    private String ftpPassword;
}
