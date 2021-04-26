/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PostgreSQLJDBC;

import java.sql.*;
import java.io.*;
import java.util.Scanner;
import java.util.*;
/**
 *
 * @author Alex
 */
public class App 
{

    public static ResultSet runQuery(String course, PreparedStatement pStatement) throws SQLException {
        pStatement.clearParameters();
        pStatement.setString(1, course);
        ResultSet r = pStatement.executeQuery();
        return r;
    }
    
    public static void DFS(ResultSet r, LinkedList<String> v, PreparedStatement pStatement) throws SQLException {
        while(r.next()) {  
            if (!v.contains(r.getString(1)))
            {
                v.add(r.getString(1));
                DFS(runQuery(r.getString(1), pStatement), v, pStatement);
            }
        }
        for (String prereq : v)
        {
            System.out.println(prereq);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, IOException
    {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql:Assignment1", "postgres", "student");
            Statement statement = connection.createStatement();
            )
        {
            String course_id;
            LinkedList<String> visited = new LinkedList<String>();
            String stmt1 = "select prereq_id from prereq where course_id = ?";
            PreparedStatement p = connection.prepareStatement(stmt1);
            System.out.println("Please enter a course id");
            Scanner sc = new Scanner(System.in);
            course_id = sc.nextLine();
            DFS(runQuery(course_id, p), visited, p);            
                            
        } catch (Exception sqle) {
        System.out.println("Exception: " + sqle);
        }
    }
    
}

