package com.abede.model;

import javax.ws.rs.FormParam;

public class FileMultipartRequest {
    @FormParam("file")
    public byte[] file;
}
