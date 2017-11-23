package javafxapplication.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public final class DatabaseHandler
{
    private static DatabaseHandler handler = null;
    private static Connection conn = null;
    private static Statement stmt = null;
    
    //Constructer that calls all create table methods on object creation.
    public DatabaseHandler()
    {
        createConnection();
        setupBookTable();
        setupPublisherTable();
        setupStudentTable();
        setupAuthorTable();
        setupIssueTable();
    }

    public static DatabaseHandler getInstance()
    {
        if (handler == null)
        {
            handler = new DatabaseHandler();
        }
        return handler;
    }
    
    //Method to connect to mysql database server
    void createConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","root");
            System.out.println("Successfully connected");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    //Method to create table: PUBLISHER
    void setupPublisherTable()
    {
        String TABLE_NAME = "PUBLISHER";
        try
        {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next())
            {
                System.out.println("Table " + TABLE_NAME + "already exists. Proceed to next!");
            }
            else
            {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "	name varchar(200) primary key,\n"
                        + "	address varchar(200),\n"
                        + "	phone varchar(20)"
                        + " )");
            }
        } catch (SQLException e)
        {
            System.err.println(e.getMessage() + " --- setupDatabase PUBLISHER");
        }
    }

    //Method to create table: BOOK
    void setupBookTable()
    {
        String TABLE_NAME = "BOOK";
        try
        {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next())
            {
                System.out.println("Table " + TABLE_NAME + "already exists. Ready for go!");
            }
            else
            {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "	id varchar(200) primary key,\n"
                        + "	title varchar(200),\n"
                        + "	publisherName varchar(200),\n"
                        + "	copies integer,\n"
                        + "	isAvail boolean default true,"
                        + "     foreign key(publisherName) references publisher(name)"
                        + " )");
            }
        } catch (SQLException e)
        {
            System.err.println(e.getMessage() + " --- setupDatabase BOOK");
        } 
    }
    
    //Method to create table: STUDENT
    void setupStudentTable()
    {
        String TABLE_NAME = "STUDENT";
        try
        {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if(tables.next())
            {
                System.out.println("Table " + TABLE_NAME + " already exists!!!!");
            }
            else
            {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + " id varchar(200) primary key,\n"
                        + " name varchar(200),\n"
                        + " mobile varchar(20),"
                        + " email varchar(200),\n"
                        + " borrowed int default 0,\n"
                        + " fine int default 0" 
                        + ")");
            }
        } catch(SQLException e)
        {
            System.err.println(e.getMessage() + " --- setupDatabase STUDENT");
        } 
    }
    
    //Method to create table: AUTHOR
    void setupAuthorTable()
    {
        String TABLE_NAME = "AUTHOR";
        try
        {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next())
            {
                System.out.println("Table " + TABLE_NAME + "already exists. Ready for go!");
            }
            else
            {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "	name varchar(200) ,\n"
                        + "	id varchar(200),\n"
                        + "	category varchar(200),\n"
                        + "     PRIMARY KEY(name, id),\n"
                        + "     FOREIGN KEY(id) REFERENCES book(id)"
                        + " )");
            }
        } catch (SQLException e)
        {
            System.err.println(e.getMessage() + " --- setupDatabase AUTHOR");
        } 
    }
    
    //Method to create table: ISSUE
    void setupIssueTable()
    {
        String TABLE_NAME = "ISSUE";
        try
        {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if(tables.next())
            {
                System.out.println("Table " + TABLE_NAME + " already exists!!!!");
            }
            else
            {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "     bookID varchar(200) ,\n"
                        + "     studentID varchar(200) ,\n"
                        + "     issueDate date,\n"
                        + "     returnDate date,\n"
                        + "     PRIMARY KEY(bookID, studentID), \n"
                        + "	FOREIGN KEY (bookID) REFERENCES book(id),\n"
                        + "	FOREIGN KEY (studentID) REFERENCES student(id)"
                        + ")");
            }
        } catch(SQLException e)
        {
            System.err.println(e.getMessage() + " --- setupDatabase ISSUE");
        } 
    }
    
    //Method to call the procedure : fineCalculate
    public void fineCalculate(int days, String studentId)
    {
        try
        {
            CallableStatement state = conn.prepareCall("{call fine_calculate(?,?)}");
            state.setInt(1, days);
            state.setString("studentId", studentId);
            state.execute();
            System.out.println("Fine Procedure executed"); 
        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    
    public ResultSet execQuery(String query)
    {
        ResultSet result;
        try
        {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex)
        {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage() );
            return null;
        }
        return result;
    }
    
    public boolean execAction(String qu)
    {
        try
        {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execAction:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
    }
    
}
