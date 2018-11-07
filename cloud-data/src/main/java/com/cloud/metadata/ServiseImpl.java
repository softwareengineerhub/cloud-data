/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.metadata;

/**
 *
 * @author kerch
 */
public class ServiseImpl implements Servise{

    @Override
    public String fileNameParser(String headerValue) {
        int startIndex = headerValue.indexOf("filename=\"");
        int endIndex = headerValue.indexOf(".pdf");
        return headerValue.substring(startIndex, endIndex);
    }
    
}
