/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankproject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NotFoundException extends WebApplicationException {
    public NotFoundException() {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity("An error occured! The record you are looking for may not exist.").type(MediaType.TEXT_PLAIN).build());
    }
}
