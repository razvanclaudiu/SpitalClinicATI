package com.example.ati.repository;

import java.sql.SQLException;

/**
 *  Custom Repository exception
 */
public class RepositoryException extends Exception{

    public RepositoryException(SQLException err){
        super(err);
    }
}
