/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel.Managers;

import DataModel.Model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author x3041557
 */
public abstract class AbstractManager {
    //abstract as it does not define any useful methods
    ConnectionManager conn;
    Model model;
    Map<String,PreparedStatement> statementMap = new HashMap<>();

    public AbstractManager(Model model) {
        this.model = model;
        this.conn = model.getConnection();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Shared Database Methods">
    PreparedStatement prepareStatement(String sql, String key) 
            throws SQLException 
    {
        PreparedStatement stmt;
        if(!statementMap.containsKey(key)) {
            stmt = conn.getConnection().prepareStatement(sql);
            statementMap.put(key, stmt);
        } else {
            stmt = statementMap.get(key);
        }
        return stmt;
    }
    
    List<Object> createFromRecordset (
            Function<Map<String,Object>,Object> mapFunction,
            ResultSet rs
    ) throws SQLException {
        //this is a generic method to take a result set from a select query,
        //and map these to a specific object type using the sepcified Function
        //result is typed as object as we do not know what will get returned
        List<Object> list = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        while(rs.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            //make a map of column name to value
            //iterate through metadata of column names
            for(int i = 1; i <= colCount ; i++ ) {
                String name = rsmd.getColumnName(i);
                Object val = rs.getObject(name);
                rowMap.put(name, val);
            }
            //pass this map of colname / value to the mapping function
            Object obj = mapFunction.apply(rowMap);
            //add the result to the list
            list.add(obj);
        }
        return list;
    }
    
    List<Object> getList(
            String sql, 
            Object[] args, 
            String stmtKey,
            Function<Map<String,Object>,Object> mapFunc
    ) 
    {
        List<Object> result = new ArrayList<>();
        try {
            PreparedStatement localStmt = prepareStatement(sql, stmtKey);
            addArgs(localStmt, args);
            ResultSet rs = localStmt.executeQuery();
            result = createFromRecordset(mapFunc, rs);
        } catch (Exception e) {
            return null;
        } finally {
            return result;
        }
    }
    
    Object getSingle(
            String sql, 
            Object[] args, 
            String stmtKey,
            Function<Map<String,Object>,Object> mapFunc
    ) 
    {
        List<Object> all = getList(sql, args, stmtKey, mapFunc);
        try {
            if(all.size() > 1 || all.size() <= 0 ) {
                return null;
            } else {
                return all.get(0);
            }
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    int createRecord(String sql, Object[] args, String stmtKey ) {
        int result = -1;
        try {
            PreparedStatement localStmt = prepareStatement(sql, stmtKey);
            addArgs(localStmt, args);
            result = localStmt.executeUpdate();
        } catch (Exception e) {
            result = -1;
            //e.printStackTrace();
        } finally {
            return result;
        }
    }
    
    void addArgs(PreparedStatement stmt, Object[] args) throws SQLException {
        for(int i = 1; i <= args.length; i++) {
            stmt.setObject(i, args[i-1]);
        }
    }
    //</editor-fold>
}
