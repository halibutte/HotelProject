package DataModel.Managers;

import DataModel.BillableItem;
import DataModel.BilledItem;
import DataModel.Booking;
import DataModel.Model;
import DataModel.ModelException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author x3041557
 */
public class BillableManager extends AbstractManager {

    public BillableManager(Model model) {
        super(model);
    }
    public List<BilledItem> getBilledItems(int bref) {
        //get all the billed items for this booking reference
        String sql = "SELECT * FROM bookingitem WHERE b_ref = ?";
        Object[] args = {
            bref
        };
        return (List<BilledItem>)(List<?>)getList(
            sql, 
            args, 
            "getBilledItems", 
            r -> mapToBilledItem(r)
        );
    }
    
    public BilledItem getBilledItem(int id) {
        String sql = "SELET * FROM bookingitem WHERE id = ?";
        Object[] args = {
            id
        };
        return (BilledItem)getSingle(
            sql, 
            args, 
            "getBilledItemById",
            r -> mapToBilledItem(r)
        );
    }
    
    public BilledItem addBilledItem(
            int bref, 
            String code, 
            String desc, 
            double price) throws ModelException 
    {
        String sql = "INSERT INTO bookingitem (item_code, b_ref, item_desc, price) " + 
                "VALUES (?, ?, ?, ?)";
        Object[] args = {
            code,
            bref,
            desc,
            price
        };
        Object[] res =  createRecord(sql, args, "createBilledItem");
        Integer res1 = (Integer)res[0];
        if(res1 > 0) {
            return getBilledItem(res1);
        } else if (res1 == 0) {
            throw new ModelException("Unable to add item to bill, may violate constraints");
        } else {
            throw new ModelException("SQL Exception when creating customer");
        }
    }
    
    public boolean deleteBilledItem(int id) {
        String sql = "DELETE FROM bookingitem WHERE id = ?";
        Object[] args = {
            id
        };
        return updateRecord(sql, args, "deleteBilledItem");
    }
    
    public List<BillableItem> getAllBillableItems() {
        String sql = "SELECT * FROM billableitem";
        Object[] args = {  
        };
        return (List<BillableItem>)(List<?>)getList(sql, args, "getAllBillableItems", BillableManager::mapToBillableItem);
    }
    
    public BillableItem getBillableItemFromCode(String code) {
        String sql = "SELECT * FROM billableitem WHERE item_code = ?";
        Object[] args = {
            code
        };
        return (BillableItem)getSingle(sql, args, "getBillableItemCode", BillableManager::mapToBillableItem);
    }
    
    private BilledItem mapToBilledItem(Map<String, Object> map) {
        BillableItem bi = getBillableItemFromCode((String)map.get("item_code"));
        BilledItem build = new BilledItem();
        build.setItem(bi);
        build.setBref((Integer)map.get("b_ref"));
        build.setCode((String)map.get("item_code"));
        build.setId((Integer)map.get("id"));
        build.setDescription((String)map.get("item_desc"));
        build.setPrice(((BigDecimal)map.get("price")).doubleValue());
        return build;
    }
    
    private static BillableItem mapToBillableItem(Map<String, Object> map) {
        BillableItem bi = new BillableItem();
        bi.setCode((String)map.get("item_code"));
        bi.setName((String)map.get("item_name"));
        bi.setPrice(((BigDecimal)map.get("item_price")).doubleValue());
        return bi;
    }
    
    public static void main(String[] args) {
        try {
            Model m = new Model();
            Booking b = m.BOOKINGS.getBooking(13906);
            m.BILLABLES.addBilledItem(b.getRef(), "FOOD", "Pizza", 10.95);
            b = m.BOOKINGS.getBooking(13906);
            System.out.println(b.getBilledItems().size());
            
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }
}
